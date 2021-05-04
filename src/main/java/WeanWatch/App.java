/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package WeanWatch;

import java.awt.Dimension;

import WeanWatch.controller.RootCtrl;
import WeanWatch.model.PDMSConn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        // Launch JavaFX
        //launch(args); 

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
		
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getClassLoader().getResource("view/LoginView.fxml"));
        BorderPane view = (BorderPane) loader.load();
        
        // FXMLLoader loader = new FXMLLoader();
        // loader.setLocation(App.class.getClassLoader().getResource("view/rootView.fxml"));
        // BorderPane view = (BorderPane) loader.load();
        

		try {
			primaryStage.setScene(new Scene(view, width, height));
			primaryStage.show();

            
		} catch (Exception e) {
			e.printStackTrace();
		}

        // TODO Auto-generated method stub
        
    }
}
