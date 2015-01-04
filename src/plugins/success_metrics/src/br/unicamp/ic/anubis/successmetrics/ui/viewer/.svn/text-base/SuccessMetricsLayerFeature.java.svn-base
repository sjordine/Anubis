package br.unicamp.ic.anubis.successmetrics.ui.viewer;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.successmetrics.ui.settings.menu.SuccessMetricsMenuAction;
import br.unicamp.ic.anubis.ui.ILayerSet;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.LayerSetPosition;
import br.unicamp.ic.anubis.ui.menu.MenuItemDefinition;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_DATACONNECTOR_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.SEQUENCE_SUCCESS_DATACONNECTOR_ID;

public class SuccessMetricsLayerFeature implements IFeature {

	private static UUID id = UUID
			.fromString("37fb9a0b-8ba2-438b-9e9a-a45352e41cbe");
	private static UUID featureId = UUID
			.fromString("a79cc9c6-23b7-4b6e-9beb-548a2ac62083");

	private static UUID pluginMenuId = UUID
			.fromString("56679cd7-6d91-4977-bac2-95893032e1e4");

	private static UUID successMetricsMenuId = UUID
			.fromString("604b7699-4e66-455e-87ed-c1181829a799");

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
		requiredInterfaces.add(RESIDUE_SUCCESS_DATACONNECTOR_ID);
		requiredInterfaces.add(SEQUENCE_SUCCESS_DATACONNECTOR_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		IUIManager uiManager = manager.getUIManager();
		IResourceManager resourceManager = manager.getResourceManager();
		IEventManager eventManager = manager.getEventManager();

		if (uiManager != null) {
			SuccessMetricsLayer layer = new SuccessMetricsLayer();
			ILayerSet alignmentLayerSet = uiManager.getLayerSet(LayerSetPosition.ALIGNMENT);
			alignmentLayerSet.addDrawingLayer(layer);
			if (eventManager!=null){
				eventManager.register(PropertyChangedEvent.class, layer);
			}
		}

		// Set menu
		if (resourceManager != null) {
			MenuItemDefinition baseMenu = (MenuItemDefinition) resourceManager
					.getResource(pluginMenuId);
			if (baseMenu != null) {
				MenuItemDefinition menuItem = new MenuItemDefinition();
				menuItem.setText("Success Metrics");
				menuItem.setOrder(600);
				menuItem.setAction(new SuccessMetricsMenuAction());
				resourceManager.setResource(successMetricsMenuId, menuItem);
				baseMenu.addChildren(menuItem);
			}
		}
		

	}

}
