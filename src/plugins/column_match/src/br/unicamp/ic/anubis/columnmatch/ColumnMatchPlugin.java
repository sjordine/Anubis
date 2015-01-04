package br.unicamp.ic.anubis.columnmatch;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.columnmatch.dataconnector.ColumnMatchDataConnectorFeature;
import br.unicamp.ic.anubis.columnmatch.datarepository.ColumnMatchDataRepository;
import br.unicamp.ic.anubis.columnmatch.datarepository.ColumnMatchDataRepositoryFeature;
import br.unicamp.ic.anubis.columnmatch.ui.viewer.ColumnMatchLayerFeature;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.plugin.IPlugin;


public class ColumnMatchPlugin implements IPlugin {
	
	public static final UUID ID = UUID.fromString("640dd35d-96f5-44c1-9162-b5793e8d4045");
	private static final String PLUGIN_NAME="Column Match Viewer";
	
	
	

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
		
		features.add(new ColumnMatchDataRepositoryFeature());
		features.add(new ColumnMatchDataConnectorFeature());
		features.add(new ColumnMatchLayerFeature());
		
		return features;
	}

}
