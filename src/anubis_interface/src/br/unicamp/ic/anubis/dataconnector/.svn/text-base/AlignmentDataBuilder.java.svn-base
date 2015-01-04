package br.unicamp.ic.anubis.dataconnector;

import java.lang.reflect.Array;
import java.util.List;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.alignment.ViewBlock;

public class AlignmentDataBuilder {

	public static <T> T[][] getData(IAlignmentDataRepository dataRepository,
			int alignmentIndex, int startColumn, int startRow, int columns,
			int rows, Class elementType) {
		AnubisManager manager = AnubisManager.getInstance();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();

		T[][] returnValue = null;

		if (dataRepository != null && dataRepository.isLoaded(alignmentIndex)
				&& alignmentManager != null) {

			int numberOfRows = alignmentManager.getNumberOfRows(alignmentIndex);
			int numberOfColumns = alignmentManager
					.getNumberOfColumns(alignmentIndex);

			if (startColumn >= 0 && startRow >= 0
					&& startColumn < numberOfColumns && startRow < numberOfRows) {

				int lastColumn = Math.min(startColumn + columns,
						numberOfColumns - 1);
				int lastRow = Math.min(startRow + rows, numberOfRows - 1);

				List<ViewBlock> rowList = alignmentManager.getRows(
						alignmentIndex, startRow, lastRow);
				List<ViewBlock> columnList = alignmentManager.getColumns(
						alignmentIndex, startColumn, lastColumn);

				int rowPos = 0;

				if (rowList != null && columnList != null && rowList.size() > 0
						&& columnList.size() > 0) {
					// Workaround to create a generic type array on java
					// @SuppressWarnings("unchecked") T[][] array = (T[][])new
					// Object[lastRow - startRow + 1][lastColumn- startColumn +
					// 1];
					T[][] array = (T[][]) Array.newInstance(elementType,
							lastRow - startRow + 1, lastColumn - startColumn
									+ 1);
					returnValue = array;
					// Get and map blocks
					for (int i = 0; i < rowList.size(); i++) {
						ViewBlock rowBlock = rowList.get(i);
						int colPos = 0;
						for (int j = 0; j < columnList.size(); j++) {
							ViewBlock columnBlock = columnList.get(j);
							T[][] block = (T[][]) dataRepository.getData(
									alignmentIndex, columnBlock.getStart(),
									rowBlock.getStart(), columnBlock.getEnd(),
									rowBlock.getEnd());
							if (block != null) {
								for (int k = 0; k < block.length; k++) {
									System.arraycopy(block[k], 0,
											returnValue[rowPos + k], colPos,
											block[k].length);
								}
							} else {
								returnValue = null;
								break;
							}
							colPos += columnBlock.getSize();
						}
						rowPos += rowBlock.getSize();
					}
				}

			}

		}
		return returnValue;
	}

}
