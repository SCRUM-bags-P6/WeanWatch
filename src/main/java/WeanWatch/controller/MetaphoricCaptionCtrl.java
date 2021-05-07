package WeanWatch.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MetaphoricCaptionCtrl<MetaphoricCaptionView> {

@FXML
public GridPane metaphoricCaptionGridPane;

@FXML
public Label lastOccurrenceLabel;
    
@FXML
public Label numberOfOccurrencesLabel;
    
@FXML
public Label caseNameLabel;

@FXML
public Label timeLabel;

private String caseName; 

private String timeStamp;

private Integer numOccurrences; 

private Integer lastOccurrence; 

private MetaphoricCaptionView MetaphoricCaptionView; 

public MetaphoricCaptionCtrl() {
	

}
// Constructor
public void setCaption(String caseName, String timeStamp, Integer numOccurrences, Integer lastOccurrence) {
	

}





}
