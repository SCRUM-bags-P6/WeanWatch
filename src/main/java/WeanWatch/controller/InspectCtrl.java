package WeanWatch.controller;

import java.util.ArrayList;

import WeanWatch.model.Case;
import WeanWatch.model.DetectedCase;
import WeanWatch.model.Patient;
import WeanWatch.model.TriangleMetaphoricFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class InspectCtrl extends NavigatableCtrl {

    @FXML
    private ScrollPane overviewScrolling;
    
    @FXML
    private VBox rowsBox;

    private Case caseTypeToDisplay;

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
            ArrayList<DetectedCase> casesToDisplay = this.parentNode.getPatient().getDetectedCaseHandler().getDetectedCases(this.caseTypeToDisplay.getName());
            // Prepare a figure factory
            TriangleMetaphoricFactory metaphoricFactory = new TriangleMetaphoricFactory();
            // Prepare a placeholder for the hbox 
            HBox hbox = null;
            // Loop through all cases
            for (int i = 0; i < casesToDisplay.size(); i++) {
                if (i % 3 == 0) {
                    // Add the hbox, if it has been filled with figures
                    if (hbox != null) {
                        this.rowsBox.getChildren().add(hbox);
                    }
                    // Create a hbox for each three cases
                    hbox = new HBox();
                }	
                // Create a vbox for the cases
                VBox vbox = new VBox(200D);
                // Add a metaforic figure to the vbox
                Pane metaphoricFigure = metaphoricFactory.create(casesToDisplay.get(i));
                // Create labels to caption time
                // The year is removed and labels are split at the "T"
                    Label labelCaseInterval = new Label("From " + casesToDisplay.get(i).getCaseInterval().getNewestTime().toString().split("T", 2)[1] 
                + "  " + casesToDisplay.get(i).getCaseInterval().getNewestTime().toString().substring(5, 16).split("T", 2)[0] 
                + "  to  " + casesToDisplay.get(i).getCaseInterval().getOldestTime().toString().split("T", 2)[1] 
                + "  " + casesToDisplay.get(i).getCaseInterval().getOldestTime().toString().substring(5, 16).split("T", 2)[0]);
                // Set font size and type for label
                labelCaseInterval.setFont(new Font("Arial",15));
                // Add metaphoricalfigure, number of occurrences and time duration to the Vbox
                vbox.getChildren().addAll(metaphoricFigure,labelCaseInterval);
                // Add the vbox to the hbox
                hbox.getChildren().add(vbox);
            }
            // Add the last hbox created to the rowsBox
            this.rowsBox.getChildren().add(hbox);
        }       
    }
    
    public void handleBackClick() {
        // TODO: Implement
    }
}
