package WeanWatch.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class rootCtrl {
    
    @FXML
    private StackPane rootSubAnchorPane;

    @FXML
    private void initialize() {

        try {
        
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/patientSelectView.fxml"));
            VBox patientSelectView = (VBox) loader.load();

            this.rootSubAnchorPane.getChildren().add(patientSelectView);
            
            List<String> patientCPR = new ArrayList<String>();
            
            patientCPR.add("11223344-1122");
            patientCPR.add("11223344-1122");
            patientCPR.add("11223344-1122");
            patientCPR.add("11223344-1122");
            patientCPR.add("11223344-1122");
            patientCPR.add("11223344-1122");
            patientCPR.add("11223344-1122");
            patientCPR.add("11223344-1122");

            for (String cpr : patientCPR) {
                //
            }


        } catch (Exception e) {
            //TODO: handle exception
        }


    }

}
