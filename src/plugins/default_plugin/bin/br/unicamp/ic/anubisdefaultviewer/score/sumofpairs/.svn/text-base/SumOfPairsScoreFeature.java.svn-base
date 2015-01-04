package br.unicamp.ic.anubisdefaultviewer.score.sumofpairs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.settingsdialog.SettingsTab;
import br.unicamp.ic.anubis.ui.settingsdialog.SettingsUIDefinition;
import br.unicamp.ic.anubis.ui.settingsdialog.fields.integerfield.IntegerField;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.GENERAL_SETTINGS_TAB;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SUM_OF_PAIRS_SETTINGS_DIALOG_ID;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.ALIGNMENT_DATA_REPOSITORY_ID;
import static br.unicamp.ic.anubisdefaultviewer.score.sumofpairs.SumOfPairsContants.GAP_PENALTY_PROPERTY;
import static br.unicamp.ic.anubisdefaultviewer.score.sumofpairs.SumOfPairsContants.GAP_GAP_PENALTY_PROPERTY;

public class SumOfPairsScoreFeature implements IFeature {

	private static UUID id = UUID
			.fromString("5ac74828-4687-4538-82ec-5b8ec54ad1c1");
	private static UUID featureId = UUID
			.fromString("1fe48568-5d43-441e-ab9c-ad48bfacacce");
	


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
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(ALIGNMENT_DATA_REPOSITORY_ID);
		return requiredInterfaces;
	}


	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();
		IResourceManager resourceManager = manager.getResourceManager();
		IEventManager eventManager = manager.getEventManager();
		
		SumOfPairsScore scoreMethod = new SumOfPairsScore();
		
		if (alignmentManager!=null){
			alignmentManager.addScoreMethod(scoreMethod);
		}
		
		if (resourceManager!=null){
			//Create settings dialog
			SettingsUIDefinition settingsDialogDefinition = new SettingsUIDefinition();
			resourceManager.setResource(SUM_OF_PAIRS_SETTINGS_DIALOG_ID, settingsDialogDefinition);
			
			// Add plugin path to settings dialog
			SettingsTab tab = new SettingsTab();
			tab.setName("General");
			tab.setId(GENERAL_SETTINGS_TAB);
			tab.setPriority(100);
			settingsDialogDefinition.addTab(tab);
			
			// Add configurable fields
			IntegerField field = new IntegerField("Gap penalty",
					GAP_PENALTY_PROPERTY);
			field.setPriority(100);
			tab.addField(field);
			
			field = new IntegerField("Gap/Gap penalty",
					GAP_GAP_PENALTY_PROPERTY);
			field.setPriority(200);
			tab.addField(field);
		}
		
		if (eventManager!=null){
			eventManager.register(PropertyChangedEvent.class, scoreMethod);
		}
		
	}

}
