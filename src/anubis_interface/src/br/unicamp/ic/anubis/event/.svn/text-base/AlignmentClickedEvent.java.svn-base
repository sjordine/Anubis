package br.unicamp.ic.anubis.event;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import br.unicamp.ic.anubis.mechanism.messaging.IEvent;

public class AlignmentClickedEvent implements IEvent {

	public static final int LEFT_MOUSE_BUTTON = 0;
	public static final int MIDDLE_MOUSE_BUTTON = 1;
	public static final int RIGHT_MOUSE_BUTTON = 2;

	private Point point;
	private int button;
	private int alignmentId;
	private Component container;

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public int getButton() {
		return button;
	}

	public void setButton(int button) {
		this.button = button;
	}

	public int getAlignmentId() {
		return alignmentId;
	}

	public void setAlignmentId(int alignmentId) {
		this.alignmentId = alignmentId;
	}

	public void setButton(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			button = AlignmentClickedEvent.LEFT_MOUSE_BUTTON;
		}
		if (SwingUtilities.isMiddleMouseButton(e)) {
			button = AlignmentClickedEvent.MIDDLE_MOUSE_BUTTON;
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			button = AlignmentClickedEvent.RIGHT_MOUSE_BUTTON;
		}
	}

	public void setContainer(Component container) {
		this.container = container;
	}

	public Component getContainer() {
		return container;
	}

}
