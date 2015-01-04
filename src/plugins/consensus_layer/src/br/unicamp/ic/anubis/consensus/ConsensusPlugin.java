package br.unicamp.ic.anubis.consensus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.consensus.dataconnector.ConsensusDataConnectorFeature;
import br.unicamp.ic.anubis.consensus.datarepository.ConsensusDataRepositoryFeature;
import br.unicamp.ic.anubis.consensus.ui.viewer.ConsensusLayerFeature;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.plugin.IPlugin;

public class ConsensusPlugin implements IPlugin {
	
	public static final UUID ID = UUID
			.fromString("123eecb7-aa13-4ed8-99da-4d2e34cc16f5");
	private static final String PLUGIN_NAME = "Consensus";

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

		features.add(new ConsensusDataRepositoryFeature());
		features.add(new ConsensusDataConnectorFeature());
		features.add(new ConsensusLayerFeature());

		return features;
	}

}
