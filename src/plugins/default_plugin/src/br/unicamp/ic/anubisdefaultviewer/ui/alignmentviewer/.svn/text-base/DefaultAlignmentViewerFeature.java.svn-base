package br.unicamp.ic.anubisdefaultviewer.ui.alignmentviewer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.ILayerSet;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.LayerSetPosition;

public class DefaultAlignmentViewerFeature implements IFeature {

	private static UUID id = UUID
			.fromString("7ebdb2bc-2939-4f3b-b678-235a62395b90");
	private static UUID featureId = UUID
			.fromString("c5f1755d-35d8-4f50-ae32-869bf4450d37");
	
	private static UUID alignmentDataConnectorInterfaceID = UUID
			.fromString("d13127e4-8a75-4b7a-9066-29ebd6551076");
	
	
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
		requiredInterfaces.add(alignmentDataConnectorInterfaceID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		IUIManager uiManager = manager.getUIManager();

		
		DefaultAlignmentLayer layer = new DefaultAlignmentLayer();
		ILayerSet layerSet = uiManager.getLayerSet(LayerSetPosition.ALIGNMENT);
		layerSet.addTextLayer(layer);
		
	}

}
