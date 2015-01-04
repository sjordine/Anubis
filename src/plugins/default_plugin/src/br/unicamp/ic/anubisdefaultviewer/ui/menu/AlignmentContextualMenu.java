package br.unicamp.ic.anubisdefaultviewer.ui.menu;

import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.menu.MenuItemDefinition;

import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.ALIGNMENT_CONTEXT_MENU_FEATURE;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.COLUMN_MENU;
import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.ROW_MENU;



public class AlignmentContextualMenu implements IFeature {
	
	private static UUID id = UUID
			.fromString("a62ca702-ecc1-4044-b7f1-2fe1456d70ea");

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		return ALIGNMENT_CONTEXT_MENU_FEATURE;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		return null;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		
		if (resourceManager != null) {
			MenuItemDefinition menu = new MenuItemDefinition();
			resourceManager.setResource(COLUMN_MENU, menu);
			
			menu = new MenuItemDefinition();
			resourceManager.setResource(ROW_MENU, menu);
			
			
		}

	}

}
