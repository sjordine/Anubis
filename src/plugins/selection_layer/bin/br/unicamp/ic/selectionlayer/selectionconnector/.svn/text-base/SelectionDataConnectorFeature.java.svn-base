package br.unicamp.ic.selectionlayer.selectionconnector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;

import static br.unicamp.ic.selectionlayer.common.CommonConstants.SELECTION_MANAGER_ID;
import static br.unicamp.ic.selectionlayer.common.CommonConstants.SELECTION_DATA_CONNECTOR_ID;

public class SelectionDataConnectorFeature implements IFeature {
	private static UUID id = UUID
			.fromString("6da9b031-e0c8-442a-a9d6-6c9231a06552");

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		return SELECTION_DATA_CONNECTOR_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(SELECTION_MANAGER_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		// Assign data connector to resource database
		AnubisManager anubisManager = AnubisManager.getInstance();
		IResourceManager resourceManager = anubisManager.getResourceManager();

		if (resourceManager != null) {
			resourceManager
					.setResource(SELECTION_DATA_CONNECTOR_ID, new SelectionDataConnector());

		}

	}
}
