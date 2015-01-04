package br.unicamp.ic.anubisdefaultviewer.ui.statusbar.score;

import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.AlignmentScoreChangeEvent;
import br.unicamp.ic.anubis.event.AlignmentScoreMethodsChanged;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.plugin.IFeature;

public class ScoreStatusBarFeature implements IFeature {

	private static UUID id = UUID
			.fromString("7437feaa-4986-4928-9caf-2d5cd19a1289");
	private static UUID featureId = UUID
			.fromString("81047152-f102-4a14-a968-2cdc05bdef00");
	


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
		ScoreStatusBar statusBarSection = new ScoreStatusBar();
		statusBarSection.create();
		
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();
		
		if (eventManager!=null){
			eventManager.register(AlignmentScoreMethodsChanged.class,statusBarSection);
			eventManager.register(AlignmentScoreChangeEvent.class,statusBarSection);
			eventManager.register(AlignmentDataLoadedEvent.class,statusBarSection);
		}
		
	}

}
