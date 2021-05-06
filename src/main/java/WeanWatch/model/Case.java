package WeanWatch.model;

import java.util.List;

public class Case {
    // Store the case name and description
    private String name;
    private String description;

    // Store the case indicators
    private List<Indicator> characteristics;

    // Constructor
    public Case(String name, String description, Indicator[] characteristics) {
        this.name = name;
        this.description = description;
        // Add the characteristics
        for (Indicator characteristic : characteristics) {
            this.characteristics.add(characteristic);
        }
    }

    // Getters
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Indicator[] getCharacteristics() {
        // Prepare an array of Indicators with the size of the list of predicators
        Indicator[] indicators = new Indicator[this.characteristics.size()];
        // Fill the array with the indicators
        for (int num = 0; num < this.characteristics.size(); num++) {
            indicators[num] = this.characteristics.get(num);
        }
        // Return the array of indicators
        return indicators;
    }
}
