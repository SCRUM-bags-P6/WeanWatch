package WeanWatch.model;

import java.util.ArrayList;

import WeanWatch.model.Event.Severity;

public class EventHandler {
	private static EventHandler instance = null;

	private ArrayList<Event> events = new ArrayList<Event>();

	private EventHandler() {
	}

	public static EventHandler getInstance() {
		if (EventHandler.instance == null) {
            // Create an instance and store it
            EventHandler.instance = new EventHandler();
        }
        // Return the stored instance
        return EventHandler.instance;
	}

	public void addEvent(String name, String description, Severity severity, ArrayList<Indicator> characteristics){
		Event patientEvent = new Event(name, description, severity, characteristics);
		this.events.add(patientEvent);
	}

	public void addEventIndicator(String name, String description, Severity severity, ArrayList<Indicator> characteristics) {
		Event patientEvent = new Event(name, description, severity, characteristics);
		this.events.add(patientEvent);
	}

	public void addEventAlgo(String name, String description, Severity severity, DetectionAlgorithm algo) {
		Event patientEvent = new Event(name, description, severity, algo);
		this.events.add(patientEvent);
	}

	public void removeEvent(String eventName) {
		this.events.removeIf(x -> x.getName().equals(eventName));
	}

	public Event getEvent(String eventName) {
		for (Event tempEvent : this.events) {
			if (tempEvent.getName().equals(eventName)) {
				return tempEvent;
			}
		}
		return null;
	}

	public ArrayList<Event> getEvents() {
		return this.events;
	}
}
