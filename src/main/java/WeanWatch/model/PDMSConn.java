package WeanWatch.model;

import java.io.FileReader;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

public class PDMSConn extends Server {
    // Store an instance of the object, that can be shared
    private static PDMSConn instance = null;

    // For development purposes, the patient data is loaded from CSV files
    // Store the spark session
    private static SparkSession session;
    // Store the data frame reader
    private static DataFrameReader dataFrameReader;

    // Set the constructor private
    private PDMSConn() {
        // Call the super constructor

        // For development purposes, the patient data is loaded from CSV files
        // Create the spark session
        //session = SparkSession.builder().appName("PDMSConn").config("spark.master", "local").getOrCreate();
        // Create a data frame reader
        //dataFrameReader = session.read();

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
    public void getPatients() {

        // For development purposes, the patient data is loaded from CSV files
        // Prepare a JSON parser
        JSONParser jsonParser = new JSONParser();
        // Try reading the patients.json file
        try (
            // Try getting the patients.json file
            FileReader fileReader = new FileReader("data/patients.json");
        ) {
            // Try parsing the json file
            JSONObject parsedJSON = (JSONObject) jsonParser.parse(fileReader);

            JSONArray parsedPatients = (JSONArray) parsedJSON.get("patients");

            parsedPatients.forEach(patient -> {
                System.out.println(patient);
            });

            // Try reading the JSON from the parsed file
            //JSONArray parsedJSON = (JSONArray) parsedObj;


            //System.out.println(parsedJSON);

        } catch (Exception e) {
            //TODO: handle exception
            System.err.println(e);
        }

        

        // Dataset<Row> data = PDMSConn.dataFrameReader.option("header", true).csv("data/stud1.csv");

        // Column column = data.col("UnitId");

        // System.out.println( data.select(column).count() );

        //data.select(col("UnitId"))

        // Patient[] patients = {
        //     new Patient("123456-1122", "John Johnson", 69,)
        // };

    }

}
