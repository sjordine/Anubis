package br.unicamp.ic.anubis.treeviewer.mechanism;

import java.util.List;

public interface ITreeManager {
	
	public void addAlgorithm(ITreeAlgorithm treeAlgorithm);
	public void addDistanceMethod(IDistanceMethodAlgorithm distanceMethod);
	
	public List<ITreeAlgorithm> getAlgorithms();
	public List<IDistanceMethodAlgorithm> getDistanceMethod();
	
}
