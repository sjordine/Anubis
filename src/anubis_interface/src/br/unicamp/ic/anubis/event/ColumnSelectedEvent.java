package br.unicamp.ic.anubis.event;

import br.unicamp.ic.anubis.mechanism.messaging.IEvent;

public class ColumnSelectedEvent implements IEvent {
	
	private int column;
	private int alignmentId;
	
	public ColumnSelectedEvent(int alignmentId, int column){
		this.column = column;
		this.alignmentId = alignmentId;
	}

	public int getColumn() {
		return column;
	}

	public int getAlignmentId() {
		return alignmentId;
	}


}
