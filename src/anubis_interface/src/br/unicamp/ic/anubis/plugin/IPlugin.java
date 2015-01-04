package br.unicamp.ic.anubis.plugin;

import java.util.List;
import java.util.UUID;

public interface IPlugin {

	public String getName();

	public UUID getId();
	
	public List<IFeature> getFeatures();
	

}
