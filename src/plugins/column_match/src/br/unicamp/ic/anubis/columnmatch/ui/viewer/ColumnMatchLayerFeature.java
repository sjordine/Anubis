package br.unicamp.ic.anubis.columnmatch.ui.viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.ILayerSet;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.LayerSetPosition;

public class ColumnMatchLayerFeature implements IFeature {

	private static UUID id = UUID
			.fromString("34b81e39-22a1-45f0-8276-13507303c0d1");
	private static UUID featureId = UUID
			.fromString("4b8433a9-c352-4630-85f0-de62fc15378c");
	
	private static final UUID COLUMN_MATCH_DATA_REPOSITORY_ID = UUID
			.fromString("9df9b11f-c0b1-48e2-88ee-37fe3911e9e7");
	
	
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
		requiredInterfaces.add(COLUMN_MATCH_DATA_REPOSITORY_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		IUIManager uiManager = manager.getUIManager();

		
		ColumnMatchLayer layer = new ColumnMatchLayer();
		ILayerSet alignmentLayerSet = uiManager.getLayerSet(LayerSetPosition.ALIGNMENT);
		alignmentLayerSet.addDrawingLayer(layer);
		
	}

}
