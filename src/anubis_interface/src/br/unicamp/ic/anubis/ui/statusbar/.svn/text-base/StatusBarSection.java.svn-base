package br.unicamp.ic.anubis.ui.statusbar;

import javax.swing.JPanel;

public class StatusBarSection {
	
	public enum Orientation{
		LEFT,
		RIGHT
	}
	
	private double columnWeight;
	private int numberOfColumns;
	private JPanel sectionPanel;
	private int priority;
	private Orientation orientation;
	
	public StatusBarSection(){
		orientation = Orientation.LEFT;
	}
	
	
	public double getColumnWeight() {
		return columnWeight;
	}
	public void setColumnWeight(double columnWeight) {
		this.columnWeight = columnWeight;
	}
	public int getNumberOfColumns() {
		return (numberOfColumns <= 0)?1:numberOfColumns;
	}
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	public JPanel getSectionPanel() {
		return sectionPanel;
	}
	public void setSectionPanel(JPanel sectionPanel) {
		this.sectionPanel = sectionPanel;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Orientation getOrientation() {
		return orientation;
	}
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	

}
