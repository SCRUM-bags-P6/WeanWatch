package WeanWatch.model;


import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.sql.Row;

import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class TriangleMetaphoricFactory extends MetaphoricFactory {
   
    public TriangleMetaphoricFactory() {

    }

    
    public BorderPane create(DetectedCase detectedCase) {
        // Figure root pane, hvor alle dele af figuren skal være i
        BorderPane figureRoot = new BorderPane();
        
        // Figure VBox, hvor der skal være fire felter - Label, head, topTriag, butTriag 
        VBox figureVBox = new VBox();
        figureVBox.setPadding(new Insets(4));
        figureVBox.setSpacing(200); //Find korrekte spacing
    
        // Label til casenavn + detaljer
        Label caseLabel = new Label("Apnea"/*detectedCase.getCase().getName()*/);
        caseLabel.setFont(new Font("Arial",30));
        caseLabel.setMaxWidth(Double.MAX_VALUE);
        caseLabel.setAlignment(Pos.CENTER);
        
        figureVBox.getChildren().add(caseLabel);

        // Pane til hovedet på trekanten
        BorderPane headPane = new BorderPane();
        
        // Draw head
        // Overvej om vi skal bruge ImageView til at sætte sur og glad smiley ind
        Circle head = new Circle();
        head.setCenterX(100.0f);
        head.setCenterY(0.0f);
        head.setRadius(50.0f);
        //head.setFill();
        head.setLayoutX(1.00);
        head.setLayoutY(1.00);
        
        headPane.getChildren().add(head);
        figureVBox.getChildren().add(headPane);

        // Pane to contain top triangle
        BorderPane topTriagPane = new BorderPane();

        //Hent det rå patient data + tidsinterval + tilhørende række for den pågældende parameter. 
        //Gennemsnittet for Patientdata for tidsintervallet skal regnes, hvorefter det skal omregnes til koordinater.
        Double X1 = 160.00; 
        Double Y1 = 100.00; //detectedCase.getPatient().getData(); 
        Double X2 = 120.00; //detectedCase.getPatient().getData();
        Double Y2 = 0.00;
        Double X3 = 60.00;
        Double Y3 = 0.00;

        PatientHandler.getInstance().getPatients()[0].getPatientData().foreach((ForeachFunction<Row>) row -> {
            System.out.println(row.getString(0));
        });
        

        // Draw top triangle
        Polygon topPolygon = new Polygon();
        topPolygon.getPoints().addAll(new Double[] {
            X1, Y1,
            X2, Y2,
            X3, Y3
        });

        topTriagPane.getChildren().add(topPolygon);
        figureVBox.getChildren().add(topTriagPane);

        
        // Pane til group med bunden til trekanten
        BorderPane botTriagPane = new BorderPane();

        // Draw bottom triangle
        Polygon botPolygon = new Polygon();
        botPolygon.getPoints().addAll(new Double[] {
            X1, Y1,
            X2, Y2,
            X3, Y3
        });
        botTriagPane.getChildren().add(botPolygon);
        figureVBox.getChildren().add(botTriagPane);
        
        //Set figurVBox in figureRoots center
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

    //MetaphoricFigure.setFill(Colors[Color]);	

return MetaphoricFigure;
}
*/
