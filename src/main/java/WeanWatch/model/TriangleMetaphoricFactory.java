package WeanWatch.model;

import javafx.scene.Group;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
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

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;


public class TriangleMetaphoricFactory extends MetaphoricFactory {

    private final static Double figureMaxWidth = 300D;
    private final static Double figureMaxHeight = 200D;

    public BorderPane create(DetectedCase detectedCase) {
    
        // Figure root pane, hvor alle dele af figuren skal være i
        BorderPane figureRoot = new BorderPane();
        
        //VBoxes til at sætte Label og hoved i den øverste og stackPanes med trekanter i den nederste
        VBox figureVBoxTop = new VBox(20D); //Spacing set to 20D
        VBox figureVBoxBot = new VBox();

        //Til at stacke linechart og metaforisk figur
        StackPane figureStackerBotTop = new StackPane();
        StackPane figureStackerBotBot = new StackPane();
        

		//Sætter akser for top figuren
		String[] CategoriesXTop = {"100 ", "95 ", "90 ", "85 ", "80 ", "75 ", " ", "75", "80", "85", "90", "95", "100"};        
		String[] CategoriesYTop = {"0.21", "0.25", "0.28", "0.32", "0.35", "0.40","0.50", "0.60", "0.80", "1"};
            
        CategoryAxis xAxisTop = new CategoryAxis();
        CategoryAxis yAxisTop = new CategoryAxis();

        //Indsætter ticklabels på top figuren
		xAxisTop.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(CategoriesXTop)));
		yAxisTop.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(CategoriesYTop)));

		//Sætter akser for bund figuren
		String[] CategoriesXBot = {"Very good ", "Good ", "Medium ", "Weak ", "Very Weak ", " ", "Very Weak", "Weak", "Medium", "Good", "Very Good"};        
        String[] CategoriesYBot = {"68", "61", "54", "47", "40", "33", "26", "19", "12", "5"};
            
        CategoryAxis xAxisBot = new CategoryAxis();
        CategoryAxis yAxisBot = new CategoryAxis();

        //Indsætter ticklabels på bundfiguren, og sætter x-aksen i toppen af linechart
		xAxisBot.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(CategoriesXBot)));
        xAxisBot.setSide(Side.TOP);
		yAxisBot.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(CategoriesYBot)));

        //Skaber vores LineCharts og indsætter akserne
        LineChart<String, String> figureAxisTop = new LineChart<String, String>(xAxisTop, yAxisTop);
        LineChart<String, String> figureAxisBot = new LineChart<String, String>(xAxisBot, yAxisBot);

        //Fjerner grid i LineCharts
        figureAxisTop.setHorizontalGridLinesVisible(false);
        figureAxisBot.setHorizontalGridLinesVisible(false);
        figureAxisTop.setVerticalGridLinesVisible(false);
        figureAxisBot.setVerticalGridLinesVisible(false);
      
      
       // figureAxisTop.resize(figureMaxWidth, figureMaxHeight);
       // figureAxisBot.resize(figureMaxWidth, figureMaxHeight);
        
        //Sætte højden og bredden af LineCharts
        

        /*
        figureAxisTop.setWidth(figureMaxWidth);
        figureAxisTop.setHeight(figureMaxHeight);

        figureAxisBot.setHeight(figureMaxHeight);
        figureAxisBot.setWidth(figureMaxWidth);
        */
        /*
        figureAxisTop.setPrefWidth(figureMaxWidth);
        figureAxisTop.setPrefHeight(figureMaxHeight);

        figureAxisBot.setPrefHeight(figureMaxHeight);
        figureAxisBot.setPrefWidth(figureMaxWidth);
        */
        
        
        System.out.println("Bund figur bredde = " + figureAxisBot.getWidth());
        System.out.println("Bund figur højde = " + figureAxisBot.getHeight());
        System.out.println("Top figur bredde = " + figureAxisTop.getWidth());
        System.out.println("Top figur højde = " + figureAxisTop.getHeight());
        System.out.println("SE HER");System.out.println("SE HER");
        System.out.println("SE HER");
        System.out.println("SE HER");
        System.out.println("SE HER");
        System.out.println("SE HER");System.out.println("SE HER");
        System.out.println("SE HER");
        System.out.println("SE HER");
        System.out.println("SE HER");
        

        /*
        figureAxisTop.setMaxHeight(figureMaxHeight);
        figureAxisTop.setMinHeight(figureMaxHeight);

        //SE HER
        figureAxisTop.setMaxWidth(figureMaxWidth);
        figureAxisTop.setMinWidth(figureMaxWidth);

        figureAxisBot.setMaxHeight(figureMaxHeight);
        figureAxisBot.setMinHeight(figureMaxHeight);

        figureAxisBot.setMaxWidth(figureMaxWidth);
        figureAxisBot.setMinWidth(figureMaxWidth);
        */
        

        // Load the case label
        Label caseLabel = this.loadCaseLabel(detectedCase.getCase().getName());
        // Load the figure head
        BorderPane figureHead = this.loadHead(detectedCase.getCase().getSeverity()); //loadHead metoden er i bunden
        // Get the case data
        Dataset<Row> caseData = this.getCaseDataset(detectedCase.getPatient().getData(), detectedCase.getCaseInterval());
        // Calculate case average values
        Dataset<Row> averageValues = caseData.select(
            avg("SpO2"),
            avg("FiO2Set"),
            avg("Rf"),
            avg("Comp"),
            avg("RSBI"),
            avg("EE")
        );
        // Get the top triangle
        BorderPane topTriag = this.loadTopTriangle(
            averageValues.first().getDouble(0), 
            averageValues.first().getDouble(1)
        );
        
        // Get the bottom triangle
        BorderPane butTriag = this.loadButTriangle(
            averageValues.first().getDouble(2), 
            averageValues.first().getDouble(3), 
            averageValues.first().getDouble(4),
            averageValues.first().getDouble(5)
        );
        
        //Tilføjer Label og Hoved i top VBox
        figureVBoxTop.getChildren().addAll(caseLabel, figureHead);

        //Tilføjer trekanter og tilhørende akser til StackPanes
        figureStackerBotTop.getChildren().addAll(figureAxisTop,topTriag);
        figureStackerBotBot.getChildren().addAll(figureAxisBot,butTriag);
        /*      
        System.out.println("Stackpane højde = " + figureStackerBotTop.getHeight());
        System.out.println("Stackpane bredde = " + figureStackerBotTop.getWidth());
        System.out.println("SE HER");System.out.println("SE HER");
        System.out.println("SE HER");
        System.out.println("SE HER");
        System.out.println("SE HER");
        */

        //Justere indryk af trekanter, så de passer med akser
        figureStackerBotTop.setMargin(topTriag, new Insets(160D,0,0,110D));
		figureStackerBotBot.setMargin(butTriag, new Insets(80D,0,0,110D));

        /*
        figureStackerBotBot.setPrefHeight(figureMaxHeight);
        figureStackerBotBot.setPrefWidth(figureMaxWidth);
        figureStackerBotTop.setPrefHeight(figureMaxHeight);
        figureStackerBotTop.setPrefWidth(figureMaxWidth);
        */
        //Tilføjer StackPanes til nederste VBox
        figureVBoxBot.getChildren().addAll(figureStackerBotTop, figureStackerBotBot);

        
        /* Beholdes da det måske skal bruges senere til at justere Label og Hoved ift. resten af figuren
        Double labelWidth = caseLabel.getBoundsInLocal().getWidth(); //Bruges til at centrere figurerne dynamisk
        Double headWidth = figureHead.getBoundsInLocal().getWidth();
        */
        
        caseLabel.setPadding(new Insets(0,0,0,(TriangleMetaphoricFactory.figureMaxWidth / 2D) - 50/*(labelWidth)*/));
        figureHead.setPadding(new Insets(0,0,0,(TriangleMetaphoricFactory.figureMaxWidth / 2D) - 50 /*(headWidth)*/));

        
        //Set figurVBoxTop in center and figureVBoxBot in Bottom of figureRoot
        figureRoot.setCenter(figureVBoxTop);
        figureRoot.setBottom(figureVBoxBot);
        
        // Return the figure
        return figureRoot;
    }

    private Label loadCaseLabel(String labelText) {
        // Label til casenavn + detaljer
        Label caseLabel = new Label(labelText);
        caseLabel.setFont(new Font("Arial",30));
        caseLabel.setMaxWidth(Double.MAX_VALUE);
        //caseLabel.setAlignment(Pos.CENTER);
        System.out.println("LABEL!!!!");
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
            imageView.setFitWidth(100); 
            imageView.setPreserveRatio(true); 
            // Place the image in the headpane
            headPane.setCenter(imageView);             
            System.out.println("HOVED");
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
        // Enforce size
        topTriagPane.prefHeight(TriangleMetaphoricFactory.figureMaxHeight);
        topTriagPane.prefWidth(TriangleMetaphoricFactory.figureMaxWidth);
        // Calculate the height of the FiO2 setting
        // (1 - 1) * 50 = 0
        // (1 - 0.21) * 50 = 39.5
        
        Double heightFiO2 = (1 - meanFiO2) * TriangleMetaphoricFactory.figureMaxHeight;
        // Calcualte the width of the SpO2
        // Prevent negative fractions, by enforcing meanFiO2 above 0.75
        if (meanFiO2.compareTo(0.75D) > 0) {
            meanFiO2 = 0.75D;
        }
        // Calcuate fraction of range for meanFiO2
        Double fraction = (meanFiO2 - 0.75D) / (0.98D - 0.75D);
        // Get the middle point
        Double middle = (TriangleMetaphoricFactory.figureMaxWidth / 2D);
        // Calculate the coordinates
        Double leftWidthSpO2 = ((middle - (middle * 0.2D)) - (middle - (middle * 0.2D)) * fraction);
        Double rightWidthSpO2 = (middle + (middle - leftWidthSpO2));
        // Width coordinates SpO2
        Double X1 = leftWidthSpO2; 
        Double Y1 = TriangleMetaphoricFactory.figureMaxHeight;
        //Double Y1 = heightFiO2; 
        Double X2 = rightWidthSpO2; 
        Double Y2 = TriangleMetaphoricFactory.figureMaxHeight;
        //Double Y2 = heightFiO2;
        // Height coordinates FiO2
        Double X3 = middle; 
        //Double Y3 = 0D - heightFiO2;
        //Double Y3 = 0D;
        Double Y3 = heightFiO2;

        // Draw top triangle
        //Double Y1 = 0D;
        //Double Y2 = 0D;

        Polygon topPolygon = new Polygon();
        topPolygon.getPoints().addAll(new Double[] {
            X1, Y1,
            X2, Y2,
            X3, Y3,
        });
        
        System.out.println("ØVERSTE TREKANT");
        //System.out.println(" 1. " + X1 + Y1 + " 2. " + X2 + Y2 + " 3. " + X3 + Y3);
        
        System.out.println(" 1. " + "X = " + X1 + "Y = " + Y1 + " 2. " + "X = " +X2 + "Y = " + Y2 + " 3. " + "X = " + X3 + "Y = " +Y3);

        // Add the prolygon the the pane
        topTriagPane.getChildren().add(topPolygon);
        topTriagPane.setAlignment(topPolygon, Pos.TOP_CENTER);
        // return the pane
        return topTriagPane;
    }

    private BorderPane loadButTriangle(Double meanRR, Double meanComp, Double meanRSBI, Double meanEE) {
        // Create the border pane
        BorderPane butTriagPane = new BorderPane();
        // Enforce size
        butTriagPane.prefHeight(TriangleMetaphoricFactory.figureMaxHeight);
        butTriagPane.prefWidth(TriangleMetaphoricFactory.figureMaxWidth);
        // y = 0,0143x
        //Height of RR
        if (meanRR.compareTo(70D) > 0){
            meanRR = 70D;
        }
        //Y-value of the triag height
        Double heightRR = (meanRR*0.0143D) * TriangleMetaphoricFactory.figureMaxHeight;
        //X-value equals maxWidth/2
        // Get the middle point
        Double middle = (TriangleMetaphoricFactory.figureMaxWidth / 2);
        Double diagStrength = meanComp*meanEE*meanRSBI;
        // Calculate the width of diaphragmaStrenght Triag baseline
        // y = -5*10^16*x^4 + 2*10^-11*x^3 - 2E-07x^2 + 0,001x - 0,526
        //Double widthDiagStrength = (middle + (Math.pow(-5, -16) * Math.pow(diagStrenght, 4) + Math.pow(2, -11D) * Math.pow(diagStrength, 3) - Math.pow(2, -0.7D) * Math.pow(diagStrength, 2) + 0.001D * diagStrength - 0.526D))
        Double x4 = (-5D) * Math.pow(10D, (-16D)) * Math.pow(diagStrength, 4D);
        Double x3 = 2D * Math.pow(10D, (-11D)) * Math.pow(diagStrength, 3D);
        Double x2 = 2D * Math.pow(10D, (-7D)) * Math.pow(diagStrength, 2D);
        Double x1 = 0.001D * diagStrength;
        Double offset = 0.526D;
        /*
        System.out.println(x4 +" x4");
        System.out.println(x3 +" x3");
        System.out.println(x2 +" x2");
        System.out.println(x1 +" x1");
        System.out.println(offset + " offset");
        */
        Double regressionDiagStrength = x4 + x3 - x2 + x1 - offset;
        
        //Double regressionDiagStrength = (( + ((2D*Math.pow(10D, (-11D)) * Math.pow(diagStrength, 3D)) - ((2D*Math.pow(10D, (-0.7D))) * Math.pow(diagStrength, 2D)) + (0.001D * diagStrength) );
        //System.out.println(regressionDiagStrength+ "SE MIG HER");
        //System.out.println(diagStrength + "JEG ER DIAGSTRENGTH");
        // Calculate the coordinates
        Double leftWidthDS = middle - (regressionDiagStrength * middle);
        Double rightWidthDS = middle + (regressionDiagStrength * middle);
        // Width coordinates SpO2
        Double X1 = leftWidthDS; Double Y1 = 0D;
        Double X2 = rightWidthDS; Double Y2 = 0D;
        // Height coordinates FiO2
        Double X3 = middle; Double Y3 = heightRR;
        // Draw top triangle
        Polygon butPolygon = new Polygon();
        butPolygon.getPoints().addAll(new Double[] {
            X1, Y1,
            X2, Y2,
            X3, Y3,
        });
        System.out.println("NEDERSTE");
        System.out.println(" 1. " + "X = " + X1 + "Y = " + Y1 + " 2. " + "X = " + X2 + "Y = " + Y2 + " 3. " + "X = " + X3 + "Y = " +Y3);
        // Add the polygon the the pane
        butTriagPane.getChildren().add(butPolygon);
        butTriagPane.setAlignment(butPolygon, Pos.BOTTOM_CENTER);
        // return the pane
        return butTriagPane;
    }


}
