package br.unicamp.ic.anubis.treeviewer.defaulttreeview.algorithms.upgma;

import java.util.List;

import jebl.evolution.distances.DistanceMatrix;
import jebl.evolution.trees.ClusteringTreeBuilder;
import jebl.evolution.trees.Tree;
import jebl.evolution.trees.TreeBuilderFactory;
import jebl.evolution.trees.TreeBuilderFactory.Method;
import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.treeviewer.defaulttreeview.algorithms.common.ClusteringTreeAlgorithm;
import br.unicamp.ic.anubis.treeviewer.event.TreeReadyEvent;
import br.unicamp.ic.anubis.treeviewer.mechanism.IDistanceMethodAlgorithm;
import br.unicamp.ic.anubis.treeviewer.mechanism.ITreeAlgorithm;
import br.unicamp.ic.anubis.treeviewer.mechanism.ITreeManager;

import static br.unicamp.ic.anubis.treeviewer.common.CommonConstants.TREEVIEW_MANAGER_ID;
import static br.unicamp.ic.anubis.treeviewer.common.CommonConstants.SELECTED_DISTANCE_METHOD;

public class UPGMAAlgorithm extends ClusteringTreeAlgorithm {

	@Override
	protected Method getMethod() {
		return Method.UPGMA;
	}

	@Override
	public String getName() {
		return "UPGMA";
	}

	
}
