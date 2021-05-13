package WeanWatch.controller;

import WeanWatch.model.DetectedCase;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;

import java.util.ArrayList;

import WeanWatch.controller.*;




public class MetaphoricHandlerCtrl {
	@FXML
	private VBox FigureVBox;

	@FXML
	private HBox FigureHBoxTop;

	@FXML
	private HBox FigureHBoxBottom;

	private TriangleMetaphoricCtrl metaphoricCtrl;

	public MetaphoricCaptionCtrl() {
	}


	public void handleFigureClick() {
	
	}



	public void dispFigures(ArrayList<DetectedCase> casesToDisplay, ArrayList<MetaphoricCaptionCtrl> captionsToDisplay) {
		// Lav HBox
		HBox hbox;

		for (int i = 0; i < casesToDisplay.size(); i++) { //tæl hvor mange cases vi har der skal displayes 
			// Find ud af om der er plads i HBox
				// Er i % 3
					// Hvis ja, lav ny HBox
					// Hvis nej, indsæt i den nuværende HBox
			if (i % 3 == 0) {
				hbox = new HBox();
			}
			GenericMetaphoricCtrl figure = new TriangleMetaphoricCtrl(casesToDisplay.get(i));
			hbox.getChildren().add(figure);			
		}
		// Tilføj til VBox
	}



/**
 * Sætter den metaforiske figur i øverste HBox
 */
	public void insertMetaphoricFigureTop(Group metaphoricFigure){
		BorderPane pane = new BorderPane();
		pane.getChildren().add(metaphoricFigure);

	/*
	DEN HER DEL HØRER TIL "DET GAMLE" KODE, HVOR DER BLEV HARDCODED EN TOP OG EN BUND
	this.FigureHBoxTop.setSpacing(700.0);
	this.FigureHBoxTop.getChildren().add(pane);
	*/


	//DEN HER DEL ER TIL DEN NYE KODE, HVOR DER DYNAMISK SKABES HBOXe. PROBLEMET ER LIGENU, AT DEN LAVER EN HBOX FOR HVER FIGUR DEN SÆTTER IND
	//OG DET SKAL I STEDET VÆRE SÅDAN, AT DEN SÆTTER 3 FIGURER i 1 HBOX, OG SÅ 3 FIGURER I EN NY HBOX OSV.
	//PRøv at lave counter der tæller antallet af metaphoricFigures der bliver lavet
	//Udfra counteren kan der laves if eller for loop, der laver ny HBox for hver tredje figur der bliver lavet

		HBox HBox = createHBox(metaphoricFigure);

		this.FigureVBox.getChildren().add(HBox);

	}

/**
 * Sætter den metaforiske figur i nederste HBox
 */
public void insertMetaphoricFigureBottom(Group metaphoricFigure){
	BorderPane pane = new BorderPane();
	pane.getChildren().add(metaphoricFigure);
	
	this.FigureHBoxBottom.setSpacing(700.0);
	this.FigureHBoxBottom.getChildren().add(pane);
	}

public void setMetaphoricCtrl(TriangleMetaphoricCtrl metaphoricCtrl){
	this.metaphoricCtrl = metaphoricCtrl;

}


public HBox createHBox (Group metaphoricFigure){
	HBox HBox = new HBox();

	HBox.setSpacing(700.0);
	HBox.getChildren().add(metaphoricFigure);

	return HBox;
}

}