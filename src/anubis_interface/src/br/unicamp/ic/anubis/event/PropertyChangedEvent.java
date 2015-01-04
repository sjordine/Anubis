package br.unicamp.ic.anubis.event;

import br.unicamp.ic.anubis.mechanism.messaging.IEvent;

public class PropertyChangedEvent implements IEvent {
	
	private String propertyName;
	
	
	public PropertyChangedEvent(String propertyName){
		this.propertyName = propertyName;
	}
	

	public String getPropertyName() {
		return propertyName;
	}

}
