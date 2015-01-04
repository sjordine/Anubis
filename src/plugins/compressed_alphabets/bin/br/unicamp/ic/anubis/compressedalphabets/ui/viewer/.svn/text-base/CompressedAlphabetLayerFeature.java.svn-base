package br.unicamp.ic.anubis.compressedalphabets.ui.viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.compressedalphabets.ui.settings.menu.ConfigureCompressedAlphabetAction;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.ILayerSet;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.LayerSetPosition;
import br.unicamp.ic.anubis.ui.menu.MenuItemDefinition;

import static br.unicamp.ic.anubis.compressedalphabets.util.CommonConstants.COMPRESSED_ALPHABET_DATACONNECTOR_INTERFACE_ID;
import static br.unicamp.ic.anubis.compressedalphabets.util.CommonConstants.COMPRESSED_ALPHABET_VIEWER_INTERFACE_ID;

public class CompressedAlphabetLayerFeature implements IFeature {

	private static UUID id = UUID
			.fromString("d4a739df-845a-4234-9409-2d6dbda02f0a");

	private static UUID pluginMenuId = UUID
			.fromString("56679cd7-6d91-4977-bac2-95893032e1e4");

	private static UUID compressedAlphabetMenuId = UUID
			.fromString("084f0144-fe8c-45a3-ab2c-8dc74eac9735");

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		return COMPRESSED_ALPHABET_VIEWER_INTERFACE_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(COMPRESSED_ALPHABET_DATACONNECTOR_INTERFACE_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		IUIManager uiManager = manager.getUIManager();
		IResourceManager resourceManager = manager.getResourceManager();

		if (uiManager != null) {
			CompressedAlphabetLayer layer = new CompressedAlphabetLayer();
			ILayerSet alignmentLayerSet = uiManager.getLayerSet(LayerSetPosition.ALIGNMENT);
			alignmentLayerSet.addDrawingLayer(layer);
		}

		// Set menu
		if (resourceManager != null) {
			MenuItemDefinition baseMenu = (MenuItemDefinition) resourceManager
					.getResource(pluginMenuId);
			if (baseMenu != null) {
				MenuItemDefinition menuItem = new MenuItemDefinition();
				menuItem.setText("Compressed Alphabets");
				menuItem.setOrder(500);
				menuItem.setAction(new ConfigureCompressedAlphabetAction());	
				resourceManager.setResource(compressedAlphabetMenuId, menuItem);
				baseMenu.addChildren(menuItem);
			}			
		}

	}

}
