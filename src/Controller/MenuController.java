package Controller;

import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MenuController {
    

    public void loadTimelinePage(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("TimeLine");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProfilePage(ActionEvent actionEvent) {
        try {
            new PageLoader().load("OwnerProfile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadNewPostPage(ActionEvent actionEvent) {
        try {
            new PageLoader().load("Publish");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent actionEvent) {

    }
}
