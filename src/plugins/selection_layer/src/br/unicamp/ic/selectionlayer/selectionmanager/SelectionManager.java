package br.unicamp.ic.selectionlayer.selectionmanager;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.anubis.mechanism.selection.ISelectionManager;
import br.unicamp.ic.anubis.mechanism.selection.SelectionArea;

public class SelectionManager implements ISelectionManager {

	private List<List<SelectionArea>> selectionList;

	public SelectionManager() {
		selectionList = new ArrayList<List<SelectionArea>>();
		selectionList.add(new ArrayList<SelectionArea>());
		selectionList.add(new ArrayList<SelectionArea>());
	}

	public void addSelection(int alignmentId, int startColumn, int startRow,
			int endColumn, int endRow) {
		SelectionArea area = new SelectionArea(startColumn, startRow,
				endColumn, endRow);
		addSelection(alignmentId, area);
	}

	public void addSelection(int alignmentId, SelectionArea area) {
		boolean intersects = false;

		List<SelectionArea> alignmentSelection = selectionList.get(alignmentId);

		for (int i = 0; i < alignmentSelection.size() && !intersects; i++) {
			SelectionArea currentArea = alignmentSelection.get(i);
			intersects = intersects || currentArea.intersects(area);
		}

		if (!intersects) {
			alignmentSelection.add(area);
		}

	}

	@Override
	public List<SelectionArea> getSelection(int alignmentId) {
		return selectionList.get(alignmentId);
	}

	@Override
	public void clearSelection(int alignmentId) {
		List<SelectionArea> alignmentSelection = selectionList.get(alignmentId);
		alignmentSelection.clear();
	}

	@Override
	public void clearAllSelection() {
		selectionList.get(0).clear();
		selectionList.get(1).clear();
	}

}
