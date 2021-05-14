package WeanWatch.controller;

import java.sql.SQLException;
import java.util.function.Consumer;

import WeanWatch.model.LoginServerConn;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import WeanWatch.model.Personnel;


public class LoginCtrl {
    @FXML
    private TextField useridField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    private Consumer<Personnel> loginCallback;

    public void handleLoginClick() {
        // Get the userid and password
        String userid = this.useridField.getText();
        String password = this.passwordField.getText();
        // Authenticate the userid and password
        try {
            if (true) { // (LoginServerConn.getInstance().authenticateUser(userid, password)) {
                // Create a new patient instance with the user information
                Personnel user = new Personnel(userid);
                // Change the view
                this.loginCallback.accept(user);
            } else {
                // Show an alert
                LoginCtrl.showInvalidLogin();
                // Clear the content of the login fields
                this.useridField.clear();
                this.passwordField.clear();
            } 
        // } catch (SQLException e) {
        //     // Show timeout error
        //     LoginCtrl.showUnableToConnect();
        } catch (Exception e) {
            System.err.println("Failed to perform user login");
            e.printStackTrace();
        }
    }

    public void setLoginCallback(Consumer<Personnel> callback) {
        this.loginCallback = callback;
    }

    private static void showInvalidLogin() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Invalid username or password");
        alert.setHeaderText(null);
        alert.setContentText("The username and password combination does not match any known records. Please try again.");
        alert.show();
    }

    private static void showUnableToConnect() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("No connection");
        alert.setHeaderText(null);
        alert.setContentText("A connection to the login server could not be established. Please try again later.");
        alert.show();
    }
}
