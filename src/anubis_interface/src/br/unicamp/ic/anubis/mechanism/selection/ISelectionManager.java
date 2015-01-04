package br.unicamp.ic.anubis.mechanism.selection;

import java.util.List;

public interface ISelectionManager {
	
	public void addSelection(int alignmentId, int startColumn, int startRow, int endColumn, int endRow);	
	public void addSelection(int alignmentId, SelectionArea area);
	
	public List<SelectionArea> getSelection(int alignmentId);
	public void clearSelection(int alignmentId);
	public void clearAllSelection();
		

}
