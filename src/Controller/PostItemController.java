package Controller;

import Model.DataTypes.Post.Post;
import Model.DataTypes.Post.Posts;
import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class PostItemController {

    public Label username;
    public Label title;
    public ImageView profileImage;
    public TextField description;
    public Button like;
    public Button viewComments;
    public Button comment;
    public Button repost;
    public Label likeCount;
    public Label repostCount;
    public AnchorPane root;


    Posts post;

    public PostItemController(Posts post) throws IOException {
        new PageLoader().load("PostItem", this);
        this.post = post;
    }

    public AnchorPane init() {
        return root;
    }

    public void likePost(ActionEvent actionEvent) {
        likeCount.setText(likeCount.getText() + " 1");
    }
}
