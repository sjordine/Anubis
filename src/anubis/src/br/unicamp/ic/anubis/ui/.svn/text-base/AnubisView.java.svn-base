package br.unicamp.ic.anubis.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.controller.AlignmentContextMenuWatcher;
import br.unicamp.ic.anubis.data.Session;
import br.unicamp.ic.anubis.event.AlignmentColumnPopUpMenuEvent;
import br.unicamp.ic.anubis.event.AlignmentRowPopUpMenuEvent;
import br.unicamp.ic.anubis.event.CloseEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.model.AlignmentScrollBarSynchronizer;
import br.unicamp.ic.anubis.ui.layerselection.model.LayerSelectionListModel;
import br.unicamp.ic.anubis.ui.layerselection.view.LayerSelectionList;
import br.unicamp.ic.anubis.ui.menu.MenuBuilder;
import br.unicamp.ic.anubis.ui.statusbar.StatusBar;
import br.unicamp.ic.anubis.ui.statusbar.StatusBarSection;
import br.unicamp.ic.anubis.ui.viewer.AlignmentViewer;
import br.unicamp.ic.anubis.ui.viewer.ILayer;
import br.unicamp.ic.anubis.ui.viewer.ITextLayer;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.ANUBIS_VIEW;
import static br.unicamp.ic.anubis.mechanism.resources.ResourceConstants.ALIGNMENT_HORIZONTAL_SCROLL_SYNCHRONIZER;
import static br.unicamp.ic.anubis.mechanism.resources.ResourceConstants.ALIGNMENT_VERTICAL_SCROLL_SYNCHRONIZER;
import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.ROOT_MENU;


public class AnubisView extends JFrame implements IEventHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public AnubisView() {

		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();
		IResourceManager resourceManager = manager.getResourceManager();
		
