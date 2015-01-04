package br.unicamp.ic.anubis.columnmatch.datarepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;

public class ColumnMatchDataRepositoryFeature implements IFeature {

	public static final UUID ID = UUID
			.fromString("b755573b-e111-4b63-b85e-c386247351ab");
	private static final UUID INTERFACE_ID = UUID
			.fromString("9df9b11f-c0b1-48e2-88ee-37fe3911e9e7");

	private static UUID alignmentDataRepositoryInterfaceID = UUID
			.fromString("a663f847-7aa8-4714-9d91-810ac40a7fbd");

	@Override
	public UUID getId() {
		return ID;
	}

	@Override
	public UUID getInterfaceID() {
		return INTERFACE_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(alignmentDataRepositoryInterfaceID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		// Clean isLoaded flag

		// Prepare repository
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IEventManager eventManager = manager.getEventManager();

		if (resourceManager != null && eventManager!=null) {
			ColumnMatchDataRepository dataRepository = new ColumnMatchDataRepository();
			resourceManager.setResource(INTERFACE_ID, dataRepository);
			eventManager.register(AlignmentDataLoadedEvent.class, dataRepository);
		}
	}

}
