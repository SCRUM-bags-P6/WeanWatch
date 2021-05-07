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
private HBox FigureHBox;

@FXML
private VBox FigureVBox;

@FXML
private Label label;

private TriangleMetaphoricCtrl metaphoricCtrl;



public void insertMetaphoricFigure(Group metaphoricFigure){
BorderPane pane = new BorderPane();
pane.getChildren().add(metaphoricFigure);


//this.FigureVBox.autosize();
this.FigureVBox.getChildren().add(pane);
}

public void setMetaphoricCtrl(TriangleMetaphoricCtrl metaphoricCtrl){
this.metaphoricCtrl = metaphoricCtrl;

}



/*
figureHBox.setPadding(new Insets(100, 100, 100, 100));
figureHBox.setSpacing(20);

*/
}


