package br.unicamp.ic.anubis.treeviewer.controller.dialog;

import static br.unicamp.ic.anubis.treeviewer.common.CommonConstants.SELECTED_DISTANCE_METHOD;
import static br.unicamp.ic.anubis.treeviewer.common.CommonConstants.SELECTED_TREE_ALGORITHM;
import static br.unicamp.ic.anubis.treeviewer.common.CommonConstants.TREEVIEW_MANAGER_ID;
import jam.controlpalettes.BasicControlPalette;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jebl.evolution.graphs.Node;
import jebl.evolution.io.ImportException;
import jebl.evolution.io.NewickImporter;
import jebl.evolution.trees.Tree;
import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.treeviewer.event.RotateNodeEvent;
import br.unicamp.ic.anubis.treeviewer.event.TreeReadyEvent;
import br.unicamp.ic.anubis.treeviewer.mechanism.IDistanceMethodAlgorithm;
import br.unicamp.ic.anubis.treeviewer.mechanism.ITreeAlgorithm;
import br.unicamp.ic.anubis.treeviewer.mechanism.ITreeManager;
import br.unicamp.ic.anubis.treeviewer.ui.dialog.ComboItem;
import br.unicamp.ic.anubis.treeviewer.ui.dialog.FigTreeNexusImporter;
import figtree.treeviewer.AttributeColourController;
import figtree.treeviewer.ExtendedTreeViewer;
import figtree.treeviewer.TreeAppearanceController;
import figtree.treeviewer.painters.BasicLabelPainter;
import figtree.treeviewer.painters.LabelPainterController;
import figtree.treeviewer.treelayouts.PolarTreeLayout;
import figtree.treeviewer.treelayouts.RadialTreeLayout;
import figtree.treeviewer.treelayouts.RectilinearTreeLayout;

public class TreeViewDialogController implements IEventHandler, ItemListener {

	public enum Layout {
		RECTILINEAR, POLAR, RADIAL
	}

	private ExtendedTreeViewer benchmarkView;
	private ExtendedTreeViewer targetView;

	private JComboBox cbAlgorithm;
	private JComboBox cbDistanceMethod;

	public ExtendedTreeViewer getBenchmarkView() {
		return benchmarkView;
	}

	public void setBenchmarkView(ExtendedTreeViewer benchmarkView) {
		this.benchmarkView = benchmarkView;
	}

	public ExtendedTreeViewer getTargetView() {
		return targetView;
	}

	public void setTargetView(ExtendedTreeViewer targetView) {
		this.targetView = targetView;
	}

	public TreeViewDialogController() {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();
		if (eventManager != null) {
			eventManager.register(RotateNodeEvent.class, this);
			eventManager.register(PropertyChangedEvent.class, this);
			eventManager.register(TreeReadyEvent.class, this);
		}
	}

