package WeanWatch.model;

import java.io.Serializable;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class Patient implements Serializable{
    // Parameters
    private Dataset<Row> patientData; // Patient dataset
    private DetectedCaseHandler patientCaseHandler = null; // Case handler
    
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

    public DetectedCaseHandler getDetectedCaseHandler() {
        // If the patient has no case handler, create one
        if (this.patientCaseHandler == null) {
            this.patientCaseHandler = new DetectedCaseHandler();
        }
        // Return the case handler
        return this.patientCaseHandler;
    }

    public Dataset<Row> getData() {
        return this.patientData;
    }
    
}
