package br.unicamp.ic.anubis.successmetrics.residue.dataconnector;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_REPOSITORY_ID;

public class ResidueSuccessWatcher implements IEventHandler {

	@Override
	public void eventRaised(IEvent message) {
		if (message instanceof AlignmentDataLoadedEvent) {
			AlignmentDataLoadedEvent alignmentLoadedMessage = (AlignmentDataLoadedEvent) message;

			if (RESIDUE_SUCCESS_REPOSITORY_ID.equals(alignmentLoadedMessage
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
