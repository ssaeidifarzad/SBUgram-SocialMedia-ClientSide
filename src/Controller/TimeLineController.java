package Controller;

import Model.DataTypes.Post.Post;
import Model.PageLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class TimeLineController {


    public ListView<Post> postList;

    ArrayList<Post> posts = new ArrayList<>();

    @FXML
    public void initialize() {
        //initialize posts array list to be shown in list view
        for (int i = 1; i <= 5; i++) {
            Post p = new Post();
            posts.add(p);
        }

        //show the post array in list view
        postList.setItems(FXCollections.observableArrayList(posts));

        //customize each cell of postList with new graphic object PostItem
        postList.setCellFactory(postList -> new PostItem());
    }

    public void loadMenuPage(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
