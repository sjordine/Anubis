package br.unicamp.ic.anubis.mechanism.alignment;

public class ViewBlock {

	private int start;
	private int end;

	private boolean visible;

	public ViewBlock(int start, int end, boolean visible) {
		setValues(start, end, visible);
	}

	public void resize(int start, int end, boolean visible) {
		setValues(end, start, visible);
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public int getSize() {
		return (end - start) + 1;
	}

	private void setValues(int start, int end, boolean visible) {
		if (end < start) {
			int tmp = start;
			start = end;
			end = tmp;
		}

		this.start = start;
		this.end = end;
		this.visible = visible;

	}

	public boolean isVisible() {
		return visible;
	}

	public boolean intersects(int startPos, int endPos) {
		boolean returnValue = false;

		returnValue = (startPos <= end) && (endPos >= start);

		return returnValue;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();

		if (isVisible()) {
			buffer.append('[');
		} else {
			buffer.append('{');
		}

		buffer.append(getStart());
		buffer.append("-");
		buffer.append(getEnd());

		if (isVisible()) {
			buffer.append(']');
		} else {
			buffer.append('}');
		}

		return buffer.toString();
	}

}
