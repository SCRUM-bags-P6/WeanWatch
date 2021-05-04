package WeanWatch.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class PatientSelectCtrl {
    
    @FXML
    private ListView<String> patientListView;

    @FXML
    private void initialize() {

        try {

            List<String> patientCPR = new ArrayList<String>();
            
            patientCPR.add("11223344-1122");
            patientCPR.add("11223344-1122");
            patientCPR.add("11223344-1122");
            patientCPR.add("11223344-1122");
            patientCPR.add("11223344-1122");
            patientCPR.add("11223344-1122");
            patientCPR.add("11223344-1122");
            patientCPR.add("11223344-1122");

            patientListView.getItems().setAll(patientCPR);


        } catch (Exception e) {
            //TODO: handle exception
        }


    }


}
