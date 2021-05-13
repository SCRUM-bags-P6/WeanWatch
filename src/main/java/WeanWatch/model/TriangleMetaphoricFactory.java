package WeanWatch.model;


import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.geometry.Insets;


public class TriangleMetaphoricFactory extends MetaphoricFactory {
    
   
    public TriangleMetaphoricFactory() {

    }

    // Figure root pane, hvor alle dele af figuren skal være i
    public BorderPane create(DetectedCase detectedCase) {
        BorderPane figureRoot = new BorderPane();
        
        // Figure VBox, hvor der skal være fire felt - Label, head, topTriag, butTriag
            //angiv størrelse, placering og evt. spacing 
        VBox figureVBox = new VBox();
        figureVBox.setPadding(new Insets(4));
        figureVBox.setSpacing(8); //Find korrekte spacing
    

        // Label til casenavn
        Label caseLabel = new Label(detectedCase.getCase().getName());
        figureVBox.getChildren().add(caseLabel);


        // Pane til hovedet på trekanten
        BorderPane headPane = new BorderPane();
        // Draw figure
        figureVBox.getChildren().add(headPane);

        // Pane til group med toppen til trekanten
        BorderPane topTriagPane = new BorderPane();
        // Draw figure
        topTriagPane.getChildren().add()
        figureVBox.getChildren().add(topTriagPane);
        
    

        // Pane til group med bunden til trekanten
        BorderPane butTriagPane = new BorderPane();
        // Draw figure
        figureVBox.getChildren().add(butTriagPane);
        
        figureRoot.setCenter(figureVBox);
        return figureRoot;
    }
}


/*
public Polygon drawFigure(int Color, Double firstX, Double firstY, Double secondX, Double secondY, Double thirdX, Double thirdY){
    Polygon MetaphoricFigure = new Polygon();

    MetaphoricFigure.getPoints().addAll(new Double[]{
        firstX, firstY,
        secondX, secondY,
        thirdX, thirdY,
    });

    MetaphoricFigure.setFill(Colors[Color]);	

return MetaphoricFigure;
}

*/