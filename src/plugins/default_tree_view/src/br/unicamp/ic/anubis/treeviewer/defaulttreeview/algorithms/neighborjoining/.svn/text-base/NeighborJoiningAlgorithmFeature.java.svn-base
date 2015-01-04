package br.unicamp.ic.anubis.treeviewer.defaulttreeview.algorithms.neighborjoining;

import static br.unicamp.ic.anubis.treeviewer.common.CommonConstants.TREEVIEW_MANAGER_ID;
import static br.unicamp.ic.anubis.treeviewer.defaulttreeview.common.CommonConstants.ALIGNMENT_DATAREPOSITORY_INTERFACE_ID;
import static br.unicamp.ic.anubis.treeviewer.defaulttreeview.common.CommonConstants.TREE_VIEWER_INTERFACE_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.treeviewer.defaulttreeview.algorithms.upgma.UPGMAAlgorithm;
import br.unicamp.ic.anubis.treeviewer.mechanism.ITreeManager;

public class NeighborJoiningAlgorithmFeature implements IFeature {
	public static final UUID ID = UUID
			.fromString("09ab6f90-dfdf-4b08-b3c5-3774ddf9f26a");
	private static final UUID INTERFACE_ID = UUID
			.fromString("92e8cd51-b2ab-47bf-8187-87f4d2626133");

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
			NeighborJoiningAlgorithm neighborJoining = new NeighborJoiningAlgorithm();
			resourceManager.setResource(INTERFACE_ID, neighborJoining);
			
			// Add distance algorithm to list
			ITreeManager treeManager = (ITreeManager) resourceManager
					.getResource(TREEVIEW_MANAGER_ID);
			if (treeManager != null) {
				treeManager.addAlgorithm(neighborJoining);
			}
		}

	}
}
