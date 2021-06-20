package Model;


import Model.Messages.ClientMessages.ClientMessage;
import Model.Messages.ImageMessage;
import Model.Messages.ServerMessages.ServerMessage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class Connection {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 1111;

    public static Socket getSocket() {
        return socket;
    }

    private static Socket socket;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;

    public static void init() {
        try {
            socket = new Socket(HOST, PORT);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(ClientMessage message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerMessage receiveMessage() {
        try {
            return ((ServerMessage) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ServerMessage() {
        };
    }

    public static void sendImage(File photo, String format) {
        try {
            BufferedImage image = ImageIO.read(photo);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, format, byteArrayOutputStream);
            objectOutputStream.writeObject(new ImageMessage(byteArrayOutputStream.toByteArray(), format));
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageMessage receiveImage() {
        try {
            return ((ImageMessage) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void disconnect() {
        try {
            socket.close();
            objectOutputStream.close();
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
