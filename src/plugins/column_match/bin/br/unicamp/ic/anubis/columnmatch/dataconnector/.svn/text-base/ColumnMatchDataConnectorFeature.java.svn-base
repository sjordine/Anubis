package br.unicamp.ic.anubis.columnmatch.dataconnector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;

public class ColumnMatchDataConnectorFeature implements IFeature {

	private static UUID id = UUID
			.fromString("a252d2bf-e001-456a-8bb6-e528b79fd9cf");
	private static UUID featureId = UUID
			.fromString("d0539b15-600f-4bf5-b7ff-de7cb8e61b6e");

	private static UUID matchDataRepositoryInterfaceID = UUID
			.fromString("9df9b11f-c0b1-48e2-88ee-37fe3911e9e7");

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
		requiredInterfaces.add(matchDataRepositoryInterfaceID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		// Assign data connector to resource database
		AnubisManager anubisManager = AnubisManager.getInstance();
		IResourceManager resourceManager = anubisManager.getResourceManager();
		IEventManager eventManager = anubisManager.getEventManager();

		if (resourceManager != null && eventManager != null) {
			resourceManager
					.setResource(featureId, new ColumnMatchDataConnector());

			ColumnMatchWatcher watcher = new ColumnMatchWatcher();
			eventManager.register(AlignmentDataLoadedEvent.class, watcher);
		}

	}

}
