package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MyInfoController {

    public void CancelButtonPushed(ActionEvent event)
    {
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.close();
    }

    public void ModifyButtonPushed(ActionEvent event)
    {
        /**
         * Ajouter la partie envoi de données à la bd
         */
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.close();
    }

}
