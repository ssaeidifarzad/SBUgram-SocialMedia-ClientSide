package Controller;

import Model.Connection;
import Model.DataTypes.Post.Posts;
import Model.DataTypes.Post.RepostedPosts;
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

public class RepostedPostItemController {
    public Label username;
    public Label title;
    public ImageView profileImage;
    public TextField description;
    public Label likeCount;
    public Label repostCount;
    public Label repostedBy;
    public Button repostButton;
    public Button likeButton;
    Posts post;
    public AnchorPane root;
    private boolean like = false;
    private boolean repost = false;

    public RepostedPostItemController(Posts post) throws IOException {
        new PageLoader().load("RepostedPost", this);
        this.post = post;
    }

    public AnchorPane init() {
        if (ThisUser.getThisUser().getPosts().contains(post)) {
            repostButton.setVisible(false);
        }
        if (post.getOwner().equals(ThisUser.getThisUser())) {
            likeButton.setVisible(false);
            repostButton.setVisible(false);
        }
        username.setText(post.getOwner().getUsername());
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        likeCount.setText(String.valueOf(post.getLikes()));
        repostCount.setText(String.valueOf(post.getReposts()));
        repostedBy.setText(((RepostedPosts) post).getRepostUsername());
        return root;
    }

    public void like(ActionEvent actionEvent) {
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
