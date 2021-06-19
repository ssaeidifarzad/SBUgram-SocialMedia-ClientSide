package Controller;

import Model.Connection;
import Model.DataTypes.User.Gender;
import Model.Messages.ClientMessages.SignupRequest;
import Model.Messages.ServerMessages.SignupResponse;
import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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

    private boolean hasPhoto = false;
    private String photoFormat;
    private File photo;

    public void uploadPhoto(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("jpg Files", "*.jpg")
                , new FileChooser.ExtensionFilter("png Files", "*.png")
        );
        File photo = fileChooser.showOpenDialog(new Stage());
        this.photo = photo;
        ProfilePhoto.setImage(new Image(photo.toURI().toString()));
        hasPhoto = true;
        photoFormat = photo.getName().split("\\.")[1];
    }

    public void loadLoginPage(ActionEvent actionEvent) {
        try {
            new PageLoader().load("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signup(ActionEvent actionEvent) {
        UsernameUnavailableLabel.setVisible(false);
        WrongPasswordLabel.setVisible(false);
        WrongDateFormatLabel.setVisible(false);
        SignupRequest signupRequest = new SignupRequest(
                UsernameField.getText(),
                PasswordField.getText(),
                FirstNameField.getText(),
                LastNameField.getText(),
                BirthDateField.getText(),
                getGender(),
                hasPhoto
        );
        Connection.sendMessage(signupRequest);
        if (hasPhoto) {
            Connection.sendImage(photo, photoFormat);
        }
        SignupResponse signupResponse = (SignupResponse) Connection.receiveMessage();
        if (checkSignup(signupResponse)) {
            new Alert(Alert.AlertType.CONFIRMATION, "You are registered successfully!").showAndWait();
            try {
                new PageLoader().load("Login");
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    private boolean checkSignup(SignupResponse sr) {
        if (sr.getResponses().size() == 1 && sr.getResponses().get(0).equals("success")) {
            return true;
        }
        for (String s : sr.getResponses()) {
            switch (s) {
                case "unavailable_username" -> UsernameUnavailableLabel.setVisible(true);
                case "wrong_password_format" -> WrongPasswordLabel.setVisible(true);
                case "wrong_date_format" -> WrongDateFormatLabel.setVisible(true);
            }
        }
        return false;
    }

    private Gender getGender() {
        if (genderMenuButton.getText().equals("Male"))
            return Gender.MALE;
        if (genderMenuButton.getText().equals("Female"))
            return Gender.FEMALE;
        return Gender.OTHER;
    }
}
