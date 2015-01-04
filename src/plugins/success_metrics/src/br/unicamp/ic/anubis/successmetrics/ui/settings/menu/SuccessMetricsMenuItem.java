package br.unicamp.ic.anubis.successmetrics.ui.settings.menu;

import java.util.UUID;

import br.unicamp.ic.anubis.successmetrics.common.metricsstrategy.ISuccessMetricsStrategy;



public class SuccessMetricsMenuItem {
	

    private UUID id;
    private String name;

    public SuccessMetricsMenuItem(ISuccessMetricsStrategy strategy) {
        this.id = strategy.getId();
        this.name = strategy.getName();
    }

    public UUID getId() {
        return id;
    }
   
    public String toString(){
        return this.name;
    }

}
