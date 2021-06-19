package Controller;

import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class PublishController {
    public TextField postTitle;
    public TextField postDescription;

    public void loadMenuPage(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void publishPost(ActionEvent actionEvent) {
    }
}
