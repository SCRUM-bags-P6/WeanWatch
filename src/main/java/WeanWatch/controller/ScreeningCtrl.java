package WeanWatch.controller;

import WeanWatch.model.DetectedCase;
import WeanWatch.model.TriangleFigure;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ScreeningCtrl {

@FXML
public ScrollPane scroll;

public void initialize(){

	DetectedCase detectedCase = new DetectedCase();
		

	TriangleMetaphoricCtrl MCtrl = new TriangleMetaphoricCtrl(detectedCase);
	


	TriangleFigure root = MCtrl.drawFigure(0, 31.00, 250.00, 151.00, 250.00, 91.00, 350.00, 3, 31.00, 260.00, 151.00, 260.00, 91.00, 160.00);
	TriangleFigure root2 = MCtrl.drawFigure(0, 31.00, 250.00, 151.00, 250.00, 91.00, 350.00, 3, 31.00, 260.00, 151.00, 260.00, 91.00, 160.00);
	TriangleFigure root3 = MCtrl.drawFigure(0, 31.00, 250.00, 151.00, 250.00, 91.00, 350.00, 3, 31.00, 260.00, 151.00, 260.00, 91.00, 160.00);
		
try {
	FXMLLoader loader = new FXMLLoader();
	//loader.setLocation(App.class.getClassLoader().getResource("view/MetaphoricHandlerView.fxml"));
	loader.setLocation(getClass().getResource("/view/MetaphoricHandlerView.fxml"));
	VBox view = (VBox) loader.load();

	this.scroll.setContent(view);

		
	MetaphoricHandlerCtrl MHCtrl = loader.getController();
	MHCtrl.setMetaphoricCtrl(MCtrl);

	//Indsæt figur i top
	MHCtrl.insertMetaphoricFigureTop(root.getFigureTop());
	MHCtrl.insertMetaphoricFigureTop(root2.getFigureTop());
	MHCtrl.insertMetaphoricFigureTop(root3.getFigureTop());

	//Indsæt figur i bottom
	MHCtrl.insertMetaphoricFigureBottom(root.getFigureBottom());
	MHCtrl.insertMetaphoricFigureBottom(root2.getFigureBottom());
	MHCtrl.insertMetaphoricFigureBottom(root3.getFigureBottom());
		
	
} catch (Exception e) {
	//TODO: handle exception
	System.out.println("We did the wrong");
	System.out.println(e);
	e.printStackTrace();
}
	
		
}


}
