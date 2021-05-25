package WeanWatch.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
        EventHandler.getInstance().addEvent("     Apnea   ", "Apnea", Event.Severity.INTERMEDIATE, null);
        EventHandler.getInstance().addEvent("Too little O2", "Too little O2", Event.Severity.SEVERE, null);
        EventHandler.getInstance().addEvent("Too much O2 ", "Too much O2", Event.Severity.MILD, null);
        EventHandler.getInstance().addEvent("Too much CO2 ", "Too much CO2", Event.Severity.MILD, null);
		//For test purposes, it is neccesary to create some indicators to pass to the eventhandler
		/*
        ArrayList<Indicator> indicators = new ArrayList<Indicator>();
		indicators.add(new Indicator(250, (Predicate<Row> & Serializable)(Row x) -> {
            return Double.compare(x.getDouble(x.fieldIndex("PEEPSet")), 12D) == 0; 
        }));
                
        indicators.add(new Indicator(250, (Predicate<Row> & Serializable)(Row x) -> {
            return Double.compare(x.getDouble(x.fieldIndex("SpO2")), 0.9D) > 0; 
        }));
		DetectionAlgorithm algo = new IndicatorAlgorithm(indicators);
		//For test purposes, it is neccesary to create some indicators to pass to the Eventhandler

        EventHandler.getInstance().addEventAlgo("Apnea", "Apnea", Event.Severity.INTERMEDIATE, algo);
        */
        PatientHandler.getInstance().getPatients()[0].getDetectedEventHandler().addEvent(
            new DetectedEvent(
                PatientHandler.getInstance().getPatients()[0], 
                EventHandler.getInstance().getEvent("     Apnea   "),
                //Ændre til LocalDateTime
                new TimeInterval(
                    LocalDateTime.of(2021, 05, 16, 9, 00, 00),
                    LocalDateTime.of(2021, 05, 16, 9, 50, 00)
                    ))
        );

        PatientHandler.getInstance().getPatients()[0].getDetectedEventHandler().addEvent(
            new DetectedEvent(
            PatientHandler.getInstance().getPatients()[0], 
                EventHandler.getInstance().getEvent("Too little O2"),
                //Ændre til LocalDateTime
                new TimeInterval(
                    LocalDateTime.of(2021, 05, 16, 7, 50, 00),
                    LocalDateTime.of(2021, 05, 16, 8, 20, 00)
                    ))
        );

        PatientHandler.getInstance().getPatients()[0].getDetectedEventHandler().addEvent(
            new DetectedEvent(
            PatientHandler.getInstance().getPatients()[0], 
                EventHandler.getInstance().getEvent("Too much O2 "),
                //Ændre til LocalDateTime
                new TimeInterval(
                    LocalDateTime.of(2021, 05, 19, 9, 00, 00),
                    LocalDateTime.of(2021, 05, 19, 10, 20, 00)
                    ))
        );

        PatientHandler.getInstance().getPatients()[0].getDetectedEventHandler().addEvent(
            new DetectedEvent(
            PatientHandler.getInstance().getPatients()[0], 
                EventHandler.getInstance().getEvent("Too much CO2 "),
                //Ændre til LocalDateTime
                new TimeInterval(
                    LocalDateTime.of(2021, 05, 20, 9, 00, 00),
                    LocalDateTime.of(2021, 05, 20, 10, 20, 00)
                    ))
        );
    }

    private ArrayList<DailyOccurrence> getDailyEventOccurrencesToDisplay() {
        // Get the all patients detected Events
        ArrayList<DetectedEvent> detectedEvents = this.parentNode.getPatient().getDetectedEventHandler().getDetectedEvents();
        /* Create a MultiKeyMap to store Events to be displayed. MultiKeyMap is used as there should be one
        *  DetectedEvent to be displayed, for each Event and day
        */
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
        // Clear the rowsBox
        this.rowsBox.getChildren().clear();
        // Get the most recent daily occurrences of each event to display
        ArrayList<DailyOccurrence> eventsToDisplay = this.getDailyEventOccurrencesToDisplay();
		// Prepare a figure factory
        TriangleMetaphoricFactory metaphoricFactory = new TriangleMetaphoricFactory();
        // Prepare a placeholder for the hbox 
        HBox hbox = null;
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
        this.rowsBox.getChildren().add(hbox);
    }
	
	public void handleGridClick(Event eventToFocus) {
        // Trigger the view to change
        this.parentNode.handleShowInspectClick(eventToFocus);
	}
}
