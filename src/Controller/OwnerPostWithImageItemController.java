package Controller;

import Model.DataTypes.Post.Posts;
import Model.ImageHandler;
import Model.PageLoader;
import Model.ThisUser;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OwnerPostWithImageItemController {
    public AnchorPane root;
    public Label title;
    public Text description;
    public Label likeCount;
    public Label repostCount;
    public Label dateAndTime;
    public ImageView postImage;
    Posts post;

    public OwnerPostWithImageItemController(Posts post) throws IOException {
        new PageLoader().load("OwnerPostWithImageItem", this);
        this.post = post;
    }

    public AnchorPane init() {
        Path imgPath = Paths.get("src/Model/Temp/" + ThisUser.getThisUser().getUsername()
                + "_" + post.getTitle() + post.getPublishTime() + ".jpg");
        if (Files.exists(imgPath)) {
            postImage.setImage(new Image(imgPath.toUri().toString()));
        } else {
            setPostImage(post.getImageData(), imgPath);
        }
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        dateAndTime.setText(post.getDateAndTime());
        likeCount.setText(String.valueOf(post.getLikes()));
        repostCount.setText(String.valueOf(post.getReposts()));
        return root;
    }

    public void showComments(ActionEvent actionEvent) {
        try {
            new PageLoader().loadPage("Comments", new CommentPageController(post, "ownerProfile"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPostImage(byte[] imageData, Path path) {
        ImageHandler.readImage(imageData, path.toString());
        postImage.setImage(new Image(path.toUri().toString()));
    }
}
