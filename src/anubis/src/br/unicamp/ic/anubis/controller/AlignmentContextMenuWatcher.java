package br.unicamp.ic.anubis.controller;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.Session;
import br.unicamp.ic.anubis.event.AlignmentClickedEvent;
import br.unicamp.ic.anubis.event.AlignmentColumnPopUpMenuEvent;
import br.unicamp.ic.anubis.event.AlignmentRowPopUpMenuEvent;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.ui.menu.AlignmentContextMenuBuilder;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.ANUBIS_VIEW;
import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.COLUMN_MENU;
import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.ROW_MENU;

public class AlignmentContextMenuWatcher implements IEventHandler {

	@Override
	public void eventRaised(IEvent message) {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		Session session = manager.getSession();

		if (resourceManager != null && session != null) {
			if (message instanceof AlignmentColumnPopUpMenuEvent) {
				AlignmentColumnPopUpMenuEvent event = (AlignmentColumnPopUpMenuEvent) message;

				JPopupMenu menu = AlignmentContextMenuBuilder.build(
						COLUMN_MENU, event.getAlignmentId(),
						event.getSelectedColumn(), -1);

				if (menu != null) {
					JFrame view = (JFrame) session.get(ANUBIS_VIEW);
					Component container = event.getContainer();
					container = (container == null)?view:container;
					
					menu.show(container, event.getPoint().x, event.getPoint().y);
				}
			}

			if (message instanceof AlignmentRowPopUpMenuEvent) {
				AlignmentRowPopUpMenuEvent event = (AlignmentRowPopUpMenuEvent) message;
				
				JPopupMenu menu = AlignmentContextMenuBuilder.build(
						ROW_MENU, event.getAlignmentId(),
						-1, event.getSelectedRow());

				if (menu != null) {
					JFrame view = (JFrame) session.get(ANUBIS_VIEW);
					Component container = event.getContainer();
					container = (container == null)?view:container;
					
					menu.show(container, event.getPoint().x, event.getPoint().y);
				}
			}
		}
	}
}
