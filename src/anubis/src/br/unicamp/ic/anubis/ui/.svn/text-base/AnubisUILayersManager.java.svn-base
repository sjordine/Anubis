package br.unicamp.ic.anubis.ui;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.anubis.ui.viewer.ILayer;
import br.unicamp.ic.anubis.ui.viewer.ISelectionLayer;
import br.unicamp.ic.anubis.ui.viewer.ITextLayer;

class AnubisUILayersManager {

	private List<ILayer> layers;
	private List<ITextLayer> textLayers;
	private List<ILayer> columnLayers;
	private List<ITextLayer> columnTextLayers;
	private List<ITextLayer> sequenceTextLayers;
	private List<ILayer> sequenceLayers;
	
	private ISelectionLayer selectionLayer;

	private int currentTextLayer;
	private int currentColumnTextLayer;
	private int currentSequenceTextLayer;

	AnubisUILayersManager() {
		layers = new ArrayList<ILayer>();
		textLayers = new ArrayList<ITextLayer>();
		columnLayers = new ArrayList<ILayer>();
		columnTextLayers = new ArrayList<ITextLayer>();
		sequenceLayers = new ArrayList<ILayer>();
		sequenceTextLayers = new ArrayList<ITextLayer>();
		currentTextLayer = -1;
		currentSequenceTextLayer = -1;
	}

	public List<ILayer> getDrawingLayers() {
		return layers;
	}

	public void addDrawingLayer(ILayer layer) {
		if (layer != null) {
			if (layer instanceof ITextLayer) {
				addTextLayer((ITextLayer) layer);
			} else {
				layer.setEnabled(true);
				layers.add(layer);
			}
		}
	}

	public void resetDrawingLayers() {
		layers = new ArrayList<ILayer>();
		textLayers = new ArrayList<ITextLayer>();
		columnLayers = new ArrayList<ILayer>();
		columnTextLayers = new ArrayList<ITextLayer>();
		sequenceLayers = new ArrayList<ILayer>();
		sequenceTextLayers = new ArrayList<ITextLayer>();
		currentTextLayer = -1;
		currentColumnTextLayer = -1;
		currentSequenceTextLayer = -1;
	}

	public List<ITextLayer> getTextLayers() {
		return textLayers;
	}

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

	public void setCurrentTextLayer(int index) {

		if (index >= 0 && index <= textLayers.size() - 1) {
			currentTextLayer = index;
			for (int i=0; i < textLayers.size(); i++){
				textLayers.get(i).setEnabled(i==index);
			}
		}
		

	}

	public ITextLayer getCurrentTextLayer() {
		ITextLayer layer = null;

		if (currentTextLayer >= 0) {
			layer = textLayers.get(currentTextLayer);
		}

		return layer;
	}

	public List<ILayer> getColumnLayers() {
		return columnLayers;
	}

	public void addColumnLayer(ILayer layer) {
		if (layer != null) {
			if (layer instanceof ITextLayer) {
				addTextLayer((ITextLayer) layer);
			} else {
				columnLayers.add(layer);
			}
		}
	}

	public List<ITextLayer> getColumnTextLayers() {
		return columnTextLayers;
	}

	public int addColumnTextLayer(ITextLayer layer) {
		int index = -1;

		if (layer != null) {
			columnTextLayers.add(layer);
			index = columnTextLayers.size() - 1;
			if (index == 0) {
				setCurrentColumnTextLayer(index);
			}
		}

		return index;
	}

	public void setCurrentColumnTextLayer(int index) {
		if (index >= 0 && index <= columnTextLayers.size() - 1) {
			currentColumnTextLayer = index;
		}

	}

	public ITextLayer getCurrentColumnTextLayer() {
		ITextLayer layer = null;

		if (currentColumnTextLayer >= 0) {
			layer = columnTextLayers.get(currentColumnTextLayer);
		}

		return layer;
	}

	public List<ILayer> getSequenceLayers() {
		return sequenceLayers;
	}

	public void addSequenceLayer(ILayer layer) {
		if (layer != null) {
			if (layer instanceof ITextLayer) {
				addSequenceTextLayer((ITextLayer) layer);
			} else {
				sequenceLayers.add(layer);
			}
		}
	}

	public List<ITextLayer> getSequenceTextLayers() {
		return sequenceTextLayers;
	}

	public int addSequenceTextLayer(ITextLayer layer) {
		int index = -1;
		
		if (layer != null) {
			sequenceTextLayers.add(layer);
			index = sequenceTextLayers.size() - 1;
			if (index == 0) {
				setCurrentSequenceTextLayer(index);
			}
		}

		return index;
	}

	public void setCurrentSequenceTextLayer(int index) {
		if (index >= 0 && index <= columnTextLayers.size() - 1) {
			currentSequenceTextLayer = index;
		}

	}

	public ITextLayer getCurrentSequenceTextLayer() {
		ITextLayer layer = null;

		if (currentSequenceTextLayer >= 0) {
			layer = sequenceTextLayers.get(currentSequenceTextLayer);
		}
		
		return layer;
	}

	public ISelectionLayer getSelectionLayer() {
		return selectionLayer;
	}

	public void setSelectionLayer(ISelectionLayer layer) {
		selectionLayer = layer;
	}

}
