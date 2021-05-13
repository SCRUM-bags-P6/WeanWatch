package WeanWatch.controller;

import java.util.ArrayList;
import java.util.List;

import WeanWatch.model.Patient;
import WeanWatch.model.Personnel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class RootCtrl {
    
    private Personnel user;

    private Patient selectedPatient; 

    private NavigatableCtrl childNode;  

    @FXML
    private StackPane rootSubAnchorPane;

    // Constructor for RootCtrl
    public RootCtrl() {

    }




    @FXML
    private void initialize() {
        // Show the patient select on the first run
        handleSelectedPatientClick();
    }

    public void handleSelectedPatientClick() {
        changeView("/view/PatientSelectView.fxml");
        // try {
        //     // Load the patient select view
        //     FXMLLoader loader = new FXMLLoader();
        //     loader.setLocation(getClass().getResource("/view/PatientSelectView.fxml"));
        //     VBox patientSelectView = (VBox) loader.load();
        //     // Display the patient select view
        //     this.rootSubAnchorPane.getChildren().setAll(patientSelectView);
        // } catch (Exception e) {
        //     System.out.println("Failed to display the patient select view, with error:");
        //     e.printStackTrace();
        // }
    } 

    public void handleShowInspectClick(InspectCtrl inspectCtrl) {
        
    }

    public void handleShowOverviewClick() {
        changeView("/view/OverviewView.fxml");
        //   try {
        //     // Load the patient select view
        //     FXMLLoader loader = new FXMLLoader();
        //     loader.setLocation(getClass().getResource("/view/OverviewView.fxml"));
        //     VBox Overview = (VBox) loader.load();
        //     // Display the patient select view
        //     this.rootSubAnchorPane.getChildren().setAll(Overview);
        // } catch (Exception e) {
        //     System.out.println("Failed to display the overview, with error:");
        //     e.printStackTrace();
        // }

    }

    public void handleLogoutClick() {

    }

    private void changeView(String path) {
        try {
            // Load the view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            Region loadedView = (Region) loader.load();
            // Display the view
            this.rootSubAnchorPane.getChildren().setAll(loadedView);
        } catch (Exception e) {
            System.out.println("Failed to display view with path: " + path + ", with error:");
            e.printStackTrace();
        }
    }

    // Return the selected patient
    public Patient getPatient() {
        return this.selectedPatient;
    }

    // Set the patient
    public void setPatient(Patient patient) {
        // Set the patient
        this.selectedPatient = patient;
        // Make DetectorThread priotitize the newly selected patient
        //TODO: Prioritize patient in thread
    }

    public void setPersonnel(Personnel personnel) {

    }


   

    

}
