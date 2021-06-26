package Controller;


import Model.Connection;
import Model.DataTypes.Post.Posts;
import Model.Messages.ClientMessages.UpdatedPostRequest;
import Model.Messages.ServerMessages.UpdatedPostResponse;
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
    private final String loadingPage;
    public OwnerPostItemController(Posts post,String loadingPage) throws IOException {
        new PageLoader().load("OwnerPostItem", this);
        this.post = post;
        this.loadingPage = loadingPage;
    }

    public AnchorPane init() {
        Connection.sendMessage(new UpdatedPostRequest(post));
        post = ((UpdatedPostResponse) Connection.receiveMessage()).getPost();
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        dateAndTime.setText(post.getDateAndTime());
        likeCount.setText(String.valueOf(post.getLikes()));
        repostCount.setText(String.valueOf(post.getReposts()));
        return root;
    }

    public void showComments(ActionEvent actionEvent) {
        try {
            new PageLoader().loadPage("Comments", new CommentPageController(post, loadingPage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
