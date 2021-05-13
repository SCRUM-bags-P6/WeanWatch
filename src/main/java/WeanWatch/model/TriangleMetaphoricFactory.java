package WeanWatch.model;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.*;
import javafx.scene.web.*;
import javafx.scene.Group;


public class TriangleMetaphoricFactory extends MetaphoricFactory {
    
   
    public TriangleMetaphoricFactory() {

    }

    public Pane create(DetectedCase detectedCase) {
        Pane figureRoot = new Pane();
        // Figure root pane, hvor alle dele af figuren skal være i
         //angiv størrelse og placering
    
        VBox figureVBox = new VBox();
        figureVBox.setPadding(new Insets(4));
        figureVBox.setSpacing(8); //Find korrekte spacing

        //Label caseLabel = new Label(detectedCase.getCase().getName());
        //figureVBox.getChildren().add(caseLabel);

        Pane headPane = new Pane();
        figureVBox.getChildren().add(headPane);

        Pane topTriagPane = new Pane();
        figureVBox.getChildren().add(topTriagPane);

        Pane butTriagPane = new Pane();
        figureVBox.getChildren().add(butTriagPane);

        // Figure VBox, hvor der skal være fire felt - Label, head, topTriag, butTriag
            //angiv størrelse, placering og evt. spacing
                //måske skal der i VBoxen angives placering til de tre panes og label
        
        // Label til casenavn
            //angiv størrelse, placering og
        // Pane til hovedet på trekanten
            //angiv størrelse og placering
        // Pane til group med toppen til trekanten
            //angiv størrelse og placering
        // Pane til group med bunden til trekanten
            //angiv størrelse og placering
        figureRoot.setCenter(figureVBox);
        return figureRoot;
    }
}