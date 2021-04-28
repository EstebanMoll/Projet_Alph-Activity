package Controller;

import App.AlphActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

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

        SpinnerValueFactory<Integer> listHeure = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        this.HeureSpinner.setValueFactory(listHeure);

        SpinnerValueFactory<Integer> listMinute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0);
        this.MinuteSpinner.setValueFactory(listMinute);

        SpinnerValueFactory<Integer> listSeconde = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0);
        this.SecondeSpinner.setValueFactory(listSeconde);

        String activities = AlphActivity.client.getActivities();
        String[] tabActivities = activities.split(";");

        for (String act: tabActivities)
        {
            this.ActivityComboBox.getItems().add(act);
        }

        this.ActivityComboBox.getSelectionModel().selectFirst();
    }

    public void addButtonPushed(ActionEvent event) throws IOException {
        if(AlphActivity.client.addActivity(ActivityComboBox.getItems().toString(), (double)DistanceSpinner.getValue(), (int)HeureSpinner.getValue(), (int)MinuteSpinner.getValue(), (int)SecondeSpinner.getValue(), textComment.getText()))
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

}
