package br.unicamp.ic.anubis.mechanism.plugin;

import java.util.ArrayList;
import java.util.List;

public class FeatureSort {

	public static List<FeatureNode> sort(List<FeatureNode> featureNodes) throws FeatureDependencyException {

		List<FeatureNode> sortedNodes = new ArrayList<FeatureNode>();
		List<FeatureNode> startingNodes = new ArrayList<FeatureNode>();

		for (FeatureNode node : featureNodes) {
			if (node.getIncomingNodes() == null
					|| node.getIncomingNodes().size() == 0) {
				startingNodes.add(node);
			}
		}

		while (startingNodes.size() > 0) {
			// remove a node from the inspection list
			FeatureNode currentNode = startingNodes.get(0);
			startingNodes.remove(0);
			// add to topological sorted list
			sortedNodes.add(currentNode);
			for (FeatureNode destinationNode : currentNode.getOutgoingNodes()) {
				destinationNode.getIncomingNodes().remove(currentNode);
				if (destinationNode.getIncomingNodes().size() == 0) {
					startingNodes.add(destinationNode);
				}
			}
		}

		// Check if there are edges in the graph
		// in this case there is at least one cycle into the dependency graph
		boolean cyclicGraph = false;
		for (int i = 0; i < featureNodes.size() && !cyclicGraph; i++) {
			cyclicGraph = cyclicGraph
					|| (featureNodes.get(i).getIncomingNodes().size() > 0);
		}
		
		if (cyclicGraph){
			//There are cyclical dependecies between the features
			//an exception is launched to indicate it
			throw new FeatureDependencyException("There is at least one cyclical dependency between the features");
		}
		

		return sortedNodes;
	}

}
