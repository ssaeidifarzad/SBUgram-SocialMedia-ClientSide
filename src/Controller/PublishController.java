package Controller;

import Model.Connection;
import Model.DataTypes.Post.Post;
import Model.ImageHandler;
import Model.Messages.ClientMessages.PublishRequest;
import Model.Messages.ServerMessages.PublishResponse;
import Model.PageLoader;
import Model.ThisUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

public class PublishController {
    public TextField postTitle;
    public TextArea postDescription;
    public Label publishedLabel;
    public ImageView postImage;
    private boolean hasPhoto = false;
    private byte[] imageData = new byte[0];

    public void initialize() {
        publishedLabel.setVisible(false);
        postDescription.setText("");
        postTitle.setText("");
    }

    public void loadMenuPage(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void publishPost(ActionEvent actionEvent) {
        publishedLabel.setVisible(false);
        Connection.sendMessage(new PublishRequest(new Post(
                postTitle.getText(),
                postDescription.getText(),
                LocalDateTime.now().toString(),
                new Date().getTime(),
                hasPhoto,
                imageData
        )));
        PublishResponse publishResponse = ((PublishResponse) Connection.receiveMessage());
        if (checkPublish(publishResponse)) {
            ThisUser.init(publishResponse.getUser());
            publishedLabel.setVisible(true);
            postDescription.setText("");
            postTitle.setText("");
            postImage.setImage(null);
            hasPhoto = false;
            imageData = new byte[0];
        }
    }

    private boolean checkPublish(PublishResponse publishResponse) {
        return publishResponse.getResponses().size() == 1 && publishResponse.getResponses().get(0).equals("success");
    }

    public void uploadImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpg files", "*.jpg"));
        File image = fileChooser.showOpenDialog(new Stage());
        if (image != null) {
            postImage.setImage(new Image(image.toURI().toString()));
            hasPhoto = true;
            imageData = ImageHandler.writeImageToArray(image);
        }
    }
}
