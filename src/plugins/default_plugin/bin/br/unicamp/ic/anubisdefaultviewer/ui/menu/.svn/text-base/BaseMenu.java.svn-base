package br.unicamp.ic.anubisdefaultviewer.ui.menu;

import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.menu.MenuItemDefinition;
import br.unicamp.ic.anubis.ui.menu.MenuSeparator;
import br.unicamp.ic.anubisdefaultviewer.controller.menu.CloseAnubisAction;
import br.unicamp.ic.anubisdefaultviewer.controller.menu.ShowSettingsDialogAction;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.ROOT_MENU;
import static br.unicamp.ic.anubis.ui.menu.DefaultMenuTypes.ROOT_MENU_ITEM;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.BASE_MENU_FEATURE;

public class BaseMenu implements IFeature {
	


	private static UUID id = UUID
			.fromString("5b000023-3b8b-4746-8eb7-b3a0fbd40a79");
	
	private static UUID fileMenuId=UUID.fromString("41a39630-6778-4f52-b1d6-cd889d130864");
	private static UUID viewMenuId=UUID.fromString("e08ce585-ced3-4423-95a7-9d9983210507");
	private static UUID fontMenuId=UUID.fromString("7acdbacc-1389-4053-bd5e-989e54d34198");
	private static UUID settingsMenuId = UUID.fromString("bd9976ed-7a55-4c9f-9941-638e7768f78d");
	private static UUID pluginMenuId=UUID.fromString("56679cd7-6d91-4977-bac2-95893032e1e4");
	private static UUID helpMenuId=UUID.fromString("b13b3dce-a9af-408c-a18a-e6d4ce31eb4e");
	


	private static UUID exitMenuId=UUID.fromString("49bb689a-0f35-4a3e-9fc1-ce1ffa7568b4");

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		return BASE_MENU_FEATURE;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		return null;
	}

	@Override
	public void enable() {
		// Get {menu root
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();

		if (resourceManager != null) {
			MenuItemDefinition root = (MenuItemDefinition) resourceManager.getResource(ROOT_MENU);
			if (root != null) {
				//TODO refactor this code, menus can be used in other features...
				// Add first level menus
				MenuItemDefinition menuItem = new MenuItemDefinition();
				menuItem.setText("File");
				menuItem.setOrder(100);
				menuItem.setMenuType(ROOT_MENU_ITEM);
				resourceManager.setResource(fileMenuId, menuItem);
				root.addChildren(menuItem);
				
				menuItem = new MenuItemDefinition();
				menuItem.setText("View");
				menuItem.setOrder(300);
				menuItem.setMenuType(ROOT_MENU_ITEM);
				resourceManager.setResource(viewMenuId, menuItem);
				root.addChildren(menuItem);
				
				MenuItemDefinition viewMenu = menuItem;
				menuItem = new MenuItemDefinition();
				menuItem.setText("Font");
				menuItem.setOrder(500);
				resourceManager.setResource(fontMenuId, menuItem);
				viewMenu.addChildren(menuItem);
				
				menuItem = new MenuSeparator();
				menuItem.setOrder(1050);
				viewMenu.addChildren(menuItem);
				
				menuItem = new MenuItemDefinition();
				menuItem.setText("Settings");
				menuItem.setOrder(1100);
				menuItem.setAction(new ShowSettingsDialogAction());
				resourceManager.setResource(settingsMenuId, menuItem);
				viewMenu.addChildren(menuItem);
				
				menuItem = new MenuItemDefinition();
				menuItem.setText("Plugins");
				menuItem.setOrder(500);
				menuItem.setMenuType(ROOT_MENU_ITEM);
				resourceManager.setResource(pluginMenuId, menuItem);
				root.addChildren(menuItem);
				
				menuItem = new MenuItemDefinition();
				menuItem.setText("Help");
				menuItem.setOrder(1000);
				menuItem.setMenuType(ROOT_MENU_ITEM);
				resourceManager.setResource(helpMenuId, menuItem);
				root.addChildren(menuItem);
				
				
				
				
				
				//Add exit menu
				MenuItemDefinition parentMenu = (MenuItemDefinition)resourceManager.getResource(fileMenuId);
				
				menuItem = new MenuSeparator();
				menuItem.setOrder(900);
				parentMenu.addChildren(menuItem);
				
				menuItem = new MenuItemDefinition();
				menuItem.setText("Exit");
				menuItem.setOrder(1000);
				menuItem.setAction(new CloseAnubisAction());			
				resourceManager.setResource(exitMenuId, menuItem);				
				parentMenu.addChildren(menuItem);
				
			}
		}
	}

}
