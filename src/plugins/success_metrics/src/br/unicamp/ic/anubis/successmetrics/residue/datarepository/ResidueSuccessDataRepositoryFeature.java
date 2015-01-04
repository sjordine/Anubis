package br.unicamp.ic.anubis.successmetrics.residue.datarepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.COLUMN_MATCH_REPOSITORY_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_REPOSITORY_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_CALCULATOR_ID;

public class ResidueSuccessDataRepositoryFeature implements IFeature {

	public static final UUID ID = UUID
			.fromString("e6f4ce81-b911-4195-b90e-44a0c9855217");

	@Override
	public UUID getId() {
		return ID;
	}

	@Override
	public UUID getInterfaceID() {
		return RESIDUE_SUCCESS_REPOSITORY_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(COLUMN_MATCH_REPOSITORY_ID);
		requiredInterfaces.add(RESIDUE_SUCCESS_CALCULATOR_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		// Prepare repository
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IEventManager eventManager = manager.getEventManager();

		if (resourceManager != null && eventManager != null) {
			ResidueSuccessDataRepository dataRepository = new ResidueSuccessDataRepository();
			resourceManager.setResource(RESIDUE_SUCCESS_REPOSITORY_ID,
					dataRepository);
			eventManager.register(AlignmentDataLoadedEvent.class,
					dataRepository);
		}
	}

}
