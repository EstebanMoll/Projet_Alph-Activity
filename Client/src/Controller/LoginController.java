package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public void registerHyperlinkPushed(ActionEvent event) throws IOException
    {
        Parent registerParent = FXMLLoader.load(getClass().getResource("../View/register.fxml"));
        Scene registerScene = new Scene(registerParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(registerScene);
        window.show();
    }

    public void loginButtonPushed(ActionEvent event) throws IOException
    {
        Parent homeParent = FXMLLoader.load(getClass().getResource("../View/home.fxml"));
        Scene homeScene = new Scene(homeParent);
        homeScene.getStylesheets().add(getClass().getResource("../Style/home.css").toString());

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setResizable(true);

        window.setScene(homeScene);
        window.setFullScreen(true);
        window.show();
    }

}
