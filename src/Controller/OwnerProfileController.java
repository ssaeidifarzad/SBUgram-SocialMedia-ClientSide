package Controller;

import Model.DataTypes.User.User;
import Model.PageLoader;
import Model.ThisUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class OwnerProfileController {
    public ImageView profilePhoto;
    public Text ownerUsername;
    public Text ownerName;
    public Text ownerBirthDate;
    public Button editProfileButton;

    @FXML
    public void initialize() {
        ownerUsername.setText(ThisUser.getThisUser().getUsername());
        ownerName.setText(ThisUser.getThisUser().getFirstName() + " " + ThisUser.getThisUser().getLastName());
        ownerBirthDate.setText(ThisUser.getThisUser().getBirthDate());
    }

    public void loadEditProfilePage(ActionEvent actionEvent) {
        try {
            new PageLoader().load("EditProfile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMenu(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
