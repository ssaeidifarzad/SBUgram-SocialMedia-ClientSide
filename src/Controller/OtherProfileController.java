package Controller;

import Model.Connection;
import Model.DataTypes.Post.Posts;
import Model.DataTypes.User.SafeUserData;
import Model.Messages.ClientMessages.FollowRequest;
import Model.Messages.ClientMessages.UnfollowRequest;
import Model.Messages.ClientMessages.UpdatedSafeUserRequest;
import Model.Messages.ClientMessages.UpdatedUserRequest;
import Model.Messages.ServerMessages.FollowResponse;
import Model.Messages.ServerMessages.UnfollowResponse;
import Model.Messages.ServerMessages.UpdatedSafeUserResponse;
import Model.Messages.ServerMessages.UpdatedUserResponse;
import Model.PageLoader;
import Model.ThisUser;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OtherProfileController {
    public Button followButton;
    public Button unfollowButton;
    public Text otherUserUsername;
    public Text otherUserName;
    public Text otherUserBirthDate;
    public ListView<Posts> postList;
    public Label followersCount;
    public Label followingsCount;
    private SafeUserData safeUser;
    public ImageView otherProfilePhoto;

    public OtherProfileController(String safeUser) {
        Connection.sendMessage(new UpdatedSafeUserRequest(safeUser));
        this.safeUser = ((UpdatedSafeUserResponse) Connection.receiveMessage()).getSafeUser();
    }

    public void initialize() {
        Connection.sendMessage(new UpdatedUserRequest());
        ThisUser.init(((UpdatedUserResponse) Connection.receiveMessage()).getUser());
        if (ThisUser.getThisUser().containsFollowing(safeUser.getUsername())) {
            followButton.setVisible(false);
            unfollowButton.setVisible(true);
        }
        otherUserUsername.setText(safeUser.getUsername());
        otherUserName.setText(safeUser.getFirstName() + " " + safeUser.getLastName());
        otherUserBirthDate.setText(safeUser.getBirthDate());
        followersCount.setText(String.valueOf(safeUser.getFollowersCount()));
        followingsCount.setText(String.valueOf(safeUser.getFollowingsCount()));
        if (safeUser.hasPhoto()) {
            if (Files.exists(Paths.get("src/Model/Temp/" + safeUser.getUsername() + "_profilePhoto." + safeUser.getPhotoFormat()))) {
                setImage(safeUser);
            } else {

            }
        }
        postList.setItems(FXCollections.observableArrayList(safeUser.getPosts()));
        postList.setCellFactory(p -> new PostItem());
    }

    public void follow(ActionEvent actionEvent) {
        Connection.sendMessage(new FollowRequest(safeUser));
        ThisUser.init(
                ((FollowResponse) Connection.receiveMessage()).getUser()
        );
        followButton.setVisible(false);
        unfollowButton.setVisible(true);
        followersCount.setText(String.valueOf(Integer.parseInt(followersCount.getText()) + 1));
    }

    public void unfollow(ActionEvent actionEvent) {
        Connection.sendMessage(new UnfollowRequest(safeUser));
        ThisUser.init(
                ((UnfollowResponse) Connection.receiveMessage()).getUser()
        );
        followButton.setVisible(true);
        unfollowButton.setVisible(false);
        followersCount.setText(String.valueOf(Integer.parseInt(followersCount.getText()) - 1));
    }

    private void setImage(SafeUserData user) {
        otherProfilePhoto.setImage(new Image(
                Paths.get("src/Model/Temp/" + user.getUsername() + "_profilePhoto." + user.getPhotoFormat()).
                        toUri().toString()));
    }


    public void loadMenu(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
