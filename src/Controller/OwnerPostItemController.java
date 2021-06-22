package Controller;


import Model.DataTypes.Post.Posts;
import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OwnerPostItemController {
    public AnchorPane root;
    public Label title;
    public TextField description;
    public Label likeCount;
    public Label repostCount;
    Posts post;

    public OwnerPostItemController(Posts post) throws IOException {
        new PageLoader().load("OwnerPostItem", this);
        this.post = post;
    }

    public AnchorPane init() {
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        likeCount.setText(String.valueOf(post.getLikes()));
        repostCount.setText(String.valueOf(post.getReposts()));
        return root;
    }

    public void viewComments(ActionEvent actionEvent) {
    }
}
