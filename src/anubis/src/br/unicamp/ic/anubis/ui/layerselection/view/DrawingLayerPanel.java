package br.unicamp.ic.anubis.ui.layerselection.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.unicamp.ic.anubis.ui.viewer.ILayer;

public class DrawingLayerPanel extends LayerPanel implements ItemListener, ChangeListener {

	JCheckBox chkBoxLayerSelection;
	JPanel pnlSettings;
	JSlider sldTransparency;
	
	
	GridLayout fullLayout;
	FlowLayout layout;

	public DrawingLayerPanel(ILayer layer) {
		super(layer);
		fullLayout = new GridLayout(2, 1);
		this.setLayout(fullLayout);
		chkBoxLayerSelection = new JCheckBox();
		
		pnlSettings = new JPanel();
		layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		pnlSettings.setLayout(layout);

		chkBoxLayerSelection.addItemListener(this);
		
		if (layer != null) {
			sldTransparency = new JSlider();
			sldTransparency.setMinimum(0);
			sldTransparency.setMaximum(100);
			sldTransparency.setPreferredSize(new Dimension(100,20));

			sldTransparency.setValue(layer.getTransparency());
			sldTransparency.addChangeListener(this);
			
			
			pnlSettings.add(sldTransparency);
			
			chkBoxLayerSelection.setSelected(layer.IsEnabled());
			chkBoxLayerSelection.setText(layer.getLabel());
		}
		this.add(chkBoxLayerSelection);
		this.add(pnlSettings);
	}

	@Override
	protected void setLabel(String label) {
		chkBoxLayerSelection.setText(label);
	}

	@Override
	public void itemStateChanged(ItemEvent event) {		
		
		if (layer!=null){
			int state = event.getStateChange();
			if (state == ItemEvent.SELECTED) {
				layer.setEnabled(true);
			} else {
				layer.setEnabled(false);
			}
		}

	}

	@Override
	public void stateChanged(ChangeEvent event) {
		if (layer!=null){
			JSlider source = (JSlider)event.getSource();
		    layer.setTransparency(source.getValue());
		}
	}

}
