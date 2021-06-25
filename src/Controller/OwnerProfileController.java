package Controller;

import Model.Connection;
import Model.DataTypes.Post.Posts;
import Model.Messages.ClientMessages.UpdatedUserRequest;
import Model.Messages.ClientMessages.gettingOwnerData;
import Model.Messages.ServerMessages.UpdatedUserResponse;
import Model.PageLoader;
import Model.ThisUser;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class OwnerProfileController {
    public ImageView profilePhoto;
    public Text ownerUsername;
    public Text ownerName;
    public Text ownerBirthDate;
    public Button editProfileButton;
    public Label followersCount;
    public Label followingsCount;
    public ListView<Posts> ownerPostList;

    @FXML
    public void initialize() {
        Connection.sendMessage(new gettingOwnerData());
        Connection.sendMessage(new UpdatedUserRequest());
        ThisUser.init(((UpdatedUserResponse) Connection.receiveMessage()).getUser());
        if (ThisUser.getThisUser().hasPhoto()) {
            profilePhoto.setImage(new Image(
                    Paths.get("src/Model/Temp/image." + ThisUser.getThisUser().getPhotoFormat()).toUri().toString()));
        }
        ownerUsername.setText(ThisUser.getThisUser().getUsername());
        ownerName.setText(ThisUser.getThisUser().getFirstName() + " " + ThisUser.getThisUser().getLastName());
        ownerBirthDate.setText(ThisUser.getThisUser().getBirthDate());
        followersCount.setText(String.valueOf(ThisUser.getThisUser().getFollowers().size()));
        followingsCount.setText(String.valueOf(ThisUser.getThisUser().getFollowings().size()));
        Vector<Posts> posts = new Vector<>(ThisUser.getThisUser().getPosts());
        Collections.reverse(posts);
        ownerPostList.setItems(FXCollections.observableArrayList(posts));
        ownerPostList.setCellFactory(p -> new OwnerPostItem());
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
