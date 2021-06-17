package Model;

import Model.Messages.IncomingMessage;
import Model.Messages.OutgoingMessage;

import java.io.*;
import java.net.Socket;

public class Connection {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 1111;
    private static Socket socket = null;
    private static ObjectOutputStream objectOutputStream = null;
    private static ObjectInputStream objectInputStream = null;

    public static void init() {
        try {
            socket = new Socket(HOST, PORT);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(OutgoingMessage message) {
        try {
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void receiveMessage() {
        try {
            ((IncomingMessage) objectInputStream.readObject()).handle();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
