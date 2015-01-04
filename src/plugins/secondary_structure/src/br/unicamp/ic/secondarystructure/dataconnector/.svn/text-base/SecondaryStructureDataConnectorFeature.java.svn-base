package br.unicamp.ic.secondarystructure.dataconnector;

import static br.unicamp.ic.secondarystructure.common.CommonConstants.SECONDARY_STRUCTURE_DATA_CONNECTOR_ID;
import static br.unicamp.ic.secondarystructure.common.CommonConstants.SECONDARY_STRUCTURE_REPOSITORY_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;

public class SecondaryStructureDataConnectorFeature implements IFeature {

	public static final UUID ID = UUID
			.fromString("2bea5cd7-007b-40b3-aeb7-474c11819c4a");

	@Override
	public UUID getId() {
		return ID;
	}

	@Override
	public UUID getInterfaceID() {
		return SECONDARY_STRUCTURE_DATA_CONNECTOR_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(SECONDARY_STRUCTURE_REPOSITORY_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		// Assign data connector to resource database
		AnubisManager anubisManager = AnubisManager.getInstance();
		IResourceManager resourceManager = anubisManager.getResourceManager();
		IEventManager eventManager = anubisManager.getEventManager();

		if (resourceManager != null && eventManager != null) {
			resourceManager.setResource(SECONDARY_STRUCTURE_DATA_CONNECTOR_ID,
					new SecondaryStructureDataConnector());
		}
	}

}
