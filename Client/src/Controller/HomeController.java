package Controller;

import App.AlphActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML private Button addActivity;
    @FXML private RadioButton radio1;
    @FXML private RadioButton radio2;
    @FXML private RadioButton radio3;
    @FXML private RadioButton radio4;
    @FXML private Tab historyTab;
    @FXML private Tab statTab;
    @FXML private Tab lastTrainingTab;
    @FXML private Tab rankTab;

    public void addActivityButtonPushed(ActionEvent event) throws IOException
    {
        Parent activityParent = FXMLLoader.load(getClass().getResource("../View/addActivity.fxml"));
        Scene activityScene = new Scene(activityParent);

        Stage window = new Stage();
        window.getIcons().add(new Image(HomeController.class.getResourceAsStream("/ressources/Logo.png")));

        window.setScene(activityScene);
        window.show();
    }

    public void myInfoHyperlinkPushed(ActionEvent event) throws IOException
    {
        Parent myInfoParent = FXMLLoader.load(getClass().getResource("../View/myInfo.fxml"));
        Scene myInfoScene = new Scene(myInfoParent);

        Stage window = new Stage();
        window.getIcons().add(new Image(HomeController.class.getResourceAsStream("/ressources/Logo.png")));

        window.setScene(myInfoScene);
        window.show();
    }

    public void logoutButtonPushed(ActionEvent event) throws IOException
    {
        Parent loginParent = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(loginScene);
        window.show();
    }

    @FXML
    public void initialize()
    {
        ToggleGroup toggleGroup = new ToggleGroup();

        radio1.setToggleGroup(toggleGroup);
        radio2.setToggleGroup(toggleGroup);
        radio3.setToggleGroup(toggleGroup);
        radio4.setToggleGroup(toggleGroup);

    }

    public void radio1Pushed(ActionEvent event)
    {

    }
}
