package br.unicamp.ic.anubis.mechanism.selection;

public class SelectionArea {

	private int startColumn, endColumn, startRow, endRow;

	public SelectionArea(int startColumn, int startRow, int endColumn,
			int endRow) {
		this.startColumn = startColumn;
		this.startRow = startRow;
		this.endColumn = endColumn;
		this.endRow = endRow;
		swapPositions();
	}

	public int getStartColumn() {
		return startColumn;
	}

	public int getEndColumn() {
		return endColumn;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	private void swapPositions() {

		int tmp;

		if (endRow < startRow) {
			tmp = endRow;
			endRow = startRow;
			startRow = tmp;
		}

		if (endColumn < startColumn) {

			tmp = endColumn;
			endColumn = startColumn;
			startColumn = tmp;
		}
	}

	public boolean intersects(SelectionArea area) {
		boolean result = false;

		result = (area != null)
				&& (!(area.startColumn > this.endColumn
						|| area.endColumn < this.startColumn
						|| area.startRow > this.endRow || area.endRow < this.startRow));

		return result;
	}

	public SelectionArea getIntersection(SelectionArea area) {
		SelectionArea returnValue = null;

		if (intersects(area)) {
			int startColumn = Math.max(this.startColumn, area.startColumn);
			int endColumn = Math.min(this.endColumn, area.endColumn);

			int startRow = Math.max(this.startRow, area.startRow);
			int endRow = Math.min(this.endRow, area.endRow);

			returnValue = new SelectionArea(startColumn, startRow, endColumn,
					endRow);
		}

		return returnValue;
	}
	
	@Override
	public String toString(){
		String returnValue = "("+startColumn+","+startRow+"-"+endColumn+","+endRow+")";
		return returnValue;
	}

}
