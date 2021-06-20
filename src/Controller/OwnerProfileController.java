package Controller;

import Model.Connection;
import Model.DataTypes.User.User;
import Model.Messages.ClientMessages.ImageRequest;
import Model.Messages.ImageMessage;
import Model.PageLoader;
import Model.ThisUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OwnerProfileController {
    public ImageView profilePhoto;
    public Text ownerUsername;
    public Text ownerName;
    public Text ownerBirthDate;
    public Button editProfileButton;

    @FXML
    public void initialize() {
        if (ThisUser.getThisUser().hasPhoto()) {
            if (!Files.exists(Paths.get("src/Model/Temp/image." + ThisUser.getThisUser().getPhotoFormat()))) {
                Connection.sendMessage(new ImageRequest());
                ImageMessage image = Connection.receiveImage();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.writeBytes(image.getData());
                try (FileOutputStream fileOutputStream = new FileOutputStream("src/Model/Temp/image." + image.getFormat())) {
                    byteArrayOutputStream.writeTo(fileOutputStream);
                    byteArrayOutputStream.close();
                    ThisUser.getThisUser().setHasPhoto(true);
                    ThisUser.getThisUser().setPhotoFormat(image.getFormat());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            profilePhoto.setImage(new Image(
                    new File("src/Model/Temp/image." + ThisUser.getThisUser().getPhotoFormat())
                            .toURI().toString()));
        }
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
