package br.unicamp.ic.anubisdefaultviewer.ui.sequenceViewer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.ILayerSet;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.LayerSetPosition;
import br.unicamp.ic.anubisdefaultviewer.ui.columnViewer.DefaultColumnLayer;

public class DefaultSequenceLayerFeature implements IFeature {

	private static UUID id = UUID
			.fromString("cb74a234-dc6a-4e76-bb79-ca7fc40d6b03");
	private static UUID featureId = UUID
			.fromString("1cc56f7e-e1e3-4be3-893b-fd298b565248");
	
	//TODO: use the proper interface
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
		
		DefaultSequenceLayer layer = new DefaultSequenceLayer();
		ILayerSet layerSet = uiManager.getLayerSet(LayerSetPosition.SEQUENCES);
		layerSet.addTextLayer(layer);
	}

}
