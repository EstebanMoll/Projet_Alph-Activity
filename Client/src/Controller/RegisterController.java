package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML private Spinner PoidsSpinner;

    @FXML private Spinner TailleSpinner;

    @FXML private ComboBox SexeComboBox;

    @FXML private ComboBox NiveauComboBox;

    @FXML private Button CreateAccountButton;

    public void createAccountButtonPushed(ActionEvent event) throws IOException
    {
        Parent loginParent = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(loginScene);
        window.show();
    }

    @FXML
    public void initialize()
    {
        SpinnerValueFactory<Integer> listPoids = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,200,70);
        this.PoidsSpinner.setValueFactory(listPoids);

        SpinnerValueFactory<Integer> listTaille = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,250,170);
        this.TailleSpinner.setValueFactory(listTaille);

        this.SexeComboBox.getItems().addAll("Homme", "Femme", "Autre");

        this.NiveauComboBox.getItems().addAll("Débutant", "Intermédiaire", "Confirmé", "Expert", "Alpha");
    }

    public void CancelButtonPushed(ActionEvent event) throws IOException
    {
        Parent loginParent = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(loginScene);
        window.show();
    }
}
