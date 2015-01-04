package br.unicamp.ic.anubisdefaultviewer.score.gapaffinitysumofpairs;

import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.ALIGNMENT_DATA_REPOSITORY_ID;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SCORE_LIST_ID;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SELECTED_MATRIX_INDEX_PROPERTY;
import static br.unicamp.ic.anubisdefaultviewer.score.gapaffinitysumofpairs.GapAffinitySumOfPairsConstants.GAP_GAP_PENALTY_PROPERTY;
import static br.unicamp.ic.anubisdefaultviewer.score.gapaffinitysumofpairs.GapAffinitySumOfPairsConstants.OPEN_GAP_PENALTY;
import static br.unicamp.ic.anubisdefaultviewer.score.gapaffinitysumofpairs.GapAffinitySumOfPairsConstants.EXTEND_GAP_PENALTY;
import static br.unicamp.ic.anubisdefaultviewer.score.gapaffinitysumofpairs.GapAffinitySumOfPairsConstants.GAP_AFFINITY_SUM_OF_PAIRS_SETTINGS_DIALOG_ID;

import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.event.AlignmentScoreChangeEvent;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.score.IScoreMethod;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.jbio.matrix.DistanceMatrix;
import br.unicamp.ic.jbio.matrix.MatrixUtil;

public class GapAffinitySumOfPairsScore implements IScoreMethod, IEventHandler {

	private static final int OPEN_GAP_DEFAULT_VALUE = -5;
	private static final int EXTEND_GAP_DEFAULT_VALUE = -5;
	private static final int GAP_GAP_DEFAULT_VALUE = 0;

	@Override
	public double getScore(int alignmentId) {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();
		ISettingsManager settingsManager = manager.getSettingsManager();

		double score = 0;

		if (resourceManager != null && alignmentManager != null
				&& settingsManager != null) {

			Integer openGapValue = (Integer) settingsManager
					.getProperty(OPEN_GAP_PENALTY);
			Integer extendGapValue = (Integer) settingsManager
					.getProperty(EXTEND_GAP_PENALTY);
			Integer gapGapValue = (Integer) settingsManager
					.getProperty(GAP_GAP_PENALTY_PROPERTY);

			int gapGapPenalty = (gapGapValue == null) ? GAP_GAP_DEFAULT_VALUE
					: gapGapValue;
			int openGapPenalty = (openGapValue == null) ? OPEN_GAP_DEFAULT_VALUE
					: openGapValue;
			int extendGapPenalty = (extendGapValue == null) ? EXTEND_GAP_DEFAULT_VALUE
					: extendGapValue;

			IAlignmentDataRepository dataRepository = (IAlignmentDataRepository) resourceManager
					.getResource(ALIGNMENT_DATA_REPOSITORY_ID);
			if (dataRepository != null && dataRepository.isLoaded(alignmentId)) {
				// Get selected matrix
				DistanceMatrix matrix = getDistanceMatrix(resourceManager,
						settingsManager);

				if (matrix != null) {
					int numberOfRows = alignmentManager
							.getNumberOfRows(alignmentId);
					int numberOfColumns = alignmentManager
							.getNumberOfColumns(alignmentId);
					Character[][] alignment = (Character[][]) dataRepository
							.getData(alignmentId, 0, 0, numberOfColumns - 1,
									numberOfRows - 1);
					for (int i = 0; i < numberOfColumns; i++) {
						for (int j = 0; j < numberOfRows - 1; j++) {
							for (int k = j; k < numberOfRows; k++) {
								char residue1 = alignment[j][i];
								char residue2 = alignment[k][i];
								if (AlignmentUtil.isGap(residue1)) {
									if (AlignmentUtil.isGap(residue2)) {
										score += gapGapPenalty;
									} else {
										if (i > 0
												&& AlignmentUtil
														.isGap(alignment[j][i - 1])) {
											// Gap extension
											score += extendGapPenalty;
										} else {
											// Gap opening
											score += openGapPenalty
													+ extendGapPenalty;
										}
									}
								} else {
									if (AlignmentUtil.isGap(residue2)) {
										if (i > 0
												&& AlignmentUtil
														.isGap(alignment[k][i - 1])) {
											// Gap extension
											score += extendGapPenalty;
										} else {
											// Gap opening
											score += openGapPenalty
													+ extendGapPenalty;
										}
									} else {
										score += MatrixUtil.getScore(matrix,
												residue1, residue2);
									}
								}
							}
						}
					}
				}
			}
		}

		return score;
	}

	@Override
	public String getName() {
		return "Sum of Pairs with Gap Affinity";
	}

	@Override
	public UUID getSettingsId() {
		return GAP_AFFINITY_SUM_OF_PAIRS_SETTINGS_DIALOG_ID;
	}

	@Override
	public void eventRaised(IEvent message) {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();

		if (message instanceof PropertyChangedEvent) {
			PropertyChangedEvent event = (PropertyChangedEvent) message;
			String propertyName = event.getPropertyName();
			if (SELECTED_MATRIX_INDEX_PROPERTY.equals(propertyName)
					|| OPEN_GAP_PENALTY.equals(propertyName)
					|| EXTEND_GAP_PENALTY.equals(propertyName)
					|| GAP_GAP_PENALTY_PROPERTY.equals(propertyName)) {
				// Recalculate score
				AlignmentScoreChangeEvent scoreChanged = new AlignmentScoreChangeEvent(
						AlignmentUtil.BOTH_ALIGNMENTS);
				if (eventManager != null) {
					eventManager.raise(scoreChanged);
				}
			}
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
