package br.unicamp.ic.anubis.treeviewer.mechanism;

import jebl.evolution.distances.DistanceMatrix;

public interface IDistanceMethodAlgorithm {
	
	public boolean isLoaded(int alignmentId);
	public String[] getSequences(int alignmentId);
	public DistanceMatrix getDistances(int alignmentId);
	public String getName();

}
