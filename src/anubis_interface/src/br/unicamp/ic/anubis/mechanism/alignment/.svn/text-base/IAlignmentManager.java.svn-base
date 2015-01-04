package br.unicamp.ic.anubis.mechanism.alignment;

import java.util.List;

import br.unicamp.ic.anubis.mechanism.score.IScoreMethod;

public interface IAlignmentManager {
	
	public int getCurrentFirstColumn(int alignment);
	public int getCurrentFirstRow(int alignment);
	public void setCurrentFirstColumn(int alignment,int position);
	public void setCurrentFirstRow(int alignment,int position);
	
	public int getFullNumberOfColumns(int alignment);
	public int getFullNumberOfRows(int alignment);
	public void setFullNumberOfColumns(int alignment,int size);
	public void setFullNumberOfRows(int alignment,int size);
	
	public int getNumberOfColumns(int alignment);
	public int getNumberOfRows(int alignment);
	
	public int getSequenceDisplayLength(int alignment);
	public void setMaxSequenceDisplayLength(int length);
	public void setMaxSequenceLength(int alignment,int length);
	
	//Hide columns and rows methods
	public void hideColumn(int alignment, int viewColumn);
	public void hideRow(int alignment, int viewRow);
	public void unhideAll();
	public void moveRow(int alignment, int currentPosition, int finalPosition);
	
	//View and Alignment position 
	public List<ViewBlock> getColumns(int alignment, int startColumn, int endColumn);
	public List<ViewBlock> getRows(int alignment, int startRow, int endRow);
	public int getAlignmentColumnFromView(int alignment, int viewColumn);
	public int getAlignmentRowFromView(int alignment, int selectedRow);
	public int getViewColumnFromAlignment(int alignment, int alignmentColumn);
	public int getViewRowFromAlignment(int alignment, int alignmentRow);
	
	public List<IScoreMethod> getScoreMethods();
	public void addScoreMethod(IScoreMethod scoreMethod);
	public void setScoreMethod(int index);
	public IScoreMethod getScoreMethod();	

	
}
