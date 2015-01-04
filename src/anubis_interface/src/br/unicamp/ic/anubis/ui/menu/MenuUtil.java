package br.unicamp.ic.anubis.ui.menu;

import static br.unicamp.ic.anubis.ui.menu.DefaultMenuTypes.CHECKBOX_MENU_ITEM;
import static br.unicamp.ic.anubis.ui.menu.DefaultMenuTypes.DEFAULT_MENU_ITEM;
import static br.unicamp.ic.anubis.ui.menu.DefaultMenuTypes.ROOT_MENU_ITEM;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

class MenuUtil {

	public static void buildMenu(MenuItemDefinition item, IMenuDocking parent) {
		buildMenu(item, parent, null);
	}

	public static void buildMenu(MenuItemDefinition item, IMenuDocking parent,
			IEnablingStrategy strategy) {
		JMenuItem menu;

		if (item.getChildren() != null && item.getChildren().size() > 0) {
			menu = createMenu(item);
			if (menu != null) {
				// Create child menu
				item.sort();
				for (MenuItemDefinition childItem : item.getChildren()) {
					buildMenu(childItem, new MenuDocking((JMenu) menu),
							strategy);
				}
				menu.setText(item.getText());
				setAction(item, menu, strategy);
				if (item.getShortcut() != null) {
					menu.setAccelerator(item.getShortcut());
				}

				if (strategy == null || strategy.isEnable(item)) {
					parent.add(menu);
				}
			}

		} else {
			if (item instanceof MenuSeparator) {
				parent.addSeparator();
			} else {
				menu = createMenu(item);
				if (menu != null) {
					menu.setText(item.getText());
					setAction(item, menu, strategy);
					if (item.getShortcut() != null) {
						menu.setAccelerator(item.getShortcut());
					}
					if (strategy == null || strategy.isEnable(item)) {
						parent.add(menu);
					}
				}
			}
		}
	}

	private static JMenuItem createMenu(MenuItemDefinition item) {
		JMenuItem returnValue = null;

		if (item != null) {
			switch (item.getMenuType()) {
			case DEFAULT_MENU_ITEM:
				if (item.getChildren() != null && item.getChildren().size() > 0) {
					returnValue = new JMenu();
				} else {
					returnValue = new JMenuItem();
				}
				break;
			case ROOT_MENU_ITEM:
				returnValue = new JMenu();
				break;
			case CHECKBOX_MENU_ITEM:
				returnValue = new JCheckBoxMenuItem();
				break;
			default:
				// TODO: Add possible menu decorators from plugins (for
				// unhandled menu types)
				break;
			}
		}

		return returnValue;
	}

	private static void setAction(MenuItemDefinition item, JMenuItem menu, IEnablingStrategy strategy) {
		if (item.getAction() != null) {
			MenuActionWrapper wrapper;
			try {
				MenuAction action = (MenuAction) item.getAction().clone();
				
				if (strategy!=null && strategy.getContext()!=null){
					action.setParentContext(strategy.getContext());
				}
				
				wrapper = new MenuActionWrapper(menu,
						action);
				menu.addActionListener(wrapper);
			} catch (CloneNotSupportedException e) {
				// TODO handle error
			}			
		}
	}

}
