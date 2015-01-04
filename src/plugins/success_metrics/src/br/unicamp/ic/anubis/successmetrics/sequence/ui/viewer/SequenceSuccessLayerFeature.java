package br.unicamp.ic.anubis.successmetrics.sequence.ui.viewer;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.SEQUENCE_SUCCESS_DATACONNECTOR_ID;
import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.SUCCESS_METRICS_STRATEGY_KEY;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.successmetrics.common.metricsstrategy.SuccessMetricsStrategyFactory;

public class SequenceSuccessLayerFeature implements IFeature {

	private static UUID id = UUID
			.fromString("37fb9a0b-8ba2-438b-9e9a-a45352e41cbe");
	private static UUID featureId = UUID
			.fromString("a79cc9c6-23b7-4b6e-9beb-548a2ac62083");

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
		requiredInterfaces.add(SEQUENCE_SUCCESS_DATACONNECTOR_ID);
		return requiredInterfaces;
	}
	@Override
	public void enable() {
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settingsManager  = manager.getSettingsManager();

		SuccessMetricsStrategyFactory strategyFactory = SuccessMetricsStrategyFactory.getInstance();
		if (strategyFactory!=null){
			SequenceSuccessStrategy strategy = new SequenceSuccessStrategy();
			strategyFactory.register(strategy);
			if (settingsManager!=null){
				settingsManager.setProperty(SUCCESS_METRICS_STRATEGY_KEY, strategy.getId());
			}
		}
	}

}
