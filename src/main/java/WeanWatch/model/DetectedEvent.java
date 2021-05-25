package WeanWatch.model;

public class DetectedEvent {
	// Store the patient for which the Event is detected
	private Patient patient;
	// Store a reference to the Event
	private Event eventType;
	// Store the time interval in which the Event occured
	private TimeInterval eventInterval;

	// Constructor
	public DetectedEvent(Patient patient, Event eventType, TimeInterval eventInterval){
		this.patient = patient;
		this.eventType = eventType;
		this.eventInterval = eventInterval;
	}
	
	// Getters
	public Patient getPatient() {
		return this.patient;
	}

	public Event getEvent() {
		return this.eventType;
	}

	public TimeInterval getEventInterval() {
		return this.eventInterval;
	}
}
