package WeanWatch.controller;

import java.util.ArrayList;
import java.util.List;

import WeanWatch.model.Patient;
import WeanWatch.model.PatientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class PatientSelectCtrl {
    
    @FXML
    private ListView<Patient> patientListView;

    private ObservableList<Patient> patientList;

    public PatientSelectCtrl() {
        // Create an observable list for the patients
        patientList = FXCollections.observableArrayList();
        // Load patients form the handler and add the patients to the observable list
        patientList.addAll(PatientHandler.getInstance().getPatients());
    }

    @FXML
    private void initialize() {
        try {
            // Add the loaded patients to the list view
            patientListView.setItems(patientList);
            // Add a cell factory for each cell to create a custome using the controller
            patientListView.setCellFactory(listView -> new PatientSelectListCell());
            // Define the on click handler
            patientListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Patient>() {
                    public void changed(ObservableValue<? extends Patient> ov, final Patient oldPatient, final Patient newPatient) {
                        handlePatientClick(ov, oldPatient, newPatient);
                    }
                }
            );

        } catch (Exception e) {
            
            //TODO: handle exception
            System.err.println("Failed to display patients in list view with error:" + e);
            e.printStackTrace();

        }
    }

    public void handlePatientClick(ObservableValue<? extends Patient> ov, Patient oldPatient, Patient newPatient) {

        // TODO: Set the patient, and navigate to overview screen
        System.out.println(oldPatient);

        System.out.println(newPatient);

    }


}
