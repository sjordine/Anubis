package br.unicamp.ic.anubis.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

class AnubisUIAlignmentManager {
	
	private Insets fontBorders = null;
	private Insets topBorders = null;
	private Insets leftBorders = null;
	private Color fontColor = null;

	private Font font = null;
	private int fontSize = 10;
	
	public AnubisUIAlignmentManager() {
		this.setFontSize(10);
		fontBorders = new Insets(0, 0, 0, 0);
		topBorders = new Insets(0, 0, 0, 0);
		leftBorders = new Insets(0, 0, 0, 0);
		fontColor = Color.BLACK;
	}
	

	public int getFontSize() {
		return fontSize;
	}


	public void setFontSize(int size) {
		fontSize = size;
		font = new Font("Courier", Font.PLAIN, fontSize);
	}


	public Font getFont() {
		return font;
	}

	public void setBorders(Insets dimension) {
		if (dimension!=null){
			setBorders(dimension.left, dimension.top, dimension.right, dimension.bottom);
		}
	}


	public void setBorders(int leftBorder, int topBorder, int rightBorder,
			int bottomBorder) {
		int top = fontBorders.top;
		int bottom = fontBorders.bottom;
		int left = fontBorders.left;
		int right = fontBorders.right;
		
		int topTop = topBorders.top;
		int topBottom = topBorders.bottom;

		fontBorders.set(Math.max(top, topBorder), Math.max(left, leftBorder),
				Math.max(bottom, bottomBorder), Math.max(right, rightBorder));
		
		topBorders.set(topTop, Math.max(left, leftBorder),topBottom, Math.max(right, rightBorder));
	}


	public void resetBorders() {
		fontBorders = new Insets(0, 0, 0, 0);
		topBorders = new Insets(0, 0, 0, 0);
	}

	public Insets getBorders() {
		return fontBorders;
	}


	public void setFontColor(Color color) {
		this.fontColor = color;

	}

	public Color getFontColor() {
		return fontColor;
	}
	
	public void setColumnBorders(int topBorder, int bottomBorder) {
		int left = fontBorders.left;
		int right = fontBorders.right;
		
		int top = topBorders.top;
		int bottom = topBorders.bottom;
		
		topBorders.set(Math.max(top, topBorder), left,Math.max(bottom, bottomBorder), right);
		
	}

	public Insets getColumnBorders() {
		return topBorders;
	}


	public void setSequenceBorders(int leftBorder, int rightBorder) {
		int left = leftBorders.left;
		int right = leftBorders.right;
		
		int top = fontBorders.top;
		int bottom = fontBorders.bottom;
		
		leftBorders.set(top, Math.max(left, leftBorder), bottom, Math.max(right, rightBorder));
		
	}


	public Insets getSequenceBorders() {
		return leftBorders;
	}

}
