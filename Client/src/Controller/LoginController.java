package Controller;

import App.AlphActivity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginController {

    @FXML private TextField usernameTextField;
    @FXML private PasswordField pwdField;
    @FXML private Label labelEmpty;
    @FXML private Label errorLogin;

    public void registerHyperlinkPushed(ActionEvent event) throws IOException
    {
        Parent registerParent = FXMLLoader.load(getClass().getResource("../View/register.fxml"));
        Scene registerScene = new Scene(registerParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(registerScene);
        window.show();
    }

    public void loginButtonPushed(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        labelEmpty.setVisible(false);
        if(isEmpty(usernameTextField) || isEmpty(pwdField))
        {
            labelEmpty.setVisible(true);
        }
        else {
            String pwdHash = hash256(pwdField);

                if (AlphActivity.client.login(usernameTextField.getText(), pwdHash)) {
                    Parent homeParent = FXMLLoader.load(getClass().getResource("../View/home.fxml"));
                    Scene homeScene = new Scene(homeParent);
                    homeScene.getStylesheets().add(getClass().getResource("../Style/home.css").toString());

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    window.setResizable(true);

                    window.setScene(homeScene);
                    window.setFullScreen(true);
                    window.show();
                } else {
                    errorLogin.setVisible(true);
                }
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

    private boolean isEmpty(PasswordField pf)
    {
        if(pf.getText() == null || pf.getText().trim().isEmpty())
        {
            return true;
        }
        return false;
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
