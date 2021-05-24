package WeanWatch.model;

import java.io.Serializable;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class Patient implements Serializable{
    // Parameters
    private Dataset<Row> patientData; // Patient dataset
    private DetectedEventHandler patientEventHandler = null; // Event handler
    
    // Patient information
    private String cpr;
    private int age;
    private String name;

    // Constructor
    public Patient(String cpr, String name, int age, Dataset<Row> patientData) {
        // Store the input
        this.cpr = cpr;
        this.name = name;
        this.age = age;
        this.patientData = patientData;
    }

    // Getters
    public String getCPR() {
        return this.cpr;
    }

    public int getAge(){
        return this.age;
    }

    public String getName() {
        return this.name;
    }

    public DetectedEventHandler getDetectedEventHandler() {
        // If the patient has no Event handler, create one
        if (this.patientEventHandler == null) {
            this.patientEventHandler = new DetectedEventHandler();
        }
        // Return the Event handler
        return this.patientEventHandler;
    }

    public Dataset<Row> getData() {
        return this.patientData;
    }
    
}
