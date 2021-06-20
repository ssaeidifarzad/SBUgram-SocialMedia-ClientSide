package Model;

import Model.Messages.ClientMessages.ExitMessage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        PageLoader.initStage(primaryStage);
        new PageLoader().load("Login");
        Connection.init();
        Files.createDirectory(Paths.get("src/Model/Temp"));
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        Connection.sendMessage(new ExitMessage());
        Connection.disconnect();
        try {
            try (Stream<Path> walk = Files.walk(Paths.get("src/Model/Temp"))) {
                walk.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
