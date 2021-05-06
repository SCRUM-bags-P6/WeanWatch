package WeanWatch.controller;

import WeanWatch.model.DetectedCase;
import javafx.scene.shape.Polygon;

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

}
