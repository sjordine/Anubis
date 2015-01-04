package br.unicamp.ic.selectionlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.plugin.IPlugin;
import br.unicamp.ic.selectionlayer.eventhandler.SelectionWatcherFeature;
import br.unicamp.ic.selectionlayer.selectionconnector.SelectionDataConnectorFeature;
import br.unicamp.ic.selectionlayer.selectionmanager.SelectionFeature;
import br.unicamp.ic.selectionlayer.ui.DefaultSelectionLayerFeature;

public class SelectionPlugin implements IPlugin {

	public static final UUID ID = UUID.fromString("63bcdcf5-1745-4797-8183-2b80da6a1b73");
	private static final String PLUGIN_NAME="Selection Viewer";
	
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
		
		features.add(new SelectionFeature());
		features.add(new DefaultSelectionLayerFeature());
		features.add(new SelectionDataConnectorFeature());
		features.add(new SelectionWatcherFeature());
				
		return features;
	}

}
