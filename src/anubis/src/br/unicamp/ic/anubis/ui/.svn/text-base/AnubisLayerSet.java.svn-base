package br.unicamp.ic.anubis.ui;

import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.anubis.ui.viewer.ILayer;
import br.unicamp.ic.anubis.ui.viewer.ISelectionLayer;
import br.unicamp.ic.anubis.ui.viewer.ITextLayer;
import br.unicamp.ic.anubis.util.LayerUtil;

public class AnubisLayerSet implements ILayerSet {
	
	private List<ILayer> layers;
	private List<ITextLayer> textLayers;	
	
	private ISelectionLayer selectionLayer;
	
	private int currentTextLayer;
	
	private Insets borders = null;
	
	public AnubisLayerSet(){
		borders = new Insets(0, 0, 0, 0);
		layers = new ArrayList<ILayer>();
		textLayers = new ArrayList<ITextLayer>();
		currentTextLayer = -1;
	}
	
	
	@Override
	public void setBorders(Insets dimension) {
		if (dimension!=null){
			setBorders(dimension.left, dimension.top, dimension.right, dimension.bottom);
		}
	}

	@Override
	public void setBorders(int leftBorder, int topBorder, int rightBorder,
			int bottomBorder) {
		borders = new Insets(topBorder, leftBorder, bottomBorder, rightBorder); 
	}

	@Override
	public Insets getBorders() {
		return (Insets)borders.clone();
	}

	@Override
	public void resetBorders() {
		borders = new Insets(0, 0, 0, 0);
	}

	@Override
	public List<ITextLayer> getTextLayers() {
		return textLayers;
	}

	@Override
	public int addTextLayer(ITextLayer layer) {
		int index = -1;

		if (layer != null) {
			layer.setEnabled(false);
			textLayers.add(layer);
			index = textLayers.size() - 1;
			if (index == 0) {
				layer.setEnabled(true);
				setCurrentTextLayer(index);
			}
		}

		return index;
	}

	@Override
	public void setCurrentTextLayer(int index) {
		if (index >= 0 && index <= textLayers.size() - 1) {
			currentTextLayer = index;
			for (int i=0; i < textLayers.size(); i++){
				textLayers.get(i).setEnabled(i==index);
			}
		}
	}

	@Override
	public ITextLayer getCurrentTextLayer() {
		ITextLayer layer = null;

		if (currentTextLayer >= 0) {
			layer = textLayers.get(currentTextLayer);
		}

		return layer;
	}

	@Override
	public List<ILayer> getDrawingLayers() {
		return layers;
	}

	@Override
	public void addDrawingLayer(ILayer layer) {
		if (layer != null) {
			if (layer instanceof ITextLayer) {
				addTextLayer((ITextLayer) layer);
			} else {
				//All layers are enabled by default
				layer.setEnabled(true);
				layers.add(layer);
			}
		}
	}

	@Override
	public void resetDrawingLayers() {
		layers = new ArrayList<ILayer>();
		textLayers = new ArrayList<ITextLayer>();
		currentTextLayer = -1;
	}

	@Override
	public ISelectionLayer getSelectionLayer() {
		return selectionLayer;
	}

	@Override
	public void setSelectionLayer(ISelectionLayer layer) {
		selectionLayer = layer;
	}


	@Override
	public Insets getMiminumBorder(int alignmentIndex) {
		Insets returnValue = new Insets(0, 0, 0, 0);
		
		returnValue = LayerUtil.getMinimumAcceptableBorder(returnValue, borders);
		
		ITextLayer currentTextLayer = getCurrentTextLayer();
		if (currentTextLayer!=null){
			returnValue = LayerUtil.getMinimumAcceptableBorder(returnValue, currentTextLayer.getBorders(alignmentIndex));
		}
		
		if (layers!=null && layers.size() >0){
			for (ILayer layer: layers){
				returnValue = LayerUtil.getMinimumAcceptableBorder(returnValue, layer.getBorders(alignmentIndex));
			}
		}
		
		if (selectionLayer!=null){
			returnValue = LayerUtil.getMinimumAcceptableBorder(returnValue, selectionLayer.getBorders(alignmentIndex));
		}
		
		
		return returnValue;
	}
	
	@Override
	public int getNumberOfTextColumns(int alignmentIndex){
		int returnValue = 0;
		
				
		ITextLayer currentTextLayer = getCurrentTextLayer();
		if (currentTextLayer!=null){
			returnValue = Math.max(returnValue, currentTextLayer.getTextColumns(alignmentIndex));
		}
		
		if (layers!=null && layers.size() >0){
			for (ILayer layer: layers){
				returnValue = Math.max(returnValue, layer.getTextColumns(alignmentIndex));
			}
		}
		
		if (selectionLayer!=null){
			returnValue = Math.max(returnValue, selectionLayer.getTextColumns(alignmentIndex));
		}
		
		
		return returnValue;
	
	}
	
	@Override
	public int getNumberOfTextRows(int alignmentIndex){
		int returnValue = 0;
		
				
		ITextLayer currentTextLayer = getCurrentTextLayer();
		if (currentTextLayer!=null){
			returnValue = Math.max(returnValue, currentTextLayer.getTextRows(alignmentIndex));
		}
		
		if (layers!=null && layers.size() >0){
			for (ILayer layer: layers){
				returnValue = Math.max(returnValue, layer.getTextRows(alignmentIndex));
			}
		}
		
		if (selectionLayer!=null){
			returnValue = Math.max(returnValue, selectionLayer.getTextRows(alignmentIndex));
		}
		
		
		return returnValue;
	
	}

}
