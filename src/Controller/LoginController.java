package Controller;

import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class LoginController {
    @FXML
    public Button loginButton;
    public PasswordField passwordField;
    public TextField usernameField;
    public Label wrongUserLabel;
    public Label wrongPasswordLabel;
    public CheckBox showPasswordCheckbox;
    public Button signupButton;
    public TextField visiblePasswordField;
    public ImageView loginPageLogo;


    public void login(ActionEvent actionEvent) {
        try {
            new PageLoader().load("TimeLine");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPassword(ActionEvent actionEvent) {
        if (!visiblePasswordField.isVisible()) {
            visiblePasswordField.setText(passwordField.getText());
            visiblePasswordField.setVisible(true);
        } else {
            passwordField.setText(visiblePasswordField.getText());
            visiblePasswordField.setVisible(false);
        }
    }

    public void loadSignupPage(ActionEvent actionEvent) {
        try {
            new PageLoader().load("SignUp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
