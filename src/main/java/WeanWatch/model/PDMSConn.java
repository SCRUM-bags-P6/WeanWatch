package WeanWatch.model;

import java.io.FileReader;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

public class PDMSConn {
    // Store an instance of the object, that can be shared
    private static PDMSConn instance = null;

    // For development purposes, the patient data is loaded from CSV files
    // Store the spark session
    private static SparkSession session;
    // Store the data frame reader
    private static DataFrameReader dataFrameReader;
    // Data folder
    private static final String dataFolder = "data/";

    // Set the constructor private
    private PDMSConn() {
        // Call the super constructor

        // For development purposes, the patient data is loaded from CSV files
        // Create the spark session
        session = SparkSession.builder().appName("PDMSConn").config("spark.master", "local").getOrCreate();
        // Create a data frame reader
        dataFrameReader = session.read();

    }

    // Create the method to get the shared instance
    public static PDMSConn getInstance() {
        // Create a instance if none has been created
        if (PDMSConn.instance == null) {
            // Create an instance and store it
            PDMSConn.instance = new PDMSConn();
        }
        // Return the stored instance
        return PDMSConn.instance;
    }

    // Get parameters avaliable for display
    public String[] getParameters() {

        String[] temp = {"Param1", "Param2"};
        
        return temp;

    }

    // Get patients
    //public Patient[] getPatients() {
    public Patient[] getPatients() {
        // For development purposes, the patient data is loaded from CSV files
        // Prepare a JSON parser
        JSONParser jsonParser = new JSONParser();
        // Try reading the patients.json file
        try (
            // Try getting the patients.json file
            FileReader fileReader = new FileReader(PDMSConn.dataFolder + "patients.json");
        ) {
            // Try parsing the json file
            JSONObject parsedJSON = (JSONObject) jsonParser.parse(fileReader);
            // Get the patients entry
            JSONArray parsedPatients = (JSONArray) parsedJSON.get("patients");
            // Prepare an array of patients
            Patient[] patients = new Patient[parsedPatients.size()];
            // Create a patient instance for each patient in the JSON
            for (int num = 0; num < parsedPatients.size(); num++) {
                // Get the patient as a JSONObject
                JSONObject patient = (JSONObject) parsedPatients.get(num);
                // Prepare a dataset to store the patient data
                Dataset<Row> patientData;
                // Load the patient dataset, if it is set
                if (patient.get("filename") != null) {
                    patientData = PDMSConn.dataFrameReader.option("header", true).csv(PDMSConn.dataFolder + patient.get("filename"));
                } else {
                    patientData = null;
                }
                // Create the patient instance and store the cpr, name and age, as well as the dataset
                patients[num] = new Patient(
                    patient.get("cpr").toString(),
                    patient.get("name").toString(),
                    Integer.parseInt(patient.get("age").toString()),
                    patientData
                );
            }
            // Return the patients
            return patients;
        } catch (Exception e) {

            //TODO: handle exception
            System.err.println("Failed to load patients from the json file with error:" + e);
            e.printStackTrace();

            return null;
        }
    }

}
