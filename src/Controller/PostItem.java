package Controller;


import Model.DataTypes.Post.Post;
import Model.DataTypes.Post.Posts;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class PostItem extends ListCell<Posts> {


    @Override
    public void updateItem(Posts post, boolean empty) {
        super.updateItem(post, empty);
        if (post != null) {
            try {
                setGraphic(new PostItemController(post).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
