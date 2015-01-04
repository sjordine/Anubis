package br.unicamp.ic.anubis.treeviewer.defaulttreeview.algorithms.neighborjoining;

import jebl.evolution.trees.TreeBuilderFactory.Method;
import br.unicamp.ic.anubis.treeviewer.defaulttreeview.algorithms.common.ClusteringTreeAlgorithm;

public class NeighborJoiningAlgorithm extends ClusteringTreeAlgorithm {

	@Override
	protected Method getMethod() {
		return Method.NEIGHBOR_JOINING;
	}

	@Override
	public String getName() {
		return "NEIGHBOR JOINING";
	}

}
