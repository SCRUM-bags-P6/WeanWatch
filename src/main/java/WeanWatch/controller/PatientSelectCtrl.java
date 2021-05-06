package WeanWatch.controller;

import java.util.ArrayList;
import java.util.List;

import WeanWatch.model.Patient;
import WeanWatch.model.PatientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class PatientSelectCtrl {
    
    @FXML
    private ListView<String> patientListView;

    

    public PatientSelectCtrl() {
        
        
    }

    @FXML
    private void initialize() {
        try {

            for(Patient patient : PatientHandler.getInstance().getPatients()) {
                patientListView.getItems().add(patient.getCPR());    
            }

        } catch (Exception e) {
            
            //TODO: handle exception
            System.err.println("Failed to display patients in list view with error:" + e);
            e.printStackTrace();

        }
    }


}
