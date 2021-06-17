package Controller;

import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class SignUpController {
    public ImageView ProfilePhoto;
    public TextField FirstNameField;
    public TextField LastNameField;
    public TextField BirthDateField;
    public TextField UsernameField;
    public PasswordField PasswordField;
    public Button PhotoUploadButton;
    public Label WrongDateFormatLabel;
    public Label UsernameUnavailableLabel;
    public Label WrongPasswordLabel;
    public Button SignUpButton;
    public Button LoginPageLoadButton;
    public MenuButton genderMenuButton;

    public void uploadPhoto(ActionEvent actionEvent) {

    }

    public void loadLoginPage(ActionEvent actionEvent) {
        try {
            new PageLoader().load("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signup(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.CONFIRMATION,"You are registered successfully!").showAndWait();
        try {
            new PageLoader().load("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGenderMale(ActionEvent actionEvent) {
        genderMenuButton.setText("Male");
    }

    public void setGenderFemale(ActionEvent actionEvent) {
        genderMenuButton.setText("Female");
    }

    public void setGenderOther(ActionEvent actionEvent) {
        genderMenuButton.setText("Other");
    }


}
