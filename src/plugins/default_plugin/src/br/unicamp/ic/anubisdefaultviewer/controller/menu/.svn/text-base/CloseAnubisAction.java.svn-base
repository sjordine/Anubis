package br.unicamp.ic.anubisdefaultviewer.controller.menu;

import java.awt.event.ActionEvent;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.CloseEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.ui.menu.MenuAction;

public class CloseAnubisAction extends MenuAction{

	@Override
	public void execute(ActionEvent event) {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();
		
		eventManager.raise(new CloseEvent());
		
	}


}
