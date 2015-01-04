package br.unicamp.ic.anubis.mechanism.plugin;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.anubis.plugin.IFeature;

class FeatureNode {
	
	private IFeature feature;
	
	private List<FeatureNode> incomingNodes;
	private List<FeatureNode> outgoingNodes;
	
	private boolean enabled;
	private boolean visited;
	
	public FeatureNode(IFeature feature) {
		
		this.feature = feature;
		
		incomingNodes = new ArrayList<FeatureNode>();
		outgoingNodes = new ArrayList<FeatureNode>();
		
		setVisited(false);
	}
	
	public void connectTo(FeatureNode destinationNode) {
		if (destinationNode !=null){
			this.getOutgoingNodes().add(destinationNode);
			destinationNode.getIncomingNodes().add(this);
		}
	}

	public IFeature getFeature() {
		return feature;
	}

	public void setFeature(IFeature feature) {
		this.feature = feature;
	}

	public List<FeatureNode> getIncomingNodes() {
		return incomingNodes;
	}

	public List<FeatureNode> getOutgoingNodes() {
		return outgoingNodes;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

}
