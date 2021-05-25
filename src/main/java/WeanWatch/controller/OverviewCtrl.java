package WeanWatch.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.spark.sql.Row;
import org.apache.commons.collections4.map.LinkedMap;

import WeanWatch.model.Event;
import WeanWatch.model.EventDetectorThread;
import WeanWatch.model.EventHandler;
import WeanWatch.model.DailyOccurrence;
import WeanWatch.model.DetectedEvent;
import WeanWatch.model.DetectionAlgorithm;
import WeanWatch.model.Indicator;
import WeanWatch.model.IndicatorAlgorithm;
import WeanWatch.model.Patient;
import WeanWatch.model.PatientHandler;
import WeanWatch.model.TimeInterval;
import WeanWatch.model.TriangleMetaphoricFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class OverviewCtrl extends NavigatableCtrl implements Serializable{

    @FXML
    private ScrollPane overviewScrolling;
    
    @FXML
    private VBox rowsBox;

    private HashMap<VBox, Event> figureLookup = new HashMap<VBox, Event>();

    public void initialize() {
        
    }

    private ArrayList<DailyOccurrence> getDailyEventOccurrencesToDisplay(Patient context) {
        // Get the all patients detected Events
        List<DetectedEvent> detectedEvents = context.getDetectedEvents();
        /* Create a MultiKeyMap to store Events to be displayed. MultiKeyMap is used as there should be one
        *  DetectedEvent to be displayed, for each Event and day
        */

        System.out.println("Events to display:");
        System.out.println(detectedEvents);

        MultiKeyMap<Object, DailyOccurrence> dailyOccurrencesMap = MultiKeyMap.multiKeyMap(new LinkedMap());
        // Loop through each Event
        for (DetectedEvent detectedEvent : detectedEvents) {
            // Get the start time of the Event
            LocalDateTime eventStartTime = detectedEvent.getEventInterval().getOldestTime();
            // Get the date at which the Event occured
            LocalDate eventDate = eventStartTime.toLocalDate();
            // Get the Event type of the detected Event
            Event eventType = detectedEvent.getEvent();
            // Create a new daily Event occurrence if it is the first occurrence of the day
            if (!dailyOccurrencesMap.containsKey(eventDate, eventType)) {
                dailyOccurrencesMap.put(eventDate, eventType, new DailyOccurrence(detectedEvent));
            } else {
                // Update the DailyOccurrence with the new occurrence
                dailyOccurrencesMap.get(eventDate, eventType).addOccurrence(detectedEvent);
            }
        }
        // Create a placeholder for all of the daily occurrences to display
        ArrayList<DailyOccurrence> eventsToDisplay = new ArrayList<DailyOccurrence>();
        // Convert the MultiKeyMap to an arraylist
        dailyOccurrencesMap.forEach((keys, dailyOccurrence) -> {
            // Add the daily occurrence to the EventsToDisplay
            eventsToDisplay.add(dailyOccurrence);
        });
        // Return the occurrences
        return eventsToDisplay;
    }

    @Override
    public void update(Patient context) {
        // Don't update if the patient is not the selected patient
        if (context != this.parentNode.getPatient()) {
            System.out.println("Skipping update for unselected patient");
            return;
        }
        // Clear the rowsBox
        this.rowsBox.getChildren().clear();
        // Get the most recent daily occurrences of each event to display
        ArrayList<DailyOccurrence> eventsToDisplay = this.getDailyEventOccurrencesToDisplay(context);
		// Prepare a figure factory
        TriangleMetaphoricFactory metaphoricFactory = new TriangleMetaphoricFactory();
        // Prepare a placeholder for the hbox 
        HBox hbox = null;
        System.out.println("Number of daily occurrences to display: " + eventsToDisplay.size());
        // Loop through all Events
        for (int i = 0; i < eventsToDisplay.size(); i++) {
			if (i % 3 == 0) {
                // Add the hbox, if it has been filled with figures
                if (hbox != null) {
                    this.rowsBox.getChildren().add(hbox);
                }
				// Create a hbox for each three Events
				hbox = new HBox(50D);
                hbox.setPadding(new Insets(0D,0D,0D,350D));
            } 	
			// Create a vbox for the Events
			VBox vbox = new VBox(10D);
            vbox.setAlignment(Pos.CENTER);
       	    // Add a metaforic figure to the vbox
			Pane metaphoricFigure = metaphoricFactory.create(eventsToDisplay.get(i).getEventToDisplay());
        	// Create labels to caption for occurrences and time
            VBox labelsPane = new VBox(10D);
            rowsBox.setSpacing(100D);

            Label labelOccurrences = new Label("Occurences: " + eventsToDisplay.get(i).getOccurrences().toString() + "  ");
            Label labelCumulativeTime = new Label("Duration: " + eventsToDisplay.get(i).getCumulativeTime());    
            //Set font size and font type
            labelOccurrences.setFont(new Font("Arial",15));
            labelCumulativeTime.setFont(new Font("Arial",15));
            //position labelsPane in vbox 
            labelsPane.setPadding(new Insets(0D, 0D, 0D, 130D));
            //labelsPane.setAlignment(Pos.TOP_RIGHT);
            //labelCumulativeTime.setAlignment(Pos.CENTER);
            
            labelsPane.getChildren().addAll(labelOccurrences,labelCumulativeTime);
            // Add metaphoricalfigure, number of occurrences and time duration to the Vbox
            vbox.getChildren().addAll(metaphoricFigure,labelsPane);


            // Store a reference to the vbox
            figureLookup.put(vbox, eventsToDisplay.get(i).getEventToDisplay().getEvent());
            // Create a click handler for each
            vbox.setOnMouseClicked((e) -> {
                // Lookup the vbox, and execute the handleGridClick
                this.handleGridClick(this.figureLookup.get(e.getSource()));
            });
            //vbox.setAlignment(Pos.CENTER);
            // Add the vbox to the hbox
        	hbox.getChildren().add(vbox);
		}
        // Add the last hbox created to the rowsBox
        if (hbox != null) {
            this.rowsBox.getChildren().add(hbox);
        } else {
            this.rowsBox.getChildren().add(new Label("No events to display"));
        }
    }
	
	public void handleGridClick(Event eventToFocus) {
        // Trigger the view to change
        this.parentNode.handleShowInspectClick(eventToFocus);
	}
}
