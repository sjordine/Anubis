package br.unicamp.ic.anubisdefaultviewer.ui.columnViewer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.ILayerSet;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.LayerSetPosition;

import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.ALIGNMENT_DATA_CONNECTOR_FEATURE;

public class DefaultColumnLayerFeature implements IFeature {
	private static UUID id = UUID
			.fromString("04b8d66d-b99e-466d-89cd-ca8bf0c0f556");
	private static UUID featureId = UUID
			.fromString("2977b319-23c2-4bac-8611-b12a9a3b1d0a");
	

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
		requiredInterfaces.add(ALIGNMENT_DATA_CONNECTOR_FEATURE);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		IUIManager uiManager = manager.getUIManager();
		
		DefaultColumnLayer layer = new DefaultColumnLayer();
		
		ILayerSet layerSet = uiManager.getLayerSet(LayerSetPosition.COLUMNS);
		layerSet.addTextLayer(layer);
		
	}
}
