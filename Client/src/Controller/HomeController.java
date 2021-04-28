package Controller;

import App.AlphActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    private Node[][] gridPaneArray = null;

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
    @FXML private BarChart rankChart;
    @FXML private GridPane gridHistoric;

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

        XYChart.Series serie = new XYChart.Series();

        for(int i = 0; i < 20; i++)
        {
            String value = (i + 1) * 5 + "%";
            serie.getData().add(new XYChart.Data(value, 100-(i*5)));
        }

        rankChart.setTitle("Classement global");
        rankChart.getData().add(serie);

        int i = 0;

        for(Node n:rankChart.lookupAll(".default-color0.chart-bar")) {
            if(i == AlphActivity.client.getClassement()) {
                n.setStyle("-fx-bar-fill: #F69321;");
            }
            i++;
        }

        initializeGridPaneArray();
        updateHistoric();

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

    private void initializeGridPaneArray()
    {
        this.gridPaneArray = new Node[11][4];
        for(Node node : this.gridHistoric.getChildren())
        {
            if(node instanceof AnchorPane) {
                this.gridPaneArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
            }
        }
    }

    public void updateHistoric() {

        Node n;
        String[][] historic = AlphActivity.client.getHistory();

        for(int i = 1; i < 11; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                n = this.gridPaneArray[i][j];
                if(n instanceof AnchorPane)
                {
                    for(Node nodeIn: ((AnchorPane)n).getChildren())
                    {
                        if(nodeIn instanceof Label)
                        {
                            ((Label)nodeIn).setText(historic[i-1][j]);
                        }
                    }
                }
            }
        }
    }

}
