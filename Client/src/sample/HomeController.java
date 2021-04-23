package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML private Button addActivity;

    public void addActivityButtonPushed(ActionEvent event) throws IOException
    {
        Parent activityParent = FXMLLoader.load(getClass().getResource("addActivity.fxml"));
        Scene activityScene = new Scene(activityParent);

        Stage window = new Stage();

        window.setScene(activityScene);
        window.show();
    }
}
