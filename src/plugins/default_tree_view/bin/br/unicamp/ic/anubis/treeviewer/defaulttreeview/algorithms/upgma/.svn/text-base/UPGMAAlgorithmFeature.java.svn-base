package br.unicamp.ic.anubis.treeviewer.defaulttreeview.algorithms.upgma;

import static br.unicamp.ic.anubis.treeviewer.common.CommonConstants.TREEVIEW_MANAGER_ID;
import static br.unicamp.ic.anubis.treeviewer.defaulttreeview.common.CommonConstants.ALIGNMENT_DATAREPOSITORY_INTERFACE_ID;
import static br.unicamp.ic.anubis.treeviewer.defaulttreeview.common.CommonConstants.TREE_VIEWER_INTERFACE_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.treeviewer.defaulttreeview.simmilarity.IdentityDataMatrix;
import br.unicamp.ic.anubis.treeviewer.mechanism.ITreeManager;

public class UPGMAAlgorithmFeature implements IFeature {

	public static final UUID ID = UUID
			.fromString("5134a940-ddec-4b47-9d20-3ba6ef3083e0");
	private static final UUID INTERFACE_ID = UUID
			.fromString("a9956f1f-64b6-4add-85e7-659bf72cf2c0");

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
			UPGMAAlgorithm upgma = new UPGMAAlgorithm();
			resourceManager.setResource(INTERFACE_ID, upgma);
			
			// Add distance algorithm to list
			ITreeManager treeManager = (ITreeManager) resourceManager
					.getResource(TREEVIEW_MANAGER_ID);
			if (treeManager != null) {
				treeManager.addAlgorithm(upgma);
			}
		}

	}
}
