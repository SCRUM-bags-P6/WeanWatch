package WeanWatch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.concurrent.CopyOnWriteArrayList;

public class DetectedEventHandler implements Serializable {
    // Store the detected Events
    private CopyOnWriteArrayList<DetectedEvent> detectedEvents;

    public DetectedEventHandler() {
		System.out.println("Creating new detected event handler");
        // Prepare the detected Event array
        this.detectedEvents = new CopyOnWriteArrayList<DetectedEvent>();
    }

	public synchronized List<DetectedEvent> getDetectedEvents() {
		System.out.println("Getting detected events");
		// Return the matching Events
        return this.detectedEvents;
	}

	/**
	 * Returnes all detected Events of the Event that has the given Event name
	 * @param EventName Name of Event to retuen detected Events for
	 * @return Array of detected Events of type Event with Event name
	 */
	public synchronized List<DetectedEvent> getDetectedEvents(String eventName) {
		// Return the matching Events
        return this.getDetectedEvents(detectedEvent -> detectedEvent.getEvent().getName().equals(eventName));
	}

	public synchronized List<DetectedEvent> getDetectedEvents(TimeInterval between) {
		// Return the matching Events
        return this.getDetectedEvents(detectedEvent -> between.contains(detectedEvent.getEventInterval()));
	}

	public synchronized List<DetectedEvent> getDetectedEvents(TimeInterval between, String eventName) {
		// Return the matching Events
        return this.getDetectedEvents(detectedEvent -> detectedEvent.getEvent().getName().equals(eventName) && between.contains(detectedEvent.getEventInterval()));
	}

	private synchronized List<DetectedEvent> getDetectedEvents(Predicate<DetectedEvent> predicate) {
		List<DetectedEvent> foundEvents = new ArrayList<DetectedEvent>();
		for (DetectedEvent detectedEvent : this.detectedEvents) {
			if (predicate.test(detectedEvent)) {
				foundEvents.add(detectedEvent);
			}
		}
		// Return the matching Events
        return foundEvents;
	}

	public synchronized void addEvent(DetectedEvent detectedEvent) {
		this.detectedEvents.add(detectedEvent);
	}

	public synchronized void removeEvent(DetectedEvent detectedEvent) {
		this.detectedEvents.remove(detectedEvent);
	}
}
