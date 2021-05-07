package WeanWatch.model;

import java.util.ArrayList;

public class Case {
    // Store the case name and description
    private String name;
    private String description;

    // Store the case indicators
    private ArrayList<Indicator> characteristics;

    // Constructor
    public Case(String name, String description, ArrayList<Indicator> characteristics) {
        this.name = name;
        this.description = description;
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
}
