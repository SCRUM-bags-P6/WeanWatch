package WeanWatch.controller;

import java.io.IOException;

import WeanWatch.model.Event;
import WeanWatch.model.EventDetectorThread;
import WeanWatch.model.Patient;
import WeanWatch.model.Personnel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class RootCtrl {
    
    @FXML
    private Label clinicianID;

    @FXML
    private Label patientID;

    private Personnel user;

    private Patient selectedPatient; 

    private NavigatableCtrl childNode;  

    private Runnable logOutCallback;

    @FXML
    private StackPane rootSubAnchorPane;

    @FXML
    private void initialize() {
        // Show the patient select on the first run
        handleSelectedPatientClick();
    }

    @FXML
    public void handleSelectedPatientClick() {
        try {
            changeView("/view/PatientSelectView.fxml");
        } catch (Exception e) {
            System.out.println("Failed to display the the patient selection screen, with error:");
            e.printStackTrace();
        }
    } 

    @FXML
    public void handleShowInspectClick(Event eventTypeToDisplay) {
        try {
            InspectCtrl inspectCtrl = (InspectCtrl) changeView("/view/InspectView.fxml");
            inspectCtrl.setEvent(eventTypeToDisplay);
        } catch (Exception e) {
            System.out.println("Failed to display the the inspect screen, with error:");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleShowOverviewClick() {
        if (this.getPatient() == null) {
            Alert wydt = new Alert(AlertType.INFORMATION, "Please select a patient!");
            wydt.show();
        } else {
            try {
                changeView("/view/OverviewView.fxml");
            } catch (Exception e) {
                System.out.println("Failed to display the the overview screen, with error:");
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleLogoutClick() {
        // Execute the logout callback
        this.logOutCallback.run();
    }

    private NavigatableCtrl changeView(String path) throws IOException {
        // Unsubscribe the current child
        this.unsubscribeChild();
        // Load the view
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        Region loadedView = (Region) loader.load();
        // Display the view
        this.rootSubAnchorPane.getChildren().setAll(loadedView);
        // Get the controller
        this.childNode = (NavigatableCtrl) loader.getController();
        // Set parent
        this.childNode.setParent(this);
        // Return the child ctrl
        return this.childNode;
    }

    private void unsubscribeChild() {
        if (this.childNode != null) {
            this.childNode.unsubscribe();
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
        // Update the view
        this.patientID.setText(patient.getCPR());
        // Change the patient of priotiry in the thread
        EventDetectorThread.getInstance().prioritizePatient(patient);
    }

    public void setPersonnel(Personnel personnel) {
        // Store the user
        this.user = personnel;
        // Update the view
        this.clinicianID.setText(personnel.getID());
    }

    public void setLogOutCallback(Runnable callback) {
        this.logOutCallback = callback;
    }
}
