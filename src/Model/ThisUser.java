package Model;

import Model.DataTypes.User.User;
import Model.Messages.ClientMessages.OwnerImageRequest;
import Model.Messages.ImageMessage;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ThisUser {
    private static User thisUser;

    private ThisUser() {

    }

    public static void init(User user) {
        ThisUser.thisUser = user;
        if (thisUser.hasPhoto()) {
            if (!Files.exists(Paths.get("src/Model/Temp/image." + ThisUser.getThisUser().getPhotoFormat()))) {
                Connection.sendMessage(new OwnerImageRequest());
                ImageMessage image = Connection.receiveImage();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if (image != null) {
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
    }

    public static User getThisUser() {
        return thisUser;
    }

}
