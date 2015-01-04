package br.unicamp.ic.anubis.event;

import br.unicamp.ic.anubis.mechanism.messaging.IEvent;

public class AlignmentScoreChangeEvent implements IEvent {
	
	private int alignmentId;

	public AlignmentScoreChangeEvent(int alignmentId){
		this.alignmentId = alignmentId;
	}

	public int getAlignmentId() {
		return alignmentId;
	}

}
