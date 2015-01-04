package br.unicamp.ic.anubis.successmetrics.sequence.dataconnector;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.SEQUENCE_SUCCESS_REPOSITORY_ID;
import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;

public class SequenceSuccessWatcher implements IEventHandler {

	@Override
	public void eventRaised(IEvent message) {
		if (message instanceof AlignmentDataLoadedEvent) {
			AlignmentDataLoadedEvent alignmentLoadedMessage = (AlignmentDataLoadedEvent) message;

			if (SEQUENCE_SUCCESS_REPOSITORY_ID.equals(alignmentLoadedMessage
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
