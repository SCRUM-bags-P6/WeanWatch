package WeanWatch.controller;

import java.sql.SQLException;

import WeanWatch.model.LoginServerConn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import WeanWatch.model.Personnel;


public class LoginCtrl {
    @FXML
    private TextField useridField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    // Constructor for loginCtrl
    public LoginCtrl() {

    }

    public void handleLoginClick() {
        String userid = this.useridField.getText();
        String password = this.passwordField.getText();

        if(LoginServerConn.getInstance().authenticateUser(userid, password)) {
            //TODO: Skift til næste pane
            Personnel(userid, password);
        } else {
            
        }
    }

    // public void handleLoginClick(ActionEvent event) throws SQLException {
    //     Window owner = loginBtn.getScene().getWindow();

    //     System.out.println(useridField.getText());
    //     System.out.println(passwordField.getText());

    //     if (useridField.getText().isEmpty()) {
    //         showAlert(Alert.AlertType.ERROR, owner, "Form Error", "Please enter username");
    //         return;
    //     }
    //     if (passwordField.getText().isEmpty()) {
    //         showAlert(Alert.AlertType.ERROR, owner, "Form Error", "Please enter password");
    //         return;
    //     }

    //     String userid = useridField.getText();
    //     String password = passwordField.getText();

    //     LoginServerConn loginServerConn = LoginServerConn.getInstance();
    //     boolean flag = loginServerConn.authenticateUser(userid, password);

    //     if(!flag) {
    //         infoBox("Please enter correct userid and password", null, "Failed");
    //     } else {
    //         infoBox("Login Succesful", null, "Failed");
    //     }
    // }    

    // public static void infoBox(String infoMessage, String headerText, String title) {
    //     Alert alert = new Alert(AlertType.CONFIRMATION);
    //     alert.setContentText(infoMessage);
    //     alert.setTitle(title);
    //     alert.setHeaderText(headerText);
    //     alert.showAndWait();
    // }

    // private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
    //     Alert alert = new Alert(alertType);
    //     alert.setTitle(title);
    //     alert.setHeaderText(null);
    //     alert.setContentText(message);
    //     alert.initOwner(owner);
    //     alert.show();
    // }
}
