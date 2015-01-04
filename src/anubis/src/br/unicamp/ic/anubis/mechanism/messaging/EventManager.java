package br.unicamp.ic.anubis.mechanism.messaging;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.unicamp.ic.anubis.event.CloseEvent;

public class EventManager implements IEventManager, Runnable, IEventHandler {

	private static final int SLEEP_TIME = 10;
	private boolean stopExecution = false;

	private List<IEvent> eventList;
	private HashMap<Class, List<IEventHandler>> eventReceiverList;

	private static EventManager _instance = new EventManager();

	private EventManager() {
		eventList = new ArrayList<IEvent>();
		eventReceiverList = new HashMap<Class, List<IEventHandler>>();

		register(CloseEvent.class, this);

		Thread processingThread = new Thread(this);
		processingThread.start();
	}

	public synchronized void register(Class eventClass, IEventHandler handler) {

		if (eventReceiverList.containsKey(eventClass)) {
			List<IEventHandler> receivers = eventReceiverList.get(eventClass);
			receivers.add(handler);
		} else {
			List<IEventHandler> receivers = new ArrayList<IEventHandler>();
			receivers.add(handler);
			eventReceiverList.put(eventClass, receivers);
		}
	}

	public synchronized void raise(IEvent event) {
		eventList.add(event);
	}

	@Override
	public void run() {

		while (!stopExecution) {

			IEvent currentEvent = null;
			synchronized (this) {
				if (eventList.size() > 0) {
					currentEvent = eventList.remove(0);
				}
			}
			if (currentEvent != null) {
				Class eventClass = currentEvent.getClass();
				List<IEventHandler> receivers = null;
				synchronized (this) {
					if (eventReceiverList.containsKey(eventClass)) {
						receivers = eventReceiverList.get(eventClass);
					}
					if (receivers != null) {
						for (IEventHandler handler : receivers) {
							EventProcess processor = new EventProcess(
									currentEvent, handler);
							Thread handlerThread = new Thread(processor);
							handlerThread.start();
						}
					}
				}
			}
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				// TODO handle exception
			}
		}
		
		//Finishes application. 
		//TODO: Check how to finish it smoothly, as
		//UI thread still running
		System.exit(0);
		
	}

	public static IEventManager getInstance() {
		return _instance;
	}

	@Override
	public void eventRaised(IEvent message) {
		if (message instanceof CloseEvent) {
			stopExecution = true;

		}

	}

	@Override
	public synchronized void unregister(IEventHandler eventHandler) {
		for (List<IEventHandler> eventList:  eventReceiverList.values()){
			if (eventList.contains(eventHandler)){
				eventList.remove(eventHandler);
			}
		}
		
	}

}

class EventProcess implements Runnable {

	private IEvent targetEvent;
	private IEventHandler eventHandler;

	public EventProcess(IEvent event, IEventHandler handler) {
		targetEvent = event;
		eventHandler = handler;
	}

	@Override
	public void run() {
		eventHandler.eventRaised(targetEvent);
	}

}
