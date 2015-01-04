package br.unicamp.ic.anubis.successmetrics.sequence.ui.viewer;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.SEQUENCE_SUCCESS_DATACONNECTOR_ID;

import java.awt.Image;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.successmetrics.common.metricsstrategy.ISuccessMetricsStrategy;
import br.unicamp.ic.anubis.successmetrics.sequence.dataconnector.SequenceSuccessDataConnector;
import br.unicamp.ic.anubis.successmetrics.ui.viewer.SuccessMetricsDrawer;
import br.unicamp.ic.anubis.ui.viewer.ILayer;

public class SequenceSuccessStrategy implements ISuccessMetricsStrategy {
	
	private static UUID strategyId = UUID
			.fromString("a3f3722d-25da-4cad-99b3-280d16763337");

	@Override
	public Integer[][] getData(int alignmentIndex, int startColumn,
			int startRow, int columns, int rows) {
		Integer[][] data = null;
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		if (resourceManager!=null){
			SequenceSuccessDataConnector connector = (SequenceSuccessDataConnector) resourceManager
					.getResource(SEQUENCE_SUCCESS_DATACONNECTOR_ID);
			if (connector!=null){
			data = (Integer[][]) connector.getData(alignmentIndex,
					startColumn, startRow, columns, rows);
			}
		}
		return data;
	}
	

	@Override
	public UUID getId() {
		return strategyId;
	}

	@Override
	public String getName() {
		return "Success % (sequence)";
	}

}
