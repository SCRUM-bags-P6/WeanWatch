package WeanWatch.model;

import java.util.ArrayList;

public class Case {
    // Store the case name and description
    private String name;
    private String description;
	private Case.Severity severity;
    private DetectionAlgorithm algorithm;

    // Store the case indicators
    private ArrayList<Indicator> characteristics;

    // Severity
    public enum Severity {
        MILD,
        INTERMEDIATE,
        SEVERE
    }

    // Constructor
    public Case(String name, String description, Case.Severity severity, ArrayList<Indicator> characteristics) {
        this.name = name;
        this.description = description;
		this.severity = severity;
        this.characteristics = characteristics;
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
