package br.unicamp.ic.anubisdefaultviewer.controller.menu;

import java.awt.event.ActionEvent;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.ui.menu.MenuAction;

public class UnhideAllColumnsAction extends MenuAction {

	@Override
	public void execute(ActionEvent event) {
		AnubisManager manager = AnubisManager.getInstance();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();

		if (alignmentManager != null) {
			alignmentManager.unhideAll();
		}
	}

}
