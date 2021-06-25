package Controller;


import Model.DataTypes.Post.Posts;
import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class OwnerPostItemController {
    public AnchorPane root;
    public Label title;
    public Text description;
    public Label likeCount;
    public Label repostCount;
    public Label dateAndTime;
    Posts post;

    public OwnerPostItemController(Posts post) throws IOException {
        new PageLoader().load("OwnerPostItem", this);
        this.post = post;
    }

    public AnchorPane init() {
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        dateAndTime.setText(post.getDateAndTime());
        likeCount.setText(String.valueOf(post.getLikes()));
        repostCount.setText(String.valueOf(post.getReposts()));
        return root;
    }

    public void showComments(ActionEvent actionEvent) {
        try {
            new PageLoader().loadPage("Comments", new CommentPageController(post, "ownerProfile"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
