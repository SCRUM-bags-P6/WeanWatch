package WeanWatch.controller;

import java.util.ArrayList;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import WeanWatch.model.DetectedCase;
import WeanWatch.model.TriangleFigure;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.*;

public class TriangleMetaphoricCtrl extends GenericMetaphoricCtrl{
	/*
	Sæt de javaFX komponenter der hører til i viewet her
	Hver komponent skal have @fxml, samt tilsvarende fx id
	*/
	



	private Color[] Colors = new Color[] {javafx.scene.paint.Color.RED, 
		javafx.scene.paint.Color.GREEN, 
		javafx.scene.paint.Color.BLUE,
		javafx.scene.paint.Color.YELLOW};


	public TriangleMetaphoricCtrl(DetectedCase detectedCase) {
		super(detectedCase);
	}

/**
 * Inputparametre er: farve, og hhv. 3 x- og y-koordinater for hver figur. 
 * Returner en TriangleFigure, der har en Group attribut for hhv. top metaforisk figur og bund metaforisk figur.
 * Formålet med TriangleFigure klassen er, at vi kan samle drawFigure() i én funktion, frem for at opdele den i top og bund.
 * 
 */
	public TriangleFigure drawFigure(int fColor, Double ffirstX, Double ffirstY, Double fsecondX, Double fsecondY, Double fthirdX, Double fthirdY, int sColor, Double sfirstX, Double sfirstY, Double ssecondX, Double ssecondY, Double sthirdX, Double sthirdY){
		Group figureTop = drawFigureTop(fColor, ffirstX, ffirstY, fsecondX, fsecondY, fthirdX, fthirdY, sColor, sfirstX, sfirstY, ssecondX, ssecondY, sthirdX, sthirdY);
		

		//Y koordinat 590 er midten af skærmen - Dette skifter figurerne fra top HBox til bund HBox
		Double Increase = 400.0;
		Double tempFfirstY = ffirstY+Increase;
		Double tempFsecondY = fsecondY+Increase;
		Double tempFthirdY = fthirdY+Increase;
		Double tempSfirstY = sfirstY+Increase;
		Double tempSsecondY = ssecondY+Increase;
		Double tempSthirdY = sthirdY+Increase;

		Group figureBottom = drawFigureBottom(fColor, ffirstX, tempFfirstY, fsecondX, tempFsecondY, fthirdX, tempFthirdY, sColor, sfirstX, tempSfirstY, ssecondX, tempSsecondY, sthirdX, tempSthirdY);

		return new TriangleFigure(figureTop, figureBottom);
	}


	/**
	 * Laver den metaforiske figur til bunden af skærmen. Der skal forskellige y-koordinater til top og bund figuren, derfor denne opdeling.
	 */
	public Group drawFigureBottom(int fColor, Double ffirstX, Double ffirstY, Double fsecondX, Double fsecondY, Double fthirdX, Double fthirdY, int sColor, Double sfirstX, Double sfirstY, Double ssecondX, Double ssecondY, Double sthirdX, Double sthirdY){
		Circle circle = drawCircleBottom();
		
		Group root = new Group(circle,
		drawFirstFigure(fColor, ffirstX, ffirstY, fsecondX, fsecondY, fthirdX, fthirdY), 
		drawSecondFigure(sColor, sfirstX, sfirstY, ssecondX, ssecondY, sthirdX, sthirdY)
		);

		return root;
	}

	/**
	 * Laver den metaforiske figur til bunden af skærmen. Der skal forskellige y-koordinater til top og bund figuren, derfor denne opdeling.
	 */
	public Group drawFigureTop(int fColor, Double ffirstX, Double ffirstY, Double fsecondX, Double fsecondY, Double fthirdX, Double fthirdY, int sColor, Double sfirstX, Double sfirstY, Double ssecondX, Double ssecondY, Double sthirdX, Double sthirdY){
		Circle circle = drawCircleTop();
		
		Group root = new Group(circle,
		drawFirstFigure(fColor, ffirstX, ffirstY, fsecondX, fsecondY, fthirdX, fthirdY), 
		drawSecondFigure(sColor, sfirstX, sfirstY, ssecondX, ssecondY, sthirdX, sthirdY)
		);

		return root;
	}

	/**
	 * Laver figuren der viser patientens iltning
	 * Tager et int input for farve, og hhv. 3 x- og y-koordinater
	 */
	public Polygon drawFirstFigure(int Color, Double firstX, Double firstY, Double secondX, Double secondY, Double thirdX, Double thirdY){
		Polygon MetaphoricFigure = new Polygon();
	
		MetaphoricFigure.getPoints().addAll(new Double[]{
			firstX, firstY,
			secondX, secondY,
			thirdX, thirdY,
		});
	
		MetaphoricFigure.setFill(Colors[Color]);	
	
	return MetaphoricFigure;
	}


	/**
	 * Laver figuren der viser patientens aktivitet.
	 * Tager et int input for farve, og hhv. 3 x- og y-koordinater
	 */
	public Polygon drawSecondFigure(int Color, Double firstX, Double firstY, Double secondX, Double secondY, Double thirdX, Double thirdY){
		Polygon MetaphoricFigure = new Polygon();
	
		MetaphoricFigure.getPoints().addAll(new Double[]{
			firstX, firstY,
			secondX, secondY,
			thirdX, thirdY,
		});
	
		MetaphoricFigure.setFill(Colors[Color]);	
	
	return MetaphoricFigure;
	}


	/**
	 * Laver circlen til figuren i toppen af skærmen.
	 * Der er forskellige y-koordinater til top og bund, derfor denne opdeling.
	 * @return circle
	 */
	public Circle drawCircleTop(){
		Circle circle = new Circle();
		circle.setCenterX(100.0f);
		circle.setCenterY(100.0f);
		circle.setRadius(50.0f);
		circle.setFill(Colors[1]);
		circle.setLayoutX(1.00);
		circle.setLayoutY(1.00);

		return circle;
	}


	/**
	 * Laver circlen til figuren i toppen af skærmen.
	 * Der er forskellige y-koordinater til top og bund, derfor denne opdeling.
	 * @return circle
	 */
	public Circle drawCircleBottom(){
		Circle circle = new Circle();
		circle.setCenterX(100.0f);
		circle.setCenterY(100.0f);
		circle.setRadius(50.0f);
		circle.setFill(Colors[1]);
		circle.setLayoutX(1.00);
		circle.setLayoutY(400.00);

		return circle;
	}

	



}
