package Controller;

import Model.PageLoader;
import Model.Post;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Paths;

public class PostItemController {
    public TextField postDescription;
    public ImageView userProfilePhoto;
    public Text usernameText;
    public Text postTitle;
    public Text postDataAndTime;
    public Button likeButton;
    public Button repostButton;
    public Button addCommentButton;
    public Button viewCommentsButton;
    public Text likeCountText;
    public Text repostCountText;
    public AnchorPane root;
    Post post;

    public PostItemController(Post post) throws IOException {
        new PageLoader().load("postItem",this);
        this.post = post;
    }

    public AnchorPane init() {
        postTitle.setText(post.getTitle());
        postDescription.setText(post.getDescription());
        return root;
    }

    public void loadUserProfile(MouseEvent mouseEvent) {

    }

    public void loadTimelinePage(MouseEvent mouseEvent) {
    }

    public void likePost(ActionEvent actionEvent) {
    }

    public void repost(ActionEvent actionEvent) {
    }

    public void loadCommentAddPage(ActionEvent actionEvent) {
    }

    public void viewComments(ActionEvent actionEvent) {
    }
}
