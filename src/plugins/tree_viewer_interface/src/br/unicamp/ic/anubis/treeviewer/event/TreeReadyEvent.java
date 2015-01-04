package br.unicamp.ic.anubis.treeviewer.event;

import jebl.evolution.trees.Tree;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;

public class TreeReadyEvent implements IEvent {
	
	private Tree tree;
	private int alignmentId;
	private int algorithmIndex;
	
	public TreeReadyEvent(int index, int alignmentId, Tree tree){
		this.algorithmIndex = index;
		this.alignmentId = alignmentId;
		this.tree = tree;
	}
	
	
	public Tree getTree() {
		return tree;
	}

	public int getAlignmentId() {
		return alignmentId;
	}

	public int getAlgorithmIndex() {
		return algorithmIndex;
	}


}
