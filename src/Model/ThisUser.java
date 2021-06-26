package Model;

import Model.DataTypes.User.User;
import Model.Messages.ClientMessages.OwnerImageRequest;
import Model.Messages.ImageMessage;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ThisUser {
    private static User thisUser;

    private ThisUser() {

    }

    public static void init(User user) {
        ThisUser.thisUser = user;
        if (thisUser.hasPhoto()) {
            if (!Files.exists(Paths.get("src/Model/Temp/image.jpg"))) {
                Connection.sendMessage(new OwnerImageRequest());
                ImageMessage image = Connection.receiveImage();
                if (image != null) {
                    ImageHandler.readImage(image.getData(), "src/Model/Temp/image.jpg");
                }
            }
        }
    }

    public static User getThisUser() {
        return thisUser;
    }

}
