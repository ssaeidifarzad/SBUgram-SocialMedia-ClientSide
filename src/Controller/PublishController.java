package Controller;

import Model.Connection;
import Model.DataTypes.Post.Post;
import Model.Messages.ClientMessages.PublishRequest;
import Model.Messages.ServerMessages.PublishResponse;
import Model.PageLoader;
import Model.ThisUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

public class PublishController {
    public TextField postTitle;
    public TextArea postDescription;
    public Label publishedLabel;

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
                new Date().getTime()
        )));
        PublishResponse publishResponse = ((PublishResponse) Connection.receiveMessage());
        if (checkPublish(publishResponse)) {
            ThisUser.init(publishResponse.getUser());
            publishedLabel.setVisible(true);
            postDescription.setText("");
            postTitle.setText("");
        }
    }

    private boolean checkPublish(PublishResponse publishResponse) {
        return publishResponse.getResponses().size() == 1 && publishResponse.getResponses().get(0).equals("success");
    }
}
