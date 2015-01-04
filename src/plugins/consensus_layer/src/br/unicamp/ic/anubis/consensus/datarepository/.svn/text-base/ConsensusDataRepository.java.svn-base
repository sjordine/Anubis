package br.unicamp.ic.anubis.consensus.datarepository;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.data.IDataRepository;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.jbio.matrix.Aminoacids;
import br.unicamp.ic.jbio.matrix.DistanceMatrix;
import br.unicamp.ic.jbio.matrix.MatrixUtil;

import static br.unicamp.ic.anubis.consensus.common.CommonConstants.ALIGNMENT_DATAREPOSITORY_INTERFACE_ID;
import static br.unicamp.ic.anubis.consensus.common.CommonConstants.CONSENSUS_DATAREPOSITORY_INTERFACE_ID;
import static br.unicamp.ic.anubis.consensus.common.CommonConstants.SCORE_LIST_ID;
import static br.unicamp.ic.anubis.consensus.common.CommonConstants.SELECTED_MATRIX_INDEX_PROPERTY;
import static br.unicamp.ic.anubis.consensus.common.CommonConstants.FIRST_COLUMN_PARAMETER;
import static br.unicamp.ic.anubis.consensus.common.CommonConstants.SIZE_PARAMETER;
import static br.unicamp.ic.anubis.consensus.common.CommonConstants.ALIGNMENT_ID_PARAMETER;

public class ConsensusDataRepository implements IDataRepository, IEventHandler {

	private Character[][] consensusSequence;
	private boolean[] isLoaded;

	public ConsensusDataRepository() {

		consensusSequence = new Character[2][];

		isLoaded = new boolean[2];
		isLoaded[AlignmentUtil.BENCHMARK_ALIGNMENT] = false;
		isLoaded[AlignmentUtil.TARGET_ALIGNMENT] = false;
	}

	@Override
	public void setData(HashMap<String, Object> parameters, Object data) {
		// This is a calculated information, therefore this
		// method will not be implemented
	}

	@Override
	public Object getData(HashMap<String, Object> parameters) {
		Character[] returnValue = null;

		// Check if parameters were passed correctly
		if (parameters.containsKey(FIRST_COLUMN_PARAMETER)
				&& parameters.containsKey(SIZE_PARAMETER)
				&& parameters.containsKey(ALIGNMENT_ID_PARAMETER)) {
			int firstColumn = (Integer) parameters.get(FIRST_COLUMN_PARAMETER);
			int columns = (Integer) parameters.get(SIZE_PARAMETER);
			int alignmentId = (Integer) parameters.get(ALIGNMENT_ID_PARAMETER);
			if (alignmentId >= AlignmentUtil.BENCHMARK_ALIGNMENT
					&& alignmentId <= AlignmentUtil.TARGET_ALIGNMENT) {

				// Check parameters
				if (isLoaded[alignmentId] && firstColumn >= 0
						&& firstColumn < consensusSequence[alignmentId].length) {

					columns = Math
							.min(columns, consensusSequence[alignmentId].length
									- firstColumn);

					returnValue = new Character[columns];
					System.arraycopy(consensusSequence[alignmentId],
							firstColumn, returnValue, 0, columns);
				}
			}
		}

		return returnValue;
	}

	@Override
	public void eventRaised(IEvent message) {
		if (message != null) {

			if (message instanceof AlignmentDataLoadedEvent) {

				AlignmentDataLoadedEvent event = (AlignmentDataLoadedEvent) message;
				UUID sourceId = event.getSource();

				if (ALIGNMENT_DATAREPOSITORY_INTERFACE_ID.equals(sourceId)) {

					int affectedAlignments = event.getAlignmentIndex();
					if (affectedAlignments == AlignmentUtil.BENCHMARK_ALIGNMENT
							|| affectedAlignments == AlignmentUtil.TARGET_ALIGNMENT) {
						processConsensus(affectedAlignments);
					} else {
						if (affectedAlignments == AlignmentUtil.BOTH_ALIGNMENTS) {
							processConsensus(AlignmentUtil.BENCHMARK_ALIGNMENT);
							processConsensus(AlignmentUtil.TARGET_ALIGNMENT);

						}
					}
				}
			}

			if (message instanceof PropertyChangedEvent) {
				PropertyChangedEvent event = (PropertyChangedEvent) message;

				if (SELECTED_MATRIX_INDEX_PROPERTY.equals(event
						.getPropertyName())) {

					processConsensus(AlignmentUtil.BENCHMARK_ALIGNMENT);
					processConsensus(AlignmentUtil.TARGET_ALIGNMENT);
				}
			}

		}
	}

