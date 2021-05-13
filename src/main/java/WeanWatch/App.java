/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package WeanWatch;

import java.awt.Color;
import java.awt.Dimension;

import WeanWatch.controller.MetaphoricHandlerCtrl;
import WeanWatch.controller.RootCtrl;
import WeanWatch.controller.TriangleMetaphoricCtrl;
import WeanWatch.model.PDMSConn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import java.io.FileInputStream;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.*;

import WeanWatch.model.*;


public class App extends Application {

    public static void main(String[] args) {
        // Launch JavaFX
        launch(args); 

        PDMSConn pdmsConn = PDMSConn.getInstance();

        pdmsConn.getPatients();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Get screen size
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        System.out.println(screenSize.getHeight());
        System.out.println(screenSize.getWidth());


        double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		
/*		
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getClassLoader().getResource("view/LoginView.fxml"));
        BorderPane view = (BorderPane) loader.load();
*/


		//Kun til test af figurer
		DetectedCase detectedcase = new DetectedCase();

		TriangleMetaphoricCtrl MCtrl = new TriangleMetaphoricCtrl(detectedcase);

		//Group root = MCtrl.drawFigure(0, 300.0, 100.0, 150.0, 200.0, 400.0, 500.0, 1, 800.0, 600.0, 400.0, 450.0, 900.0, 1000.0);
		Group root = MCtrl.drawFigure(0, 960.00, 590.00, 1080.00, 590.00, 1020.00, 800.00, 1, 960.00, 500.00, 1080.00, 500.00, 1020.0, 400.00);

		//MetaphoricHandlerCtrl MHCtrl = new MetaphoricHandlerCtrl();

		

		//MHCtrl.insertMetaphoricFigure(root);

		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(App.class.getClassLoader().getResource("view/MetaphoricHandlerView.fxml"));
		VBox view = (VBox) loader.load();

		
		MetaphoricHandlerCtrl MHCtrl = loader.getController();
		MHCtrl.setMetaphoricCtrl(MCtrl);
		MHCtrl.insertMetaphoricFigure(root);
		
		

		MHCtrl.setMetaphoricCtrl(MCtrl);
		
		//String print = loader.getController().getClass().getName();
		//System.out.println(print);

		
		

		try {
			primaryStage.setScene(new Scene(view, width, height));			
			//primaryStage.setScene(new Scene(view, width, height));
			//primaryStage.getScene().getStylesheets().add("view/Stylesheet.css");
			primaryStage.show();
            
		} catch (Exception e) {
			e.printStackTrace();
		}

		//System.out.println(firstTriangle.getPoints());        // TODO Auto-generated method stub
        
    }
}
