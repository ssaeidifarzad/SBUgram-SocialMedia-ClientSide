package Controller;

import Model.Connection;
import Model.DataTypes.User.Gender;
import Model.Messages.ClientMessages.EditProfileRequst;
import Model.Messages.ServerMessages.EditProfileResponse;
import Model.Messages.ServerMessages.SignupResponse;
import Model.PageLoader;
import Model.ThisUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    private boolean hasPhoto = false;

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
    }

    public void confirmEdit(ActionEvent actionEvent) {
        WrongPasswordFormatLabel.setVisible(false);
        WrongDateFormatLabel.setVisible(false);
        Connection.sendMessage(new EditProfileRequst(
                PasswordField.getText(),
                FirstNameField.getText(),
                LastNameField.getText(),
                BirthDateField.getText(),
                getGender(),
                hasPhoto
        ));
        EditProfileResponse response = ((EditProfileResponse) Connection.receiveMessage());
        if (chekEdit(response)) {
            ThisUser.init(response.getUser());
            new Alert(Alert.AlertType.CONFIRMATION, "Your profile info updated successfully").showAndWait();
            try {
                new PageLoader().load("OwnerProfile");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean chekEdit(EditProfileResponse response) {
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
