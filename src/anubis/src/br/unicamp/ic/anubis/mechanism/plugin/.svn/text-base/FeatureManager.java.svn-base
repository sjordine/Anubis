package br.unicamp.ic.anubis.mechanism.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.plugin.IFeatureManager;
import br.unicamp.ic.anubis.plugin.IPlugin;

public class FeatureManager implements IFeatureManager {

	private static FeatureManager instance = new FeatureManager();

	private HashMap<UUID, IFeature> featureIdDB;
	private List<IFeature> featureList;

	private FeatureManager() {
		featureIdDB = new HashMap<UUID, IFeature>();
		featureList = new ArrayList<IFeature>();
	}

	public void activateFeatures() {

		try {

			HashMap<UUID, List<FeatureNode>> interfaceDB = new HashMap<UUID, List<FeatureNode>>();
			List<FeatureNode> featureNodes = new ArrayList<FeatureNode>();
			List<FeatureNode> sortedNodes;

			createFeatureNodes(interfaceDB, featureNodes);

			buildDependencyGraph(interfaceDB, featureNodes);

			sortedNodes = FeatureSort.sort(featureNodes);

			checkEnabledFeatures(interfaceDB, sortedNodes);

			// TODO Activate features that are enabled
			for (FeatureNode featureNode:sortedNodes){
				IFeature feature = featureNode.getFeature();
				feature.enable();
			}

		} catch (FeatureDependencyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void checkEnabledFeatures(
			HashMap<UUID, List<FeatureNode>> interfaceDB,
			List<FeatureNode> sortedNodes) {
		for (FeatureNode node : sortedNodes) {
			boolean enabled = true;
			IFeature currentFeature = node.getFeature();

			// Check if feature was disabled
			enabled = featureIsEnabled(currentFeature);
			// Check if at least one feature is enabled for each required
			// interface
			if (currentFeature.getRequiredInterfaces() != null
					&& currentFeature.getRequiredInterfaces().size() > 0) {

				for (UUID currentInterface : currentFeature
						.getRequiredInterfaces()) {
					boolean interfaceIsProvided = false;
					if (interfaceDB.containsKey(currentInterface)) {
						List<FeatureNode> providers = interfaceDB
								.get(currentInterface);
						for (int i = 0; i < providers.size()
								&& !interfaceIsProvided; i++) {
							interfaceIsProvided = interfaceIsProvided
									|| providers.get(i).isEnabled();
						}
					}

					enabled = enabled && interfaceIsProvided;
				}
			}

			node.setEnabled(enabled);

		}
	}

	private void buildDependencyGraph(
			HashMap<UUID, List<FeatureNode>> interfaceDB,
			List<FeatureNode> featureNodes) {
		for (FeatureNode node : featureNodes) {
			// Get interfaces
			List<UUID> requiredInterfaces = node.getFeature()
					.getRequiredInterfaces();
			// Associate node with interface providers
			if (requiredInterfaces != null && requiredInterfaces.size() > 0) {

				for (UUID interfaceID : requiredInterfaces) {
					if (interfaceDB.containsKey(interfaceID)) {
						List<FeatureNode> providers = interfaceDB
								.get(interfaceID);
						for (FeatureNode provider : providers) {
							provider.connectTo(node);
						}
					}
				}
			}
		}
	}

	private void createFeatureNodes(
			HashMap<UUID, List<FeatureNode>> interfaceDB,
			List<FeatureNode> featureNodes) {
		for (IFeature feature : featureList) {

			List<FeatureNode> providersList = null;
			UUID interfaceID = feature.getInterfaceID();

			if (interfaceDB.containsKey(interfaceID)) {
				providersList = interfaceDB.get(interfaceID);
			} else {
				providersList = new ArrayList<FeatureNode>();
				interfaceDB.put(interfaceID, providersList);
			}

			FeatureNode node = new FeatureNode(feature);

			featureNodes.add(node);
			providersList.add(node);

		}
	}

	private boolean featureIsEnabled(IFeature feature) {
		// TODO Check whether a feature is enabled or not
		return true;
	}

	@Override
	public void Register(IFeature feature) {
		featureIdDB.put(feature.getId(), feature);
		featureList.add(feature);
	}

	public static FeatureManager getInstance() {
		return instance;
	}

}
