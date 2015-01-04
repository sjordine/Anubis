package br.unicamp.ic.anubis.columnmatch.dataconnector;

import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;

public class ColumnMatchWatcher implements IEventHandler {
	
	private static final UUID COLUMN_MATCH_DATA_REPOSITORY_ID = UUID
			.fromString("9df9b11f-c0b1-48e2-88ee-37fe3911e9e7");

	@Override
	public void eventRaised(IEvent message) {
		if (message instanceof AlignmentDataLoadedEvent) {
			AlignmentDataLoadedEvent alignmentLoadedMessage = (AlignmentDataLoadedEvent) message;

			if (COLUMN_MATCH_DATA_REPOSITORY_ID.equals(alignmentLoadedMessage
					.getSource())) {

				AnubisManager manager = AnubisManager.getInstance();
				IEventManager eventManager = manager.getEventManager();

				if (eventManager != null) {					
					
					RedrawAlignmentEvent redrawEvent = new RedrawAlignmentEvent(
							AlignmentUtil.BOTH_ALIGNMENTS);
					eventManager.raise(redrawEvent);
				}
			}
		}
	}

}
