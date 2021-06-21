package Controller;

import Model.Connection;
import Model.Messages.ClientMessages.LogoutRequest;
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
        Connection.sendMessage(new LogoutRequest());
        try {
            new PageLoader().load("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSearchPage(ActionEvent actionEvent) {
        try {
            new PageLoader().load("Search");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
