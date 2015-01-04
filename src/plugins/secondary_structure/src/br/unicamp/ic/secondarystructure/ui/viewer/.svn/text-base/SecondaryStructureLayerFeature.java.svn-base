package br.unicamp.ic.secondarystructure.ui.viewer;

import static br.unicamp.ic.secondarystructure.common.CommonConstants.SECONDARY_STRUCTURE_DATA_CONNECTOR_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.ILayerSet;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.LayerSetPosition;

public class SecondaryStructureLayerFeature implements IFeature {

	private static UUID id = UUID
			.fromString("7517790d-1658-4bac-b456-b57c3f35d9d9");
	private static UUID featureId = UUID
			.fromString("0d41f172-f8b0-4786-bf53-4073d359d794");

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
		requiredInterfaces.add(SECONDARY_STRUCTURE_DATA_CONNECTOR_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		IUIManager uiManager = manager.getUIManager();
		IEventManager eventManager = manager.getEventManager();

		if (uiManager != null && eventManager != null) {
			SecondaryStructureLayer layer = new SecondaryStructureLayer();
			SecondaryStructureWatcher watcher = new SecondaryStructureWatcher();
			ILayerSet alignmentLayerSet = uiManager
					.getLayerSet(LayerSetPosition.ALIGNMENT);
			alignmentLayerSet.addDrawingLayer(layer);
			eventManager.register(AlignmentDataLoadedEvent.class, watcher);
		}
	}

}
