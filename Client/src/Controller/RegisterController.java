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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {

    @FXML private Spinner PoidsSpinner;
    @FXML private Spinner TailleSpinner;
    @FXML private ComboBox SexeComboBox;
    @FXML private ComboBox NiveauComboBox;
    @FXML private Label errorNetwork;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField enterPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField textCountry;
    @FXML private TextField textRegion;
    @FXML private TextField textCity;
    @FXML private DatePicker birthdate;
    @FXML private Label errorCreateAccount;
    @FXML private Label errorPwd;
    @FXML private Label errorConfirmPwd;

    public void createAccountButtonPushed(ActionEvent event) throws IOException
    {
        if(checkPwd()) {
            if (AlphActivity.client.connect()) {
                if (AlphActivity.client.createAccount(usernameTextField.getText(), enterPasswordField.getText(), textCountry.getText(), textRegion.getText(), textCity.getText(), (int) PoidsSpinner.getValue(), birthdate.getValue().toString(), (int) TailleSpinner.getValue(), SexeComboBox.getValue().toString(), NiveauComboBox.getValue().toString())) {
                    Parent loginParent = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
                    Scene loginScene = new Scene(loginParent);

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    window.setScene(loginScene);
                    window.show();
                } else {
                    errorCreateAccount.setVisible(true);
                    AlphActivity.client.disconnect();
                }
            } else {
                errorNetwork.setVisible(true);
            }
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
        this.SexeComboBox.getSelectionModel().selectFirst();

        this.NiveauComboBox.getItems().addAll("Débutant", "Intermédiaire", "Confirmé", "Expert", "Alpha");
        this.NiveauComboBox.getSelectionModel().selectFirst();

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

    private boolean checkPwd()
    {
        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{7,}$");
        Matcher m = p.matcher(enterPasswordField.getText());
        if(m.matches())
        {
            Pattern confirm = Pattern.compile(enterPasswordField.getText());
            Matcher matcher = confirm.matcher(confirmPasswordField.getText());
            if(matcher.matches())
            {
                return true;
            }
            else
            {
                errorConfirmPwd.setVisible(true);
                return false;
            }
        }
        else
        {
            errorPwd.setVisible(true);
            return false;
        }
    }
}
