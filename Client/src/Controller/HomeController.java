package Controller;

import App.AlphActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    @FXML private TabPane tabPane;
    @FXML private Label loginUser;
    @FXML private Label rankUser;
    @FXML private Label imcUser;
    @FXML private Label messageDisplay;

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

        toggleGroup.selectToggle(radio1);

        loginUser.setText(AlphActivity.client.getLogin());
        rankUser.setText("Rank : " + AlphActivity.client.getRank());
        imcUser.setText("IMC : " + AlphActivity.client.getIMC());
        messageDisplay.setText(AlphActivity.client.getMessage());

    }

    public void radio1Pushed()
    {
        tabPane.getSelectionModel().select(historyTab);
    }

    public void radio2Pushed()
    {
        tabPane.getSelectionModel().select(statTab);
    }

    public void radio3Pushed()
    {
        tabPane.getSelectionModel().select(lastTrainingTab);
    }

    public void radio4Pushed()
    {
        tabPane.getSelectionModel().select(rankTab);
    }
}
