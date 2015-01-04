package br.unicamp.ic.anubis.mechanism.settings;

import java.util.HashMap;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.PropertyChangedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;

public class SettingsManager implements ISettingsManager {

	private static SettingsManager instance = new SettingsManager();
	private HashMap<String, Object> settings;

	private SettingsManager() {
		settings = new HashMap<String, Object>();
	}

	public void setProperty(String propertyName, Object property) {
		Object previousValue = null;
		if (settings.containsKey(propertyName)){
			previousValue = settings.get(propertyName);
		}
		settings.put(propertyName, property);
		if (previousValue==null || !previousValue.equals(property)){
			notifyPropertyChange(propertyName);
		}
	}

	public Object getProperty(String propertyName) {
		Object returnValue = null;

		returnValue = settings.get(propertyName);

		return returnValue;
	}

	public static SettingsManager getInstance() {
		return instance;
	}

	private void notifyPropertyChange(String propertyName) {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();
		if (eventManager != null) {
			IEvent message = new PropertyChangedEvent(propertyName);
			eventManager.raise(message);
		}
	}

}