	public void load() {

		setLayout(Layout.RECTILINEAR);

		try {
			readFromFile(new File("//home//sergio//Desktop//carnivore.tree"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean readFromFile(File file) throws IOException {
		Reader reader = null;
		try {
			reader = new FileReader(file);

			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = bufferedReader.readLine();
			while (line != null && line.length() == 0) {
				line = bufferedReader.readLine();
			}

			boolean isNexus = (line != null && line.toUpperCase().contains(
					"#NEXUS"));

			reader = new FileReader(file);

			boolean success = readData(reader, isNexus);

			reader.close();

			return success;

		} catch (IOException ioe) {
			if (reader != null) {
				reader.close();
			}
			throw ioe;
		}
	}

	protected boolean readData(Reader reader, boolean isNexus)
			throws IOException {

		List<Tree> trees = new ArrayList<Tree>();

		boolean hasSettings = false;

		try {
			Map<String, Object> settings = new HashMap<String, Object>();
			// First of all, fully populate the settings map so that
			// all the settings have defaults
			// controlPalette.getSettings(settings);

			if (isNexus) {
				FigTreeNexusImporter importer = new FigTreeNexusImporter(reader);
				while (importer.hasTree()) {
					Tree tree = importer.importNextTree();
					trees.add(tree);
				}
				// Try to find a figtree block and if found, parse the settings
				while (true) {
					try {
						importer.findNextBlock();
						if (importer.getNextBlockName().equalsIgnoreCase(
								"FIGTREE")) {
							importer.parseFigTreeBlock(settings);
							hasSettings = true;
						}
					} catch (EOFException ex) {
						break;
					}
				}
			} else {
				NewickImporter importer = new NewickImporter(reader, true);
				while (importer.hasTree()) {
					Tree tree = importer.importNextTree();
					trees.add(tree);
				}
			}

			if (trees.size() == 0) {
				throw new ImportException("This file contained no trees.");
			}

			checkLabelAttribute(trees);

			benchmarkView.setTrees(trees);
			targetView.setTrees(trees);

			// controlPalette.setSettings(settings);
		} catch (ImportException ie) {
			return false;
		}

		if (!hasSettings) {
			// If there weren't settings in the file then this wasn't a TreeDraw
			// created document so we don't want to be able to overwrite it
			// without
			// explicit action of the user...
			// setDirty();
			// clearFile();
		}

		return true;
	}

	private void checkLabelAttribute(List<Tree> trees) {

		boolean hasLabel = false;

		for (Tree tree : trees) {
			for (Node node : tree.getNodes()) {
				if (node.getAttribute("label") != null) {
					hasLabel = true;
				}
			}
		}

		if (hasLabel) {
			String labelName = null;

			do {
				labelName = JOptionPane
						.showInputDialog(
								"The node/branches of the tree are labelled\n"
										+ "(i.e., with bootstrap values or posterior probabilities).\n\n"
										+ "Please select a name for these values.",
								"label");
				if (labelName == null) {
					labelName = "label";
				}
				labelName = labelName.trim();

				if (labelName.length() == 0) {
					Toolkit.getDefaultToolkit().beep();
				}
			} while (labelName == null || labelName.length() == 0);

			if (!labelName.equals("label")) {
				for (Tree tree : trees) {
					for (Node node : tree.getNodes()) {
						Object value = node.getAttribute("label");
						if (value != null) {
							node.removeAttribute("label");
							node.setAttribute(labelName, value);
						}
					}
				}
			}
		}
	}

	public void setPainters(ExtendedTreeViewer viewer, JFrame frame) {
		BasicControlPalette controlPalette = new BasicControlPalette(200,
				BasicControlPalette.DisplayMode.ONLY_ONE_OPEN);
		controlPalette.getPanel().setBackground(new Color(231, 237, 246));
		controlPalette.getPanel().setOpaque(true);

		AttributeColourController attributeColourController = new AttributeColourController(
				benchmarkView, frame);
		controlPalette.addController(attributeColourController);

		controlPalette.addController(new TreeAppearanceController(
				benchmarkView, frame, attributeColourController));

		// Create a tip label painter and its controller
		final BasicLabelPainter tipLabelPainter = new BasicLabelPainter(
				BasicLabelPainter.PainterIntent.TIP);
		tipLabelPainter.setVisible(true);
		tipLabelPainter.setDisplayAttribute("names");
		controlPalette.addController(new LabelPainterController("Tip Labels",
				"tipLabels", tipLabelPainter, frame, attributeColourController,
				benchmarkView));
		viewer.setTipLabelPainter(tipLabelPainter);

		// Create a node label painter and its controller
		final BasicLabelPainter nodeLabelPainter = new BasicLabelPainter(
				BasicLabelPainter.PainterIntent.NODE);
		nodeLabelPainter.setVisible(true);
		nodeLabelPainter.setDisplayAttribute("posterior");
		controlPalette.addController(new LabelPainterController("Node Labels",
				"nodeLabels", nodeLabelPainter, frame,
				attributeColourController, benchmarkView));
		viewer.setNodeLabelPainter(nodeLabelPainter);
	}

	public void setLayout(Layout layout) {
		switch (layout) {
		case RECTILINEAR:
			benchmarkView.setTreeLayout(new RectilinearTreeLayout());
			targetView.setTreeLayout(new RectilinearTreeLayout());
			break;
		case POLAR:
			benchmarkView.setTreeLayout(new PolarTreeLayout());
			targetView.setTreeLayout(new PolarTreeLayout());
			break;
		case RADIAL:
			benchmarkView.setTreeLayout(new RadialTreeLayout());
			targetView.setTreeLayout(new RadialTreeLayout());
			break;
		}
	}

	public void setZoom(int value) {
		benchmarkView.setZoom(value);
		targetView.setZoom(value);
	}

	public void rotateNode() {
		benchmarkView.rotateSelectedNode();
		targetView.rotateSelectedNode();
	}

	@Override
	public void eventRaised(IEvent message) {
		if (message instanceof RotateNodeEvent) {
			rotateNode();
		}
		/*
		 * if (message instanceof PropertyChangedEvent) {
		 * 
		 * PropertyChangedEvent event = (PropertyChangedEvent) message; if
		 * (SELECTED_DISTANCE_METHOD.equals(event.getPropertyName()) ||
		 * SELECTED_TREE_ALGORITHM.equals(event.getPropertyName())) {
		 * requestTree(); } }
		 */
		if (message instanceof TreeReadyEvent) {
			TreeReadyEvent treeEvent = (TreeReadyEvent) message;
			Integer treeAlgorithmIndex = treeEvent.getAlgorithmIndex();
			Integer alignmentId = treeEvent.getAlignmentId();
			Tree createdTree = treeEvent.getTree();
			Integer currentSelectAlgorithm = getSelectedTreeAlgorithmIndex();
			// Check whether this is the current tree algorithm
			if (createdTree != null && treeAlgorithmIndex != null
					&& treeAlgorithmIndex == currentSelectAlgorithm) {
				switch (alignmentId) {
				case AlignmentUtil.BENCHMARK_ALIGNMENT:
					if (benchmarkView != null) {
						benchmarkView.setTree(createdTree);
					}
					break;
				case AlignmentUtil.TARGET_ALIGNMENT:
					if (targetView != null) {
						targetView.setTree(createdTree);
					}
					break;
				default:
					break;
				}
			}
		}
	}

	public void unregisterEvents() {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();

		if (eventManager != null) {
			eventManager.unregister(this);
		}
	}

	public void setAlgorithmComboBox(JComboBox cbAlgorithm) {
		this.cbAlgorithm = cbAlgorithm;
	}

	public void setDistanceMethodComboBox(JComboBox cbDistanceMethod) {
		this.cbDistanceMethod = cbDistanceMethod;
	}

	public void setupCombos() {
		if (cbDistanceMethod != null && cbAlgorithm != null) {
			// Get Tree manager
			AnubisManager manager = AnubisManager.getInstance();
			IResourceManager resourceManager = manager.getResourceManager();
			cbDistanceMethod.removeAllItems();
			if (resourceManager != null) {
				ITreeManager treeManager = (ITreeManager) resourceManager
						.getResource(TREEVIEW_MANAGER_ID);
				if (treeManager != null) {
					populateCombos(treeManager);
				}
				// Set selected items
				Integer distanceMethodIndex = getSelectedDistanceMethodIndex();
				Integer treeAlgorithmIndex = getSelectedTreeAlgorithmIndex();
				distanceMethodIndex = (distanceMethodIndex != null) ? distanceMethodIndex
						: 0;
				treeAlgorithmIndex = (treeAlgorithmIndex != null) ? treeAlgorithmIndex
						: 0;
				setDistanceMethodAlgorithm(distanceMethodIndex);
				setTreeAlgorithm(treeAlgorithmIndex);
				requestTree();
			}
		}
	}

	private Integer getSelectedDistanceMethodIndex() {
		Integer distanceMethodIndex = null;
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settingsManager = manager.getSettingsManager();
		if (settingsManager != null) {
			distanceMethodIndex = (Integer) settingsManager
					.getProperty(SELECTED_DISTANCE_METHOD);
		}
		return distanceMethodIndex;
	}

	private Integer getSelectedTreeAlgorithmIndex() {
		Integer treeAlgorithmIndex = null;
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settingsManager = manager.getSettingsManager();
		if (settingsManager != null) {
			treeAlgorithmIndex = (Integer) settingsManager
					.getProperty(SELECTED_TREE_ALGORITHM);
		}
		return treeAlgorithmIndex;
	}

	private void setDistanceMethodAlgorithm(Integer distanceMethodIndex) {
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settingsManager = manager.getSettingsManager();
		if (settingsManager != null && distanceMethodIndex != null
				&& distanceMethodIndex < cbDistanceMethod.getItemCount()) {
			settingsManager.setProperty(SELECTED_DISTANCE_METHOD,
					distanceMethodIndex);
			cbDistanceMethod.setSelectedIndex(distanceMethodIndex);
		}
	}

	private void setTreeAlgorithm(Integer treeAlgorithmIndex) {
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settingsManager = manager.getSettingsManager();
		if (settingsManager != null && treeAlgorithmIndex != null
				&& treeAlgorithmIndex < cbAlgorithm.getItemCount()) {
			settingsManager.setProperty(SELECTED_TREE_ALGORITHM,
					treeAlgorithmIndex);
			cbAlgorithm.setSelectedIndex(treeAlgorithmIndex);
		}
	}

	public void populateCombos(ITreeManager treeManager) {
		// Get list of distance methods
		List<IDistanceMethodAlgorithm> methods = treeManager
				.getDistanceMethod();
		for (int i = 0; i < methods.size(); i++) {
			IDistanceMethodAlgorithm algorithm = methods.get(i);
			ComboItem item = new ComboItem(algorithm.getName(), i);
			cbDistanceMethod.addItem(item);
		}

		List<ITreeAlgorithm> algorithms = treeManager.getAlgorithms();
		for (int i = 0; i < algorithms.size(); i++) {
			ITreeAlgorithm algorithm = algorithms.get(i);
			ComboItem item = new ComboItem(algorithm.getName(), i);
			cbAlgorithm.addItem(item);
		}
	}

	private void requestTree() {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		if (resourceManager != null) {
			Integer selectedIndex = getSelectedTreeAlgorithmIndex();
			ITreeManager treeManager = (ITreeManager) resourceManager
					.getResource(TREEVIEW_MANAGER_ID);
			if (treeManager != null && selectedIndex != null) {

				List<ITreeAlgorithm> algorithms = treeManager.getAlgorithms();
				if (algorithms != null && selectedIndex < algorithms.size()) {
					ITreeAlgorithm distanceAlgorithm = algorithms
							.get(selectedIndex);
					distanceAlgorithm.mountTree(selectedIndex,
							AlignmentUtil.BENCHMARK_ALIGNMENT);
					distanceAlgorithm.mountTree(selectedIndex,
							AlignmentUtil.TARGET_ALIGNMENT);
				}
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getSource() instanceof JComboBox) {
			JComboBox comboBox = (JComboBox) event.getSource();
			ComboItem selectedItem = (ComboItem) event.getItem();
			if (selectedItem != null) {
				Integer index = selectedItem.getIndex();
				if (comboBox == cbAlgorithm) {
					setTreeAlgorithm(index);
					requestTree();
				}
				if (comboBox == cbDistanceMethod) {
					setDistanceMethodAlgorithm(index);
					requestTree();
				}
			}

		}
	}

}
