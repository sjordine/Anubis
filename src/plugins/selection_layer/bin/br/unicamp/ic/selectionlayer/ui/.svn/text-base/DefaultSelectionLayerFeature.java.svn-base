package br.unicamp.ic.selectionlayer.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.ILayerSet;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.LayerSetPosition;

import static br.unicamp.ic.selectionlayer.common.CommonConstants.SELECTION_DATA_CONNECTOR_ID;

public class DefaultSelectionLayerFeature implements IFeature {
	
	private static UUID id = UUID
			.fromString("ff45203a-124b-4431-a25a-a44a99f7f74f");
	private static UUID featureId = UUID
			.fromString("e4ce92cf-2513-46f5-896a-5c73f6a8b46b");

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
		requiredInterfaces.add(SELECTION_DATA_CONNECTOR_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		// Assign data connector to resource database
		AnubisManager anubisManager = AnubisManager.getInstance();
		IUIManager uiManager = anubisManager.getUIManager();
		
		if (uiManager!=null){
			ILayerSet alignmentLayerSet = uiManager.getLayerSet(LayerSetPosition.ALIGNMENT);
			alignmentLayerSet.setSelectionLayer(new DefaultSelectionLayer());
		}
		
	}

}
