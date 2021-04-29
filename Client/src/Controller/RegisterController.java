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
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    @FXML private Label loginVoid;

    public void createAccountButtonPushed(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        errorNetwork.setVisible(false);
        errorCreateAccount.setVisible(false);
        loginVoid.setVisible(false);

        if (!isEmpty(usernameTextField)) {
            if (checkPwd()) {

                String pwdHash = hash256(enterPasswordField);

                if (AlphActivity.client.createAccount(usernameTextField.getText(), pwdHash, textCountry.getText(), textRegion.getText(), textCity.getText(), (int) PoidsSpinner.getValue(), birthdate.getValue().toString(), (int) TailleSpinner.getValue(), SexeComboBox.getValue().toString(), NiveauComboBox.getValue().toString())) {
                    Parent loginParent = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
                    Scene loginScene = new Scene(loginParent);

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    window.setScene(loginScene);
                    window.show();
                } else {
                    errorCreateAccount.setVisible(true);
                    AlphActivity.client.disconnect();
                }
            }
        }
        else
        {
            loginVoid.setVisible(true);
        }
    }

    private boolean isEmpty(TextField tf)
    {
        if(tf.getText() == null || tf.getText().trim().isEmpty())
        {
            return true;
        }
        return false;
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
        errorConfirmPwd.setVisible(false);
        errorPwd.setVisible(false);

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

    private String hash256(PasswordField pf) throws NoSuchAlgorithmException
    {
        return toHexString(getSHA(pf.getText()));
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
}
