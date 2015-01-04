package br.unicamp.ic.anubisdefaultviewer.controller.filedialog;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.ui.dialog.FileAction;
import br.unicamp.ic.jbio.msf.MSFParser;

public class MSFFileAction extends FileAction {

	private static final UUID ALIGNMENT_DATA_REPOSITORY_ID = UUID
			.fromString("a663f847-7aa8-4714-9d91-810ac40a7fbd");

	@Override
	public void execute(File targetFile) {
	
		
		if (targetFile != null && getParameter("ALIGNMENT_INDEX")!=null) {
			AnubisManager manager = AnubisManager.getInstance();
			IResourceManager resourceManager = manager.getResourceManager();
			
			int alignmentIndex = (Integer)getParameter("ALIGNMENT_INDEX");

			if (resourceManager != null) {
				// Get repository
				IAlignmentDataRepository dataRepository = (IAlignmentDataRepository) resourceManager
						.getResource(ALIGNMENT_DATA_REPOSITORY_ID);

				if (dataRepository != null) {
					// Get file name and path
					String fileName = targetFile.getName();
					String filePath = targetFile.getAbsolutePath();

					// Parse MSF file
					try {
						
						List<Entry<String, String>> alignment = MSFParser
								.load(filePath);
						
						dataRepository.setData(alignmentIndex, alignment);
						

						
					} catch (IOException e) {
						//TODO: handle errors!
						e.printStackTrace();
					}
				}
			}
		}
	}

}
