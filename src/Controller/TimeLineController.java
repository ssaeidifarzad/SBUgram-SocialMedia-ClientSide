package Controller;

import Model.Connection;
import Model.DataTypes.Post.Posts;
import Model.Messages.ClientMessages.TimelinePostsRequest;
import Model.Messages.ServerMessages.TimelinePostsResponse;
import Model.PageLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Vector;

public class TimeLineController {

    public ListView<Posts> postList;

    @FXML
    public void initialize() {
        Connection.sendMessage(new TimelinePostsRequest());
        Vector<Posts> posts = ((TimelinePostsResponse) Connection.receiveMessage()).getPosts();
        postList.setItems(FXCollections.observableArrayList(posts));
        postList.setCellFactory(p -> new PostItem());
    }

    public void loadMenuPage(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
