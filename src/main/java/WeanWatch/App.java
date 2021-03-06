 /*
 * This Java source file was generated by the Gradle 'init' task.
 */
package WeanWatch;

import java.awt.Dimension;

import WeanWatch.controller.LoginCtrl;
import WeanWatch.controller.RootCtrl;
import WeanWatch.model.Event;
import WeanWatch.model.EventDetectorThread;
import WeanWatch.model.EventHandler;
import WeanWatch.model.DetectionAlgorithm;
import WeanWatch.model.Indicator;
import WeanWatch.model.IndicatorAlgorithm;
import WeanWatch.model.PDMSConn;
import WeanWatch.model.PatientHandler;
import WeanWatch.model.Personnel;
import WeanWatch.model.TimeInterval;
import WeanWatch.model.Event.Severity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;


public class App extends Application {

    // Store the primary scene
    private Stage weanStage;

    public static void main(String[] args) {
        // Start loading the patients form the PDMS
        PatientHandler.getInstance().getPatients();
        
        // Initialize patient cases
        initialze();

        //Instantierer vores thread
		EventDetectorThread detectorGadget = EventDetectorThread.getInstance();
		//Initialiserer threaden og kører dens run() metode
		detectorGadget.initialize();
		//detectorGadget.start();

        // Launch JavaFX
        launch(args); 
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Store the primary stage
        this.weanStage = primaryStage;
        // Show the login screen
        this.userLogOutCallback();
        // Show the stage
        primaryStage.show();
    }

    protected void userLoginCallback(Personnel user) {
        // Get the scene size
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        // Create a FXML loader
        FXMLLoader loader = new FXMLLoader();
        // Set the location of the login view
		loader.setLocation(App.class.getClassLoader().getResource("view/RootView.fxml"));
        // Try loading the borderpane
        try {
            // Load the borderpane
            BorderPane rootView = (BorderPane) loader.load();
            // Set the scene
            this.weanStage.setScene(new Scene(rootView, screenSize.getWidth(), screenSize.getHeight()));
            // Get the controller
            RootCtrl rootCtrl = loader.getController();
            // Set the logout callback
            rootCtrl.setLogOutCallback(() -> {
                userLogOutCallback();
            });
            // Set the personnel
            rootCtrl.setPersonnel(user);
        } catch (IOException e) {
            System.err.println("Failed to display navigation screen.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unhandled exception.");
            e.printStackTrace();
        }
    }

    protected void userLogOutCallback() {
        // Get the scene size
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        // Create a FXML loader
        FXMLLoader loader = new FXMLLoader();
        // Set the location of the login view
		loader.setLocation(App.class.getClassLoader().getResource("view/LoginView.fxml"));
        // Try loading the borderpane
        try {
            // Load the borderpane
            BorderPane loginView = (BorderPane) loader.load();
            // Set the scene
            this.weanStage.setScene(new Scene(loginView, screenSize.getWidth(), screenSize.getHeight()));
            // Get the controller
            LoginCtrl loginCtrl = loader.getController();
            // Set the login callback
            loginCtrl.setLoginCallback((Personnel user) -> {
                userLoginCallback(user);
            });
        } catch (IOException e) {
            System.err.println("Failed to display login screen.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unhandled exception.");
            e.printStackTrace();
        }
    }

