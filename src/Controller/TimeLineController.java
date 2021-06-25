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
import java.util.List;
import java.util.stream.Collectors;

public class TimeLineController {

    public ListView<Posts> postList;

    @FXML
    public void initialize() {
        Connection.sendMessage(new TimelinePostsRequest());
        List<Posts> posts = ((TimelinePostsResponse) Connection.receiveMessage()).getPosts()
                .stream().sorted((a, b) -> ((int) (b.getPublishTime() - a.getPublishTime())))
                .collect(Collectors.toList());
        postList.setItems(FXCollections.observableArrayList(posts));
        postList.setCellFactory(p -> new PostItem("timeline"));
    }

    public void loadMenuPage(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refresh(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("TimeLine");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
