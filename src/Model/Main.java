package Model;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Main extends Application {
    private static Socket socket = null;

    public static Socket getSocket() {
        return socket;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        PageLoader.initStage(primaryStage);
        new PageLoader().load("Login");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
