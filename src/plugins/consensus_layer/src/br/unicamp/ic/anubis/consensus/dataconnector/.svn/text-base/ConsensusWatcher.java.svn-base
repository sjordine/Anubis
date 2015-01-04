package br.unicamp.ic.anubis.consensus.dataconnector;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;

import static br.unicamp.ic.anubis.consensus.common.CommonConstants.CONSENSUS_DATAREPOSITORY_INTERFACE_ID;

public class ConsensusWatcher implements IEventHandler {

	@Override
	public void eventRaised(IEvent message) {
		if (message instanceof AlignmentDataLoadedEvent) {
			AlignmentDataLoadedEvent alignmentLoadedMessage = (AlignmentDataLoadedEvent) message;

			if (CONSENSUS_DATAREPOSITORY_INTERFACE_ID.equals(alignmentLoadedMessage
					.getSource())) {

				AnubisManager manager = AnubisManager.getInstance();
				IEventManager eventManager = manager.getEventManager();

				IResourceManager resourceManager = manager.getResourceManager();

				if (eventManager != null && resourceManager != null) {

					int alignmentIndex = alignmentLoadedMessage
							.getAlignmentIndex();

					RedrawAlignmentEvent redrawEvent = new RedrawAlignmentEvent(
							alignmentIndex);
					eventManager.raise(redrawEvent);
				}

			}
		}
	}

}
