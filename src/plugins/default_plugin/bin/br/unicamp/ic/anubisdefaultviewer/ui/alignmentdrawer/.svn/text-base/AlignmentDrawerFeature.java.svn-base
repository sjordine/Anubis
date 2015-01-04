package br.unicamp.ic.anubisdefaultviewer.ui.alignmentdrawer;

import java.awt.Color;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.ILayerSet;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.LayerSetPosition;
import br.unicamp.ic.anubis.ui.viewer.ISelectionLayer;

public class AlignmentDrawerFeature implements IFeature {
	
	private static UUID id = UUID
			.fromString("cc6a0ce0-1a7b-4bd4-9d61-193bfddde609");
	private static UUID featureId = UUID
			.fromString("a0f1a503-b9cb-46b5-a60b-2bd884e37406");
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
		return null;
	}
	
	@Override
	public void enable() {
		 AnubisManager anubisManager = AnubisManager.getInstance();
		 IResourceManager resourceManager = anubisManager.getResourceManager();
		 IUIManager uiManager = anubisManager.getUIManager();
		 IAlignmentManager alignmentManager = anubisManager.getAlignmentManager();
		 
		 AlignmentDrawer drawer = new AlignmentDrawer();
		 
		 uiManager.setDrawingStrategy(drawer);
		 resourceManager.setResource(featureId, drawer);
		 
		 uiManager.setBorders(2,2,2,2);
		 ILayerSet layerSet = uiManager.getLayerSet(LayerSetPosition.SEQUENCES);
		 layerSet.setBorders(5, 2, 5, 2);
		 
		 uiManager.setFontSize(14);
		 uiManager.setFontColor(Color.RED);
	}

}
