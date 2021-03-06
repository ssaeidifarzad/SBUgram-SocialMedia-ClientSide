package Controller;


import Model.DataTypes.Post.Post;
import Model.DataTypes.Post.Posts;
import Model.DataTypes.Post.RepostedPosts;
import Model.ThisUser;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class PostItem extends ListCell<Posts> {
    private final String loadingPage;

    public PostItem(String loadingPage) {
        this.loadingPage = loadingPage;
    }

    @Override
    public void updateItem(Posts post, boolean empty) {
        super.updateItem(post, empty);
        if (post != null) {
            if (!post.hasPhoto()) {
                if (!post.getOwner().getUsername().equals(ThisUser.getThisUser().getUsername())) {
                    if (post instanceof Post) {
                        try {
                            setGraphic(new OrdinaryPostItemController(post, loadingPage).init());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (post instanceof RepostedPosts) {
                        try {
                            setGraphic(new RepostedPostItemController(post, loadingPage).init());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        setGraphic(new OwnerPostItemController(post,loadingPage).init());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (!post.getOwner().getUsername().equals(ThisUser.getThisUser().getUsername())) {
                    if (post instanceof Post) {
                        try {
                            setGraphic(new OrdinaryPostWithImageItemController(post, loadingPage).init());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (post instanceof RepostedPosts) {
                        try {
                            setGraphic(new RepostedPostWithImageItemController(post, loadingPage).init());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        setGraphic(new OwnerPostWithImageItemController(post, loadingPage).init());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
