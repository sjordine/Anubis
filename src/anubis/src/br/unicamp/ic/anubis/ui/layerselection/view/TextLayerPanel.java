package br.unicamp.ic.anubis.ui.layerselection.view;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.ui.ILayerSet;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.LayerSetPosition;
import br.unicamp.ic.anubis.ui.viewer.ILayer;
import br.unicamp.ic.anubis.ui.viewer.ITextLayer;

public class TextLayerPanel extends LayerPanel implements ItemListener {

	private JRadioButton rdbTextLayerSelection;
	private FlowLayout layout = new FlowLayout();
	private int layerIndex = -1;

	public TextLayerPanel(ILayer layer,ButtonGroup textLayerGroup) {
		super(layer);
		layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		this.setLayout(layout);
		rdbTextLayerSelection = new JRadioButton();
		rdbTextLayerSelection.addItemListener(this);

		synchronized (textLayerGroup) {
			textLayerGroup.add(rdbTextLayerSelection);
			rdbTextLayerSelection.setSelected(layer.IsEnabled());
		}
		if (layer != null) {
			rdbTextLayerSelection.setText(layer.getLabel());

			AnubisManager manager = AnubisManager.getInstance();
			IUIManager uiManager = manager.getUIManager();

			if (uiManager != null) {
				ILayerSet layerSet = uiManager.getLayerSet(LayerSetPosition.ALIGNMENT);
				List<ITextLayer> layers = layerSet.getTextLayers();
				int index = layers.indexOf(layer);
				layerIndex = index;
			}
		}
		
		this.add(rdbTextLayerSelection);
	}

	@Override
	protected void setLabel(String label) {
		rdbTextLayerSelection.setText(label);
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		AnubisManager manager = AnubisManager.getInstance();
		IUIManager uiManager = manager.getUIManager();
		if (uiManager != null) {
			if (layer != null) {
				int state = event.getStateChange();
				if (state == ItemEvent.SELECTED) {
					ILayerSet layerSet = uiManager.getLayerSet(LayerSetPosition.ALIGNMENT);
					layerSet.setCurrentTextLayer(layerIndex);
				}
			}
		}
	}

}
