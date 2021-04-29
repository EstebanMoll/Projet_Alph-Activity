package Controller;

import App.AlphActivity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MyStatsController {

    String[][] allActivities;

    @FXML private GridPane listActivity;

    @FXML
    public void initialize()
    {
        allActivities = AlphActivity.client.getAllActivities();

        for(int i = 0; i < allActivities.length; i++)
        {
            Hyperlink hl = new Hyperlink(allActivities[i][0]);
            int finalI = i;
            hl.setOnAction(event -> {
                try {
                    openActivity(Integer.parseInt(allActivities[finalI][2]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            listActivity.addRow(i+1, hl, new Label(allActivities[i][1]));

        }
    }

    private void openActivity(int id) throws IOException
    {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("../View/addActivity.fxml"));
        Parent activityParent = loader.load();
        Scene activityScene = new Scene(activityParent);

        Stage window = new Stage();
        window.setResizable(false);
        window.getIcons().add(new Image(HomeController.class.getResourceAsStream("/ressources/Logo.png")));

        window.setScene(activityScene);

        AddActivityController controller = loader.getController();
        controller.initData(id);

        window.show();
    }

    public void returnButtonPushed(ActionEvent event) {
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.close();
    }
}
