package WeanWatch.model;

import javafx.scene.Group;

public class TriangleFigure {
	private Group figureTop;
	private Group figureBottom;

	public TriangleFigure(Group figureTop,  Group figureBottom)
	{
		this.figureTop = figureTop;
		this.figureBottom = figureBottom;
	}


	/**
	 * Bruges til at hente figuren der skal i toppen af skærmen
	 */
	public Group getFigureTop(){
		return this.figureTop;
	}

	/**
	 * Bruges til at hente figuren der skal i bunden af skærmen
	 */
	public Group getFigureBottom(){
		return this.figureBottom;
	}
		
		
}
