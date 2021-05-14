package WeanWatch.controller;

import java.util.ArrayList;

import WeanWatch.model.Case;
import WeanWatch.model.CaseDetectorThread;
import WeanWatch.model.DetectedCase;
import WeanWatch.model.Patient;
import WeanWatch.model.TriangleMetaphoricFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class InspectCtrl extends NavigatableCtrl {

    @FXML
    private ScrollPane overviewScrolling;
    
    @FXML
    private VBox rowsBox;

    private Case caseTypeToDisplay;

    public InspectCtrl() {

    }

    public void initialize() {

    }

    public void setCase(Case caseTypeToDisplay) {
        this.caseTypeToDisplay = caseTypeToDisplay;
        // Force an update
        this.update(this.parentNode.getPatient());
    }

    @Override
    public void update(Patient context) {
        if (this.caseTypeToDisplay != null) {
            // Clear the rowsBox
            this.rowsBox.getChildren().clear();
            // Get the most recent daily occurrences of each case to display
            ArrayList<DetectedCase> casesToDisplay = this.parentNode.getPatient().getDetectedCaseHandler().getDetectedCases(this.caseToDisplay.getName());
            // getting total size of arrlist
            // using size() method
            int casesToDisplaySize = casesToDisplay.size();
            // Prepare a placeholder for the hbox 
            HBox hbox = null;
            // Loop through all cases
            for (int i = 0; i < casesToDisplaySize; i++) {
                if (i % 3 == 0) {
                    // Add the hbox, if it has been filled with figures
                    if (hbox != null) {
                        this.rowsBox.getChildren().add(hbox);
                    }
                    // Create a hbox for each three cases
                    hbox = new HBox();
                }	
                // Create a vbox for the cases
                VBox vbox = new VBox();
                // Add a metaforic figure to the vbox
                TriangleMetaphoricFactory metaphoricFactory = new TriangleMetaphoricFactory();
                Pane metaphoricFigure = metaphoricFactory.create(casesToDisplay.get(i));
                // Create labels to caption time
                Label labelCaseInterval = new Label(casesToDisplay.get(i).getCaseInterval().toString());
                // Add metaphoricalfigure, number of occurrences and time duration to the Vbox
                vbox.getChildren().addAll(metaphoricFigure,labelCaseInterval);
                // Add the vbox to the hbox
                hbox.getChildren().add(vbox);
            }
        }       
    }
    
    public void handleBackClick() {
        
        
    }
}
