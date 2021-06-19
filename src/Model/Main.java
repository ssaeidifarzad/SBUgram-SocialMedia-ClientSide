package Model;

import Model.Messages.ClientMessages.ExitMessage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        PageLoader.initStage(primaryStage);
        new PageLoader().load("Login");
        Connection.init();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        Connection.sendMessage(new ExitMessage());
        Connection.disconnect();
    }
}
