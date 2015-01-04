package br.unicamp.ic.secondarystructure.ui.viewer;

import static br.unicamp.ic.secondarystructure.common.CommonConstants.SECONDARY_STRUCTURE_REPOSITORY_ID;
import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;

public class SecondaryStructureWatcher implements IEventHandler{

	@Override
	public void eventRaised(IEvent message) {
		if (message instanceof AlignmentDataLoadedEvent) {
			AlignmentDataLoadedEvent alignmentLoadedMessage = (AlignmentDataLoadedEvent) message;

			if (SECONDARY_STRUCTURE_REPOSITORY_ID.equals(alignmentLoadedMessage
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
