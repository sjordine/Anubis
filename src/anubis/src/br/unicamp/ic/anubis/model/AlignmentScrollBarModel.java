package br.unicamp.ic.anubis.model;

import javax.swing.BoundedRangeModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;

public class AlignmentScrollBarModel implements BoundedRangeModel {

	public enum DIRECTION {
		COLUMNS, SEQUENCES
	}

	private ChangeEvent changeEvent = null;
	private EventListenerList listenerList = new EventListenerList();

	private AlignmentScrollBarSynchronizer synchronizer;

	private int min, max;
	private int value;
	private int extent;

	private int alignmentId;
	private DIRECTION direction;

	boolean isAdjusting;

	@Override
	public void addChangeListener(ChangeListener l) {
		listenerList.add(ChangeListener.class, l);
	}

	@Override
	public void removeChangeListener(ChangeListener l) {
		listenerList.remove(ChangeListener.class, l);
	}

	@Override
	public int getExtent() {
		return extent;
	}

	@Override
	public int getMaximum() {
		return max;
	}

	@Override
	public int getMinimum() {
		return min;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public boolean getValueIsAdjusting() {
		return isAdjusting;
	}

	@Override
	public void setValue(int value) {
		setRangeProperties(value, extent, min, max, isAdjusting);
	}

	@Override
	public void setExtent(int extent) {
		setRangeProperties(value, extent, min, max, isAdjusting);
	}

	@Override
	public void setMaximum(int max) {
		setRangeProperties(value, extent, min, max, isAdjusting);
	}

	@Override
	public void setMinimum(int min) {
		setRangeProperties(value, extent, min, max, isAdjusting);
	}

	@Override
	public void setValueIsAdjusting(boolean isAdjusting) {
		setRangeProperties(value, extent, min, max, isAdjusting);
	}

	@Override
	public void setRangeProperties(int value, int extent, int min, int max,
			boolean isAdjusting) {
		setRangeProperties(value, extent, min, max, isAdjusting, false);
	}

	private void setRangeProperties(int value, int extent, int min, int max,
			boolean isAdjusting, boolean recursiveChange) {

		boolean hasChanged = false;
		boolean valueHasChanged = false;

		int delta = 0;

		if (min > max) {
			max = min + 1;
		}

		if (value < min) {
			value = min;
		}

		if (value > max) {
			value = max;
		}

		if (this.value != value) {
			delta = value - this.value;
			this.value = value;

			if (direction != null) {
				AnubisManager manager = AnubisManager.getInstance();
				IAlignmentManager alignmentManager = manager
						.getAlignmentManager();

				if (alignmentManager != null) {
					switch (direction) {
					case COLUMNS:
						alignmentManager.setCurrentFirstColumn(alignmentId, value);
						break;
					case SEQUENCES:
						alignmentManager.setCurrentFirstRow(alignmentId, value);
						break;
					}
				}
			}

			hasChanged = true;
			valueHasChanged = true;
		}
		if (this.extent != extent) {
			this.extent = extent;
			hasChanged = true;
		}
		if (this.min != min) {
			this.min = min;
			hasChanged = true;
		}
		if (this.max != max) {
			this.max = max;
			hasChanged = true;
		}
		if (this.isAdjusting != isAdjusting) {
			this.isAdjusting = isAdjusting;
			hasChanged = true;
		}

		if (!recursiveChange && valueHasChanged && synchronizer != null) {
			synchronizer.notifyChange(this, delta);
		}

		if (hasChanged) {

			fireStateChanged();
		}
	}

	private void fireStateChanged() {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				if (changeEvent == null) {
					changeEvent = new ChangeEvent(this);
				}
				((ChangeListener) listeners[i + 1]).stateChanged(changeEvent);
			}
		}
	}

	public void adjustValue(int deltaValue) {
		setRangeProperties(value + deltaValue, extent, min, max, isAdjusting,
				true);
	}

	public AlignmentScrollBarSynchronizer getSynchronizer() {
		return synchronizer;
	}

	public void setSynchronizer(AlignmentScrollBarSynchronizer synchronizer) {
		this.synchronizer = synchronizer;
		synchronizer.addModel(this);
	}

	public int getAlignmentId() {
		return alignmentId;
	}

	public void setAlignmentId(int alignmentId) {
		this.alignmentId = alignmentId;
	}

	public DIRECTION getDirection() {
		return direction;
	}

	public void setDirection(DIRECTION direction) {
		this.direction = direction;
	}

}
