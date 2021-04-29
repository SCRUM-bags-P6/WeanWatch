package WeanWatch.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class loginCtrl {
    
    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField userPassField;

    @FXML
    private Button loginBtn;

    public void handleLoginBtnPress() {
        System.out.println("You gay!");
    }    

}
