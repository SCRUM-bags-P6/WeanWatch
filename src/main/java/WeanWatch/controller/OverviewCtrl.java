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
import WeanWatch.model.DailyOccurrence;
import WeanWatch.model.DetectedCase;
import WeanWatch.model.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class OverviewCtrl extends NavigatableCtrl {

    @FXML
    public ScrollPane overviewScrolling;
    
    @FXML
    private VBox rowsBox;

    public OverviewCtrl() {

    }

    public void initialize() {

    }

    @Override
    public void setParent(RootCtrl rootCtrl) {

        super.setParent(rootCtrl);

        // Get the most recent daily occurrences of each case to display
        ArrayList<DetectedCase> casesToDisplay = this.getDailyCaseOccurrencesToDisplay();
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
        // // Prepare a placeholder for the hbox 
        // HBox hbox = null;
        // // Loop through all cases
        // for (int i = 0; i < casesToDisplaySize; i++) {
		// 	if (i % 3 == 0) {
        //         // Add the hbox, if it has been filled with figures
        //         if (hbox != null) {
        //             this.rowsBox.getChildren().add(hbox);
        //         }
		// 		// Create a hbox for each three cases
		// 		hbox = new HBox();
        //     }	

			// Create a vbox for the cases
			VBox vbox = new VBox();
		// 	// Create a vbox for the cases
		// 	VBox vbox = new VBox();


       	    // Add a metaforic figure to the vbox
       	//     // Add a metaforic figure to the vbox

			TriangleMetaphoricFactory metaphoricFactory = new TriangleMetaphoricFactory();
		// 	TriangleMetaphoricFactory metaphoricFactory = new TriangleMetaphoricFactory();

			Pane metaphoricFigure = metaphoricFactory.create(casesToDisplay.get(i));
		// 	Pane metaphoricFigure = metaphoricFactory.create(casesToDisplay.get(i));

        	// Add caption to the vbox
        // 	// Add caption to the vbox


            // Add the vbox to the hbox
        	hbox.getChrildren().add(vbox);
        //     // Add the vbox to the hbox
        // 	hbox.getChrildren().add(vbox);

		}
		// }
    }

    private ArrayList<DetectedCase> getDailyCaseOccurrencesToDisplay() {
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
        // Create a placeholder for all of the cases to display
        ArrayList<DetectedCase> casesToDisplay = new ArrayList<DetectedCase>();
        // Convert the MultiKeyMap to an arraylist
        dailyOccurrencesMap.forEach((keys, dailyOccurrence) -> {
            // Add the daily occurrence to the casesToDisplay
            casesToDisplay.add(dailyOccurrence.getCaseToDisplay());
        });
        // Return the occurrences
        return casesToDisplay;
    }

    @Override
    public void update(Patient context) {
        // TODO Auto-generated method stub
        
    }
	
	public void handleGridClick() {
		
	}


}
