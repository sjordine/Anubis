package br.unicamp.ic.anubis.compressedalphabets.ui.settings.menu;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.compressedalphabets.ui.settings.CompressedAlphabetSettingsDialog;
import br.unicamp.ic.anubis.data.Session;
import br.unicamp.ic.anubis.ui.menu.MenuAction;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.ANUBIS_VIEW;

public class ConfigureCompressedAlphabetAction extends MenuAction {

	@Override
	public void execute(ActionEvent event) {
		AnubisManager manager = AnubisManager.getInstance();
		Session session = manager.getSession();

		if (session != null) {
			JFrame view = (JFrame) session.get(ANUBIS_VIEW);
			if (view != null) {
				CompressedAlphabetSettingsDialog dialog = new CompressedAlphabetSettingsDialog(view);
				dialog.showModal();
			}
		}
	}

}
