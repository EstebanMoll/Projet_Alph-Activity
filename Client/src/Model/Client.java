package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;


public class Client {

    static final String serverName = "localhost";
    static final int serverPort = 9999;
    static int count = 0;
    static boolean isConnected = false;
    static String log,mdp;

    public static void main(String[] args) throws Exception {

        //   System.out.println("Client recoit: " + ""+AppelServeur(""));

        //Test login FALSE
        System.out.println("LOG1 Client recoit: " + ""+login("MarlonLeBG", "FauxMDP"));

        //Test login TRUE
        System.out.println("LOG2 Client recoit: " + ""+login("MarlonLeBG", "TroDURaTrouverWLH"));


        //Test create false
        System.out.println("REG1 Client recoit: " + ""+createAccount());


/*
        System.out.println("Client recoit: " + ""+AppelServeur(""));
        System.out.println("Client recoit: " + ""+AppelServeur(""));
        System.out.println("Client recoit: " + ""+AppelServeur(""));
        System.out.println("Client recoit: " + ""+AppelServeur(""));
        System.out.println("Client recoit: " + ""+AppelServeur(""));
*/
    }

    public static Object AppelServeur(String root) throws Exception {
        Socket socket = new Socket(serverName, serverPort);
        System.out.println("Socket client: " + socket);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        System.out.println("Client a cree les flux");

        out.writeObject(root);
        out.flush();
        Object rec = in.readObject();

        System.out.println("Client: donnees emises");

        in.close();
        out.close();
        socket.close();
        return(rec);

    }

