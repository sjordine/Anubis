package br.unicamp.ic.anubisdefaultviewer.ui.fontmanagement;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.ROOT_MENU;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.KeyStroke;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.menu.MenuItemDefinition;
import br.unicamp.ic.anubisdefaultviewer.controller.menu.DecreaseFontSizeAction;
import br.unicamp.ic.anubisdefaultviewer.controller.menu.IncreaseFontSizeAction;
import br.unicamp.ic.anubisdefaultviewer.controller.menu.SetFontColorAction;

public class FontManagementFeature implements IFeature {

	private static UUID id = UUID
			.fromString("fb4c1a23-2788-4c4c-ac19-4f1bac4f2b63");
	private static UUID featureId = UUID
			.fromString("535389ba-a4d1-411f-a015-3ab7afd0a0d0");
	
	
	private static UUID DECREASE_FONT_SIZE_ID = UUID.fromString("9bb74e31-38d2-4024-b171-1fb00a760916");
	private static UUID INCREASE_FONT_SIZE_ID = UUID.fromString("4fdc6a84-5a92-4cb2-90ae-a75d76506d7b");
	private static UUID SET_FONT_COLOR_ID = UUID.fromString("dceb52f0-825d-4c50-97fc-e2787a345a9b");

	private static UUID BASIC_MENU_FEATURE = UUID
			.fromString("33314751-3095-442d-b492-b17e2c1e9aac");
	private static UUID FONT_MENU_ID = UUID.fromString("7acdbacc-1389-4053-bd5e-989e54d34198");

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
		requiredInterfaces.add(BASIC_MENU_FEATURE);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		// Get {menu root
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();

		if (resourceManager != null) {
			MenuItemDefinition fontMenu = (MenuItemDefinition) resourceManager.getResource(FONT_MENU_ID);
			
			MenuItemDefinition menuItem = new MenuItemDefinition();
			menuItem.setText("Decrease font size");
			menuItem.setAction(new DecreaseFontSizeAction());
			menuItem.setOrder(100);
			menuItem.setShortcut(KeyStroke.getKeyStroke('-',InputEvent.CTRL_DOWN_MASK ));
			resourceManager.setResource(DECREASE_FONT_SIZE_ID, menuItem);
			fontMenu.addChildren(menuItem);
			
			menuItem = new MenuItemDefinition();
			menuItem.setText("Increase font size");
			menuItem.setAction(new IncreaseFontSizeAction());
			menuItem.setOrder(300);
			menuItem.setShortcut(KeyStroke.getKeyStroke('=',InputEvent.CTRL_DOWN_MASK ));
			//menuItem.setShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS,KeyEvent.CTRL_MASK));
			resourceManager.setResource(INCREASE_FONT_SIZE_ID, menuItem);
			fontMenu.addChildren(menuItem);
			
			menuItem = new MenuItemDefinition();
			menuItem.setText("Color");
			menuItem.setAction(new SetFontColorAction());
			menuItem.setOrder(500);
			menuItem.setShortcut(KeyStroke.getKeyStroke('C',InputEvent.ALT_DOWN_MASK ));
			resourceManager.setResource(SET_FONT_COLOR_ID, menuItem);
			fontMenu.addChildren(menuItem);
			
		}

	}

}
