package br.unicamp.ic.anubis.data;

import java.util.HashMap;

public interface IAlignmentDataRepository {

	public void setData(int alignmentId, Object data);
	public Object getData(int alignmentId,int startColumn, int startRow, int endColumn, int endRow);
	public Object getSequences(int alignmentId,int startRow, int endRow);
	
	public boolean isLoaded(int alignmentId);
	
	public int numberOfSequences(int alignmentId);
	public int length(int alignmentId);
	
}
