package Controller;

import Model.DataTypes.Post.Posts;
import Model.PageLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class TimeLineController {

    public ListView<Posts> postList;

    ArrayList<Posts> posts = new ArrayList<>();

    @FXML
    public void initialize() {
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
