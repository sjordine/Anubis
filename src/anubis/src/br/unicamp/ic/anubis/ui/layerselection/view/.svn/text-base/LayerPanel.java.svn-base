package br.unicamp.ic.anubis.ui.layerselection.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.unicamp.ic.anubis.ui.viewer.ILayer;

public abstract class LayerPanel extends JPanel {
	
	private String caption;
	protected ILayer layer;

	
	public LayerPanel(ILayer layer){
		this.layer = layer;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
		setLabel(caption);
	}

	protected abstract void setLabel(String label);
	

}
