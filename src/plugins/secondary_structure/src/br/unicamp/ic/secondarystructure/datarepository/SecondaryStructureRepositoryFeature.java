package br.unicamp.ic.secondarystructure.datarepository;

import static br.unicamp.ic.secondarystructure.common.CommonConstants.ALIGNMENT_DATAREPOSITORY_INTERFACE_ID;
import static br.unicamp.ic.secondarystructure.common.CommonConstants.SECONDARY_STRUCTURE_SETTINGS_FEATURE_ID;
import static br.unicamp.ic.secondarystructure.common.CommonConstants.SECONDARY_STRUCTURE_REPOSITORY_ID;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;

public class SecondaryStructureRepositoryFeature implements IFeature {

	private static UUID id = UUID
			.fromString("d27cbf4a-3896-4533-8c52-e4593cb84de3");


	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		return SECONDARY_STRUCTURE_REPOSITORY_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(ALIGNMENT_DATAREPOSITORY_INTERFACE_ID);
		requiredInterfaces.add(SECONDARY_STRUCTURE_SETTINGS_FEATURE_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		IEventHandler repository = new SecondaryStructureDataRepository();
		// Prepare repository
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IEventManager eventManager = manager.getEventManager();
		
		if (resourceManager != null && eventManager != null) {
			resourceManager.setResource(SECONDARY_STRUCTURE_REPOSITORY_ID, repository);
			eventManager.register(AlignmentDataLoadedEvent.class, repository);
			eventManager.register(PropertyChangedEvent.class, repository);
		}
	}

}
