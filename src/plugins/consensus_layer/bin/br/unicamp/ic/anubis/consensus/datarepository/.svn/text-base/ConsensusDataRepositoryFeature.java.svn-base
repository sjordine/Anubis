package br.unicamp.ic.anubis.consensus.datarepository;

import java.util.List;
import java.util.UUID;

import static br.unicamp.ic.anubis.consensus.common.CommonConstants.CONSENSUS_DATAREPOSITORY_INTERFACE_ID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;

public class ConsensusDataRepositoryFeature implements IFeature {

	public static final UUID ID = UUID
			.fromString("1fc5eef3-2002-410b-ba21-f99ad5b90a8d");

	@Override
	public UUID getId() {
		return ID;
	}

	@Override
	public UUID getInterfaceID() {
		// TODO Auto-generated method stub
		return CONSENSUS_DATAREPOSITORY_INTERFACE_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		return null;
	}

	@Override
	public void enable() {
		// Prepare repository
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IEventManager eventManager = manager.getEventManager();

		if (resourceManager != null && eventManager != null) {
			ConsensusDataRepository dataRepository = new ConsensusDataRepository();
			resourceManager.setResource(CONSENSUS_DATAREPOSITORY_INTERFACE_ID,
					dataRepository);
			eventManager.register(AlignmentDataLoadedEvent.class,
					dataRepository);
			eventManager.register(PropertyChangedEvent.class,
					dataRepository);
		}
	}

}
