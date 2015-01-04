package br.unicamp.ic.anubisdefaultviewer.datarepository;

import java.awt.event.ActionEvent;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.ui.dialog.OpenFileDialog;
import br.unicamp.ic.anubis.ui.menu.MenuAction;

public class LoadBenchmarkAction extends MenuAction{
	
	private static UUID loadAlignmentDialogId = UUID.fromString("04fa2535-ef1c-4210-be43-bf04de98e948");

	@Override
	public void execute(ActionEvent event) {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		
		OpenFileDialog dialog = (OpenFileDialog)resourceManager.getResource(loadAlignmentDialogId);		
		
		dialog.setParentContext(this);
		dialog.showDialog();
		
	}

}
