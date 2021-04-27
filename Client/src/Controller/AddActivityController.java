package Controller;

import App.AlphActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class AddActivityController {

    @FXML private ComboBox ActivityComboBox;
    @FXML private Spinner DistanceSpinner;
    @FXML private Spinner HeureSpinner;
    @FXML private Spinner MinuteSpinner;
    @FXML private Spinner SecondeSpinner;
    @FXML private Button addButton;
    @FXML private Button cancelButton;

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
    }

    public void addButtonPushed(ActionEvent event)
    {
        /**
         * Ajouter l'envoi des données au serveur
         */

        closeWindow(addButton);

    }

    public void cancelButtonPushed(ActionEvent event)
    {
        closeWindow(cancelButton);
    }

    private void closeWindow(Button bt)
    {
        Stage stage = (Stage) bt.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}
