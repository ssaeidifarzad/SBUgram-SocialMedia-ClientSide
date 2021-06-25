package Controller;

import Model.Connection;
import Model.DataTypes.User.SecurityQuestions;
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
import java.util.Map;
import java.util.TreeMap;

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
    public javafx.scene.control.PasswordField repeatPasswordField;
    public Label passwordMatchLabel;
    public TextField SecurityQuestion1Answer;
    public TextField SecurityQuestion2Answer;
    public MenuButton Q1MenuButton;
    public MenuButton Q2MenuButton;
    public Label sameQuestionsError;

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
        passwordMatchLabel.setVisible(false);
        sameQuestionsError.setVisible(false);
        if (!PasswordField.getText().equals(repeatPasswordField.getText())) {
            passwordMatchLabel.setVisible(true);
            return;
        }
        if (Q1MenuButton.getText().equals(Q2MenuButton.getText())) {
            sameQuestionsError.setVisible(true);
            return;
        }
        Map<SecurityQuestions, String> securityQuestions = new TreeMap<>();
        securityQuestions.put(getButtonQuestion(Q1MenuButton.getText()), SecurityQuestion1Answer.getText());
        securityQuestions.put(getButtonQuestion(Q2MenuButton.getText()), SecurityQuestion2Answer.getText());
        Connection.sendMessage(new SignupRequest(
                UsernameField.getText(),
                PasswordField.getText(),
                FirstNameField.getText(),
                LastNameField.getText(),
                BirthDateField.getText(),
                hasPhoto,
                securityQuestions
        ));
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

    public void setQ1toQ1(ActionEvent actionEvent) {
        Q1MenuButton.setText(SecurityQuestions.ELEMENTARY_SCHOOL_NAME.getQuestion());
    }

    public void setQ2toQ1(ActionEvent actionEvent) {
        Q1MenuButton.setText(SecurityQuestions.CHILDHOOD_FAVORITE_GAME.getQuestion());
    }

    public void setQ3toQ1(ActionEvent actionEvent) {
        Q1MenuButton.setText(SecurityQuestions.FAVORITE_FOOD.getQuestion());
    }

    public void setQ4toQ1(ActionEvent actionEvent) {
        Q1MenuButton.setText(SecurityQuestions.FAVORITE_SINGER.getQuestion());
    }

    public void setQ1toQ2(ActionEvent actionEvent) {
        Q2MenuButton.setText(SecurityQuestions.ELEMENTARY_SCHOOL_NAME.getQuestion());
    }

    public void setQ2toQ2(ActionEvent actionEvent) {
        Q2MenuButton.setText(SecurityQuestions.CHILDHOOD_FAVORITE_GAME.getQuestion());
    }

    public void setQ3toQ2(ActionEvent actionEvent) {
        Q2MenuButton.setText(SecurityQuestions.FAVORITE_FOOD.getQuestion());
    }

    public void setQ4toQ2(ActionEvent actionEvent) {
        Q2MenuButton.setText(SecurityQuestions.FAVORITE_SINGER.getQuestion());
    }

    private SecurityQuestions getButtonQuestion(String s) {
        for (SecurityQuestions sq : SecurityQuestions.values()) {
            if (sq.getQuestion().equals(s))
                return sq;
        }
        return null;
    }
}
