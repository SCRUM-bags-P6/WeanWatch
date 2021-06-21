package WeanWatch.controller;

import java.util.ArrayList;
import java.util.List;

import WeanWatch.model.Event;
import WeanWatch.model.DetectedEvent;
import WeanWatch.model.Patient;
import WeanWatch.model.TriangleMetaphoricFactory;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class InspectCtrl extends NavigatableCtrl {

    @FXML
    private ScrollPane overviewScrolling;
    
    @FXML
    private VBox rowsBox;

    private Event eventTypeToDisplay;

    public void setEvent(Event eventTypeToDisplay) {
        this.eventTypeToDisplay = eventTypeToDisplay;
        // Force an update
        this.update(this.parentNode.getPatient());
    }

    @Override
    public void update(Patient context) {
        if (this.eventTypeToDisplay != null) {
            // Clear the rowsBox
            this.rowsBox.getChildren().clear();
            // Get the most recent daily occurrences of each event to display
            List<DetectedEvent> eventsToDisplay = this.parentNode.getPatient().getDetectedEvents(this.eventTypeToDisplay.getName());
            // Prepare a figure factory
            TriangleMetaphoricFactory metaphoricFactory = new TriangleMetaphoricFactory();
            // Prepare a placeholder for the hbox 
            HBox hbox = null;
            // Loop through all events
            for (int i = 0; i < eventsToDisplay.size(); i++) {
                if (i % 3 == 0) {
                    // Add the hbox, if it has been filled with figures
                    if (hbox != null) {
                        this.rowsBox.getChildren().add(hbox);
                    }
                    // Create a hbox for each three events
                    hbox = new HBox(50D);
                    hbox.setPadding(new Insets(0D,0D,0D,350D));
                }	

				if (eventsToDisplay.get(i).getInspectFigure() != null) {
					// Add the vbox to the hbox
					hbox.getChildren().add(eventsToDisplay.get(i).getInspectFigure());
	
				} else {
					// Create a vbox for the events
					VBox vbox = new VBox();
					rowsBox.setSpacing(100D);
					// Add a metaforic figure to the vbox
					Pane metaphoricFigure = metaphoricFactory.create(eventsToDisplay.get(i));
					// Create labels to caption time
					// The year is removed and labels are split at the "T"
					Label labelEventInterval = new Label("From " + eventsToDisplay.get(i).getEventInterval().getNewestTime().toString()/*.substring(5, 16)*/.split("T", 2)[0] + "  " + 
					eventsToDisplay.get(i).getEventInterval().getNewestTime().toString().split("T", 2)[1] +
					"  to  " + eventsToDisplay.get(i).getEventInterval().getOldestTime().toString()/*.substring(5, 16)*/.split("T", 2)[0] + 
					"  " + eventsToDisplay.get(i).getEventInterval().getOldestTime().toString().split("T", 2)[1]);
					// Set font size and type for label
					labelEventInterval.setFont(new Font("Arial",15));

					//Laver BorderPane hvori label sættes, således at vi kan få den rigtige spacing mellem figurerne
					//Pane labelsPane = new Pane();
					//labelsPane.setPadding(new Insets(0D, 0D, 0D, 130D));
				// labelsPane.getChildren().addAll(labelEventInterval);

					System.out.println(labelEventInterval.getText());
					System.out.println("SE HER");
					System.out.println("SE HER");System.out.println("SE HER");System.out.println("SE HER");System.out.println("SE HER");System.out.println("SE HER");System.out.println("SE HER");System.out.println("SE HER");System.out.println("SE HER");
					// Add metaphoricalfigure, number of occurrences and time duration to the Vbox
					vbox.getChildren().addAll(metaphoricFigure,labelEventInterval);
					vbox.setMargin(labelEventInterval,new Insets(0,0,0,50D));
					// Add the vbox to the hbox
					hbox.getChildren().add(vbox);
					eventsToDisplay.get(i).setInspectFigure(vbox);
				}
            }
            // Add the last hbox created to the rowsBox
            this.rowsBox.getChildren().add(hbox);
        }       
    }
    
    public void handleBackClick() {
        // TODO: Implement
    }
}
