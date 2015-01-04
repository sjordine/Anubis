package br.unicamp.ic.secondarystructure.ui.settings;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.SETTINGS_DIALOG;
import static br.unicamp.ic.secondarystructure.common.CommonConstants.SECONDARY_STRUCTURE_PATH_PROPERTY;
import static br.unicamp.ic.secondarystructure.common.CommonConstants.SECONDARY_STRUCTURE_SETTINGS_FEATURE_ID;
import static br.unicamp.ic.secondarystructure.common.CommonConstants.SECONDARY_STRUCTURE_SETTINGS_TAB;

import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.settingsdialog.SettingsTab;
import br.unicamp.ic.anubis.ui.settingsdialog.SettingsUIDefinition;
import br.unicamp.ic.anubis.ui.settingsdialog.fields.folderfield.FolderField;

public class SecondaryStructureSettingsFeature implements IFeature{
	
	private static UUID id = UUID
			.fromString("2de03b4b-4599-4a55-bc0f-8c74a8f6272f");


	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		return SECONDARY_STRUCTURE_SETTINGS_FEATURE_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		return null;
	}

	@Override
	public void enable() {
		
		//Get related managers
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IEventManager eventManager = manager.getEventManager();
		
		if (resourceManager!=null && eventManager!=null){
			//Get Settings definitions
			SettingsUIDefinition settingsDialogDefinition = (SettingsUIDefinition)resourceManager.getResource(SETTINGS_DIALOG);
			if (settingsDialogDefinition!=null){
				//Create a settings tab for secondary structure
				SettingsTab tab = new SettingsTab();
				tab.setName("Secondary Structure");
				tab.setId(SECONDARY_STRUCTURE_SETTINGS_TAB);
				tab.setPriority(600);
				settingsDialogDefinition.addTab(tab);
				
				//Add folder field
				FolderField fieldPath = new FolderField("Secondary Structure", SECONDARY_STRUCTURE_PATH_PROPERTY);
				fieldPath.setPriority(100);
				tab.addField(fieldPath);
			}
		}
	}

}
