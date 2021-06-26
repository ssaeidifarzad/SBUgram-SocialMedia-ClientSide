package Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageHandler {
    public static byte[] writeImageToArray(File photo) {
        byte[] imageData = new byte[0];
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(photo);
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            imageData = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageData;
    }

    public static void readImage(byte[] imageData, String path) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byteArrayOutputStream.writeBytes(imageData);
            byteArrayOutputStream.writeTo(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
