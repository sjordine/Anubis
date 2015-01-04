package br.unicamp.ic.secondarystructure;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.plugin.IPlugin;
import br.unicamp.ic.secondarystructure.dataconnector.SecondaryStructureDataConnectorFeature;
import br.unicamp.ic.secondarystructure.datarepository.SecondaryStructureRepositoryFeature;
import br.unicamp.ic.secondarystructure.ui.settings.SecondaryStructureSettingsFeature;
import br.unicamp.ic.secondarystructure.ui.viewer.SecondaryStructureLayerFeature;

public class SecondaryStructureLayerPlugin implements IPlugin {
	
	public static final UUID ID = UUID.fromString("9154b28c-5352-4821-80ec-83585e4fee28");
	private static final String PLUGIN_NAME="Secondary Structure Viewer";

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

		features.add(new SecondaryStructureSettingsFeature());
		features.add(new SecondaryStructureRepositoryFeature());
		features.add(new SecondaryStructureDataConnectorFeature());
		features.add(new SecondaryStructureLayerFeature());
		
		return features;
	}

}
