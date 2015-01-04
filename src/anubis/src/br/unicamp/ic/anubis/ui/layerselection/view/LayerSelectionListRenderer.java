package br.unicamp.ic.anubis.ui.layerselection.view;

import java.awt.Component;

import javax.swing.ButtonGroup;

import br.unicamp.ic.anubis.ui.viewer.ILayer;
import br.unicamp.ic.anubis.ui.viewer.ITextLayer;

public class LayerSelectionListRenderer {

	public static Component getListCellRendererComponent(ILayer item,ButtonGroup group) {

		LayerPanel renderedItem = null;

		ILayer layer = (ILayer) item;
		renderedItem = getLayerRender(layer, group);

		return renderedItem;
	}

	private static LayerPanel getLayerRender(ILayer layer,ButtonGroup group) {
		LayerPanel panel = null;

		if (layer instanceof ITextLayer) {
			panel = new TextLayerPanel(layer,group);
		} else {
			panel = new DrawingLayerPanel(layer);
		}

		return panel;
	}

}
