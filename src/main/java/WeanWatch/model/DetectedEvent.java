package WeanWatch.model;

import javafx.scene.layout.VBox;

public class DetectedEvent {
	// Store the patient for which the Event is detected
	private Patient patient;
	// Store a reference to the Event
	private Event eventType;
	// Store the time interval in which the Event occured
	private TimeInterval eventInterval;

	private VBox inspectFigure = null;
	private VBox overviewFigure = null;

	// Constructor
	public DetectedEvent(Patient patient, Event eventType, TimeInterval eventInterval){
		this.patient = patient;
		this.eventType = eventType;
		this.eventInterval = eventInterval;
	}
	
	// Getters
	public synchronized Patient getPatient() {
		return this.patient;
	}

	public synchronized Event getEvent() {
		return this.eventType;
	}

	public synchronized TimeInterval getEventInterval() {
		return this.eventInterval;
	}

	public synchronized void setInspectFigure(VBox inspectFigure) {
		this.inspectFigure = inspectFigure;
	}

	public synchronized VBox getInspectFigure() {
		return this.inspectFigure;
	}

	public synchronized void setOverviewFigure(VBox overviewFigure) {
		this.overviewFigure = overviewFigure;
	}

	public synchronized VBox getOverviewFigure() {
		return this.overviewFigure;
	}
}
