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
        } catch (Exception e) {
            
            //TODO: handle exception
            System.err.println("Failed to display patients in list view with error:" + e);
            e.printStackTrace();

        }
    }


}