		try {
			Image icon = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource(
							"/br/unicamp/ic/anubis/ui/resources/anubis-icon.png"));
			this.setIconImage(icon);

			
		} catch (Exception ex) {
			// Use default textual label if image could not be loaded
		}

		if (eventManager != null && resourceManager != null) {

			eventManager.register(CloseEvent.class, this);
			eventManager.register(AlignmentColumnPopUpMenuEvent.class, new AlignmentContextMenuWatcher());
			eventManager.register(AlignmentRowPopUpMenuEvent.class, new AlignmentContextMenuWatcher());


			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					AnubisManager manager = AnubisManager.getInstance();
					IEventManager eventManager = manager.getEventManager();

					eventManager.raise(new CloseEvent());
				}
			});

			Session session = manager.getSession();
			session.set(ANUBIS_VIEW, this);

			setTitle("Anubis");

			// Create alignment scroll bar synchronizers;
			AlignmentScrollBarSynchronizer synchronizer = new AlignmentScrollBarSynchronizer();
			resourceManager.setResource(ALIGNMENT_HORIZONTAL_SCROLL_SYNCHRONIZER, synchronizer);
			synchronizer = new AlignmentScrollBarSynchronizer();
			resourceManager.setResource(ALIGNMENT_VERTICAL_SCROLL_SYNCHRONIZER, synchronizer);
			
			// Set Components
			JMenuBar menuBar = MenuBuilder.build(ROOT_MENU);
			this.setJMenuBar(menuBar);

			// this.getContentPane().setLayout(new GridLayout(2, 2));

			Container contentPane = this.getContentPane();			
			contentPane.setLayout(new BorderLayout());
			
			JPanel mainPanel = new JPanel();
			contentPane.add(mainPanel,BorderLayout.CENTER);

			GridBagLayout gbl_contentPane = new GridBagLayout();
			gbl_contentPane.columnWidths = new int[] { 10 };
			gbl_contentPane.rowHeights = new int[] { 10 };
			gbl_contentPane.columnWeights = new double[] { 0.0, 1.5, 0.0, 0.4,
					0.0 };
			gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 1.0 };
			mainPanel.setLayout(gbl_contentPane);

			// AlignmentViewerPanel panel = new AlignmentViewerPanel();
			AlignmentViewer panelBenchmark = new AlignmentViewer();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridwidth = 2;
			gbc_panel.ipadx = 3;
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.insets = new Insets(5, 5, 5, 5);
			gbc_panel.gridx = 1;
			gbc_panel.gridy = 1;
			mainPanel.add(panelBenchmark, gbc_panel);

			AlignmentViewer panelTarget = new AlignmentViewer();
			gbc_panel = new GridBagConstraints();
			gbc_panel.gridwidth = 2;
			gbc_panel.ipadx = 3;
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.insets = new Insets(5, 5, 5, 5);
			gbc_panel.gridx = 1;
			gbc_panel.gridy = 2;
			mainPanel.add(panelTarget, gbc_panel);

			panelBenchmark.setAlignmentId(0);
			panelTarget.setAlignmentId(1);

			LayerSelectionList alignmentLayerSelection = new LayerSelectionList();
			LayerSelectionList topLayerSelection = new LayerSelectionList();
			LayerSelectionList bottomLayerSelection = new LayerSelectionList();
			LayerSelectionList leftLayerSelection = new LayerSelectionList();
			LayerSelectionList rightLayerSelection = new LayerSelectionList();
			
			JTabbedPane layerSelectionPanel = new JTabbedPane();
			
			addLayerSelectionPanel("Alignment",layerSelectionPanel,alignmentLayerSelection);
			addLayerSelectionPanel("Top",layerSelectionPanel,topLayerSelection);
			addLayerSelectionPanel("Left",layerSelectionPanel,leftLayerSelection);
			addLayerSelectionPanel("Bottom",layerSelectionPanel,bottomLayerSelection);
			addLayerSelectionPanel("Right",layerSelectionPanel,rightLayerSelection);
			
			
		
			gbc_panel = new GridBagConstraints();
			gbc_panel.gridwidth = 1;
			gbc_panel.gridheight = 2;
			gbc_panel.ipadx = 3;
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.insets = new Insets(5, 5, 5, 5);
			gbc_panel.gridx = 3;
			gbc_panel.gridy = 1;
			mainPanel.add(layerSelectionPanel, gbc_panel);
						
			
			StatusBar statusBar = new StatusBar();
			contentPane.add(statusBar, BorderLayout.SOUTH);
			
			this.setMinimumSize(new Dimension(1024, 768));


			// Set Menu
			pack();
			
			//Bind status bar
			statusBar.create();

			// Bind layer selection model
			addLayers(LayerSetPosition.ALIGNMENT,alignmentLayerSelection, manager);
			addLayers(LayerSetPosition.COLUMNS,topLayerSelection, manager);
			addLayers(LayerSetPosition.BOTTOM,bottomLayerSelection, manager);
			addLayers(LayerSetPosition.SEQUENCES,leftLayerSelection, manager);
			addLayers(LayerSetPosition.RIGHT,rightLayerSelection, manager);
			
			this.invalidate();
		}
	}

	public void addLayers(LayerSetPosition position,LayerSelectionList alignmentLayerSelection,
			AnubisManager manager) {
		LayerSelectionListModel layerModel = alignmentLayerSelection.getModel();

		IUIManager uimanager = manager.getUIManager();
		ILayerSet layerSet = uimanager.getLayerSet(position);
		List<ITextLayer> textLayers = layerSet.getTextLayers();
		if (textLayers != null && textLayers.size() > 0) {
			for (int i = 0; i < textLayers.size(); i++) {
				layerModel.addLayer(textLayers.get(i));
			}
		}
		List<ILayer> drawingLayers = layerSet.getDrawingLayers();
		if (drawingLayers != null && drawingLayers.size() > 0) {
			for (int i = 0; i < drawingLayers.size(); i++) {
				layerModel.addLayer(drawingLayers.get(i));
			}
		}
	}

	public void addLayerSelectionPanel(String name,JTabbedPane layerSelectionPanel,
			LayerSelectionList alignmentLayerSelection) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(alignmentLayerSelection, BorderLayout.CENTER);
		layerSelectionPanel.addTab(name,panel);
	}

	@Override
	public void eventRaised(IEvent message) {
		processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		dispose();
	}

}
