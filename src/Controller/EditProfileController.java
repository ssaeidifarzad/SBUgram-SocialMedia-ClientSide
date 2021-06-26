package Controller;

import Model.Connection;
import Model.ImageHandler;
import Model.Messages.ClientMessages.EditProfileRequest;
import Model.Messages.ServerMessages.EditProfileResponse;
import Model.PageLoader;
import Model.ThisUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EditProfileController {
    public TextField FirstNameField;
    public TextField LastNameField;
    public TextField BirthDateField;
    public javafx.scene.control.PasswordField PasswordField;
    public Button PhotoUploadButton;
    public Label WrongDateFormatLabel;
    public Button confirmEditButton;
    public Label WrongPasswordFormatLabel;
    public ImageView ProfilePhoto;
    public javafx.scene.control.PasswordField repeatPasswordField;
    public Label passwordMatchLabel;

    private boolean hasPhoto = false;
    private byte[] photo = new byte[0];

    @FXML
    public void initialize() {
        FirstNameField.setText(ThisUser.getThisUser().getFirstName());
        LastNameField.setText(ThisUser.getThisUser().getLastName());
        BirthDateField.setText(ThisUser.getThisUser().getBirthDate());
        if (Files.exists(Paths.get("src/Model/Temp/image.jpg"))) {
            ProfilePhoto.setImage(new Image(Paths.get("src/Model/Temp/image.jpg").toUri().toString()));
        }
    }

    public void uploadPhoto(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("jpg Files", "*.jpg")
        );
        File photo = fileChooser.showOpenDialog(new Stage());
        if (photo != null) {
            ProfilePhoto.setImage(new Image(photo.toURI().toString()));
            hasPhoto = true;
            this.photo = ImageHandler.writeImageToArray(photo);
        }
    }

    public void confirmEdit(ActionEvent actionEvent) {
        WrongPasswordFormatLabel.setVisible(false);
        WrongDateFormatLabel.setVisible(false);
        passwordMatchLabel.setVisible(false);
        if (!PasswordField.getText().equals(repeatPasswordField.getText())) {
            passwordMatchLabel.setVisible(true);
            return;
        }
        Connection.sendMessage(new EditProfileRequest(
                PasswordField.getText(),
                FirstNameField.getText(),
                LastNameField.getText(),
                BirthDateField.getText(),
                hasPhoto,
                photo));
        EditProfileResponse response = ((EditProfileResponse) Connection.receiveMessage());
        if (checkEdit(response)) {
            ThisUser.init(response.getUser());
            new Alert(Alert.AlertType.CONFIRMATION, "Your profile info updated successfully").showAndWait();
            try {
                new PageLoader().load("OwnerProfile");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkEdit(EditProfileResponse response) {
        if (response.getResponses().get(0).equals("success"))
            return true;
        for (String s : response.getResponses()) {
            switch (s) {
                case "wrong_password_format" -> WrongPasswordFormatLabel.setVisible(true);
                case "wrong_date_format" -> WrongDateFormatLabel.setVisible(true);
            }
        }
        return false;
    }

}
