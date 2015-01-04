package br.unicamp.ic.anubis.successmetrics.residue.residuesuccess;



import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_CALCULATOR_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.COLUMN_MATCH_REPOSITORY_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.CONSIDER_GAPS_KEY;

public class ResidueSuccessWatcher implements IEventHandler {

	@Override
	public void eventRaised(IEvent message) {
		if (message != null) {
			if (message instanceof AlignmentDataLoadedEvent) {
				AlignmentDataLoadedEvent event = (AlignmentDataLoadedEvent) message;
				UUID sourceId = event.getSource();

				if (COLUMN_MATCH_REPOSITORY_ID.equals(sourceId)) {
					recalculateResidueSuccess();
				}
			}
			if (message instanceof PropertyChangedEvent){
				PropertyChangedEvent event = (PropertyChangedEvent) message;
				if (CONSIDER_GAPS_KEY.equals(event.getPropertyName())){
					recalculateResidueSuccess();
				}
			}
		}
	}

	public void recalculateResidueSuccess() {
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager
				.getResourceManager();
		if (resourceManager != null) {
			ResidueSuccess residueSuccess = (ResidueSuccess) resourceManager
					.getResource(RESIDUE_SUCCESS_CALCULATOR_ID);
			if (residueSuccess != null) {
				residueSuccess.processResidueSuccess();
				// TODO: handle errors
				notifyDataChanged(manager);
			}
		}
	}

	private void notifyDataChanged(AnubisManager manager) {
		// Notify calculated data, if successful
		IEventManager eventManager = manager.getEventManager();
		if (eventManager != null) {
			IEvent event = new AlignmentDataLoadedEvent(
					RESIDUE_SUCCESS_CALCULATOR_ID,
					AlignmentUtil.BOTH_ALIGNMENTS);
			eventManager.raise(event);
		}
	}

}