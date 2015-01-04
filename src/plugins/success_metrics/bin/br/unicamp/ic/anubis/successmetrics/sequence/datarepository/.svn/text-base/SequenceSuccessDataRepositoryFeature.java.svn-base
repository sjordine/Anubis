package br.unicamp.ic.anubis.successmetrics.sequence.datarepository;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.COLUMN_MATCH_REPOSITORY_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_CALCULATOR_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.SEQUENCE_SUCCESS_REPOSITORY_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;

public class SequenceSuccessDataRepositoryFeature implements IFeature {

	public static final UUID ID = UUID
			.fromString("1fc5eef3-2002-410b-ba21-f99ad5b90a8d");

	@Override
	public UUID getId() {
		return ID;
	}

	@Override
	public UUID getInterfaceID() {
		return SEQUENCE_SUCCESS_REPOSITORY_ID;
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
			SequenceSuccessDataRepository dataRepository = new SequenceSuccessDataRepository();
			resourceManager.setResource(SEQUENCE_SUCCESS_REPOSITORY_ID,
					dataRepository);
			eventManager.register(AlignmentDataLoadedEvent.class,
					dataRepository);
		}
	}

}
