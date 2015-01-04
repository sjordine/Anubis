package br.unicamp.ic.anubis.compressedalphabets.dataconnector;

import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;

import static br.unicamp.ic.anubis.compressedalphabets.util.CommonConstants.COMPRESSED_ALPHABET_INTERFACE_ID;


public class CompressedAlphabetWatcher implements IEventHandler {
		

		@Override
		public void eventRaised(IEvent message) {
			if (message instanceof AlignmentDataLoadedEvent) {
				AlignmentDataLoadedEvent alignmentLoadedMessage = (AlignmentDataLoadedEvent) message;

				if (COMPRESSED_ALPHABET_INTERFACE_ID.equals(alignmentLoadedMessage
						.getSource())) {

					AnubisManager manager = AnubisManager.getInstance();
					IEventManager eventManager = manager.getEventManager();

					if (eventManager != null) {					
						
						RedrawAlignmentEvent redrawEvent = new RedrawAlignmentEvent(
								alignmentLoadedMessage.getAlignmentIndex());
						eventManager.raise(redrawEvent);
					}
				}
			}
		}
}
