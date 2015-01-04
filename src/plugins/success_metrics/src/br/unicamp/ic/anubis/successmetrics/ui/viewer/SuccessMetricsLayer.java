package br.unicamp.ic.anubis.successmetrics.ui.viewer;

import java.awt.Image;
import java.awt.Insets;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.successmetrics.common.metricsstrategy.ISuccessMetricsStrategy;
import br.unicamp.ic.anubis.successmetrics.common.metricsstrategy.SuccessMetricsStrategyFactory;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.SUCCESS_METRICS_STRATEGY_KEY;

import br.unicamp.ic.anubis.ui.viewer.ILayer;

public class SuccessMetricsLayer implements ILayer, IEventHandler {

	private boolean enabled;
	private int transparency = 100;

	@Override
	public Image GetImage(int alignmentIndex, int width, int height, Insets borders) {
		
		ISuccessMetricsStrategy strategy = getStrategy();
		
		Image image = SuccessMetricsDrawer.GetImage(alignmentIndex, strategy,
				width, height, transparency, borders);

		return image;
	}

	@Override
	public String getLabel() {
		return "Success %";
	}

	@Override
	public boolean IsEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		notifyRedraw();
	}

	@Override
	public int getTransparency() {
		double factor = ((double) transparency) / 255.0;
		return (int) (factor * 100);
	}

	@Override
	public void setTransparency(int transparency) {
		double factor = ((double) transparency) / 100.0;
		this.transparency = (int) (255 * factor);
		notifyRedraw();
	}

	private  ISuccessMetricsStrategy getStrategy() {
		ISuccessMetricsStrategy strategy = null;
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		ISettingsManager settingsManager = manager.getSettingsManager();
		SuccessMetricsStrategyFactory metricsFactory = SuccessMetricsStrategyFactory
				.getInstance();

		if (resourceManager != null) {
			if (settingsManager != null && metricsFactory != null) {
				// Get strategy
				UUID strategyId = (UUID) settingsManager
						.getProperty(SUCCESS_METRICS_STRATEGY_KEY);
				if (strategyId != null) {
					strategy = metricsFactory.get(strategyId);
				}
			}
		}

		return strategy;
	}

	public void notifyRedraw() {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();

		if (eventManager != null) {

			RedrawAlignmentEvent redrawEvent = new RedrawAlignmentEvent(
					AlignmentUtil.BOTH_ALIGNMENTS);
			eventManager.raise(redrawEvent);
		}
	}

	@Override
	public void eventRaised(IEvent message) {
		if (message instanceof PropertyChangedEvent){
			PropertyChangedEvent event = (PropertyChangedEvent)message;
			if (SUCCESS_METRICS_STRATEGY_KEY.equals(event.getPropertyName())){
				notifyRedraw();
			}
		}
	}
	
	@Override
	public Insets getBorders(int alignmentIndex) {
		return new Insets(2,2,2,2);
	}


	@Override
	public int getTextRows(int alignmentIndex) {
		return 0;
	}

	@Override
	public int getTextColumns(int alignmentIndex) {
		return 0;
	}

}
