package br.unicamp.ic.anubisdefaultviewer.score.settings;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.SETTINGS_DIALOG;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SCORE_SETTINGS_TAB;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.MATRIX_PATH_PROPERTY;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SELECTED_MATRIX_INDEX_PROPERTY;

import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.settingsdialog.SettingsTab;
import br.unicamp.ic.anubis.ui.settingsdialog.SettingsUIDefinition;
import br.unicamp.ic.anubis.ui.settingsdialog.fields.folderfield.FolderField;
import br.unicamp.ic.anubis.ui.settingsdialog.fields.selectionfield.SelectionField;
import br.unicamp.ic.anubisdefaultviewer.score.matrixloader.MatrixLoaderWatcher;

public class ScoreSettingsFeature implements IFeature {

	private static UUID id = UUID
			.fromString("d679d6d2-abb3-4a3c-b9e9-571aab690b51");
	private static UUID featureId = UUID
			.fromString("921d6e2e-ca2e-4147-96f3-479818cffc49");
	


	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		return featureId;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		return null;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IEventManager eventManager = manager.getEventManager();
		
		if (resourceManager!=null && eventManager!=null){
			SettingsUIDefinition settingsDialogDefinition = (SettingsUIDefinition)resourceManager.getResource(SETTINGS_DIALOG);
			if (settingsDialogDefinition!=null){
				SettingsTab tab = new SettingsTab();
				tab.setName("Scoring");
				tab.setId(SCORE_SETTINGS_TAB);
				tab.setPriority(300);
				settingsDialogDefinition.addTab(tab);
				
				//Add fields
				FolderField fieldPath = new FolderField("Matrix folder", MATRIX_PATH_PROPERTY);
				fieldPath.setPriority(100);
				tab.addField(fieldPath);
				
				SelectionField selectionField = new SelectionField("Matrix", SELECTED_MATRIX_INDEX_PROPERTY, new ScoreMatrixProvider());
				selectionField.setPriority(200);
				tab.addField(selectionField);
				
				//Add listener
				eventManager.register(PropertyChangedEvent.class, new MatrixLoaderWatcher());
			}
		}
		
	}

}
