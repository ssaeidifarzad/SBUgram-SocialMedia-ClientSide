package Model;

import Model.DataTypes.User.User;
import Model.Messages.ClientMessages.ImageRequest;
import Model.Messages.ImageMessage;
import javafx.scene.image.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ThisUser {
    private static User thisUser;
    private static byte[] profilePhotoData;

    private ThisUser() {

    }

    public static void init(User user) {
        ThisUser.thisUser = user;
        System.out.println("init " + thisUser.hasPhoto());
        if (thisUser.hasPhoto()) {
            if (!Files.exists(Paths.get("src/Model/Temp/image." + ThisUser.getThisUser().getPhotoFormat()))) {
                Connection.sendMessage(new ImageRequest());
                ImageMessage image = Connection.receiveImage();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.writeBytes(image.getData());
                try (FileOutputStream fileOutputStream = new FileOutputStream("src/Model/Temp/image." + image.getFormat())) {
                    byteArrayOutputStream.writeTo(fileOutputStream);
                    byteArrayOutputStream.close();
                    ThisUser.getThisUser().setHasPhoto(true);
                    ThisUser.getThisUser().setPhotoFormat(image.getFormat());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static User getThisUser() {
        return thisUser;
    }

    public static byte[] getProfilePhotoData() {
        return profilePhotoData;
    }

    public static void setProfilePhotoData(byte[] profilePhotoData) {
        ThisUser.profilePhotoData = profilePhotoData;
    }
}
