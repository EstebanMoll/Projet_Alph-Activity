package Controller;

import App.AlphActivity;
import Model.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AddActivityController {

    @FXML private ComboBox ActivityComboBox;
    @FXML private Spinner DistanceSpinner;
    @FXML private Spinner HeureSpinner;
    @FXML private Spinner MinuteSpinner;
    @FXML private Spinner SecondeSpinner;
    @FXML private TextArea textComment;
    @FXML private Button addButton;
    @FXML private Button cancelButton;
    @FXML private Label errorAddActivity;

    @FXML
    public void initialize()
    {
            SpinnerValueFactory<Double> listDistance = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 1000.0, 5.0, 0.1);
            this.DistanceSpinner.setValueFactory(listDistance);

            SpinnerValueFactory<Integer> listHeure = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0);
            this.HeureSpinner.setValueFactory(listHeure);

            SpinnerValueFactory<Integer> listMinute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
            this.MinuteSpinner.setValueFactory(listMinute);

            SpinnerValueFactory<Integer> listSeconde = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
            this.SecondeSpinner.setValueFactory(listSeconde);

            String activities = Client.getActivities();
            String[] tabActivities = activities.split(";");

            for (String act : tabActivities) {
                this.ActivityComboBox.getItems().add(act);
            }

            this.ActivityComboBox.getSelectionModel().selectFirst();

    }

    public void addButtonPushed(ActionEvent event) throws IOException {

        if(Client.addActivity(ActivityComboBox.getValue().toString(), (double)DistanceSpinner.getValue(), (int)HeureSpinner.getValue(), (int)MinuteSpinner.getValue(), (int)SecondeSpinner.getValue(), textComment.getText()))
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/home.fxml"));
            Parent homeParent = (Parent)loader.load();
            HomeController controller = loader.getController();
            controller.updateHistoric();

            closeWindow(addButton);
        }
        else
        {
            errorAddActivity.setVisible(true);
        }
    }

    public void cancelButtonPushed()
    {
        closeWindow(cancelButton);
    }

    private void closeWindow(Button bt)
    {
        Stage stage = (Stage) bt.getScene().getWindow();
        stage.close();
    }

    public void initData(int id) {

        String activityData = AlphActivity.client.getActivityInfo(id);
        String[] tabActivityData = activityData.split(";");

        this.ActivityComboBox.setValue(tabActivityData[0]);
        this.ActivityComboBox.setDisable(true);

        SpinnerValueFactory<Double> listDistance = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 1000.0, Double.parseDouble(tabActivityData[1]));
        this.DistanceSpinner.setValueFactory(listDistance);
        this.DistanceSpinner.setDisable(true);

        SpinnerValueFactory<Integer> listHeure = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, Integer.parseInt(tabActivityData[2]));
        this.HeureSpinner.setValueFactory(listHeure);
        this.HeureSpinner.setDisable(true);

        SpinnerValueFactory<Integer> listMinute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, Integer.parseInt(tabActivityData[3]));
        this.MinuteSpinner.setValueFactory(listMinute);
        this.MinuteSpinner.setDisable(true);

        SpinnerValueFactory<Integer> listSeconde = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, Integer.parseInt(tabActivityData[4]));
        this.SecondeSpinner.setValueFactory(listSeconde);
        this.SecondeSpinner.setDisable(true);

        this.textComment.setText(tabActivityData[5]);
        this.textComment.setDisable(true);

        this.addButton.setVisible(false);
        this.cancelButton.setText("Retour");
    }
}