	private void processConsensus(int alignmentId) {

		isLoaded[alignmentId] = false;

		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IAlignmentDataRepository alignmentRepository = (IAlignmentDataRepository) resourceManager
				.getResource(ALIGNMENT_DATAREPOSITORY_INTERFACE_ID);
		ISettingsManager settingsManager = manager.getSettingsManager();

		if (alignmentRepository != null && settingsManager != null) {
			// Get base alignment information
			if (alignmentRepository.isLoaded(alignmentId)) {
				int numberOfSequences = alignmentRepository
						.numberOfSequences(alignmentId);
				int length = alignmentRepository.length(alignmentId);
				Character[][] baseAlignment = (Character[][]) alignmentRepository
						.getData(alignmentId, 0, 0, length - 1,
								numberOfSequences - 1);
				// Get matrix
				DistanceMatrix matrix = getDistanceMatrix(resourceManager,
						settingsManager);

				if (baseAlignment != null && matrix != null) {
					consensusSequence[alignmentId] = new Character[length];
					for (int i = 0; i < length; i++) {
						Aminoacids consensusResidue = Aminoacids.GAP;
						int maxValue = Integer.MIN_VALUE;
						// Handle exception case. All gaps
						int numberOfGaps = 0;
						for (int k = 0; k < numberOfSequences; k++) {
							Character currentResidue = baseAlignment[k][i];
							if (AlignmentUtil.isGap(currentResidue)) {
								numberOfGaps++;
							}
						}
						if (numberOfGaps != numberOfSequences) {
							for (int j = 0; j <= Aminoacids.B.getIndex(); j++) {
								int currentValue = 0;

								for (int k = 0; k < numberOfSequences; k++) {
									Character currentResidue = baseAlignment[k][i];
									if (!AlignmentUtil.isGap(currentResidue)) {
										currentValue += MatrixUtil.getScore(
												matrix, j, currentResidue);
									}
								}

								if (currentValue > maxValue) {
									maxValue = currentValue;
									consensusResidue = Aminoacids.values()[j];
								}
							}
						} else {
							// Handle exception case. All gaps
							consensusResidue = Aminoacids.GAP;
						}
						consensusSequence[alignmentId][i] = (consensusResidue == Aminoacids.GAP) ? '-'
								: consensusResidue.toString().charAt(0);
					}
					isLoaded[alignmentId] = true;

				}

				notifyDataChanged(manager, alignmentId);
			}
		}
	}

	private void notifyDataChanged(AnubisManager manager, int alignmentId) {

		// Notify loaded alignment, if successful
		IEventManager eventManager = manager.getEventManager();
		if (eventManager != null) {
			IEvent event = new AlignmentDataLoadedEvent(
					CONSENSUS_DATAREPOSITORY_INTERFACE_ID, alignmentId);
			eventManager.raise(event);
		}
	}

	public DistanceMatrix getDistanceMatrix(IResourceManager resourceManager,
			ISettingsManager settingsManager) {
		DistanceMatrix matrix = null;
		List<DistanceMatrix> matrices = (List<DistanceMatrix>) resourceManager
				.getResource(SCORE_LIST_ID);
		Integer matrixIndex = (Integer) settingsManager
				.getProperty(SELECTED_MATRIX_INDEX_PROPERTY);

		if (matrices != null && matrixIndex != null
				&& matrices.size() > matrixIndex) {
			matrix = matrices.get(matrixIndex);
		}
		return matrix;
	}

}
