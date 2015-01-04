package br.unicamp.ic.anubis.admin;

import br.unicamp.ic.anubis.data.Session;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.selection.ISelectionManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.plugin.IFeatureManager;
import br.unicamp.ic.anubis.plugin.IPluginManager;
import br.unicamp.ic.anubis.ui.IUIManager;

public class AnubisManager {
	
	private static AnubisManager instance = new AnubisManager();
	
	private IPluginManager pluginManager;
	private IFeatureManager featureManager;
	private ISettingsManager settingsManager;
	private IResourceManager resourceManager;
	private IEventManager eventManager;
	private IUIManager uiManager;
	private IAlignmentManager alignmentManager;
	private ISelectionManager selectionManager;
	private Session session;
	
	
	public static AnubisManager getInstance(){
		return instance;
	}

	public IPluginManager getPluginManager() {
		return pluginManager;
	}

	public void setPluginManager(IPluginManager pluginManager) {
		this.pluginManager = pluginManager;
	}

	public IFeatureManager getFeatureManager() {
		return featureManager;
	}

	public void setFeatureManager(IFeatureManager featureManager) {
		this.featureManager = featureManager;
	}

	public ISettingsManager getSettingsManager() {
		return settingsManager;
	}

	public void setSettingsManager(ISettingsManager settingsManager) {
		this.settingsManager = settingsManager;
	}

	public IResourceManager getResourceManager() {
		return resourceManager;
	}

	public void setResourceManager(IResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	public IEventManager getEventManager() {
		return eventManager;
	}

	public void setEventManager(IEventManager eventManager) {
		this.eventManager = eventManager;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public IUIManager getUIManager() {
		return uiManager;
	}

	public void setUIManager(IUIManager uiManager) {
		this.uiManager = uiManager;
	}

	public IAlignmentManager getAlignmentManager() {
		return alignmentManager;
	}

	public void setAlignmentManager(IAlignmentManager alignmentManager) {
		this.alignmentManager = alignmentManager;
	}

	public ISelectionManager getSelectionManager() {
		return selectionManager;
	}

	public void setSelectionManager(ISelectionManager selectionManager) {
		this.selectionManager = selectionManager;
	}

}
