package br.unicamp.ic.anubis.treeviewer.mechanism;

import java.util.ArrayList;
import java.util.List;

public class TreeViewManager implements ITreeManager {

	private static TreeViewManager instance = new TreeViewManager();

	private List<ITreeAlgorithm> treeAlgorithms;
	private List<IDistanceMethodAlgorithm> distanceAlgorithms;

	private int selectedAlgorithm;
	private int selectedDistanceMethod;

	private TreeViewManager() {
		treeAlgorithms = new ArrayList<ITreeAlgorithm>();
		distanceAlgorithms = new ArrayList<IDistanceMethodAlgorithm>();

		selectedAlgorithm = -1;
		selectedDistanceMethod = -1;
	}

	@Override
	public void addAlgorithm(ITreeAlgorithm treeAlgorithm) {
		if (treeAlgorithm != null) {
			treeAlgorithms.add(treeAlgorithm);
			if (selectedAlgorithm == -1) {
				selectedAlgorithm = 0;
			}
		}
	}

	@Override
	public void addDistanceMethod(IDistanceMethodAlgorithm distanceMethod) {
		if (distanceMethod != null) {
			distanceAlgorithms.add(distanceMethod);
			if (selectedDistanceMethod == -1) {
				selectedDistanceMethod = 0;
			}
		}
	}

	@Override
	public List<ITreeAlgorithm> getAlgorithms() {
		return treeAlgorithms;
	}

	@Override
	public List<IDistanceMethodAlgorithm> getDistanceMethod() {
		return distanceAlgorithms;
	}

	public static TreeViewManager getInstance() {
		return instance;
	}

}
