package Controller;

import Model.Connection;
import Model.DataTypes.User.SafeUser;
import Model.ImageHandler;
import Model.Messages.ClientMessages.SearchRequest;
import Model.Messages.ImageMessage;
import Model.Messages.ServerMessages.SearchResponse;
import Model.PageLoader;
import Model.ThisUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Paths;

public class SearchController {
    public TextField searchedUsername;
    public ImageView otherUserProfilePhoto;
    public Text otherUserUsername;
    public Text otherUserName;
    public Text otherUserBirthDate;
    public Label userFound;
    public Label noSuchAUser;
    public Pane pane;
    private SafeUser user;

    public void initialize() {

    }

    public void loadMenu(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search(ActionEvent actionEvent) {
        if (searchedUsername.getText().equals(ThisUser.getThisUser().getUsername()))
            return;
        userFound.setVisible(false);
        noSuchAUser.setVisible(false);
        pane.setVisible(false);
        Connection.sendMessage(new SearchRequest(searchedUsername.getText()));
        SearchResponse searchResponse = ((SearchResponse) Connection.receiveMessage());
        if (checkSearch(searchResponse)) {
            userFound.setVisible(true);
            otherUserUsername.setText(searchResponse.getSafeUserData().getUsername());
            otherUserName.setText(searchResponse.getSafeUserData().getFirstName() + " " + searchResponse.getSafeUserData().getLastName());
            otherUserBirthDate.setText(searchResponse.getSafeUserData().getBirthDate());
            this.user = searchResponse.getSafeUserData();
            if (searchResponse.getSafeUserData().hasPhoto()) {
                setImage(searchResponse.getSafeUserData().getUsername());
            }
            pane.setVisible(true);
        } else {
            noSuchAUser.setVisible(true);
        }
    }

    public void loadOtherUserProfile(MouseEvent mouseEvent) {
        try {
            new PageLoader().loadPage("OtherProfile", new OtherProfileController(user.getUsername()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkSearch(SearchResponse searchResponse) {
        return searchResponse.getResponses().get(0).equals("success");
    }

    private void setImage(String username) {
        ImageMessage image = Connection.receiveImage();
        String path = "src/Model/Temp/" + username + "_profilePhoto.jpg";
        if (image != null) {
            ImageHandler.readImage(image.getData(), path);
            otherUserProfilePhoto.setImage(new Image(Paths.get(path).toUri().toString()));
        }
    }

}















