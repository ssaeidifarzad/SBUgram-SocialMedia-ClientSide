package Controller;

import Model.Connection;
import Model.DataTypes.Post.Posts;
import Model.DataTypes.User.SafeUser;
import Model.ImageHandler;
import Model.Messages.ClientMessages.*;
import Model.Messages.ImageMessage;
import Model.Messages.ServerMessages.LikeResponse;
import Model.Messages.ServerMessages.RepostResponse;
import Model.Messages.ServerMessages.UpdatedPostResponse;
import Model.Messages.ServerMessages.UpdatedSafeUserResponse;
import Model.PageLoader;
import Model.ThisUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OrdinaryPostWithImageItemController {
    public Label username;
    public Label title;
    public ImageView profileImage;
    public Text description;
    public Label likeCount;
    public Label repostCount;
    public AnchorPane root;
    public Button repostButton;
    public Button likeButton;
    public Label dateAndTime;
    private final String loadingPage;
    public ImageView postImage;
    Posts post;

    public OrdinaryPostWithImageItemController(Posts post, String loadingPage) throws IOException {
        this.loadingPage = loadingPage;
        new PageLoader().load("OrdinaryPostWithImageItem", this);
        this.post = post;
    }

    public AnchorPane init() {
        Connection.sendMessage(new UpdatedPostRequest(post));
        post = ((UpdatedPostResponse) Connection.receiveMessage()).getPost();
        Connection.sendMessage(new UpdatedSafeUserRequest(post.getOwner().getUsername()));
        SafeUser user = ((UpdatedSafeUserResponse) Connection.receiveMessage()).getSafeUser();
        Path imgPath = Paths.get("src/Model/Temp/" + user.getUsername() + "_" + post.getTitle() + post.getPublishTime() + ".jpg");
        if (Files.exists(imgPath)) {
            postImage.setImage(new Image(imgPath.toUri().toString()));
        } else {
            setPostImage(post.getImageData(), imgPath);
        }
        if (user.hasPhoto()) {
            Path path = Paths.get("src/Model/Temp/" + user.getUsername() + "_profilePhoto.jpg");
            if (Files.exists(path)) {
                profileImage.setImage(new Image(path.toUri().toString()));
            } else {
                Connection.sendMessage(new ProfileImageRequest(user.getUsername()));
                setProfileImage(path);
            }
        }
        if (post.getOwner().getUsername().equals(ThisUser.getThisUser().getUsername())) {
            likeButton.setVisible(false);
            repostButton.setVisible(false);
        }
        username.setText(post.getOwner().getUsername());
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        dateAndTime.setText(post.getDateAndTime());
        likeCount.setText(String.valueOf(post.getLikes()));
        repostCount.setText(String.valueOf(post.getReposts()));
        return root;
    }

    public void likePost(ActionEvent actionEvent) {
        Connection.sendMessage(new LikeRequest(post));
        LikeResponse likeResponse = ((LikeResponse) Connection.receiveMessage());
        if (likeResponse.getResponses().get(0).equals("success")) {
            likeCount.setText(String.valueOf(post.getLikes() + 1));
        }
        ThisUser.init(likeResponse.getUser());
    }

    public void repost(ActionEvent actionEvent) {
        Connection.sendMessage(new RepostRequest(post));
        RepostResponse repostResponse = ((RepostResponse) Connection.receiveMessage());
        if (repostResponse.getResponses().get(0).equals("success")) {
            repostCount.setText(String.valueOf(post.getReposts() + 1));
        }
        ThisUser.init(repostResponse.getUser());
    }

    public void showComments(ActionEvent actionEvent) {
        try {
            new PageLoader().loadPage("Comments", new CommentPageController(post, loadingPage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProfileImage(Path path) {
        ImageMessage image = Connection.receiveImage();
        if (image != null) {
            ImageHandler.readImage(image.getData(), path.toString());
            profileImage.setImage(new Image(path.toUri().toString()));
        }
    }


    @FXML
    private void loadPostOwnerProfile(MouseEvent mouseEvent) {
        try {
            new PageLoader().loadPage("OtherProfile", new OtherProfileController(post.getOwner().getUsername()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPostImage(byte[] imageData, Path path) {
        ImageHandler.readImage(imageData, path.toString());
        postImage.setImage(new Image(path.toUri().toString()));
    }
}
