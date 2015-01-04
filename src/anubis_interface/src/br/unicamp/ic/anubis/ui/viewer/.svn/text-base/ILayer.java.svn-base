package br.unicamp.ic.anubis.ui.viewer;

import java.awt.Image;
import java.awt.Insets;

public interface ILayer {
	
	public Image GetImage(int alignmentIndex, int width, int height,Insets borders);
	
	public String getLabel();
	
	public boolean IsEnabled();
	public void setEnabled(boolean enabled);
	
	public int getTransparency();
	public void setTransparency(int transparency);
	
	/**
	 * Get the minimum acceptable border for this layer
	 * 
	 * @param alignmentIndex alignment to be considered
	 * @return minimum acceptable border for this layer
	 */
	public Insets getBorders(int alignmentIndex);
	
	/**
	 * Get Number of text rows, zero if it can vary
	 * 
	 * @return number of text rows
	 */
	public int getTextRows(int alignmentIndex);
	
	/**
	 * Get Number of text columns, zero if it can vary
	 * 
	 * @return number of text columns
	 */
	public int getTextColumns(int alignmentIndex);

}
