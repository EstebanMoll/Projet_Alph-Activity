package Model
;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
/*
 * www.codeurjava.com
 */
public class Client {

    public static void main(String[] args) {

        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc = new Scanner(System.in);//pour lire à partir du clavier

        try {
            /*
             * les informations du serveur ( port et adresse IP ou nom d'hote
             * 127.0.0.1 est l'adresse local de la machine
             */
            clientSocket = new Socket("127.0.0.1",5000);

            //flux pour envoyer
            out = new PrintWriter(clientSocket.getOutputStream());
            //flux pour recevoir
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Thread envoyer = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    while(true){
                        msg = sc.nextLine();
                        out.println(msg);
                        out.flush();
                    }
                }
            });
            envoyer.start();

            Thread recevoir = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    try {
                        msg = in.readLine();
                        while(msg!=null){
                            System.out.println("Serveur : "+msg);
                            msg = in.readLine();
                        }
                        System.out.println("Serveur déconecté");
                        out.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            recevoir.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction pour se connecter à l'application
     * @param username String nom de l'utilisateur
     * @param psw String mot de passe hashé de l'utilisateur
     * @return true si login Ok sinon false
     */
    public boolean login(String username, String psw)
    {
        return true;
    }

    /**
     * Connexion au serveur
     * @return true si connexion sinon false
     */
    public boolean connect()
    {
        return true;
    }

    /**
     * Deconnexion au serveur
     * @return true si la déconnexion OK sinon false
     */
    public boolean disconnect()
    {
        return true;
    }

    /**
     * Creation d'un compte utilisateur
     * @param username String nom de l'utilisateur
     * @param pwd String mot de passe hashé de l'utilisateur
     * @param country String pays
     * @param region String region
     * @param city String ville
     * @param weight Float poids
     * @param birthdate String date de naissance
     * @param size Float taille
     * @param sexe String sexe
     * @param level String niveau
     * @return true si création OK sinon false
     */
    public boolean createAccount(String username, String pwd, String country, String region, String city, float weight, String birthdate, float size, String sexe, String level)
    {
        return true;
    }

    /**
     * Récupération de tous les types d'activités
     * @return String de toutes les activités séparées par des points virgules. Exemple("Course à pied;Marche;Vélo;VTT;Aviron")
     */
    public String getActivities()
    {
        return "Course à pied;Marche;Vélo;VTT;Aviron";
    }

    /**
     * Ajout d'une activité dans la base de données
     * @param activityComboBox String activité
     * @param distanceSpinner Float distance
     * @param heureSpinner int Heure
     * @param minuteSpinner int Minute
     * @param secondeSpinner int Seconde
     * @param textComment String commentaire
     * @return true si ajout OK sinon false
     */
    public Boolean addActivity(String activityComboBox, double distanceSpinner, int heureSpinner, int minuteSpinner, int secondeSpinner, String textComment)
    {
        return true;
    }

    /**
     * Modification des données d'un utilisateur
     * @param pwd String mot de passe hashé
     * @param country String pays
     * @param region String région
     * @param city String ville
     * @param weight int poids
     * @param height int taille
     * @param sexe String sexe
     * @param level String niveau
     * @return true si modification OK sinon false
     */
    public boolean modifyData(String pwd, String country, String region, String city, int weight, int height, String sexe, String level)
    {
        return true;
    }

    /**
     * Vérification de l'ancien mot de passe
     * @param oldPwdHash ancien mot de passe hashé
     * @return true si comparaison OK sinon false
     */
    public boolean checkOldPwd(String oldPwdHash)
    {
        return true;
    }

    /**
     * Récupération des inforamations de l'utilisateur
     * @return String de toutes les informations séparées par des points virgules.Exemple("France;Marne;Reims;70;180;Homme;Débutant)
     */
    public String getUserData()
    {
        return "France;Marne;Reims;70;180;Homme;Débutant";
    }

    /**
     * Récupération du login de l'utilisateur
     * @return String login
     */
    public String getLogin() {
        return "bidule02";
    }

    /**
     * Récupération du classement de l'utilisateur (proportion par rapport à tous les autres sportif)
     * @return String classement
     */
    public String getRank() {
        return "24%";
    }

    /**
     * Récupération de l'IMC de l'utilisateur
     * @return String IMC
     */
    public String getIMC() {
        return "25";
    }

    /**
     * Récupération d'un message d'encouragement pour l'utilisateur
     * @return String message
     */
    public String getMessage() {
        return "Bon travail, continué sur votre lancé !!";
    }

    /**
     * Récupération de la tranche dans laquelle se trouve l'utilisateur (entre 0,19, 0 -> 5%, 19 -> 100%)
     * @return int classment
     */
    public int getClassement()
    {
        return 2;
    }

    /**
     * Récupération de l'historique de l'utilisateur (10 dernières activités)
     * @return Tableau de String 10x4 sous la forme (Activité, Date, Distance, Temps)
     */
    public String[][] getHistory() {
        String [][] retour = new String[10][4];
        Random r = new Random();
        String alphabet = "azertyuiopqsdfghjklmwxcvbn";
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                retour[i][j] = String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
            }
        }

        return retour;
    }
}