package WeanWatch.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.apache.spark.sql.Row;

public class Event {
    // Store the Event name and description
    private String name;
    private String description;
	private Event.Severity severity;
    private DetectionAlgorithm algorithm;

    // Store the Event indicators
    private ArrayList<Indicator> characteristics;


    // Severity
    public enum Severity {
        MILD,
        INTERMEDIATE,
        SEVERE
    }

    // Constructor 
    public Event(String name, String description, Event.Severity severity, ArrayList<Indicator> characteristics) {
        this.name = name;
        this.description = description;
		this.severity = severity;
        this.characteristics = characteristics;
    }

	public Event(String name, String description, Event.Severity severity, DetectionAlgorithm algo) {
        this.name = name;
        this.description = description;
		this.severity = severity;
        this.algorithm= algo;
    }

	

	

    // Getters
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public ArrayList<Indicator> getCharacteristics() {
        // Return the array of indicators
        return this.characteristics;
    }

	public DetectionAlgorithm getAlgorithm(){
		return this.algorithm;
	}
    
    public Severity getSeverity() {
        return this.severity;
    }
}
