package br.unicamp.ic.anubis.mechanism.settings;

public interface ISettingsManager {
	
	
	public void setProperty(String propertyName, Object property);
		
	public Object getProperty(String propertyName);
		

}
