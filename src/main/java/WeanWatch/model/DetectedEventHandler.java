package WeanWatch.model;

import java.util.ArrayList;
import java.util.function.Predicate;

public class DetectedEventHandler {
    // Store the detected Events
    private ArrayList<DetectedEvent> detectedEvents;

    public DetectedEventHandler() {
        // Prepare the detected Event array
        this.detectedEvents = new ArrayList<DetectedEvent>();
    }

	public ArrayList<DetectedEvent> getDetectedEvents() {
		// Return the matching Events
        return this.detectedEvents;
	}

	/**
	 * Returnes all detected Events of the Event that has the given Event name
	 * @param EventName Name of Event to retuen detected Events for
	 * @return Array of detected Events of type Event with Event name
	 */
	public ArrayList<DetectedEvent> getDetectedEvents(String eventName) {
		// Return the matching Events
        return this.getDetectedEvents(detectedEvent -> detectedEvent.getEvent().getName().equals(eventName));
	}

	public ArrayList<DetectedEvent> getDetectedEvents(TimeInterval between) {
		// Return the matching Events
        return this.getDetectedEvents(detectedEvent -> between.contains(detectedEvent.getEventInterval()));
	}

	public ArrayList<DetectedEvent> getDetectedEvents(TimeInterval between, String eventName) {
		// Return the matching Events
        return this.getDetectedEvents(detectedEvent -> detectedEvent.getEvent().getName().equals(eventName) && between.contains(detectedEvent.getEventInterval()));
	}

	private ArrayList<DetectedEvent> getDetectedEvents(Predicate<DetectedEvent> predicate) {
		ArrayList<DetectedEvent> foundEvents = new ArrayList<DetectedEvent>();
		for (DetectedEvent detectedEvent : this.detectedEvents) {
			if (predicate.test(detectedEvent)) {
				foundEvents.add(detectedEvent);
			}
		}
		// Return the matching Events
        return foundEvents;
	}

	public void addEvent(DetectedEvent detectedEvent) {
		this.detectedEvents.add(detectedEvent);
	}

	public void removeEvent(DetectedEvent detectedEvent) {
		this.detectedEvents.remove(detectedEvent);
	}
}