    /**
     * Fonction pour se connecter à l'application
     * @param username String nom de l'utilisateur
     * @param psw String mot de passe hashé de l'utilisateur
     * @return true si login Ok sinon false
     */
    public static boolean login(String username, String psw)
    {
        String root = "get.login/"+username+"/"+psw ;

        try {
            if((Boolean) AppelServeur(root)){
                isConnected = true;
                log = username;
                mdp = psw;
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * Connexion au serveur
     * @return true si connexion sinon false
     */
    public static boolean connect()
    {
        return isConnected;
    }

    /**
     * Deconnexion au serveur
     * @return true si la déconnexion OK sinon false
     */
    public static boolean disconnect()
    {
        isConnected = false;
        return isConnected;
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
    public static boolean createAccount(String username, String pwd, String country, String region, String city, float weight, String birthdate, float size, String sexe, String level)
    {
        String root = "set.createAccount/"+username+"/"+pwd+"/"+country+"/"+region+"/"+city+"/"+weight+"/"+birthdate+"/"+size+"/"+sexe+"/"+level ;

        try {
            Boolean t = (Boolean) AppelServeur(root);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createAccount()
    {
        String root = "set.createAccount/teszdtLOG2/testMD2P/testPAYS/testREGION/testVI2LLE/1/2/3/4/5" ;
        try {
            Boolean t = (Boolean) AppelServeur(root);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Récupération de tous les types d'activités
     * @return String de toutes les activités séparées par des points virgules. Exemple("Course à pied;Marche;Vélo;VTT;Aviron")
     */
    public static String getActivities()
    {
        String root = "get.listeActi/";
        try {
            String t = (String) AppelServeur(root);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
        //return "Course à pied;Marche;Vélo;VTT;Aviron";
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
    public static Boolean addActivity(String activityComboBox, double distanceSpinner, int heureSpinner, int minuteSpinner, int secondeSpinner, String textComment)
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
    public static boolean modifyData(String pwd, String country, String region, String city, int weight, int height, String sexe, String level)
    {
        return true;
    }

    /**
     * Vérification de l'ancien mot de passe
     * @param oldPwdHash ancien mot de passe hashé
     * @return true si comparaison OK sinon false
     */
    public static boolean checkOldPwd(String oldPwdHash)
    {
        return true;
    }

    /**
     * Récupération des inforamations de l'utilisateur
     * @return String de toutes les informations séparées par des points virgules.Exemple("France;Marne;Reims;70;180;Homme;Débutant)
     */
    public static String getUserData()
    {
        String root = "get.userData/"+log+"/";
        try {
            String t = (String) AppelServeur(root);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
        //return "France;Marne;Reims;70;180;Homme;Débutant";
    }

    /**
     * Récupération du login de l'utilisateur
     * @return String login
     */
    public static String getLogin() {
        return log;
    }

    /**
     * Récupération du classement de l'utilisateur (proportion par rapport à tous les autres sportif)
     * @return String classement
     */
    public static String getRank() {
        String root = "get.rank/"+log+"/";
        try {
            String t = (String) AppelServeur(root);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
        //return "8%";
    }

    /**
     * Récupération de l'IMC de l'utilisateur
     * @return String IMC
     */
    public static String getIMC() {
        String root = "get.imc/"+log+"/";
        try {
            String t = (String) AppelServeur(root);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
        //return "25";
    }

    /**
     * Récupération d'un message d'encouragement pour l'utilisateur
     * @return String message
     */
    public static String getMessage() {
        String root = "get.message/"+log+"/";
        try {
            String t = (String) AppelServeur(root);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
        //return "Bon travail, continué sur votre lancé !!";
    }

    /**
     * Récupération de la tranche dans laquelle se trouve l'utilisateur (entre 0,19, 0 -> 5%, 19 -> 100%)
     * @return int classment
     */
    public static int getClassement()
    {
        String root = "get.classement/"+log+"/";
        try {
            int t = (Integer) AppelServeur(root);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;

        //return 2;
    }

    /**
     * Récupération de l'historique de l'utilisateur (10 dernières activités)
     * @return Tableau de String 10x4 sous la forme (Activité, Date, Distance, Temps)
     */
    public static String[][] getHistory() {
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

    /**
     * Récupération du nombre de fois que 'litilisateur a fait une activité
     * @return Tableau de String (Type d'activité, nobre de fois pratiqué)
     */
    public static String[][] getNbActivity()
    {
        String[][]retour = new String[5][2];

        retour[0][0] = "Course à pied";
        retour[0][1] = "12";
        retour[1][0] = "Marche";
        retour[1][1] = "4";
        retour[2][0] = "Vélo";
        retour[2][1] = "1";
        retour[3][0] = "VTT";
        retour[3][1] = "8";
        retour[4][0] = "Aviron";
        retour[4][1] = "40";

        return retour;
    }

    /**
     * Récupération du nombre d'activité pratiquée par semaine en moyenne
     * @return String nombre d'activité moyenne par semaine
     */
    public static String getMoyenneActivityPerWeek() {
        return "3";
    }

    /**
     * Récupération des distance moyenne par activité
     * @return Tableau de String (Activité, distance)
     */
    public static String[][] getMoyenneDistance() {
        String[][]retour = new String[5][2];

        retour[0][0] = "Course à pied";
        retour[0][1] = "12";
        retour[1][0] = "Marche";
        retour[1][1] = "4";
        retour[2][0] = "Vélo";
        retour[2][1] = "1";
        retour[3][0] = "VTT";
        retour[3][1] = "8";
        retour[4][0] = "Aviron";
        retour[4][1] = "40";

        return retour;
    }

    /**
     * Récupération des temps moyen par activité
     * @return Tableau de String (Activité, temps en minute)
     */
    public static String[][] getMoyenneTemps() {
        String[][]retour = new String[5][2];

        retour[0][0] = "Course à pied";
        retour[0][1] = "128";
        retour[1][0] = "Marche";
        retour[1][1] = "35";
        retour[2][0] = "Vélo";
        retour[2][1] = "200";
        retour[3][0] = "VTT";
        retour[3][1] = "145";
        retour[4][0] = "Aviron";
        retour[4][1] = "55";

        return retour;
    }

    /**
     * Récupération des vitesses moyennes par activité
     * @return Tableau de String (Activité, vitesse)
     */
    public static String[][] getMoyenneVitesse() {
        String[][]retour = new String[5][2];

        retour[0][0] = "Course à pied";
        retour[0][1] = "12";
        retour[1][0] = "Marche";
        retour[1][1] = "4";
        retour[2][0] = "Vélo";
        retour[2][1] = "1";
        retour[3][0] = "VTT";
        retour[3][1] = "8";
        retour[4][0] = "Aviron";
        retour[4][1] = "40";

        return retour;
    }

    /**
     * Récupération de la derniere activité pratiquée
     * @return String derniere activité
     */
    public static String getPracActivity() {
        return "Course à pied";
    }

    /**
     * Récupération du nombre de jour depuis la derniere activité
     * @return String nombre de jours
     */
    public static String getDayAgo() {
        return "5";
    }

    /**
     * Récupération des 6 dernieres sorties pour l'activité donnée
     * @param lastACtivity String activité donnée
     * @return Tableau de double (temps, distance).
     */
    public static Double[][] getLastActivityRecap(String lastACtivity) {
        Double[][]retour = new Double[7][2];

        retour[0][0] = 2.1;
        retour[0][1] = 20.0;
        retour[1][0] = 10.0;
        retour[1][1] = 1.2;
        retour[2][0] = 5.2;
        retour[2][1] = 35.0;
        retour[3][0] = 6.1;
        retour[3][1] = 25.4;
        retour[4][0] = 9.1;
        retour[4][1] = 6.55;
        retour[5][0] = 18.5;
        retour[5][1] = 2.33;
        retour[6][0] = 12.6;
        retour[6][1] = 1.12;

        return retour;
    }
}