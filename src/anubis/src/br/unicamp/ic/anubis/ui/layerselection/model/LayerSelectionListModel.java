package br.unicamp.ic.anubis.ui.layerselection.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import br.unicamp.ic.anubis.ui.viewer.ILayer;
import br.unicamp.ic.anubis.ui.viewer.ITextLayer;

public class LayerSelectionListModel {
	
	private EventListenerList listenerList = new EventListenerList();
	
	private List<ILayer> textLayerlist;
	private List<ILayer> drawingLayerlist;
	
	public LayerSelectionListModel(){
		textLayerlist = new ArrayList<ILayer>();
		drawingLayerlist = new ArrayList<ILayer>();
	}
	
	
	public void addChangeListener(ChangeListener l) {
		listenerList.add(ChangeListener.class, l);
	}

	public void removeChangeListener(ChangeListener l) {
		listenerList.remove(ChangeListener.class, l);
	}

	protected void fireStateChanged() {
		ChangeEvent event = new ChangeEvent(this);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				((ChangeListener) listeners[i + 1]).stateChanged(event);
			}
		}
	}
	
	public void addLayer(ILayer layer){
		if (layer instanceof ITextLayer){
			textLayerlist.add(layer);
		} else {
			drawingLayerlist.add(layer);
		}
		fireStateChanged();
	}
	
	public void clearLayers(){
		textLayerlist.clear();
		drawingLayerlist.clear();
		fireStateChanged();
	}
	
	public List<ILayer> getLayers(){
		List<ILayer> returnValue = new ArrayList<ILayer>();
		
		returnValue.addAll(textLayerlist);
		returnValue.addAll(drawingLayerlist);
				
		return returnValue;
	}	
	
}
