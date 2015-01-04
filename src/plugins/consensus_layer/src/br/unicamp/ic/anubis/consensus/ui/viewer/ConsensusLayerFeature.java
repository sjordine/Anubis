package br.unicamp.ic.anubis.consensus.ui.viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.ILayerSet;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.LayerSetPosition;

import static br.unicamp.ic.anubis.consensus.common.CommonConstants.CONSENSUS_DATAREPOSITORY_INTERFACE_ID;

public class ConsensusLayerFeature implements IFeature {
	
	private static UUID id = UUID
			.fromString("ebdcb1ff-4d83-44bf-b0e8-e4a1ecb24162");
	private static UUID featureId = UUID
			.fromString("af89527c-6b50-4762-b7fa-53fff9d4c1c3");
	

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		return featureId;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(CONSENSUS_DATAREPOSITORY_INTERFACE_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		IUIManager uiManager = manager.getUIManager();
		
		ConsensusLayer layer = new ConsensusLayer();
		
		ILayerSet layerSet = uiManager.getLayerSet(LayerSetPosition.BOTTOM);
		layerSet.addTextLayer(layer);
	}

}
