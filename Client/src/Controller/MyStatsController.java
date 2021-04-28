package Controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class MyStatsController {

    public void returnButtonPushed(ActionEvent event) {
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.close();
    }
}
