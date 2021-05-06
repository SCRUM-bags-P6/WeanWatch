package WeanWatch.controller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;


public class LoginCtrl {
    
    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField userPassField;

    @FXML
    private Button loginBtn;

    public void handleLoginBtnPress(ActionEvent event) throws SQLException {
        Window owner = loginBtn.getScene().getWindow();

        System.out.println(userNameField.getText());
        System.out.println(userPassField.getText());

        if (userNameField.getText().isEmpty()) {
            //showAlert(Alert.AlertType.ERROR, owner, "Form Error", "Please enter username");
            
        }
    }    


}
