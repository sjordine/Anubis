package br.unicamp.ic.anubis.successmetrics.sequence.dataconnector;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.SEQUENCE_SUCCESS_REPOSITORY_ID;
import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.dataconnector.AlignmentDataBuilder;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;

public class SequenceSuccessDataConnector {

	public Object getData(int alignmentIndex, int startColumn, int startRow,
			int columns, int rows) {
		Object returnValue = null;

		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();

		if (resourceManager != null) {
			// Get repository
			IAlignmentDataRepository dataRepository = (IAlignmentDataRepository) resourceManager
					.getResource(SEQUENCE_SUCCESS_REPOSITORY_ID);
			if (dataRepository != null
					&& dataRepository.isLoaded(alignmentIndex)) {
				returnValue = AlignmentDataBuilder.<Integer> getData(
						dataRepository, alignmentIndex, startColumn, startRow,
						columns, rows, Integer.class);

			}
		}

		return returnValue;
	}

	public Object getSequences(int alignmentIndex, int startRow, int rows) {
		Object returnValue = null;

		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();

		if (resourceManager != null) {
			// Get repository
			IAlignmentDataRepository dataRepository = (IAlignmentDataRepository) resourceManager
					.getResource(SEQUENCE_SUCCESS_REPOSITORY_ID);
			if (dataRepository != null
					&& dataRepository.isLoaded(alignmentIndex)) {
				int numberOfRows = dataRepository
						.numberOfSequences(alignmentIndex);

				if (startRow >= 0 && startRow < numberOfRows) {
					int lastRow = Math.min(startRow + rows, numberOfRows - 1);

					returnValue = dataRepository.getSequences(alignmentIndex,
							startRow, lastRow);
				}
			}
		}

		return returnValue;
	}
}
