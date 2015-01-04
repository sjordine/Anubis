package br.unicamp.ic.anubis.treeviewer.controller.menu;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.ANUBIS_VIEW;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.Session;
import br.unicamp.ic.anubis.treeviewer.ui.dialog.TreeViewDialog;
import br.unicamp.ic.anubis.ui.menu.MenuAction;

public class TreeViewDialogMenuAction extends MenuAction {

	@Override
	public void execute(ActionEvent event) {
		AnubisManager manager = AnubisManager.getInstance();
		Session session = manager.getSession();

		if (session != null) {
			JFrame view = (JFrame) session.get(ANUBIS_VIEW);
			if (view != null) {
				TreeViewDialog dialog = new TreeViewDialog(view);
				dialog.showModal();
			}
		}
		
	}

}
