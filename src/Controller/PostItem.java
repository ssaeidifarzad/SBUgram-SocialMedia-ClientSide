package Controller;


import Model.DataTypes.Post.Post;
import Model.DataTypes.Post.Posts;
import Model.DataTypes.Post.RepostedPosts;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class PostItem extends ListCell<Posts> {


    @Override
    public void updateItem(Posts post, boolean empty) {
        super.updateItem(post, empty);
        if (post != null) {
            if (post instanceof Post) {
                try {
                    setGraphic(new OrdinaryPostItemController(post).init());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (post instanceof RepostedPosts) {
                try {
                    setGraphic(new RepostedPostItemController(post).init());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
