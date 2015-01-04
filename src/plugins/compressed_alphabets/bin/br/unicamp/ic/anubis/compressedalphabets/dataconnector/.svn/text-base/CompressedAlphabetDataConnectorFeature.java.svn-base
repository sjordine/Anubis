package br.unicamp.ic.anubis.compressedalphabets.dataconnector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;

import static br.unicamp.ic.anubis.compressedalphabets.util.CommonConstants.COMPRESSED_ALPHABET_INTERFACE_ID;
import static br.unicamp.ic.anubis.compressedalphabets.util.CommonConstants.COMPRESSED_ALPHABET_DATACONNECTOR_INTERFACE_ID;

public class CompressedAlphabetDataConnectorFeature implements IFeature {

	private static UUID id = UUID
			.fromString("4655d723-5b6b-4749-8d62-e80cae4378b7");

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		return COMPRESSED_ALPHABET_DATACONNECTOR_INTERFACE_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(COMPRESSED_ALPHABET_INTERFACE_ID);
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
					.setResource(COMPRESSED_ALPHABET_DATACONNECTOR_INTERFACE_ID, new CompressedAlphabetDataConnector());

			CompressedAlphabetWatcher watcher = new CompressedAlphabetWatcher();
			eventManager.register(AlignmentDataLoadedEvent.class, watcher);
		}

	}

}
