package Controller;

import Model.DataTypes.Post.Posts;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class OwnerPostItem extends ListCell<Posts> {

    @Override
    public void updateItem(Posts post, boolean empty) {
        super.updateItem(post, empty);
        if (post != null) {
            try {
                setGraphic(new OwnerPostItemController(post).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
