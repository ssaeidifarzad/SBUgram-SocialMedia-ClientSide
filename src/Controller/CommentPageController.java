package Controller;

import Model.Connection;
import Model.DataTypes.Post.Comment;
import Model.DataTypes.Post.Posts;
import Model.DataTypes.Post.RepostedPosts;
import Model.Messages.ClientMessages.CommentRequest;
import Model.Messages.ClientMessages.UpdatedPostRequest;
import Model.Messages.ServerMessages.CommentResponse;
import Model.Messages.ServerMessages.UpdatedPostResponse;
import Model.PageLoader;
import Model.ThisUser;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Vector;

public class CommentPageController {
    public ListView<Comment> commentList;
    public Label commentCount;
    public TextField commentField;
    private final Posts post;
    private final String previousPage;

    public CommentPageController(Posts post, String previousPage) {
        Connection.sendMessage(new UpdatedPostRequest(post));
        this.post = ((UpdatedPostResponse) Connection.receiveMessage()).getPost();
        this.previousPage = previousPage;
    }

    public void initialize() {
        Vector<Comment> comments = post.getComments();
        commentCount.setText(String.valueOf(comments.size()));
        commentList.setItems(FXCollections.observableArrayList(comments));
        commentList.setCellFactory(c -> new CommentItem());
    }

    public void sendComment(ActionEvent actionEvent) {
        Connection.sendMessage(new CommentRequest(
                post,
                new Comment(
                        commentField.getText(),
                        ThisUser.getThisUser().getUsername(),
                        LocalDateTime.now().toString()
                )
        ));
        ThisUser.init(((CommentResponse) Connection.receiveMessage()).getUser());
        try {
            new PageLoader().loadPage("Comments", new CommentPageController(post, previousPage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backToPreviousPage(MouseEvent mouseEvent) {
        if (previousPage.equals("ownerProfile")) {
            try {
                new PageLoader().load("OwnerProfile");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (previousPage.equals("otherProfile")) {
            if (post instanceof RepostedPosts) {
                try {
                    new PageLoader().loadPage("OtherProfile", new OtherProfileController(((RepostedPosts) post).getRepostUsername()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    new PageLoader().loadPage("OtherProfile", new OtherProfileController(post.getOwner().getUsername()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                new PageLoader().load("TimeLine");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
