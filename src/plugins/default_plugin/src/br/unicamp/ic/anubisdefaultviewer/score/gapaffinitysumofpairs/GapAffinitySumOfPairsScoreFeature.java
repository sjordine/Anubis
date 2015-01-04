package br.unicamp.ic.anubisdefaultviewer.score.gapaffinitysumofpairs;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.GENERAL_SETTINGS_TAB;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.ALIGNMENT_DATA_REPOSITORY_ID;
import static br.unicamp.ic.anubisdefaultviewer.score.gapaffinitysumofpairs.GapAffinitySumOfPairsConstants.GAP_AFFINITY_SUM_OF_PAIRS_SETTINGS_DIALOG_ID;
import static br.unicamp.ic.anubisdefaultviewer.score.gapaffinitysumofpairs.GapAffinitySumOfPairsConstants.GAP_GAP_PENALTY_PROPERTY;
import static br.unicamp.ic.anubisdefaultviewer.score.gapaffinitysumofpairs.GapAffinitySumOfPairsConstants.OPEN_GAP_PENALTY;
import static br.unicamp.ic.anubisdefaultviewer.score.gapaffinitysumofpairs.GapAffinitySumOfPairsConstants.EXTEND_GAP_PENALTY;


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
import br.unicamp.ic.anubisdefaultviewer.score.sumofpairs.SumOfPairsScore;

public class GapAffinitySumOfPairsScoreFeature implements IFeature{
	private static UUID id = UUID
			.fromString("1a3a2748-3561-469a-80cc-abff9a0d65b6");
	private static UUID featureId = UUID
			.fromString("61f36b46-4ea2-47b0-a393-d3792cbac06b");
	


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
		
		GapAffinitySumOfPairsScore scoreMethod = new GapAffinitySumOfPairsScore();
		
		if (alignmentManager!=null){
			alignmentManager.addScoreMethod(scoreMethod);
		}
		
		if (resourceManager!=null){
			//Create settings dialog
			SettingsUIDefinition settingsDialogDefinition = new SettingsUIDefinition();
			resourceManager.setResource(GAP_AFFINITY_SUM_OF_PAIRS_SETTINGS_DIALOG_ID, settingsDialogDefinition);
			
			// Add plugin path to settings dialog
			SettingsTab tab = new SettingsTab();
			tab.setName("General");
			tab.setId(GENERAL_SETTINGS_TAB);
			tab.setPriority(100);
			settingsDialogDefinition.addTab(tab);
			
			// Add configurable fields
			IntegerField field = new IntegerField("Open gap penalty",
					OPEN_GAP_PENALTY);
			field.setPriority(100);
			tab.addField(field);
			
			// Add configurable fields
			field = new IntegerField("Extend gap penalty",
					EXTEND_GAP_PENALTY);
			field.setPriority(200);
			tab.addField(field);
			
			field = new IntegerField("Gap/Gap penalty",
					GAP_GAP_PENALTY_PROPERTY);
			field.setPriority(300);
			tab.addField(field);
		}
		
		if (eventManager!=null){
			eventManager.register(PropertyChangedEvent.class, scoreMethod);
		}
		
	}

}
