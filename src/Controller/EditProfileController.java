package Controller;

import Model.PageLoader;
import javafx.event.ActionEvent;
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
        try {
            new PageLoader().load("OwnerProfile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
