package br.unicamp.ic.anubis.ui.menu;


import java.util.UUID;

import javax.swing.JPopupMenu;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.DefaultParameterizedContextContainer;
import br.unicamp.ic.anubis.plugin.IParameterizedContext;

public class AlignmentContextMenuBuilder {

	
	public static JPopupMenu build(UUID menuResource, int alignmentId, int column, int row) {
		JPopupMenu returnValue = null;
		
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();

		if (resourceManager != null) {

			MenuItemDefinition rootMenu = (MenuItemDefinition) resourceManager
					.getResource(menuResource);
			
			if (rootMenu != null) {
				IEnablingStrategy strategy = new AlignmentContextMenuEnablingStrategy(alignmentId, column, row);
				
				IParameterizedContext context = new DefaultParameterizedContextContainer();
				context.setParameter("ALIGNMENT_ID", alignmentId);
				context.setParameter("COLUMN", column);
				context.setParameter("ROW", row);
				
				strategy.setContext(context);
				
				returnValue = new JPopupMenu();
				for (MenuItemDefinition item : rootMenu.getChildren()) {
					item.sort();
					MenuUtil.buildMenu(item, new PopupMenuDocking(returnValue),strategy);
				}			
			}
		}
		
		return returnValue;
	}
}
