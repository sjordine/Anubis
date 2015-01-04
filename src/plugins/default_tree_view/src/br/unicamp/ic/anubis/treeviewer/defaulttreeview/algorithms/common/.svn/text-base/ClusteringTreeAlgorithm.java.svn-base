package br.unicamp.ic.anubis.treeviewer.defaulttreeview.algorithms.common;

import static br.unicamp.ic.anubis.treeviewer.common.CommonConstants.SELECTED_DISTANCE_METHOD;
import static br.unicamp.ic.anubis.treeviewer.common.CommonConstants.TREEVIEW_MANAGER_ID;

import java.util.List;

import jebl.evolution.distances.DistanceMatrix;
import jebl.evolution.trees.ClusteringTreeBuilder;
import jebl.evolution.trees.Tree;
import jebl.evolution.trees.TreeBuilderFactory;
import jebl.evolution.trees.TreeBuilderFactory.Method;
import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.treeviewer.event.TreeReadyEvent;
import br.unicamp.ic.anubis.treeviewer.mechanism.IDistanceMethodAlgorithm;
import br.unicamp.ic.anubis.treeviewer.mechanism.ITreeAlgorithm;
import br.unicamp.ic.anubis.treeviewer.mechanism.ITreeManager;

public abstract class ClusteringTreeAlgorithm implements ITreeAlgorithm {

	@Override
	public void mountTree(int index, int alignmentId) {
		Tree tree = null;

		// Get selected distance method
		DistanceMatrix distances = getDistanceMatrix(alignmentId);
		if (distances != null) {
			// Build tree
			@SuppressWarnings("unchecked")
			ClusteringTreeBuilder<Tree> treeBuilder = TreeBuilderFactory
					.getBuilder(getMethod(), distances);
			tree = treeBuilder.build();
		}
		// Send message
		raiseTreeReadyEvent(index, alignmentId, tree);
	}

	protected abstract Method getMethod();

	public DistanceMatrix getDistanceMatrix(int alignmentId) {
		DistanceMatrix returnValue = null;

		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		ISettingsManager settingsManager = manager.getSettingsManager();
		if (resourceManager != null && settingsManager != null) {
			Integer selectedIndex = (Integer) settingsManager
					.getProperty(SELECTED_DISTANCE_METHOD);
			ITreeManager treeManager = (ITreeManager) resourceManager
					.getResource(TREEVIEW_MANAGER_ID);
			if (treeManager != null && selectedIndex != null) {
				List<IDistanceMethodAlgorithm> algorithms = treeManager
						.getDistanceMethod();
				if (algorithms != null && selectedIndex < algorithms.size()) {
					IDistanceMethodAlgorithm distanceAlgorithm = algorithms
							.get(selectedIndex);
					returnValue = distanceAlgorithm.getDistances(alignmentId);
				}
			}
		}
		return returnValue;
	}

	@Override
	public abstract String getName();

	private void raiseTreeReadyEvent(int index, int alignmentId, Tree tree) {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();
		if (eventManager != null) {
			IEvent event = new TreeReadyEvent(index, alignmentId, tree);
			eventManager.raise(event);
		}
	}

}
