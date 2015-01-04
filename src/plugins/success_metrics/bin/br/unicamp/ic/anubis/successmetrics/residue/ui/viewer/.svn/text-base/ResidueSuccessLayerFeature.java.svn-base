package br.unicamp.ic.anubis.successmetrics.residue.ui.viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.successmetrics.common.metricsstrategy.ISuccessMetricsStrategy;
import br.unicamp.ic.anubis.successmetrics.common.metricsstrategy.SuccessMetricsStrategyFactory;
import br.unicamp.ic.anubis.ui.IUIManager;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_DATACONNECTOR_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.SUCCESS_METRICS_STRATEGY_KEY;

public class ResidueSuccessLayerFeature implements IFeature {

	private static UUID id = UUID
			.fromString("7dcdeb7e-1fa7-4695-aa21-eab71fed286d");
	private static UUID featureId = UUID
			.fromString("4140ff4c-cf03-4eb9-94c4-35250bdc1b8b");

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
		List<UUID> requiredInterfaces = new ArrayList<UUID>();
		requiredInterfaces.add(RESIDUE_SUCCESS_DATACONNECTOR_ID);
		return requiredInterfaces;
	}

	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settingsManager  = manager.getSettingsManager();

		SuccessMetricsStrategyFactory strategyFactory = SuccessMetricsStrategyFactory.getInstance();
		if (strategyFactory!=null){
			ResidueSuccessDrawingStrategy strategy = new ResidueSuccessDrawingStrategy();
			strategyFactory.register(strategy);
			if (settingsManager!=null){
				settingsManager.setProperty(SUCCESS_METRICS_STRATEGY_KEY, strategy.getId());
			}
		}
	}

}
