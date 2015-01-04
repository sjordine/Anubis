package br.unicamp.ic.selectionlayer.eventhandler;

import static br.unicamp.ic.selectionlayer.common.CommonConstants.SELECTION_MANAGER_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.ColumnSelectedEvent;
import br.unicamp.ic.anubis.event.RowSelectedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.plugin.IFeature;

public class SelectionWatcherFeature implements IFeature {
	
	private static UUID id = UUID
			.fromString("709363b2-0c23-48ce-a8c3-7dd4247f24be");
	private static UUID featureId = UUID
			.fromString("8e4c1aa4-3a6b-45c3-a9f6-be97bd05137c");


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
		requiredInterfaces.add(SELECTION_MANAGER_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();
		if (eventManager!=null){
			SelectionWatcher watcher = new SelectionWatcher();
			eventManager.register(ColumnSelectedEvent.class, watcher);
			eventManager.register(RowSelectedEvent.class, watcher);
			eventManager.register(AlignmentDataLoadedEvent.class, watcher);
		}
	}

}
