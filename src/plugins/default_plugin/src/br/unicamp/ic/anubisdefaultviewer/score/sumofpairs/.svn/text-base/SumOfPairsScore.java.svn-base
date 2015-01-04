package br.unicamp.ic.anubisdefaultviewer.score.sumofpairs;

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

import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.ALIGNMENT_DATA_REPOSITORY_ID;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SCORE_LIST_ID;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SUM_OF_PAIRS_SETTINGS_DIALOG_ID;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SELECTED_MATRIX_INDEX_PROPERTY;
import static br.unicamp.ic.anubisdefaultviewer.score.sumofpairs.SumOfPairsContants.GAP_PENALTY_PROPERTY;
import static br.unicamp.ic.anubisdefaultviewer.score.sumofpairs.SumOfPairsContants.GAP_GAP_PENALTY_PROPERTY;

public class SumOfPairsScore implements IScoreMethod,IEventHandler {
	
	private static final int GAP_DEFAULT_VALUE = -5;
	private static final int GAP_GAP_DEFAULT_VALUE = 0;



	@Override
	public double getScore(int alignmentId) {		
	
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();
		ISettingsManager settingsManager = manager.getSettingsManager();
		
		double score = 0;

		if (resourceManager != null && alignmentManager != null && settingsManager!=null) {
			
			Integer gapResidueValue = (Integer)settingsManager.getProperty(GAP_PENALTY_PROPERTY);
			Integer gapGapValue = (Integer)settingsManager.getProperty(GAP_GAP_PENALTY_PROPERTY);
			
			int gapGapPenalty = (gapGapValue == null)?GAP_GAP_DEFAULT_VALUE:gapGapValue;
			int gapResiduePenalty = (gapResidueValue == null)? GAP_DEFAULT_VALUE:gapResidueValue;
			
			
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
								if (AlignmentUtil.isGap(residue1)){
									if (AlignmentUtil.isGap(residue2)){
										score+=gapGapPenalty;
									} else {
										score+=gapResiduePenalty;
									}
								} else {
									if (AlignmentUtil.isGap(residue2)){
										score+=gapResiduePenalty;
									} else {
										score+=MatrixUtil.getScore(matrix, residue1, residue2);
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

	@Override
	public String getName() {
		return "Sum of Pairs";
	}

	@Override
	public UUID getSettingsId() {
		return SUM_OF_PAIRS_SETTINGS_DIALOG_ID;
	}

	@Override
	public void eventRaised(IEvent message) {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();
		
		if (message instanceof PropertyChangedEvent){
			PropertyChangedEvent event = (PropertyChangedEvent)message;
			String propertyName = event.getPropertyName();
			if (GAP_PENALTY_PROPERTY.equals(propertyName) || GAP_GAP_PENALTY_PROPERTY.equals(propertyName)){
				//Recalculate score
				AlignmentScoreChangeEvent scoreChanged = new AlignmentScoreChangeEvent(AlignmentUtil.BOTH_ALIGNMENTS);
				if (eventManager!=null){
					eventManager.raise(scoreChanged);
				}
			}
		}
	}

}
