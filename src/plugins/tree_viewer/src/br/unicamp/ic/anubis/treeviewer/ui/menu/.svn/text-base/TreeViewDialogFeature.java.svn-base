package br.unicamp.ic.anubis.treeviewer.ui.menu;

import static br.unicamp.ic.anubis.treeviewer.common.CommonConstants.TREEVIEW_MANAGER_ID;
import static br.unicamp.ic.anubis.treeviewer.common.CommonConstants.TREE_VIEW_MENU_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.treeviewer.controller.menu.RotateNodeDialogMenuAction;
import br.unicamp.ic.anubis.treeviewer.controller.menu.TreeViewDialogMenuAction;
import br.unicamp.ic.anubis.treeviewer.mechanism.ITreeManager;
import br.unicamp.ic.anubis.treeviewer.mechanism.TreeViewManager;
import br.unicamp.ic.anubis.ui.menu.MenuItemDefinition;

public class TreeViewDialogFeature implements IFeature {

	private static UUID id = UUID
			.fromString("c136f20f-bf31-474a-97f0-99242c0fd33e");
	private static UUID featureId = UUID
			.fromString("5e009fae-e310-48cc-aa28-ce04945fc188");

	public static final UUID BASE_MENU_FEATURE = UUID
			.fromString("33314751-3095-442d-b492-b17e2c1e9aac");
	
	
	private static UUID viewMenuId=UUID.fromString("e08ce585-ced3-4423-95a7-9d9983210507");
	private static UUID treeViewMenuId=UUID.fromString("fa4c393b-5fc1-4b9b-a52d-529208911232");

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
		requiredInterfaces.add(BASE_MENU_FEATURE);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		// Get menu root
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();

		if (resourceManager != null) {
			MenuItemDefinition viewMenu = (MenuItemDefinition) resourceManager.getResource(viewMenuId);
			
			buildMenus(resourceManager, viewMenu);
			
			ITreeManager treeManager = TreeViewManager.getInstance();
			resourceManager.setResource(TREEVIEW_MANAGER_ID, treeManager);
			
		}
	}

	public void buildMenus(IResourceManager resourceManager,
			MenuItemDefinition viewMenu) {
		MenuItemDefinition menuItem = new MenuItemDefinition();
		menuItem.setText("Tree View");
		menuItem.setOrder(300);
		menuItem.setAction(new TreeViewDialogMenuAction());
		resourceManager.setResource(treeViewMenuId, menuItem);
		viewMenu.addChildren(menuItem);
		
		
		//Dialog menu
		MenuItemDefinition rootMenu = new MenuItemDefinition();
		resourceManager.setResource(TREE_VIEW_MENU_ID, rootMenu);
		
		menuItem = new MenuItemDefinition();
		menuItem.setText("Tree");
		rootMenu.addChildren(menuItem);
		MenuItemDefinition treeMenu = menuItem;
		
		menuItem = new MenuItemDefinition();
		menuItem.setText("Rotate");
		menuItem.setAction(new RotateNodeDialogMenuAction());
		treeMenu.addChildren(menuItem);
		

		
	}

}
