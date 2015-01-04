package br.unicamp.ic.anubis.successmetrics.sequence.dataconnector;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.SEQUENCE_SUCCESS_REPOSITORY_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.SEQUENCE_SUCCESS_DATACONNECTOR_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;


public class SequenceSuccessDataConnectorFeature implements IFeature {

	private static UUID id = UUID
			.fromString("61e4ced1-4a7e-4e61-b28b-c34120e27f28");

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		return SEQUENCE_SUCCESS_DATACONNECTOR_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(SEQUENCE_SUCCESS_REPOSITORY_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		// Assign data connector to resource database
		AnubisManager anubisManager = AnubisManager.getInstance();
		IResourceManager resourceManager = anubisManager.getResourceManager();
		IEventManager eventManager = anubisManager.getEventManager();

		if (resourceManager != null && eventManager != null) {
			resourceManager.setResource(SEQUENCE_SUCCESS_DATACONNECTOR_ID,
					new SequenceSuccessDataConnector());

			SequenceSuccessWatcher watcher = new SequenceSuccessWatcher();
			eventManager.register(AlignmentDataLoadedEvent.class, watcher);
		}

	}

}