    private static void initialze() {
        // Initialize apnea characteristics array
        ArrayList<Indicator> apneaChar = new ArrayList<Indicator>();

        //Add indicators to array
        apneaChar.add(new Indicator(1, (Predicate<Row> & Serializable)(Row x) -> {
            return Double.compare(x.getDouble(x.fieldIndex("Rf")), 8D)  <= 0; 
        }));
        apneaChar.add(new Indicator(1, (Predicate<Row> & Serializable)(Row x) -> {
            return Double.compare(x.getDouble(x.fieldIndex("FetCO2")), 0.045D) <= 0; 
        }));
        apneaChar.add(new Indicator(1, (Predicate<Row> & Serializable)(Row x) -> {
            return Double.compare(x.getDouble(x.fieldIndex("EE")), 1000D) <= 0; 
        }));
        apneaChar.add(new Indicator(3, (Predicate<Row> & Serializable)(Row x) -> {
            return Double.compare(x.getDouble(x.fieldIndex("Comp")), 0.015D) <= 0; 
        }));

        // Define apnea detection algorithm
        DetectionAlgorithm apneaAlgo = new IndicatorAlgorithm(apneaChar);
        // Define apnea event
        EventHandler.getInstance().addEvent("    Apnea", "Apnea event", Severity.SEVERE, apneaAlgo);



        // Initialize respiratory muscle fatigue characteristics array
        ArrayList<Indicator> muscleFatigueChar = new ArrayList<Indicator>();

        //Add indicators to array
        muscleFatigueChar.add(new Indicator(15, (Predicate<Row> & Serializable)(Row x) -> {
            return Double.compare(x.getDouble(x.fieldIndex("EE")), 2400D)  >= 0; 
        }));
        /*muscleFatigueChar.add(new Indicator(15, (Predicate<Row> & Serializable)(Row x) -> {
            return Double.compare(x.getDouble(x.fieldIndex("FetCO2")), 0.05D) >= 0; 
        })); */
        muscleFatigueChar.add(new Indicator(15, (Predicate<Row> & Serializable)(Row x) -> {
            return Double.compare(x.getDouble(x.fieldIndex("RSBI")), 105D) >= 0; 
        }));
        muscleFatigueChar.add(new Indicator(30, (Predicate<Row> & Serializable)(Row x) -> {
            return Double.compare(x.getDouble(x.fieldIndex("Rf")), 25D) >= 0; 
        }));

        // Define muscle fatigue detection algorithm
        DetectionAlgorithm muscleFatigueAlgo = new IndicatorAlgorithm(muscleFatigueChar);
        // Define muscle fatigue event
        EventHandler.getInstance().addEvent("Muscle fatigue", "Muscle fatigue event", Severity.SEVERE, muscleFatigueAlgo);



        // Initialize reduced lung health characteristics array
        ArrayList<Indicator> lungHealthChar = new ArrayList<Indicator>();

        //Add indicators to array
        lungHealthChar.add(new Indicator(60, (Predicate<Row> & Serializable)(Row x) -> {
            double SpO2 = x.getDouble(x.fieldIndex("SpO2"));
            double FiO2set = x.getDouble(x.fieldIndex("FiO2Set"));

            //double a = 11700D*Math.pow((Math.pow(SpO2,-1D)-1D),-1D);
            //double b = Math.pow(Math.pow(50D,3D)+Math.pow(a,2D),0.5D);
            //double PaO2 = Math.pow(b+a,1D/3D) - Math.pow(b-a,1D/3D);
            
            //double SpPaO2 = SpO2/PaO2;
            //double PaFiO2 = PaO2/FiO2set;
            double SpFiO2 = SpO2/FiO2set;

            //return Double.compare(SpPaO2, 200D)  >= 0; 
            //return Double.compare(PaFiO2, 200D)  >= 0;
            return Double.compare(SpFiO2, 200D)  >= 0; 
        }));

        // Define lung health detection algorithm
        DetectionAlgorithm lungHealthAlgo = new IndicatorAlgorithm(lungHealthChar);
        // Define lung health event
        EventHandler.getInstance().addEvent("Reduced lung health", "Reduced lung health event", Severity.SEVERE, lungHealthAlgo);



        // Initialize too high oxygen characteristics array
        ArrayList<Indicator> highOxygenChar = new ArrayList<Indicator>();

        //Add indicators to array
        highOxygenChar.add(new Indicator(120, (Predicate<Row> & Serializable)(Row x) -> {
            return Double.compare(x.getDouble(x.fieldIndex("FiO2Set")), 0.80D)  >= 0;  /// (> betyder større end tærskel) krokodillenæb skal vende om for at detektere korrekt
        }));

        // Define high oxygen detection algorithm
        DetectionAlgorithm highOxygenAlgo = new IndicatorAlgorithm(highOxygenChar);
        // Define high oxygen event
        EventHandler.getInstance().addEvent("Too high oxygen", "Too high oxygen event", Severity.INTERMEDIATE, highOxygenAlgo);
    }

}