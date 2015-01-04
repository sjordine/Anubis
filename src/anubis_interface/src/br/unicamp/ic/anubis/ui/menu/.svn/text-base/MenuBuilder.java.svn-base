package br.unicamp.ic.anubis.ui.menu;

import java.util.UUID;


import javax.swing.JMenu;
import javax.swing.JMenuBar;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;


public class MenuBuilder {

	public static JMenuBar build(UUID resourceId) {
		JMenuBar menuBar = new JMenuBar();
		// Get root menu
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();

		if (resourceManager != null) {

			MenuItemDefinition rootMenu = (MenuItemDefinition) resourceManager
					.getResource(resourceId);

			if (rootMenu != null) {
				for (MenuItemDefinition item : rootMenu.getChildren()) {
					JMenu menu = new JMenu();
					menu.setText(item.getText());
					item.sort();
					for (MenuItemDefinition childItem : item.getChildren()) {
						MenuUtil.buildMenu(childItem, new MenuDocking(menu));
					}
					menuBar.add(menu);
				}
			}
		}

		return menuBar;
	}







}
