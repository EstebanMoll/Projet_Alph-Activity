package Controller;

import App.AlphActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class RegisterController {

    @FXML private Spinner PoidsSpinner;
    @FXML private Spinner TailleSpinner;
    @FXML private ComboBox SexeComboBox;
    @FXML private ComboBox NiveauComboBox;
    @FXML private Label errorNetwork;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField enterPasswordField;
    @FXML private TextField textCountry;
    @FXML private TextField textRegion;
    @FXML private TextField textCity;
    @FXML private DatePicker birthdate;
    @FXML private Label errorCreateAccount;

    public void createAccountButtonPushed(ActionEvent event) throws IOException
    {
        if(AlphActivity.client.connect())
        {
            if(AlphActivity.client.createAccount(usernameTextField.getText(), enterPasswordField.getText(), textCountry.getText(), textRegion.getText(), textCity.getText(), (int)PoidsSpinner.getValue(), birthdate.getValue().toString(), (int)TailleSpinner.getValue(), SexeComboBox.getValue().toString(), NiveauComboBox.getValue().toString())) {
                Parent loginParent = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
                Scene loginScene = new Scene(loginParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(loginScene);
                window.show();
            }
            else
            {
                errorCreateAccount.setVisible(true);
                AlphActivity.client.disconnect();
            }
        }
        else
        {
            errorNetwork.setVisible(true);
        }
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
        this.birthdate.setValue(LocalDate.now().minusYears(18));
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
