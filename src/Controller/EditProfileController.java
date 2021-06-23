package Controller;

import Model.Connection;
import Model.DataTypes.User.Gender;
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

public class EditProfileController {
    public TextField FirstNameField;
    public TextField LastNameField;
    public TextField BirthDateField;
    public javafx.scene.control.PasswordField PasswordField;
    public Button PhotoUploadButton;
    public Label WrongDateFormatLabel;
    public MenuButton genderMenuButton;
    public Button confirmEditButton;
    public Label WrongPasswordFormatLabel;
    public ImageView ProfilePhoto;

    private boolean hasPhoto = false;
    private String photoFormat;
    private File photo;

    @FXML
    public void initialize() {
        FirstNameField.setText(ThisUser.getThisUser().getFirstName());
        LastNameField.setText(ThisUser.getThisUser().getLastName());
        BirthDateField.setText(ThisUser.getThisUser().getBirthDate());
        genderMenuButton.setText(ThisUser.getThisUser().getGender().toString());
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

    public void confirmEdit(ActionEvent actionEvent) {
        WrongPasswordFormatLabel.setVisible(false);
        WrongDateFormatLabel.setVisible(false);
        Connection.sendMessage(new EditProfileRequest(
                PasswordField.getText(),
                FirstNameField.getText(),
                LastNameField.getText(),
                BirthDateField.getText(),
                getGender(),
                hasPhoto
        ));
        EditProfileResponse response = ((EditProfileResponse) Connection.receiveMessage());
        if (checkEdit(response)) {
            if (hasPhoto) {
                Connection.sendImage(photo, photoFormat);
            }
            ThisUser.init(response.getUser());
            if (hasPhoto)
                ThisUser.getThisUser().setHasPhoto(true);
            new Alert(Alert.AlertType.CONFIRMATION, "Your profile info updated successfully").showAndWait();
            try {
                new PageLoader().load("OwnerProfile");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkEdit(EditProfileResponse response) {
        if (response.getResponses().size() == 1 && response.getResponses().get(0).equals("success")) {
            return true;
        }
        for (String s : response.getResponses()) {
            switch (s) {
                case "wrong_password_format" -> WrongPasswordFormatLabel.setVisible(true);
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
