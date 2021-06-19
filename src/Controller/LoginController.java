package Controller;

import Model.Messages.ClientMessages.LoginRequest;
import Model.Connection;


import Model.PageLoader;
import Model.Messages.ServerMessages.LoginResponse;
import Model.ThisUser;
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
            wrongUserLabel.setVisible(false);
            wrongPasswordLabel.setVisible(false);
            String pass;
            if (showPasswordCheckbox.isSelected()) {
                pass = visiblePasswordField.getText();
            } else {
                pass = passwordField.getText();
            }
            Connection.sendMessage(new LoginRequest(
                    usernameField.getText(),
                    pass
            ));
            LoginResponse lr = (LoginResponse) Connection.receiveMessage();
            if (checkLogin(lr)) {
                ThisUser.init(lr.getUser());
                new PageLoader().load("TimeLine");
            }
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

    public boolean checkLogin(LoginResponse lr) {
        if (lr.getResponses().size() == 1 && lr.getResponses().get(0).equals("success"))
            return true;
        for (String r : lr.getResponses()) {
            switch (r) {
                case "wrong_password" -> wrongPasswordLabel.setVisible(true);
                case "no_username" -> wrongUserLabel.setVisible(true);
            }
        }
        return false;
    }
}
