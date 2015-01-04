package br.unicamp.ic.anubis.event;

import java.awt.Component;
import java.awt.Point;

import br.unicamp.ic.anubis.mechanism.messaging.IEvent;

public class AlignmentShowPopUpMenuEvent implements IEvent {
	
	private Point point;
	private int alignmentId;
	private Component container;
	
	public AlignmentShowPopUpMenuEvent(int alignmentId, Point point, Component container){
		this.point = (Point)point.clone();
		this.alignmentId = alignmentId;
		this.container = container;
	}
	
	
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public int getAlignmentId() {
		return alignmentId;
	}
	public void setAlignmentId(int alignmentId) {
		this.alignmentId = alignmentId;
	}


	public Component getContainer() {
		return container;
	}


}
