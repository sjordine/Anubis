package br.unicamp.ic.anubis.treeviewer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.plugin.IPlugin;
import br.unicamp.ic.anubis.treeviewer.ui.menu.TreeViewDialogFeature;

public class TreeViewPlugin implements IPlugin {

	public static final UUID ID = UUID
			.fromString("e8590148-6829-4222-a979-176242f68551");
	private static final String PLUGIN_NAME = "Tree Viewer";

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

		features.add(new TreeViewDialogFeature());

		return features;
	}

}
