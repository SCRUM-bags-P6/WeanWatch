package WeanWatch.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.collections4.map.LinkedMap;

import WeanWatch.model.Case;
import WeanWatch.model.CaseDetectorThread;
import WeanWatch.model.DailyOccurrence;
import WeanWatch.model.DetectedCase;
import WeanWatch.model.Patient;
import WeanWatch.model.TriangleMetaphoricFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class OverviewCtrl extends NavigatableCtrl {

    @FXML
    private ScrollPane overviewScrolling;
    
    @FXML
    private VBox rowsBox;

    public OverviewCtrl() {

    }

    public void initialize() {

    }

    private ArrayList<DailyOccurrence> getDailyCaseOccurrencesToDisplay() {
        // Get the all patients detected cases
        ArrayList<DetectedCase> detectedCases = this.parentNode.getPatient().getDetectedCaseHandler().getDetectedCases();
        /* Create a MultiKeyMap to store cases to be displayed. MultiKeyMap is used as there should be one
        *  DetectedCase to be displayed, for each case and day
        */
        MultiKeyMap<Object, DailyOccurrence> dailyOccurrencesMap = MultiKeyMap.multiKeyMap(new LinkedMap());
        // Loop through each case
        for (DetectedCase detectedCase : detectedCases) {
            // Get the start time of the case
            LocalDateTime caseStartTime = detectedCase.getCaseInterval().getOldestTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            // Get the date at which the case occured
            LocalDate caseDate = caseStartTime.toLocalDate();
            // Get the case type of the detected case
            Case caseType = detectedCase.getCase();
            // Create a new daily case occurrence if it is the first occurrence of the day
            if (!dailyOccurrencesMap.containsKey(caseDate, caseType)) {
                dailyOccurrencesMap.put(caseDate, caseType, new DailyOccurrence(detectedCase));
            } else {
                // Update the DailyOccurrence with the new occurrence
                dailyOccurrencesMap.get(caseDate, caseType).addOccurrence(detectedCase);
            }
        }
        // Create a placeholder for all of the daily occurrences to display
        ArrayList<DailyOccurrence> casesToDisplay = new ArrayList<DailyOccurrence>();
        // Convert the MultiKeyMap to an arraylist
        dailyOccurrencesMap.forEach((keys, dailyOccurrence) -> {
            // Add the daily occurrence to the casesToDisplay
            casesToDisplay.add(dailyOccurrence);
        });
        // Return the occurrences
        return casesToDisplay;
    }

    @Override
    public void update(Patient context) {
        // Clear the rowsBox
        this.rowsBox.getChildren().clear();
        // Get the most recent daily occurrences of each case to display
        ArrayList<DailyOccurrence> casesToDisplay = this.getDailyCaseOccurrencesToDisplay();
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
			Pane metaphoricFigure = metaphoricFactory.create(casesToDisplay.get(i).getCaseToDisplay());
        	// Create labels to caption for occurrences and time
            Label labelOccurrences = new Label(casesToDisplay.get(i).getOccurrences().toString());
            Label labelCumulativeTime = new Label(casesToDisplay.get(i).getCumulativeTime());
            // Add metaphoricalfigure, number of occurrences and time duration to the Vbox
            vbox.getChildren().addAll(metaphoricFigure,labelOccurrences,labelCumulativeTime);
            // Add the vbox to the hbox
        	hbox.getChildren().add(vbox);
		}
    }
	
	public void handleGridClick() {


	}
}
