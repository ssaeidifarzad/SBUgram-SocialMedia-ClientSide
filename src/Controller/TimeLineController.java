package Controller;

import Model.PageLoader;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class TimeLineController {



    public void loadMenuPage(MouseEvent mouseEvent) {
        try {
            new PageLoader().load("Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
