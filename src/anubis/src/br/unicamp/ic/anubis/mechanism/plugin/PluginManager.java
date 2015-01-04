package br.unicamp.ic.anubis.mechanism.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.settings.SettingsManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.plugin.IFeatureManager;
import br.unicamp.ic.anubis.plugin.IPlugin;
import br.unicamp.ic.anubis.plugin.IPluginManager;

import static br.unicamp.ic.anubis.mechanism.settings.DefaultSettingProperties.PLUGIN_PATH_PROPERTY;

public class PluginManager implements IPluginManager {

	private static PluginManager instance = new PluginManager();
	private HashMap<String, IPlugin> pluginNameDB;
	private HashMap<UUID, IPlugin> pluginIdDB;
	private List<IPlugin> pluginList;

	private PluginManager() {
		pluginNameDB = new HashMap<String, IPlugin>();
		pluginIdDB = new HashMap<UUID, IPlugin>();
		pluginList = new ArrayList<IPlugin>();
	}

	public void RegisterPlugins() throws PluginException {

		// Get plugin path from settings
		SettingsManager settings = SettingsManager.getInstance();
		String pluginPath = (String) settings.getProperty(PLUGIN_PATH_PROPERTY);

		if (pluginPath != null) {
			// Get plugin list
			PluginFinder crawler = new PluginFinder();
			crawler.search(pluginPath);
			List<IPlugin> loadedPlugins = crawler.getPluginCollection();

			// Register Plugins
			for (IPlugin plugin : loadedPlugins) {
				Register(plugin);
				// TODO: notify plugin registering
			}
		}

		// Register features for each plugin
		AnubisManager manager = AnubisManager.getInstance();
		IFeatureManager featureManager = manager.getFeatureManager();

		for (IPlugin plugin : pluginList) {
			// TODO: Check if plugin is enabled
			// Register plugin features
			List<IFeature> pluginFeatures = plugin.getFeatures();
			if (pluginFeatures != null) {
				for (IFeature feature : pluginFeatures) {
					featureManager.Register(feature);
				}
			}
		}

	}

	@Override
	public void Register(IPlugin plugin) {
		pluginNameDB.put(plugin.getName(), plugin);
		pluginIdDB.put(plugin.getId(), plugin);
		pluginList.add(plugin);
	}

	public static PluginManager getInstance() {
		return instance;
	}

}
