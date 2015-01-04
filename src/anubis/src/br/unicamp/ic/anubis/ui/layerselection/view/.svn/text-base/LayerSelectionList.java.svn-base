package br.unicamp.ic.anubis.ui.layerselection.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import br.unicamp.ic.anubis.ui.layerselection.model.LayerSelectionListModel;
import br.unicamp.ic.anubis.ui.viewer.ILayer;

public class LayerSelectionList extends JPanel implements ChangeListener {

	// private BoxLayout layout;
	private JPanel container;
	private FlowLayout layout;
	private VerticalLayout containerLayout;
	private JScrollPane scroller;
	
	private LayerSelectionListModel model;
	
	private ButtonGroup textLayerGroup;
	

	public LayerSelectionList() {

		layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		this.setLayout(layout);

		container = new JPanel();
		//containerLayout = new BoxLayout(container, BoxLayout.Y_AXIS);
		containerLayout = new VerticalLayout(5,VerticalLayout.LEFT,VerticalLayout.TOP);
		//containerLayout.setAlignment(CustomFlowLayout.LEFT);
		
		container.setLayout(containerLayout);

		scroller = new JScrollPane(container);
		scroller.setBorder(BorderFactory.createEmptyBorder());

		this.add(scroller);
		
		model = new LayerSelectionListModel();
		model.addChangeListener(this);
		
		textLayerGroup = new ButtonGroup();

	}
	
	@Override
	public void stateChanged(ChangeEvent e) {		
		container.removeAll();
		List<ILayer> layers = model.getLayers();
		if (layers!=null){
			for (ILayer layer:layers){
				Component layerRendering = LayerSelectionListRenderer.getListCellRendererComponent(layer, textLayerGroup);
				container.add(layerRendering, JPanel.TOP_ALIGNMENT);
			}
		}
		scroller.setPreferredSize(this.getSize());
	}




	public LayerSelectionListModel getModel() {
		return model;
	}

}
