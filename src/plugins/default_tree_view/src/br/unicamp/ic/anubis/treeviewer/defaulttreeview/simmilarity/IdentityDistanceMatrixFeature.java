package br.unicamp.ic.anubis.treeviewer.defaulttreeview.simmilarity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.treeviewer.mechanism.ITreeManager;

import static br.unicamp.ic.anubis.treeviewer.defaulttreeview.common.CommonConstants.ALIGNMENT_DATAREPOSITORY_INTERFACE_ID;
import static br.unicamp.ic.anubis.treeviewer.defaulttreeview.common.CommonConstants.TREE_VIEWER_INTERFACE_ID;
import static br.unicamp.ic.anubis.treeviewer.common.CommonConstants.TREEVIEW_MANAGER_ID;

public class IdentityDistanceMatrixFeature implements IFeature {

	public static final UUID ID = UUID
			.fromString("4cbf6ef2-48db-4523-bc60-270444081995");
	private static final UUID INTERFACE_ID = UUID
			.fromString("9bfe9a06-2829-4101-bd89-09574298af18");

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
		requiredInterfaces.add(ALIGNMENT_DATAREPOSITORY_INTERFACE_ID);
		requiredInterfaces.add(TREE_VIEWER_INTERFACE_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		// Prepare repository
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IEventManager eventManager = manager.getEventManager();

		if (resourceManager != null && eventManager != null) {
			IdentityDataMatrix dataRepository = new IdentityDataMatrix();
			resourceManager.setResource(INTERFACE_ID, dataRepository);
			eventManager.register(AlignmentDataLoadedEvent.class,
					dataRepository);
			// Add distance algorithm to list
			ITreeManager treeManager = (ITreeManager) resourceManager
					.getResource(TREEVIEW_MANAGER_ID);
			if (treeManager != null) {
				treeManager.addDistanceMethod(dataRepository);
			}
		}
	}

}
