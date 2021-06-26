package Controller;

import Model.Connection;
import Model.DataTypes.Post.Comment;
import Model.DataTypes.User.SafeUser;
import Model.ImageHandler;
import Model.Messages.ClientMessages.ProfileImageRequest;
import Model.Messages.ClientMessages.UpdatedSafeUserRequest;
import Model.Messages.ImageMessage;
import Model.Messages.ServerMessages.UpdatedSafeUserResponse;
import Model.PageLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommentItemController {
    public ImageView profilePhoto;
    public TextField commentText;
    public AnchorPane root;
    public Label username;
    public Label date;

    private final Comment comment;

    public CommentItemController(Comment comment) throws IOException {
        new PageLoader().load("CommentItem", this);
        this.comment = comment;
    }

    public AnchorPane init() {
        Connection.sendMessage(new UpdatedSafeUserRequest(comment.getOwnerUsername()));
        SafeUser user = ((UpdatedSafeUserResponse) Connection.receiveMessage()).getSafeUser();
        if (user.hasPhoto()) {
            Path path = Paths.get("src/Model/Temp/" + user.getUsername() + "_profilePhoto.jpg");
            if (Files.exists(path)) {
                profilePhoto.setImage(new Image(path.toUri().toString()));
            } else {
                Connection.sendMessage(new ProfileImageRequest(user.getUsername()));
                setProfileImage(path);
            }
        }
        username.setText(user.getUsername());
        date.setText(comment.getPublishTime());
        commentText.setText(comment.getText());
        return root;
    }

    private void setProfileImage(Path path) {
        ImageMessage image = Connection.receiveImage();
        if (image != null) {
            ImageHandler.readImage(image.getData(), path.toString());
            profilePhoto.setImage(new Image(path.toUri().toString()));
        }
    }
}
