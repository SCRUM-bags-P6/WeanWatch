package WeanWatch.model;

import org.apache.spark.sql.Dataset;

public class Patient {
    // Parameters
    private Dataset<PatientData> patientData; // Patient dataset
    private DetectedCaseHandler patientCaseHandler; // Case handler
    
    // Patient information
    private String cpr;
    private int age;
    private String name;

    // Constructor
    public Patient(String cpr, String name, int age, Dataset<PatientData> patientData) {
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

    public DetectedCaseHandler getCaseHandler() {
        return this.patientCaseHandler;
    }

    public Dataset<PatientData> getPatientData() {
        return this.patientData;
    }
    
}
