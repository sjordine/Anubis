package br.unicamp.ic.anubis.successmetrics.common.metricsstrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SuccessMetricsStrategyFactory {

	private static SuccessMetricsStrategyFactory instance = new SuccessMetricsStrategyFactory();

	private HashMap<UUID, ISuccessMetricsStrategy> strategies = null;

	private SuccessMetricsStrategyFactory() {
		strategies = new HashMap<UUID, ISuccessMetricsStrategy>();
	}

	public static SuccessMetricsStrategyFactory getInstance() {
		return instance;
	}

	public void register(ISuccessMetricsStrategy strategy) {
		if (strategy != null) {
			strategies.put(strategy.getId(), strategy);
		}
	}
	
	public ISuccessMetricsStrategy get(UUID key){
		ISuccessMetricsStrategy returnValue = null;
		
		if (strategies.containsKey(key)){
			returnValue = strategies.get(key);
		}
		
		return returnValue;
	}

	public List<ISuccessMetricsStrategy> getStrategies() {
		List<ISuccessMetricsStrategy> returnValue;

		returnValue = new ArrayList<ISuccessMetricsStrategy>(
				strategies.values());

		return returnValue;
	}

}
