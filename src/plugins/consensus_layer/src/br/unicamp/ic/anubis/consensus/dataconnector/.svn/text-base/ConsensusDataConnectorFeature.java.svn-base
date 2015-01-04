package br.unicamp.ic.anubis.consensus.dataconnector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;

import static br.unicamp.ic.anubis.consensus.common.CommonConstants.CONSENSUS_DATAREPOSITORY_INTERFACE_ID;
import static br.unicamp.ic.anubis.consensus.common.CommonConstants.CONSENSUS_DATACONNECTOR_FEATURE;

public class ConsensusDataConnectorFeature implements IFeature {

	private static UUID id = UUID
			.fromString("f55c0e2f-52b4-474b-9333-c2f15ebc1f0c");

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		return CONSENSUS_DATACONNECTOR_FEATURE;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(CONSENSUS_DATAREPOSITORY_INTERFACE_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		// Assign data connector to resource database
		AnubisManager anubisManager = AnubisManager.getInstance();
		IResourceManager resourceManager = anubisManager.getResourceManager();
		IEventManager eventManager = anubisManager.getEventManager();

		if (resourceManager != null && eventManager != null) {
			resourceManager.setResource(CONSENSUS_DATACONNECTOR_FEATURE,
					new ConsensusDataConnector());

			ConsensusWatcher watcher = new ConsensusWatcher();
			eventManager.register(AlignmentDataLoadedEvent.class, watcher);
		}
	}

}
