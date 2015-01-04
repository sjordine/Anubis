package br.unicamp.ic.anubis.successmetrics.residue.residuesuccess;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.COLUMN_MATCH_REPOSITORY_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_CALCULATOR_ID;

public class ResidueSuccessFeature implements IFeature {

	@Override
	public UUID getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID getInterfaceID() {
		return RESIDUE_SUCCESS_CALCULATOR_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(COLUMN_MATCH_REPOSITORY_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		// Prepare repository
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IEventManager eventManager = manager.getEventManager();

		if (resourceManager != null && eventManager != null) {
			ResidueSuccess residueSuccessCalculator = new ResidueSuccess();
			resourceManager.setResource(RESIDUE_SUCCESS_CALCULATOR_ID,
					residueSuccessCalculator);
			eventManager.register(AlignmentDataLoadedEvent.class,
					new ResidueSuccessWatcher());
			eventManager.register(PropertyChangedEvent.class,
					new ResidueSuccessWatcher());
		}
	}

}
