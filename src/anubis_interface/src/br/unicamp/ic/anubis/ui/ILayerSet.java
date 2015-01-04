package br.unicamp.ic.anubis.ui;

import java.awt.Insets;
import java.util.List;

import br.unicamp.ic.anubis.ui.viewer.ILayer;
import br.unicamp.ic.anubis.ui.viewer.ISelectionLayer;
import br.unicamp.ic.anubis.ui.viewer.ITextLayer;

public interface ILayerSet {
	

	
	public void setBorders(Insets dimension);
	public void setBorders(int leftBorder, int topBorder, int rightBorder, int bottomBorder);
	public Insets getBorders();
	
	public void resetBorders();
	
	public List<ITextLayer> getTextLayers();
	public int addTextLayer(ITextLayer layer);
	public void setCurrentTextLayer(int index);	
	public ITextLayer getCurrentTextLayer();
	
	public List<ILayer> getDrawingLayers();
	public void addDrawingLayer(ILayer layer);
	public void resetDrawingLayers();
	
	public ISelectionLayer getSelectionLayer();
	public void setSelectionLayer(ISelectionLayer layer);
	
	public Insets getMiminumBorder(int alignmentIndex);
	int getNumberOfTextColumns(int alignmentIndex);
	int getNumberOfTextRows(int alignmentIndex);

}
