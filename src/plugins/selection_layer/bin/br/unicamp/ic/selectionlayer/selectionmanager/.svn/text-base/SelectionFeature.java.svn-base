package br.unicamp.ic.selectionlayer.selectionmanager;

import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.plugin.IFeature;

import static br.unicamp.ic.selectionlayer.common.CommonConstants.SELECTION_MANAGER_ID;


public class SelectionFeature implements IFeature {
	
	private static UUID id = UUID
			.fromString("f0a75907-0cda-45e0-8bfd-884cf79bd10d");

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		return SELECTION_MANAGER_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		return null;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		manager.setSelectionManager(new SelectionManager());
	}

}
