package br.unicamp.ic.anubis.successmetrics.residue.ui.viewer;


import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.successmetrics.common.metricsstrategy.ISuccessMetricsStrategy;
import br.unicamp.ic.anubis.successmetrics.residue.dataconnector.ResidueSuccessDataConnector;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_DATACONNECTOR_ID;

public class ResidueSuccessDrawingStrategy implements ISuccessMetricsStrategy {
	
	private static UUID strategyId = UUID
			.fromString("3073db34-5761-47db-af1f-47ee6b42ab8b");


	@Override
	public Integer[][] getData(int alignmentIndex, int startColumn,
			int startRow, int columns, int rows) {
		Integer[][] data = null;
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		if (resourceManager!=null){
			ResidueSuccessDataConnector connector = (ResidueSuccessDataConnector) resourceManager
					.getResource(RESIDUE_SUCCESS_DATACONNECTOR_ID);
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
		return "Success % (residue)";
	}

}
