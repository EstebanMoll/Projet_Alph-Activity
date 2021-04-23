package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public void registerHyperlinkPushed(ActionEvent event) throws IOException
    {
        Parent registerParent = FXMLLoader.load(getClass().getResource("register.fxml"));
        Scene registerScene = new Scene(registerParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(registerScene);
        window.show();
    }

    public void loginButtonPushed(ActionEvent event) throws IOException
    {
        Parent homeParent = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene homeScene = new Scene(homeParent);
        homeScene.getStylesheets().add(getClass().getResource("home.css").toString());

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setResizable(true);

        window.setScene(homeScene);
        window.setFullScreen(true);
        window.show();
    }

}
