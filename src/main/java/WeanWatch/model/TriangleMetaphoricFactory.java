package WeanWatch.model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Formatter;

import javax.imageio.ImageIO;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.avg;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.connector.catalog.TableChange.After;
import org.junit.Before;

import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class TriangleMetaphoricFactory extends MetaphoricFactory {
    
    private static Integer count = 0;

    private final static Double figureMaxWidth = 50D;
    private final static Double figureMaxHeight = 50D;

    public BorderPane create(DetectedCase detectedCase) {
        // Figure root pane, hvor alle dele af figuren skal være i
        BorderPane figureRoot = new BorderPane();
        // Create a figure vbox
        VBox figureVBox = new VBox();
        // Set padding
        figureVBox.setPadding(new Insets(4));
        // Set the figure spacing
        figureVBox.setSpacing(50);
        // Load the case label
        figureVBox.getChildren().add(this.loadCaseLabel(detectedCase.getCase().getName()));
        // Load the figure head
        figureVBox.getChildren().add(this.loadHead(detectedCase.getCase().getSeverity())); //loadHead metoden er i bunden
        // Get the case data
        Dataset<Row> caseData = this.getCaseDataset(detectedCase.getPatient().getData(), detectedCase.getCaseInterval());
        // Calcualte case average values
        Dataset<Row> averageValeus = caseData.select(
            avg("SpO2"),
            avg("FiO2Set")
        );

        caseData.select(
            avg("SpO2"),
            avg("FiO2Set")
        ).show();
        averageValeus.show();
        // Get the top triangle
        figureVBox.getChildren().add(this.loadTopTriangle(
            averageValeus.first().getDouble(0), 
            averageValeus.first().getDouble(1))
        );


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

        //Hent tider fra timeinterval for detectedCase
        //LocalDateTime newestTime = LocalDateTime.of(2021, 05, 16, 23, 00, 00);
        //LocalDateTime oldestTime = LocalDateTime.of(2021, 05, 16, 23, 30, 00);      
   


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


    private boolean After(Object column) {
        return false;
    }


    private boolean Before(Object column) {
        return false;
    }


    private Object Column(int i) {
        return null;
    }

    private Label loadCaseLabel(String labelText) {
        // Label til casenavn + detaljer
        Label caseLabel = new Label(labelText);
        caseLabel.setFont(new Font("Arial",30));
        caseLabel.setMaxWidth(Double.MAX_VALUE);
        caseLabel.setAlignment(Pos.CENTER);
        return caseLabel;
    }

    private BorderPane loadHead(Case.Severity severity) {
        // Pane til hovedet på trekanten
        BorderPane headPane = new BorderPane();
        // Prepare a container for the image
        Image image = null;
        // Load the picture according the the severity
        try {
            switch(severity) {
                case MILD:
                    image = new Image(getClass().getResource("/image/Good.png").toString());
                    break;
                case INTERMEDIATE:
                    image = new Image(getClass().getResource("/image/NotGood.png").toString());
                    break;
                case SEVERE:
                    image = new Image(getClass().getResource("/image/Bad.png").toString());
                    break;
            }
        } catch (NullPointerException e) {
            System.err.println("Failed to find image in resources.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Path to file is invalid.");
            e.printStackTrace();
        }
        // Inser the image into a image view
        if (image != null) {
            // Create a image view form the image
            ImageView imageView = new ImageView(image);
            // Set the width of the image
            imageView.setFitWidth(50); 
            imageView.setPreserveRatio(true); 
            // Place the image in the headpane
            headPane.setCenter(imageView); 
        }
        return headPane;
    }
    
    private Dataset<Row> getCaseDataset(Dataset<Row> patientData, TimeInterval caseInterval) {
        return patientData.filter((Row row) -> {    
            LocalDateTime localDateTime = LocalDateTime.parse(row.getString(0));
            if(localDateTime.isAfter(caseInterval.getNewestTime()) && localDateTime.isBefore(caseInterval.getOldestTime())){
                return true;
            } else {
                return false;
            }
        });
    }

    private BorderPane loadTopTriangle(Double meanSpO2, Double meanFiO2) {
        // Create the border pane
        BorderPane topTriagPane = new BorderPane();
        // Calculate the height of the FiO2 setting
        // (1 - 1) * 50 = 0
        // (1 - 0.21) * 50 = 39.5
        // :))))))
        Double heightFiO2 = (1 - meanFiO2) * TriangleMetaphoricFactory.figureMaxHeight;
        // Calcualte the width of the SpO2
        // Prevent negative fractions, by enforcing meanFiO2 above 0.75
        if (meanFiO2.compareTo(0.75) < 0) {
            meanFiO2 = 0.75;
        }
        // Calcuate fraction of range for meanFiO2
        Double fraction = (meanFiO2 - 0.75) / (0.98 - 0.75);
        // Get the middle point
        Double middle = (TriangleMetaphoricFactory.figureMaxWidth / 2);
        // Calculate the coordinates
        Double leftWidthSpO2 = ((middle - (middle * 0.2)) - (middle - (middle * 0.2)) * fraction);
        Double rightWidthSpO2 = (middle + (middle - leftWidthSpO2));
        // Width coordinates SpO2
        Double X1 = leftWidthSpO2; Double Y1 = TriangleMetaphoricFactory.figureMaxHeight; 
        Double X2 = rightWidthSpO2; Double Y2 = TriangleMetaphoricFactory.figureMaxHeight;
        // Height coordinates FiO2
        Double X3 = (TriangleMetaphoricFactory.figureMaxWidth / 2); Double Y3 = heightFiO2;
        // Draw top triangle
        Polygon topPolygon = new Polygon();
        topPolygon.getPoints().addAll(new Double[] {
            X1, Y1,
            X2, Y2,
            X3, Y3
        });
        // Add the prolygon the the pane
        topTriagPane.getChildren().add(topPolygon);
        // return the pane
        return topTriagPane;
    }

}
