package br.unicamp.ic.anubisdefaultviewer.ui.menu;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.COLUMN_MENU;
import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.ROW_MENU;

import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.ALIGNMENT_CONTEXT_MENU_FEATURE;

import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.KeyStroke;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.menu.MenuItemDefinition;
import br.unicamp.ic.anubis.ui.menu.MenuSeparator;
import br.unicamp.ic.anubisdefaultviewer.controller.contextmenu.HideColumnAction;
import br.unicamp.ic.anubisdefaultviewer.controller.contextmenu.HideRowAction;
import br.unicamp.ic.anubisdefaultviewer.controller.contextmenu.MoveRowDownAction;
import br.unicamp.ic.anubisdefaultviewer.controller.contextmenu.MoveRowUpAction;
import br.unicamp.ic.anubisdefaultviewer.controller.menu.UnhideAllColumnsAction;

public class VisibilityMenu implements IFeature {
	
	private static UUID id = UUID
			.fromString("fb414628-ccc0-4696-af38-21a3f8d6f6bf");
	
	private static UUID hideColumnId=UUID.fromString("2c8ab2d9-6c2c-4eef-ab76-25b22b1f043d");
	private static UUID hideRowId=UUID.fromString("d579e668-a85e-4721-8ac8-e792fc1a4f3c");
	private static UUID unhideAllId=UUID.fromString("8dc62af1-e8a6-42e5-a607-b0ebce5449f4");
	
	private static UUID moveRowUpId=UUID.fromString("23412964-78d7-45cb-bf06-58ab6bd49b3e");
	private static UUID moveRowDownId=UUID.fromString("4dc56080-0615-43f1-9b5a-69e95472d615");
	
	private static UUID viewMenuId=UUID.fromString("e08ce585-ced3-4423-95a7-9d9983210507");

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(ALIGNMENT_CONTEXT_MENU_FEATURE);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		
		if (resourceManager != null) {
			MenuItemDefinition columnMenu = (MenuItemDefinition) resourceManager.getResource(COLUMN_MENU);
			if (columnMenu!=null){
				MenuItemDefinition menuItem = new MenuItemDefinition();
				menuItem.setText("Hide column");
				menuItem.setOrder(200);
				menuItem.setAction(new HideColumnAction());
				columnMenu.addChildren(menuItem);
				resourceManager.setResource(hideColumnId, menuItem);
			}
			
			MenuItemDefinition rowMenu = (MenuItemDefinition) resourceManager.getResource(ROW_MENU);
			if (columnMenu!=null){
				MenuItemDefinition menuItem = new MenuItemDefinition();
				menuItem.setText("Hide row");
				menuItem.setOrder(200);
				menuItem.setAction(new HideRowAction());
				rowMenu.addChildren(menuItem);
				resourceManager.setResource(hideRowId, menuItem);
				
				menuItem = new MenuSeparator();
				menuItem.setOrder(500);
				rowMenu.addChildren(menuItem);
				
				menuItem = new MenuItemDefinition();
				menuItem.setText("Move Row up");
				menuItem.setOrder(550);
				menuItem.setAction(new MoveRowUpAction());
				rowMenu.addChildren(menuItem);
				resourceManager.setResource(moveRowUpId, menuItem);
				
				menuItem = new MenuItemDefinition();
				menuItem.setText("Move Row down");
				menuItem.setOrder(570);
				menuItem.setAction(new MoveRowDownAction());
				rowMenu.addChildren(menuItem);
				resourceManager.setResource(moveRowDownId, menuItem);
			}
			
			MenuItemDefinition viewMenu = (MenuItemDefinition) resourceManager.getResource(viewMenuId);
			if (viewMenu!=null){
				MenuItemDefinition menuItem = new MenuItemDefinition();
				menuItem.setText("Unhide All");
				menuItem.setOrder(200);
				menuItem.setAction(new UnhideAllColumnsAction());
				menuItem.setShortcut(KeyStroke.getKeyStroke('U',InputEvent.CTRL_DOWN_MASK ));
				resourceManager.setResource(unhideAllId, menuItem);
				viewMenu.addChildren(menuItem);
			}
		}		
	}

}
