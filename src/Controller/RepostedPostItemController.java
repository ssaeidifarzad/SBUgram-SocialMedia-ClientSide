package Controller;

import Model.Connection;
import Model.DataTypes.Post.Post;
import Model.DataTypes.Post.Posts;
import Model.DataTypes.Post.RepostedPosts;
import Model.DataTypes.User.SafeUserData;
import Model.Messages.ClientMessages.*;
import Model.Messages.ImageMessage;
import Model.Messages.ServerMessages.LikeResponse;
import Model.Messages.ServerMessages.RepostResponse;
import Model.Messages.ServerMessages.UpdatedPostResponse;
import Model.Messages.ServerMessages.UpdatedSafeUserResponse;
import Model.PageLoader;
import Model.ThisUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RepostedPostItemController {
    public Label username;
    public Label title;
    public ImageView profileImage;
    public Text description;
    public Label likeCount;
    public Label repostCount;
    public Label repostedBy;
    public Button repostButton;
    public Button likeButton;
    public Label dateAndTime;
    Posts post;
    public AnchorPane root;
    private String loadingPage;

    public RepostedPostItemController(Posts post, String loadingPage) throws IOException {
        this.loadingPage = loadingPage;
        new PageLoader().load("RepostedPost", this);
        this.post = post;
    }

    public AnchorPane init() {
        Connection.sendMessage(new UpdatedPostRequest(post));
        post = ((UpdatedPostResponse) Connection.receiveMessage()).getPost();
        Connection.sendMessage(new UpdatedSafeUserRequest(post.getOwner().getUsername()));
        SafeUserData user = ((UpdatedSafeUserResponse) Connection.receiveMessage()).getSafeUser();
        if (user.hasPhoto()) {
            Path path = Paths.get("src/Model/Temp/" + user.getUsername() + "_profilePhoto." + user.getPhotoFormat());
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
        repostedBy.setText(((RepostedPosts) post).getRepostUsername());
        return root;
    }

    public void like(ActionEvent actionEvent) {
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
            new PageLoader().loadPage("Comments", new CommentPageController(((RepostedPosts) post).getPost(), loadingPage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProfileImage(Path path) {
        ImageMessage image = Connection.receiveImage();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.writeBytes(image.getData());
        try (FileOutputStream fileOutputStream = new FileOutputStream(path.toString())) {
            byteArrayOutputStream.writeTo(fileOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profileImage.setImage(new Image(path.toUri().toString()));
    }

    public void loadPostOwnerProfile(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            new PageLoader().loadPage("OtherProfile", new OtherProfileController(post.getOwner().getUsername()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
