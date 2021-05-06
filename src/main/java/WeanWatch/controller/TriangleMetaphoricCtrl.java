package WeanWatch.controller;

import WeanWatch.model.DetectedCase;
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
		javafx.scene.paint.Color.BLUE};


	public TriangleMetaphoricCtrl(DetectedCase detectedCase) {
		super(detectedCase);
	}

	public Group drawFigure(int fColor, Double ffirstX, Double ffirstY, Double fsecondX, Double fsecondY, Double fthirdX, Double fthirdY, int sColor, Double sfirstX, Double sfirstY, Double ssecondX, Double ssecondY, Double sthirdX, Double sthirdY){
		//er hardcoded, skal ændres til dynamisk kodning
		

		Circle circle = new Circle();
		circle.setCenterX(100.0f);
		circle.setCenterY(100.0f);
		circle.setRadius(50.0f);
		circle.setFill(Colors[2]);
		circle.setLayoutX(920.00);
		circle.setLayoutY(200.00);

		
		Group root = new Group(circle,
		drawFirstFigure(fColor, ffirstX, ffirstY, fsecondX, fsecondY, fthirdX, fthirdY), 
		drawSecondFigure(sColor, sfirstX, sfirstY, ssecondX, ssecondY, sthirdX, sthirdY)
		);

		return root;
	}

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

}
