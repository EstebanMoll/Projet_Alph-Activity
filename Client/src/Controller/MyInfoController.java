package Controller;

import App.AlphActivity;
import Model.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyInfoController {

    @FXML private PasswordField oldPwd;
    @FXML private PasswordField newPwd;
    @FXML private PasswordField confirmPwd;
    @FXML private Spinner PoidsSpinner;
    @FXML private Spinner TailleSpinner;
    @FXML private ComboBox SexeComboBox;
    @FXML private ComboBox NiveauComboBox;
    @FXML private Label errorModifyData;
    @FXML private TextField textCountry;
    @FXML private TextField textRegion;
    @FXML private TextField textCity;
    @FXML private Label errorPwd;
    @FXML private Label errorConfirmPwd;
    @FXML private Label errorOldPwd;

    public void CancelButtonPushed(ActionEvent event)
    {
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.close();
    }

    public void ModifyButtonPushed(ActionEvent event) throws NoSuchAlgorithmException {
        String oldPwdHash = hash256(oldPwd);

        errorModifyData.setVisible(false);
        errorOldPwd.setVisible(false);

        if(Client.checkOldPwd(oldPwdHash)) {
            if (checkPwd()) {
                String pwdhash = hash256(newPwd);

                String sexe;

                switch (SexeComboBox.getValue().toString()) {
                    case "Homme":
                        sexe = "1";
                        break;
                    case "Femme":
                        sexe = "2";
                        break;
                    default:
                        sexe = "0";
                }

                String niveau;

                switch (NiveauComboBox.getValue().toString())
                {
                    case "Débutant":
                        niveau = "1";
                        break;
                    case "Intermédiaire":
                        niveau = "2";
                        break;
                    case "Confirmé":
                        niveau = "3";
                        break;
                    case "Expert":
                        niveau = "4";
                        break;
                    case "Alpha":
                        niveau = "5";
                        break;
                    default:
                        niveau = "0";
                }

                if (Client.modifyData(pwdhash, textCountry.getText(), textRegion.getText(), textCity.getText(), (int) PoidsSpinner.getValue(), (int) TailleSpinner.getValue(), sexe, niveau)) {
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    window.close();
                } else {
                    errorModifyData.setVisible(true);
                }
            }
        }
        else
        {
            errorOldPwd.setVisible(true);
        }
    }

    @FXML
    public void initialize()
    {
        String userData = Client.getUserData();
        String[] tabUserData = userData.split(";");

        this.textCountry.setText(tabUserData[0]);
        this.textRegion.setText(tabUserData[1]);
        this.textCity.setText(tabUserData[2]);

        SpinnerValueFactory<Integer> listPoids = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,200,Integer.parseInt(tabUserData[3]));
        this.PoidsSpinner.setValueFactory(listPoids);

        SpinnerValueFactory<Integer> listTaille = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,250,Integer.parseInt(tabUserData[4]));
        this.TailleSpinner.setValueFactory(listTaille);

        this.SexeComboBox.getItems().addAll("Homme", "Femme", "Autre");

        switch (tabUserData[5]){
            case "0":
                this.SexeComboBox.setValue("Autre");
                break;
            case "1":
                this.SexeComboBox.setValue("Homme");
                break;
            case "2":
                this.SexeComboBox.setValue("Femme");
                break;
            default:
                this.SexeComboBox.setValue("erreur");
                break;
        }


        this.NiveauComboBox.getItems().addAll("Débutant", "Intermédiaire", "Confirmé", "Expert", "Alpha");

        switch (tabUserData[6])
        {
            case "1":
                this.NiveauComboBox.setValue("Débutant");
                break;
            case "2":
                this.NiveauComboBox.setValue("Intermédiaire");
                break;
            case "3":
                this.NiveauComboBox.setValue("Confirmé");
                break;
            case "4":
                this.NiveauComboBox.setValue("Expert");
                break;
            case"5":
                this.NiveauComboBox.setValue("Alpha");
                break;
            default:
                this.NiveauComboBox.setValue("erreur");
                break;
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

    private boolean checkPwd()
    {
        errorPwd.setVisible(false);
        errorConfirmPwd.setVisible(false);

        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{7,}$");
        Matcher m = p.matcher(newPwd.getText());

        if(m.matches())
        {
            Pattern confirm = Pattern.compile(newPwd.getText());
            Matcher matcher = confirm.matcher(confirmPwd.getText());
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
