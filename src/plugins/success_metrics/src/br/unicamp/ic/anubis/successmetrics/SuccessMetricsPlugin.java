package br.unicamp.ic.anubis.successmetrics;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.plugin.IPlugin;
import br.unicamp.ic.anubis.successmetrics.residue.dataconnector.ResidueSuccessDataConnectorFeature;
import br.unicamp.ic.anubis.successmetrics.residue.datarepository.ResidueSuccessDataRepositoryFeature;
import br.unicamp.ic.anubis.successmetrics.residue.residuesuccess.ResidueSuccessFeature;
import br.unicamp.ic.anubis.successmetrics.residue.ui.viewer.ResidueSuccessLayerFeature;
import br.unicamp.ic.anubis.successmetrics.sequence.dataconnector.SequenceSuccessDataConnectorFeature;
import br.unicamp.ic.anubis.successmetrics.sequence.datarepository.SequenceSuccessDataRepositoryFeature;
import br.unicamp.ic.anubis.successmetrics.sequence.ui.viewer.SequenceSuccessLayerFeature;
import br.unicamp.ic.anubis.successmetrics.ui.viewer.SuccessMetricsLayerFeature;

public class SuccessMetricsPlugin implements IPlugin {

	public static final UUID ID = UUID
			.fromString("70d7dbc9-5aca-4089-a0dd-d338a497a33a");
	private static final String PLUGIN_NAME = "Success Metrics";

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

		features.add(new ResidueSuccessDataRepositoryFeature());
		features.add(new ResidueSuccessDataConnectorFeature());
		features.add(new ResidueSuccessLayerFeature());
		features.add(new ResidueSuccessFeature());
		features.add(new SequenceSuccessDataRepositoryFeature());
		features.add(new SequenceSuccessDataConnectorFeature());
		features.add(new SequenceSuccessLayerFeature());
		features.add(new SuccessMetricsLayerFeature());
		return features;
	}

}
