package br.unicamp.ic.anubis.successmetrics.residue.dataconnector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_REPOSITORY_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_DATACONNECTOR_ID;

public class ResidueSuccessDataConnectorFeature implements IFeature {

	private static UUID id = UUID
			.fromString("a252d2bf-e001-456a-8bb6-e528b79fd9cf");

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		return RESIDUE_SUCCESS_DATACONNECTOR_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(RESIDUE_SUCCESS_REPOSITORY_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		// Assign data connector to resource database
		AnubisManager anubisManager = AnubisManager.getInstance();
		IResourceManager resourceManager = anubisManager.getResourceManager();
		IEventManager eventManager = anubisManager.getEventManager();

		if (resourceManager != null && eventManager != null) {
			resourceManager.setResource(RESIDUE_SUCCESS_DATACONNECTOR_ID,
					new ResidueSuccessDataConnector());

			ResidueSuccessWatcher watcher = new ResidueSuccessWatcher();
			eventManager.register(AlignmentDataLoadedEvent.class, watcher);
		}

	}

}
