package br.unicamp.ic.anubisdefaultviewer.controller.menu;

import java.awt.event.ActionEvent;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.ui.menu.MenuAction;
import br.unicamp.ic.anubis.ui.settingsdialog.ISettingsDialog;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.SETTINGS_DIALOG_BUILDER;
import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.SETTINGS_DIALOG;

public class ShowSettingsDialogAction extends MenuAction {

	@Override
	public void execute(ActionEvent event) {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		
		if (resourceManager!=null){
		 ISettingsDialog dialog = (ISettingsDialog)resourceManager.getResource(SETTINGS_DIALOG_BUILDER);
		 if (dialog!=null){
			 dialog.create(SETTINGS_DIALOG);
		 }
		}

	}

}
