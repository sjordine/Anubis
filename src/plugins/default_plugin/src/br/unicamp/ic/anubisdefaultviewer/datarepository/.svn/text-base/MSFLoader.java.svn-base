package br.unicamp.ic.anubisdefaultviewer.datarepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.dialog.FileAction;
import br.unicamp.ic.anubis.ui.dialog.OpenFileDialog;
import br.unicamp.ic.anubisdefaultviewer.controller.filedialog.MSFFileAction;
import br.unicamp.ic.anubisdefaultviewer.controller.filedialog.MSFFilter;


public class MSFLoader implements IFeature {

	public static final UUID ID = UUID.fromString("19691824-a1d9-43cf-a088-c8e29808871f");
	private static final UUID INTERFACE_ID=UUID.fromString("a493a53c-4814-4afb-b7f2-491a3803ccc0");
	
	private static UUID loadAlignmentDialogId = UUID.fromString("04fa2535-ef1c-4210-be43-bf04de98e948");	
	private static UUID alignmentDataRepositoryInterfaceID = UUID.fromString("a663f847-7aa8-4714-9d91-810ac40a7fbd");
	
	@Override
	public UUID getId() {
		return ID;
	}

	@Override
	public UUID getInterfaceID() {
		return INTERFACE_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(alignmentDataRepositoryInterfaceID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		
		OpenFileDialog dialog = (OpenFileDialog)resourceManager.getResource(loadAlignmentDialogId);
		
		FileAction msfFileAction = new MSFFileAction();
		

		dialog.addFileHandler(new MSFFilter(), msfFileAction);
		
	}

}
