package br.unicamp.ic.selectionlayer.selectionconnector;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.alignment.ViewBlock;
import br.unicamp.ic.anubis.mechanism.selection.ISelectionManager;
import br.unicamp.ic.anubis.mechanism.selection.SelectionArea;

public class SelectionDataConnector {

	public Object getData(int alignmentIndex, int startColumn, int startRow,
			int columns, int rows) {
		Object returnValue = null;

		AnubisManager manager = AnubisManager.getInstance();
		ISelectionManager selectionManager = manager.getSelectionManager();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();

		if (selectionManager != null && alignmentManager != null) {

			List<SelectionArea> visibleAreas = null;

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

				if (rowList != null && columnList != null && rowList.size() > 0
						&& columnList.size() > 0) {
					// Create borders
					List<SelectionArea> borderList = new ArrayList<SelectionArea>();
					for (int i = 0; i < rowList.size(); i++) {
						ViewBlock rowBlock = rowList.get(i);
						for (int j = 0; j < columnList.size(); j++) {
							ViewBlock columnBlock = columnList.get(j);
							SelectionArea border = new SelectionArea(
									columnBlock.getStart(),
									rowBlock.getStart(), columnBlock.getEnd(),
									rowBlock.getEnd());
							borderList.add(border);
						}
					}

					
					List<SelectionArea> selectionAreas = selectionManager
							.getSelection(alignmentIndex);

					if (borderList != null && borderList.size() > 0
							&& selectionAreas != null
							&& selectionAreas.size() > 0) {
						visibleAreas = new ArrayList<SelectionArea>();
						for (SelectionArea area : selectionAreas) {
							for (SelectionArea border : borderList) {
								if (area.intersects(border)) {
									// Create intersect area
									SelectionArea intersection = area
											.getIntersection(border);
									// Adjust to viewport
									int x1 = intersection.getStartColumn();
									int x2 = intersection.getEndColumn();
									int y1 = intersection.getStartRow();
									int y2 = intersection.getEndRow();
									
									x1 = alignmentManager.getViewColumnFromAlignment(alignmentIndex, x1);
									x2 = alignmentManager.getViewColumnFromAlignment(alignmentIndex, x2);
									y1 = alignmentManager.getViewRowFromAlignment(alignmentIndex, y1);
									y2 = alignmentManager.getViewRowFromAlignment(alignmentIndex, y2);
									
									if (x1 !=-1 && x2!=-1 && y1!=-1 && y2!=-1){
										
										x1 -= startColumn;
										x2 -= startColumn;
										y1 -= startRow;
										y2 -= startRow;
									
									intersection = new SelectionArea(x1, y1,
											x2, y2);
									visibleAreas.add(intersection);
									}
								}
							}
						}
						
						
//						SelectionArea bdr = new SelectionArea(startColumn,
//								startRow, startColumn + columns, startRow + rows);
//						visibleAreas = new ArrayList<SelectionArea>();
//						for (SelectionArea area : selectionAreas) {
//							if (area.intersects(bdr)) {
//								// Create intersect area
//								SelectionArea intersection = area
//										.getIntersection(bdr);
//								// Adjust to viewport
//								int x1 = intersection.getStartColumn();
//								int x2 = intersection.getEndColumn();
//								int y1 = intersection.getStartRow();
//								int y2 = intersection.getEndRow();
//								intersection = new SelectionArea(x1
//										- startColumn, y1 - startRow, x2
//										- startColumn, y2 - startRow);
//								// Add to visible areas;
//								visibleAreas.add(intersection);
//							}
//						}
					}

					if (visibleAreas != null && visibleAreas.size() > 0) {
						returnValue = visibleAreas;
					}
				}
			}

		}

		return returnValue;
	}

}
