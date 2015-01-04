package br.unicamp.ic.anubis.treeviewer.controller.menu;

import java.awt.event.ActionEvent;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.treeviewer.event.RotateNodeEvent;
import br.unicamp.ic.anubis.ui.menu.MenuAction;

public class RotateNodeDialogMenuAction extends MenuAction {

	@Override
	public void execute(ActionEvent event) {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();
		
		if (eventManager!=null){
			RotateNodeEvent rotateEvent = new RotateNodeEvent();
			eventManager.raise(rotateEvent);
		}				
	}

}
