package br.unicamp.ic.anubis.treeviewer.defaulttreeview;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.plugin.IPlugin;
import br.unicamp.ic.anubis.treeviewer.defaulttreeview.algorithms.neighborjoining.NeighborJoiningAlgorithmFeature;
import br.unicamp.ic.anubis.treeviewer.defaulttreeview.algorithms.upgma.UPGMAAlgorithmFeature;
import br.unicamp.ic.anubis.treeviewer.defaulttreeview.simmilarity.IdentityDistanceMatrixFeature;


public class DefaultTreeViewPlugin implements IPlugin {

	public static final UUID ID = UUID
			.fromString("97bc8e44-3d5b-43f5-93a3-a4db670f43eb");
	private static final String PLUGIN_NAME = "Default Tree Viewer";

	@Override
	public String getName() {
		return PLUGIN_NAME;
	}

	@Override
	public UUID getId() {
		return ID;
	}

	@Override
	public List<IFeature> getFeatures() {
		List<IFeature> features = new ArrayList<IFeature>();

		features.add(new IdentityDistanceMatrixFeature());
		features.add(new UPGMAAlgorithmFeature());
		features.add(new NeighborJoiningAlgorithmFeature());
		
		return features;
	}
}
