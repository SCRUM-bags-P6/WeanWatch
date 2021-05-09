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

import WeanWatch.controller.*;




public class MetaphoricHandlerCtrl {

@FXML
private VBox FigureVBox;

@FXML
private HBox FigureHBoxTop;

@FXML
private HBox FigureHBoxBottom;

private TriangleMetaphoricCtrl metaphoricCtrl;





/**
 * Sætter den metaforiske figur i øverste HBox
 */
public void insertMetaphoricFigureTop(Group metaphoricFigure){
BorderPane pane = new BorderPane();
pane.getChildren().add(metaphoricFigure);

this.FigureHBoxTop.setSpacing(700.0);
this.FigureHBoxTop.getChildren().add(pane);
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

}