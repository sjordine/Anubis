package br.unicamp.ic.anubisdefaultviewer.ui.sequenceLock;

import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.KeyStroke;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.menu.MenuItemDefinition;

import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.BASE_MENU_FEATURE;
import static br.unicamp.ic.anubis.ui.menu.DefaultMenuTypes.CHECKBOX_MENU_ITEM;

public class SequenceLockFeature implements IFeature {

	private static UUID id = UUID
			.fromString("e29b5cfd-d81e-4a7b-9000-60d2fa65dd2f");
	private static UUID featureId = UUID
			.fromString("174c7575-c77d-4194-8d88-a0f8db2cf678");


	private static UUID viewMenuId=UUID.fromString("e08ce585-ced3-4423-95a7-9d9983210507");
	private static UUID lockAlignmentsMenuId=UUID.fromString("842777df-22a4-4727-91c5-06dadfcc7221");

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
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();
		IResourceManager resourceManager = manager.getResourceManager();

		if (eventManager!=null && resourceManager!=null){
			MenuItemDefinition viewMenu = (MenuItemDefinition) resourceManager.getResource(viewMenuId);

			if (viewMenu!=null){
				SequenceLockMenuAction action = new SequenceLockMenuAction();
				
				
				MenuItemDefinition menuItem = new MenuItemDefinition();
				menuItem.setText("Lock Alignments");
				menuItem.setOrder(200);
				menuItem.setMenuType(CHECKBOX_MENU_ITEM);
				menuItem.setAction(action);
				menuItem.setShortcut(KeyStroke.getKeyStroke('L',InputEvent.CTRL_DOWN_MASK ));
				resourceManager.setResource(lockAlignmentsMenuId, menuItem);
				viewMenu.addChildren(menuItem);

				eventManager.register(PropertyChangedEvent.class, action);
			}
		}

	}

}
