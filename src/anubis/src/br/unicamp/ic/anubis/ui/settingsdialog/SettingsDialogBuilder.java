package br.unicamp.ic.anubis.ui.settingsdialog;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.ANUBIS_VIEW;

import java.util.UUID;

import javax.swing.JFrame;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.Session;

public class SettingsDialogBuilder implements ISettingsDialog{
	


	@Override
	public void create(UUID dialogId){
		AnubisManager manager = AnubisManager.getInstance();
		Session session = manager.getSession();

		if (session != null) {
			JFrame view = (JFrame) session.get(ANUBIS_VIEW);
			if (view != null) {
				SettingsDialog dialog = new SettingsDialog(view);
				dialog.create(dialogId);
			}
		}
	}



}
