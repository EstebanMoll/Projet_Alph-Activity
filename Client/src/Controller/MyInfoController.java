package Controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

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
