package br.unicamp.ic.anubis.compressedalphabets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.compressedalphabets.dataconnector.CompressedAlphabetDataConnector;
import br.unicamp.ic.anubis.compressedalphabets.dataconnector.CompressedAlphabetDataConnectorFeature;
import br.unicamp.ic.anubis.compressedalphabets.datarepository.CompressedAlphabetDataRepositoryFeature;
import br.unicamp.ic.anubis.compressedalphabets.ui.viewer.CompressedAlphabetLayerFeature;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.plugin.IPlugin;

public class CompressedAlphabetsPlugin implements IPlugin {

	public static final UUID ID = UUID
			.fromString("fa8c105d-4ee1-45d9-bfa6-a4b03e794731");
	private static final String PLUGIN_NAME = "Compressed Alphabet Viewer";

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

		features.add(new CompressedAlphabetDataRepositoryFeature());
		features.add(new CompressedAlphabetDataConnectorFeature());
		features.add(new CompressedAlphabetLayerFeature());

		return features;
	}

}
