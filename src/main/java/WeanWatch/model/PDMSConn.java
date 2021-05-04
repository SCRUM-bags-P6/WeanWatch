package WeanWatch.model;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class PDMSConn extends Server {
    // Store an instance of the object, that can be shared
    private static PDMSConn instance = null;

    // Set the constructor private
    private PDMSConn() {
        // Call the super constructor

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

        SparkSession session = SparkSession.builder().appName("PDMSConn").getOrCreate();



        DataFrameReader dataFrameReader = session.read();

        Dataset<Row> data = dataFrameReader.option("header", true).csv("data/stud1.csv");

        Column column = data.col("UnitId");

        System.out.println( data.select(column).count() );

        //data.select(col("UnitId"))

        // Patient[] patients = {
        //     new Patient("123456-1122", "John Johnson", 69,)
        // };

    }

}
