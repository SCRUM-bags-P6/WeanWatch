package WeanWatch.controller;

import WeanWatch.model.Patient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class PatientSelectListCell extends ListCell<Patient> {
    
    @FXML
    private Label cprLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label nameLabel;

    @FXML
    private HBox cellBox;

    private FXMLLoader fxmlLoader;

    @Override
    protected void updateItem(Patient patient, boolean empty) {
        // Call super constructor
        super.updateItem(patient, empty);
        // If create the cell if a patient was passed
        if (empty || patient == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                // Load the FXML resource
                fxmlLoader = new FXMLLoader(getClass().getResource("/view/PatientSelectListCell.fxml"));
                // Set this instance as the controller
                fxmlLoader.setController(this);
                // Try loading the view
                try {
                    fxmlLoader.load();
                } catch (Exception e) {

                    //TODO: handle exception
                    System.err.println("Failed to load list view cells with error:" + e);
                    e.printStackTrace();

                }
            }
            // Set the label text
            cprLabel.setText(patient.getCPR());
            ageLabel.setText(String.valueOf(patient.getAge()));
            nameLabel.setText(patient.getName());
            setText(null);
            setGraphic(cellBox);
        }
    }

}
