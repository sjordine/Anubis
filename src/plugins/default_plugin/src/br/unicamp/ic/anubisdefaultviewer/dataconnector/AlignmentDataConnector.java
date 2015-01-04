package br.unicamp.ic.anubisdefaultviewer.dataconnector;

import java.util.List;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.dataconnector.AlignmentDataBuilder;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.alignment.ViewBlock;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;

import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.ALIGNMENT_DATA_REPOSITORY_ID;

public class AlignmentDataConnector {



	public Object getData(int alignmentIndex, int startColumn, int startRow,
			int columns, int rows) {
		Character[][] returnValue = null;

		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();

		if (resourceManager != null && alignmentManager != null) {
			// Get repository
			IAlignmentDataRepository dataRepository = (IAlignmentDataRepository) resourceManager
					.getResource(ALIGNMENT_DATA_REPOSITORY_ID);
			if (dataRepository != null
					&& dataRepository.isLoaded(alignmentIndex)) {
				returnValue = AlignmentDataBuilder.<Character> getData(
						dataRepository, alignmentIndex, startColumn, startRow,
						columns, rows, Character.class);

			}
		}

		return returnValue;
	}

	public Object getSequences(int alignmentIndex, int startRow, int rows) {
		Object returnValue = null;

		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();

		if (resourceManager != null && alignmentManager != null) {
			// Get repository
			IAlignmentDataRepository dataRepository = (IAlignmentDataRepository) resourceManager
					.getResource(ALIGNMENT_DATA_REPOSITORY_ID);
			if (dataRepository != null
					&& dataRepository.isLoaded(alignmentIndex)) {

				int numberOfRows = alignmentManager
						.getNumberOfRows(alignmentIndex);
				int rowPos = 0;
				if (startRow >= 0 && startRow < numberOfRows) {
					int lastRow = Math.min(startRow + rows, numberOfRows - 1);
					List<ViewBlock> rowList = alignmentManager.getRows(
							alignmentIndex, startRow, lastRow);
					if (rowList != null && rowList.size() > 0) {
						String[] sequences = new String[lastRow - startRow + 1];
						for (int i = 0; i < rowList.size(); i++) {
							ViewBlock rowBlock = rowList.get(i);
							String[] sequenceSet = (String[])dataRepository.getSequences(alignmentIndex,
									rowBlock.getStart(), rowBlock.getEnd());
							System.arraycopy(sequenceSet, 0,
									sequences, rowPos,
									sequenceSet.length);
							rowPos += rowBlock.getSize();
						}
						returnValue = sequences;
					}
				}
			}
		}

		return returnValue;
	}
	
	
	public Object getColumns(int alignmentIndex, int startColumn, int columns){
		Object returnValue = null;
		
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();
		if (resourceManager != null && alignmentManager != null) {
			// Get repository
			IAlignmentDataRepository dataRepository = (IAlignmentDataRepository) resourceManager
					.getResource(ALIGNMENT_DATA_REPOSITORY_ID);
			if (dataRepository != null
					&& dataRepository.isLoaded(alignmentIndex)) {
				int numberOfColumns = alignmentManager
						.getNumberOfColumns(alignmentIndex);
				int colPos = 0;
				if (startColumn >= 0 && startColumn < numberOfColumns) {
					int lastColumn = Math.min(startColumn + columns, numberOfColumns - 1);
					List<ViewBlock> columnList = alignmentManager.getColumns(
							alignmentIndex, startColumn, lastColumn);
					if (columnList != null && columnList.size() > 0) {
						Integer[] columnArray = new Integer[lastColumn - startColumn + 1];
						for (int i = 0; i < columnList.size(); i++) {
							ViewBlock columnBlock = columnList.get(i);
							for (int j=0; j < columnBlock.getSize(); j++){
								columnArray[j+colPos]= columnBlock.getStart()+j;
							}
							colPos += columnBlock.getSize();
						}						
					
						returnValue = columnArray;
					}
				}
			}
		}
		
		return returnValue;
	}
	

}
