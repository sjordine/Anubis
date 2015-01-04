package br.unicamp.ic.anubisdefaultviewer.dataconnector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;


public class AlignmentDataConnectorFeature implements IFeature {

	private static UUID id = UUID
			.fromString("5661d02b-5e76-48fa-923b-be2ab40e609b");
	private static UUID featureId = UUID
			.fromString("d13127e4-8a75-4b7a-9066-29ebd6551076");

	private static UUID alignmentDataRepositoryInterfaceID = UUID
			.fromString("a663f847-7aa8-4714-9d91-810ac40a7fbd");

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
		requiredInterfaces.add(alignmentDataRepositoryInterfaceID);
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
					.setResource(featureId, new AlignmentDataConnector());

			AlignmentWatcher watcher = new AlignmentWatcher();
			eventManager.register(AlignmentDataLoadedEvent.class, watcher);
		}

	}

}
