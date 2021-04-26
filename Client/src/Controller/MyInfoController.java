package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class MyInfoController {

    @FXML private PasswordField oldPwd;
    @FXML private PasswordField newPwd;
    @FXML private PasswordField confirmPwd;
    @FXML private Spinner PoidsSpinner;
    @FXML private Spinner TailleSpinner;
    @FXML private ComboBox SexeComboBox;
    @FXML private ComboBox NiveauComboBox;

    public void CancelButtonPushed(ActionEvent event)
    {
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.close();
    }

    public void ModifyButtonPushed(ActionEvent event)
    {
        /**
         * Ajouter la partie envoi de données à la bd
         */
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.close();
    }

    @FXML
    public void initialize()
    {
        /**
         * Requete récupération des données à ajouter
         */

        SpinnerValueFactory<Integer> listPoids = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,200,70);
        this.PoidsSpinner.setValueFactory(listPoids);

        SpinnerValueFactory<Integer> listTaille = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,250,170);
        this.TailleSpinner.setValueFactory(listTaille);

        this.SexeComboBox.getItems().addAll("Homme", "Femme", "Autre");
        this.SexeComboBox.setValue("Femme");

        this.NiveauComboBox.getItems().addAll("Débutant", "Intermédiaire", "Confirmé", "Expert", "Alpha");
        this.NiveauComboBox.setValue("Confirmé");
    }

}
