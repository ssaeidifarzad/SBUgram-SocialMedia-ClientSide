package Controller;

import Model.Connection;
import Model.DataTypes.Post.Posts;
import Model.Messages.ClientMessages.LikeRequest;
import Model.Messages.ClientMessages.RepostRequest;
import Model.Messages.ServerMessages.LikeResponse;
import Model.Messages.ServerMessages.RepostResponse;
import Model.PageLoader;
import Model.ThisUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OrdinaryPostItemController {

    public Label username;
    public Label title;
    public ImageView profileImage;
    public TextField description;
    public Label likeCount;
    public Label repostCount;
    public AnchorPane root;
    public Button repostButton;
    public Button likeButton;
    private boolean like = false;
    private boolean repost = false;

    Posts post;

    public OrdinaryPostItemController(Posts post) throws IOException {
        new PageLoader().load("PostItem", this);
        this.post = post;
    }

    public AnchorPane init() {
        if (post.getOwner().getUsername().equals(ThisUser.getThisUser().getUsername())) {
            likeButton.setVisible(false);
            repostButton.setVisible(false);
        }
        username.setText(post.getOwner().getUsername());
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        likeCount.setText(String.valueOf(post.getLikes()));
        repostCount.setText(String.valueOf(post.getReposts()));
        return root;
    }

    public void likePost(ActionEvent actionEvent) {
        if (!like) {
            Connection.sendMessage(new LikeRequest(post));
            ThisUser.init(((LikeResponse) Connection.receiveMessage()).getUser());
            likeCount.setText(String.valueOf(post.getLikes() + 1));
            like = true;
        }
    }

    public void repost(ActionEvent actionEvent) {
        if (!repost) {
            Connection.sendMessage(new RepostRequest(post));
            ThisUser.init(((RepostResponse) Connection.receiveMessage()).getUser());
            repostCount.setText(String.valueOf(post.getReposts() + 1));
            repost = true;
        }
    }
}
