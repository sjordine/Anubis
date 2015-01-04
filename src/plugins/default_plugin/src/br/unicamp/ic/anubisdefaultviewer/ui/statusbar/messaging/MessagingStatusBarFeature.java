package br.unicamp.ic.anubisdefaultviewer.ui.statusbar.messaging;

import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.plugin.IFeature;

public class MessagingStatusBarFeature implements IFeature {

	private static UUID id = UUID
			.fromString("167bd237-6887-4964-92fd-69512ad89cb1");
	private static UUID featureId = UUID
			.fromString("1c04a70f-54cb-452a-9336-fb9e41b3b067");
	


	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public UUID getInterfaceID() {
		return featureId;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		return null;
	}

	@Override
	public void enable() {
		MessagingStatusBar statusBarSection = new MessagingStatusBar();
		statusBarSection.create();
	}
}
