package br.unicamp.ic.anubisdefaultviewer.dataconnector;

import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;

public class AlignmentWatcher implements IEventHandler{
	
	private static final UUID ALIGNMENT_DATA_REPOSITORY_ID = UUID
			.fromString("a663f847-7aa8-4714-9d91-810ac40a7fbd");

	@Override
	public void eventRaised(IEvent message) {
		if (message instanceof AlignmentDataLoadedEvent) {
			AlignmentDataLoadedEvent alignmentLoadedMessage = (AlignmentDataLoadedEvent) message;

			if (ALIGNMENT_DATA_REPOSITORY_ID.equals(alignmentLoadedMessage
					.getSource())) {

				AnubisManager manager = AnubisManager.getInstance();
				IEventManager eventManager = manager.getEventManager();

				IResourceManager resourceManager = manager.getResourceManager();

				if (eventManager != null && resourceManager != null) {

					int alignmentIndex = alignmentLoadedMessage
							.getAlignmentIndex();

					IAlignmentDataRepository alignmentRepository = (IAlignmentDataRepository) resourceManager
							.getResource(ALIGNMENT_DATA_REPOSITORY_ID);
					
					if (alignmentIndex == AlignmentUtil.BOTH_ALIGNMENTS) {
						setupAlignment(manager, AlignmentUtil.BENCHMARK_ALIGNMENT,
								alignmentRepository);
						setupAlignment(manager, AlignmentUtil.TARGET_ALIGNMENT,
								alignmentRepository);
					}

					if (alignmentIndex == AlignmentUtil.BENCHMARK_ALIGNMENT
							|| alignmentIndex == AlignmentUtil.TARGET_ALIGNMENT) {

						setupAlignment(manager, alignmentIndex,
								alignmentRepository);
					}

					RedrawAlignmentEvent redrawEvent = new RedrawAlignmentEvent(
							alignmentIndex);
					eventManager.raise(redrawEvent);
				}

			}
		}
	}
	
	private void setupAlignment(AnubisManager manager, int alignmentIndex,
			IAlignmentDataRepository alignmentRepository) {
		IAlignmentManager alignmentManager = manager
				.getAlignmentManager();

		if (alignmentManager != null) {

			int numberOfRows = 0;
			int numberOfColumns = 0;
			String[] sequences = null;
			int maxSequenceSize = 0;

			if (alignmentRepository != null) {
				numberOfRows = alignmentRepository
						.numberOfSequences(alignmentIndex);
				numberOfColumns = alignmentRepository
						.length(alignmentIndex);

				sequences = (String[]) alignmentRepository
						.getSequences(alignmentIndex, 0,
								numberOfRows - 1);
				

			}

			alignmentManager.setFullNumberOfColumns(
					alignmentIndex, numberOfColumns);
			alignmentManager.setFullNumberOfRows(
					alignmentIndex, numberOfRows);

			if (sequences != null) {
				for (int i = 0; i < sequences.length; i++) {
					maxSequenceSize = Math.max(maxSequenceSize,
							sequences[i].length());
				}
			}

			alignmentManager
					.setMaxSequenceLength(alignmentIndex, maxSequenceSize);
		}
	}
}
