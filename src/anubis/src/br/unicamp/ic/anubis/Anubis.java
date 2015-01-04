package br.unicamp.ic.anubis;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.Session;
import br.unicamp.ic.anubis.mechanism.alignment.AnubisAlignmentManager;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.EventManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.plugin.FeatureManager;
import br.unicamp.ic.anubis.mechanism.plugin.PluginException;
import br.unicamp.ic.anubis.mechanism.plugin.PluginManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.resources.ResourceManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.mechanism.settings.SettingsManager;
import br.unicamp.ic.anubis.plugin.IFeatureManager;
import br.unicamp.ic.anubis.plugin.IPluginManager;
import br.unicamp.ic.anubis.ui.AnubisSplash;
import br.unicamp.ic.anubis.ui.AnubisUIManager;
import br.unicamp.ic.anubis.ui.AnubisView;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.menu.MenuItemDefinition;
import br.unicamp.ic.anubis.ui.settingsdialog.SettingsDialogBuilder;
import br.unicamp.ic.anubis.ui.settingsdialog.SettingsTab;
import br.unicamp.ic.anubis.ui.settingsdialog.SettingsUIDefinition;
import br.unicamp.ic.anubis.ui.settingsdialog.fields.folderfield.FolderField;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.ROOT_MENU;
import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.SETTINGS_DIALOG;
import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.SETTINGS_DIALOG_BUILDER;
import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.GENERAL_SETTINGS_TAB;
import static br.unicamp.ic.anubis.mechanism.settings.DefaultSettingProperties.PLUGIN_PATH_PROPERTY;

public class Anubis {

	/**
	 * @param args
	 * @throws PluginException
	 */
	public static void main(String[] args) throws PluginException {

		AnubisView mainWindow;
		
		AnubisSplash splash = new AnubisSplash();
		splash.setVisible(true);
		

		AnubisManager manager = AnubisManager.getInstance();

		PluginManager pluginManager = PluginManager.getInstance();
		ISettingsManager settingManager = SettingsManager.getInstance();
		IFeatureManager featureManager = FeatureManager.getInstance();
		IResourceManager resourceManager = ResourceManager.getInstance();
		IEventManager eventManager = EventManager.getInstance();
		IUIManager uiManager = AnubisUIManager.getInstance();
		IAlignmentManager alignmentManager = AnubisAlignmentManager
				.getInstance();

		Session session = Session.getInstance();

		manager.setSettingsManager(settingManager);
		manager.setPluginManager(pluginManager);
		manager.setFeatureManager(featureManager);
		manager.setResourceManager(resourceManager);
		manager.setEventManager(eventManager);
		manager.setUIManager(uiManager);
		manager.setSession(session);
		manager.setAlignmentManager(alignmentManager);

		// Start up code
		startUp();

		// TODO: Use settings file
		
		//If plugin path is not defined on settings file, uses the environment variables
		if (settingManager.getProperty(PLUGIN_PATH_PROPERTY) == null){
			//Use environment variable, if available
			String path = System.getenv("ANUBIS_PLUGIN_PATH");

			if (path==null){
				String homePath =  System.getenv("HOME");
				path = homePath +"/plugins";
			}
			
			if (path!=null){
				settingManager.setProperty(PLUGIN_PATH_PROPERTY,
						path);
			}
		}
		
		pluginManager.RegisterPlugins();

		featureManager.activateFeatures();

		mainWindow = new AnubisView();
		
		splash.setVisible(false);

		mainWindow.setVisible(true);

	}

	private static void startUp() {

		MenuItemDefinition menu = new MenuItemDefinition();

		// Set root menu
		IResourceManager resourceManager = ResourceManager.getInstance();
		resourceManager.setResource(ROOT_MENU, menu);

		// Resources
		SettingsUIDefinition settingsDialogDefinition = new SettingsUIDefinition();
		resourceManager.setResource(SETTINGS_DIALOG, settingsDialogDefinition);

		// Add plugin path to settings dialog
		SettingsTab tab = new SettingsTab();
		tab.setName("General");
		tab.setId(GENERAL_SETTINGS_TAB);
		tab.setPriority(100);
		settingsDialogDefinition.addTab(tab);

		resourceManager.setResource(SETTINGS_DIALOG_BUILDER,
				new SettingsDialogBuilder());

		// Add plugin folder property
		FolderField field = new FolderField("Plugin folder",
				PLUGIN_PATH_PROPERTY);
		field.setPriority(100);
		tab.addField(field);
	}

}
