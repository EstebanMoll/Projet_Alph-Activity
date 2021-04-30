package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {

    static final String serverName = "localhost";
    static final int serverPort = 9999;
    static int count = 0;
    static boolean isConnected = false;
    static String log,mdp;

    public static void main(String[] args) throws Exception {

        //Test login FALSE
        System.out.println("LOG1 - false = "+login("MarlonLeBG", "FauxMDP"));

        //Test login TRUE
        System.out.println("LOG2 - true = "+login("MarlonLeBG", "TroDURaTrouverWLH"));
        System.out.println("===================\n");

        //Test create true
        int rdm = (int) (Math.random()*100);
        System.out.println("RDG1 - true = " +createAccount("LOG"+rdm,"testMD2P","testPAYS","testREGION","testVI2LLE",1,"18",3,"1","5"));

        //Test create false
        System.out.println("RDG2 - false = " +createAccount("test","testMD2P","testPAYS","testREGION","testVI2LLE",1,"18",3,"1","5"));
        System.out.println("===================\n");

        //Test get liste des activités acti
        System.out.println("REG1 Client recoit: " + getActivities());
        System.out.println("===================\n");


        System.out.println("LOG2 Client recoit: " + ""+login("MarlonLeBG", "TroDURaTrouverWLH"));

        System.out.println("ACTI Client recoit: " + modifyData("TroDURaTrouverWLH","country","region","city", 50, 50, "1", "5"));

        System.out.println("DATAGET Client recoit: " + getUserData());

        System.out.println("GETIMC Client recoit: " + getIMC());

        log = "Beau";
        System.out.println("GETlastday Client recoit: " + getDayAgo());

        System.out.println("GETlastday Client recoit: " + getPracActivity());

        System.out.println("GETlastday Client recoit: " + getMoyenneDistance());

        System.out.println("GETlastday Client recoit: " + getMoyenneTemps());

        System.out.println("GETlastday Client recoit: " + getMoyenneVitesse());

        System.out.println("GETlastday Client recoit: " + getRank());


        System.out.println("GETlastday Client recoit: " + getClassement());


        System.out.println("GETlastday Client recoit: " + getNbActivity());


        System.out.println("GETlastday Client recoit: " + getMoyenneActivityPerWeek());


        System.out.println("GETlastday Client recoit: " + getHistory());

        System.out.println("GETlastday Client recoit: " + getLastActivityRecap("vélo"));

        //System.out.println("GETlastday Client recoit: " + getLastActivityRecap("nage"));




    }


    public static Object AppelServeur(String root) throws Exception {
        Socket socket = new Socket(serverName, serverPort);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());


        out.writeObject(root);
        out.flush();
        Object rec = in.readObject();


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
        log = "";
        mdp = "";
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


    /**
     * Récupération de tous les types d'activités
     * @return String de toutes les activités séparées par des points virgules. Exemple("Course à pied;Marche;Vélo;VTT;Aviron")
     */
    public static String getActivities()
    {
        String root = "get.Activities/";
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
        int duree = secondeSpinner + (minuteSpinner*60) + (heureSpinner*3600);
        System.out.println(java.time.LocalDate.now());

        String root = "set.addActivity/"+log+"/"+activityComboBox+"/"+distanceSpinner+"/"+duree+"/"+java.time.LocalDate.now()+"/"+textComment;

        try {
            Boolean t = (Boolean) AppelServeur(root);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
        String root = "set.modifyData/"+log+"/"+pwd+"/"+weight+"/"+height+"/"+sexe+"/"+level+"/"+country +"/"+region +"/"+city;
        try {
            Boolean t = (Boolean) AppelServeur(root);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Vérification de l'ancien mot de passe
     * @param oldPwdHash ancien mot de passe hashé
     * @return true si comparaison OK sinon false
     */
    public static boolean checkOldPwd(String oldPwdHash)
    {
        return mdp.equals(oldPwdHash);
    }

    /**
     * Récupération des inforamations de l'utilisateur
     * @return String de toutes les informations séparées par des points virgules.Exemple("France;Marne;Reims;70;180;Homme;Débutant)
     */
    public static String getUserData()
    {
        String root = "get.userData/"+log;
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
        String root = "get.rank/"+log;
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
        String root = "get.IMC/"+log;
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
        return (int) (Float.parseFloat(getRank())/5);
    }

    /**
     * Récupération de l'historique de l'utilisateur (10 dernières activités)
     * @return Tableau de String 10x4 sous la forme (Activité, Date, Distance, Temps)
     */
    public static String[][] getHistory() {
        String root = "get.History/"+log;
        try {
            String[][] t = (String[][]) AppelServeur(root);
            for(int i=0;i<t.length;i++){
                for(int j=0;j<t[i].length;j++) {
                    System.out.print(t[i][j] + " - ");
                }
                System.out.println("");
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Récupération du nombre de fois que 'litilisateur a fait une activité
     * @return Tableau de String (Type d'activité, nobre de fois pratiqué)
     */
    public static String[][] getNbActivity()
    {
        String root = "get.NbActivity/"+log;
        try {
            String[][] t = (String[][]) AppelServeur(root);
            for(int i=0;i<t.length;i++){
                for(int j=0;j<t[i].length;j++) {
                    System.out.print(t[i][j] + " - ");
                }
                System.out.println("");
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Récupération du nombre d'activité pratiquée par semaine en moyenne
     * @return String nombre d'activité moyenne par semaine
     */
    public static String getMoyenneActivityPerWeek() {
        String root = "get.moyenneActivityPerWeek/"+log;
        try {
            String t = (String) AppelServeur(root);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    /**
     * Récupération des distance moyenne par activité
     * @return Tableau de String (Activité, distance)
     */
    public static String[][] getMoyenneDistance() {
        String root = "get.MoyenneDistance/"+log;
        try {
            String[][] t = (String[][]) AppelServeur(root);
            for(int i=0;i<t.length;i++){
                for(int j=0;j<t[i].length;j++) {
                    System.out.print(t[i][j] + " - ");
                }
                System.out.println("");
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Récupération des temps moyen par activité
     * @return Tableau de String (Activité, temps en minute)
     */
    public static String[][] getMoyenneTemps() {
        String root = "get.MoyenneTemps/"+log;
        try {
            String[][] t = (String[][]) AppelServeur(root);
            for(int i=0;i<t.length;i++){
                for(int j=0;j<t[i].length;j++) {
                    System.out.print(t[i][j] + " - ");
                }
                System.out.println("");
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Récupération des vitesses moyennes par activité
     * @return Tableau de String (Activité, vitesse)
     */
    public static String[][] getMoyenneVitesse() {
        String root = "get.MoyenneVitesse/"+log;
        try {
            String[][] t = (String[][]) AppelServeur(root);
            for(int i=0;i<t.length;i++){
                for(int j=0;j<t[i].length;j++) {
                    System.out.print(t[i][j] + " - ");
                }
                System.out.println("");
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Récupération de la derniere activité pratiquée
     * @return String derniere activité
     */
    public static String getPracActivity() {
        String root = "get.PracActivity/"+log;
        try {
            String t = (String) AppelServeur(root);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
        //return "Course à pied";
    }

    /**
     * Récupération du nombre de jour depuis la derniere activité
     * @return String nombre de jours
     */
    public static String getDayAgo() {
        String root = "get.DayAgo/"+log;
        try {
            String t = (String) AppelServeur(root);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    /**
     * Récupération des 6 dernieres sorties pour l'activité donnée
     * @param lastACtivity String activité donnée
     * @return Tableau de double (temps, distance).
     */
    public static Double[][] getLastActivityRecap(String lastACtivity) {
        String root = "get.LastActivityRecap/"+log+"/"+lastACtivity;
        try {
            Double[][] t = (Double[][]) AppelServeur(root);
            for(int i=0;i<t.length;i++){
                for(int j=0;j<t[i].length;j++) {
                    System.out.print(t[i][j] + " - ");
                }
                System.out.println("");
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}