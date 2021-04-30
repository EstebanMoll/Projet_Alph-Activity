import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Arrays;

public class Serveur {
    private static final int port = 9999;
    private static ServerSocket s;
    private static final String SEPARATEUR = "/";


    public static void main(String[] args) throws Exception {
        s = new ServerSocket(port);
        appelServ();
    }

    /**
     * Fonction principal qui tourne en boucle.
     * Elle appel les fonctions qui vont bien en
     * fonction de la demande
     *
     * @throws Exception
     */
    public static void appelServ() throws Exception {

        System.out.println("Socket serveur: " + s);
        Socket soc = s.accept(); //bloquante en attendant l'acces
        ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
        out.flush();
        ObjectInputStream in = new ObjectInputStream(soc.getInputStream());



        Object objetRecu = in.readObject();
        String root = "" + objetRecu;


        String demande = root.substring(0, root.indexOf('/'));   //la demande
        String param[] = root.substring(root.indexOf('/') + 1).split(SEPARATEUR); //les parametres



        for (int i = 0; i < param.length; i++) {
            System.out.print(param[i] + "/");
        }
        Object retour;


        switch (demande) {
            case "get.login":
                retour = get_login(param);
                break;
            case "get.Activities":
                retour = getActivities(param);
                break;
            case "get.userData":
                retour = get_data(param);
                break;
            case "get.IMC":
                retour = get_IMC(param);
                break;
            case "get.DayAgo":
                retour = getDayAgo(param);
                break;
            case "get.PracActivity":
                retour = getPracActivity(param);
                break;
            case "get.MoyenneDistance":
                retour = getMoyenneDistance(param);
                break;
            case "get.MoyenneTemps":
                retour = getMoyennetemps(param);
                break;
            case "get.MoyenneVitesse":
                retour = getMoyenneVitesse(param);
                break;
            case "get.rank":
                retour = get_rank(param);
                break;
            case "get.NbActivity":
                retour = getNbActivity(param);
                break;
            case "get.moyenneActivityPerWeek":
                retour = getMoyenneActivityPerWeek(param);
                break;
            case "get.History":
                retour = getHistory(param);
                break;
            case "get.LastActivityRecap":
                retour = getLastActivityRecap(param);
                break;
            case "get.AllActivities":
                retour = getAllActivities(param);
                break;
            case "get.ActivityInfo":
                retour = getActivityInfo(param);
                break;

            case "set.createAccount":
                retour = set_createAccount(param);
                break;
            case "set.addActivity":
                retour = set_addActivity(param);
                break;
            case "set.modifyData":
                retour = set_data(param);
                break;

            default:
                retour = "ERROR";
        }

        out.writeObject(retour);
        out.flush();

        in.close();
        out.close();
        // soc.close();
        appelServ();
    }



    /*
     *
     * LES GETTERS
     *
     */

    /**
     * Fonction pour se connecter à l'application
     * String[] param username+psw
     *
     * @return true si login Ok sinon false
     */
    private static boolean get_login(String[] param) { //test une connexion, return bool
        String[] donnees = {param[0], param[1]};

        String req = "SELECT count(*) FROM `utilisateur` WHERE `login` = ? AND`mdp`= ?";
        ResultSet retour = get_Donnees(req, donnees);
        boolean result = false;
        try {
            if (retour.next())
                result = retour.getString(1).equals("1");

            retour.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    /**
     * Récupération de tous les types d'activités
     *
     * @return String de toutes les activités séparées par des points virgules. Exemple("Course à pied;Marche;Vélo;VTT;Aviron")
     */
    private static String getActivities(String[] param) {
        String[] donnees = {};
        String req = "SELECT nom FROM `type_activité`";
        ResultSet result = get_Donnees(req, donnees);
        String txt = "";

        try {
            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            boolean encore = result.next();

            while (encore) {
                for (int i = 1; i <= nbCols; i++)
                    txt += (result.getString(i) + ";");
                encore = result.next();
            }
            result.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            //System.exit(99);
        }
        return txt;
    }

    /**
     * Récupération de l'IMC de l'utilisateur
     *
     * @return String IMC
     */
    private static String get_IMC(String[] param) {
        String[] donnees = {param[0]};
        String req = "SELECT `poids`,`taille`\n" +
                "FROM `utilisateur`\n" +
                "WHERE login=?";
        ResultSet result = get_Donnees(req, donnees);
        int poid = 0;
        float taille = 0;

        try {
            result.next();
            poid = result.getInt(1);
            taille = ((float) result.getInt(2)) / 100;

            result.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            //System.exit(99);
        }

        return "" + poid / (taille * taille);
    }

    /**
     * Récupération des inforamations de l'utilisateur
     *
     * @return String de toutes les informations séparées par des points virgules.Exemple("France;Marne;Reims;70;180;Homme;Débutant)
     */
    public static String get_data(String[] param) {
        String[] donnees = {param[0]};
        String req = "SELECT adr.`pays`,adr.`region`,adr.`ville`,utl.`poids`,utl.`taille`,utl.`sexe`,utl.`niveau`\n" +
                "FROM `utilisateur` as utl\n" +
                "INNER JOIN `adresse` as adr ON utl.`id_Adresse` = adr.`id_Adresse`\n" +
                "WHERE login=?";
        ResultSet result = get_Donnees(req, donnees);
        String txt = "";


        try {
            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            boolean encore = result.next();

            while (encore) {
                for (int i = 1; i <= nbCols; i++) {
                    txt += (result.getString(i) + ";");
                }
                encore = result.next();
            }
            result.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            //System.exit(99);
        }
        return txt;
    }

    /**
     * Récupération du classement de l'utilisateur (proportion par rapport à tous les autres sportif)
     *
     * @return String classement
     */
    public static String get_rank(String[] param) {
        String[] donnees = {param[0]};
        String req = "SELECT ROUND ((COUNT(DISTINCT login)*100)/(SELECT count(DISTINCT login)FROM `utilisateur`),2)\n" +
                "FROM `activité` \n" +
                "HAVING SUM(`temps`) > (SELECT SUM(`temps`) FROM `activité` WHERE login=?)";

        ResultSet result = get_Donnees(req, donnees);
        String txt = "";
        try {
            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            boolean encore = result.next();

            while (encore) {
                for (int i = 1; i <= nbCols; i++)
                    txt += (result.getString(i));
                encore = result.next();
            }
            result.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            //System.exit(99);
        }
        return txt;
    }

    /**
     * Récupération du nombre de jour depuis la derniere activité
     *
     * @return String nombre de jours
     */
    private static String getDayAgo(String[] param) {
        String[] donnees = {param[0]};
        String req = "SELECT DATEDIFF(NOW(),(SELECT MAX(`date_Activité`) FROM `activité` WHERE login=?))";
        ResultSet result = get_Donnees(req, donnees);
        String txt = "";

        try {
            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            boolean encore = result.next();

            while (encore) {
                for (int i = 1; i <= nbCols; i++)
                    txt += (result.getString(i));
                encore = result.next();
            }
            result.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return txt;
    }


    /**
     * Récupération de la derniere activité pratiquée
     *
     * @return String derniere activité
     */
    private static String getPracActivity(String[] param) {
        String[] donnees = {param[0], param[0]};
        String req = "SELECT `type_activité`.nom\n" +
                "    FROM `activité` as acti\n" +
                "    INNER JOIN `type_activité` ON acti.id_Type = `type_activité`.id_Type\n" +
                "    WHERE date_Activité=( SELECT MAX(date_Activité) FROM `activité` WHERE login=?) AND login=?";
        ResultSet result = get_Donnees(req, donnees);
        String txt = "";

        try {
            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            boolean encore = result.next();

            while (encore) {
                for (int i = 1; i <= nbCols; i++)
                    txt += (result.getString(i));
                encore = result.next();
            }
            result.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return txt;
    }

    /**
     * Récupération du nombre de fois que 'litilisateur a fait une activité
     *
     * @return Tableau de String (Type d'activité, nobre de fois pratiqué)
     */
    private static int getNbacti(String[] param) {
        String[] donnees = {param[0]};
        String req = "SELECT count(DISTINCT activité.id_Type)\n" +
                "FROM `type_activité`\n" +
                "INNER JOIN activité ON `type_activité`.`id_Type`=activité.id_Type\n" +
                "WHERE activité.login=?";
        ResultSet result = get_Donnees(req, donnees);
        int nb = 0;

        try {
            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            result.next();
            nb = result.getInt(1);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return nb;
    }


    /**
     * Récupération des distance moyenne par activité
     *
     * @return Tableau de String (Activité, distance)
     */
    private static String[][] getMoyenneDistance(String[] param) {
        String[] donnees = {param[0]};
        String[][] txt;

        String req = "SELECT nom, ROUND(AVG(activité.nb_Km),2)\n" +
                "    FROM `type_activité`\n" +
                "    INNER JOIN activité ON `type_activité`.`id_Type`=activité.id_Type\n" +
                "    WHERE activité.login=?\n" +
                "    GROUP BY activité.id_Type";
        ResultSet result = get_Donnees(req, donnees);

        try {

            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            int nblignes = getNbacti(param);
            txt = new String[nblignes][nbCols];

            boolean encore = result.next();

            for (int j = 1; j <= nblignes; j++) {
                for (int i = 1; i <= nbCols; i++)
                    txt[j - 1][i - 1] = (result.getString(i));
                result.next();
            }
            result.close();

            return txt;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Récupération des vitesses moyennes par activité
     *
     * @return Tableau de String (Activité, vitesse)
     */
    private static String[][] getMoyenneVitesse(String[] param) {
        String[] donnees = {param[0]};
        String[][] txt;

        String req = "SELECT nom, ROUND(AVG(activité.nb_Km/(activité.temps/3600)),2)\n" +
                "    FROM `type_activité`\n" +
                "    INNER JOIN activité ON `type_activité`.`id_Type`=activité.id_Type\n" +
                "    WHERE activité.login=?\n" +
                "    GROUP BY activité.id_Type";
        ResultSet result = get_Donnees(req, donnees);

        try {

            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            int nblignes = getNbacti(param);
            txt = new String[nblignes][nbCols];

            boolean encore = result.next();

            for (int j = 1; j <= nblignes; j++) {
                for (int i = 1; i <= nbCols; i++)
                    txt[j - 1][i - 1] = (result.getString(i));
                result.next();
            }
            result.close();

            return txt;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Récupération des temps moyen par activité
     *
     * @return Tableau de String (Activité, temps en minute)
     */
    private static String[][] getMoyennetemps(String[] param) {
        String[] donnees = {param[0]};
        String[][] txt;

        String req = "SELECT nom, ROUND(AVG(activité.temps/60),2)\n" +
                "    FROM `type_activité`\n" +
                "    INNER JOIN activité ON `type_activité`.`id_Type`=activité.id_Type\n" +
                "    WHERE activité.login=?\n" +
                "    GROUP BY activité.id_Type";
        ResultSet result = get_Donnees(req, donnees);

        try {

            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            int nblignes = getNbacti(param);
            txt = new String[nblignes][nbCols];

            boolean encore = result.next();

            for (int j = 1; j <= nblignes; j++) {
                for (int i = 1; i <= nbCols; i++)
                    txt[j - 1][i - 1] = (result.getString(i));
                result.next();
            }
            result.close();

            return txt;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Récupération de l'historique de l'utilisateur (10 dernières activités)
     *
     * @return Tableau de String 10x4 sous la forme (Activité, Date, Distance, Temps)
     */
    private static String[][] getHistory(String[] param) {
        String[] donnees = {param[0]};
        String[][] txt = new String[10][4];

        String req = "SELECT nom,`date_Activité`,`nb_Km`,`temps`\n" +
                "FROM `type_activité`\n" +
                "INNER JOIN activité ON `type_activité`.`id_Type`=activité.id_Type\n" +
                "WHERE activité.login=? \n" +
                "ORDER BY `activité`.`date_Activité` DESC\n" +
                "LIMIT 10";
        ResultSet result = get_Donnees(req, donnees);

        try {
            int j = 0;
            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            boolean encore = result.next();

            while (encore) {
                for (int i = 1; i <= nbCols; i++)
                    txt[j][i - 1] = result.getString(i);
                encore = result.next();
                j++;
            }
            result.close();

            return txt;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Récupération des 6 dernieres sorties pour l'activité donnée
     * param lastACtivity String activité donnée
     *
     * @return Tableau de double (temps, distance).
     */
    private static Double[][] getLastActivityRecap(String[] param) {
        String[] donnees = {param[0], param[1]};
        Double[][] txt = new Double[6][2];

        String req = "SELECT `nb_Km`,`temps`\n" +
                "FROM `type_activité`\n" +
                "INNER JOIN activité ON `type_activité`.`id_Type`=activité.id_Type\n" +
                "WHERE activité.login=? AND nom=?\n" +
                "ORDER BY `activité`.`date_Activité` DESC\n" +
                "LIMIT 6";
        ResultSet result = get_Donnees(req, donnees);

        try {
            int j = 0;
            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            boolean encore = result.next();

            while (encore) {
                for (int i = 1; i <= nbCols; i++)
                    txt[j][i - 1] = result.getDouble(i);
                encore = result.next();
                j++;
            }
            result.close();

            return txt;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Permet de recup le total du nb d'acti d'un user donnée
     *
     * @param param : user
     * @return : nb tot d'acti
     */
    private static int getNbActi(String[] param) {

        String[] donnees = {param[0]};
        String req = "SELECT COUNT(DISTINCT id_Activité) FROM `activité` WHERE login = ?";
        ResultSet result = get_Donnees(req, donnees);
        int tot = 0;

        try {
            result.next();
            tot = result.getInt(1);
            result.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tot;
    }


    /**
     * Récupération de toutes les activités de l'utilisateur
     *
     * @return Tableau de String (Activité, date, id)
     */

    private static String[][] getAllActivities(String[] param) {
        String[] donnees = {param[0]};
        String[][] txt = new String[getNbActi(param)][3];

        String req = "SELECT nom,date_Activité,id_Activité\n" +
                "FROM `activité`\n" +
                "INNER JOIN type_activité ON `activité`.`id_Type`=type_activité.id_Type\n" +
                "WHERE login = ?" +
                "ORDER BY `activité`.`date_Activité` DESC";

        ResultSet result = get_Donnees(req, donnees);

        try {
            int j = 0;
            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            boolean encore = result.next();

            while (encore) {
                for (int i = 1; i <= nbCols; i++)
                    txt[j][i - 1] = result.getString(i);
                encore = result.next();
                j++;
            }
            result.close();

            return txt;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private static String getActivityInfo(String[] param) {

        String[] donnees = {param[0],param[1]};
        String req = "SELECT nom,nb_Km,temps,description\n" +
                "    FROM `activité`\n" +
                "    INNER JOIN type_activité ON `activité`.`id_Type`=type_activité.id_Type\n" +
                "    WHERE login = ? AND id_Activité=?";
        ResultSet result = get_Donnees(req, donnees);
        String txt = "";


        try
        {
            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            boolean encore = result.next();

            while (encore) {
                for (int i = 1; i <= nbCols; i++) {
                    txt += (result.getString(i) + ";");
                }
                encore = result.next();
            }
            result.close();
        } catch(
        SQLException e)
        {
            System.err.println(e.getMessage());
            //System.exit(99);
        }
        return txt;
    }


    /**
     * Récupération du nombre des differents sports pratiqué
     * @return Tableau de String (Type d'activité, nobre de fois pratiqué)
     */
    private static String[][] getNbActivity(String[] param){
        String[] donnees = {param[0]};
        String[][] txt ;

        String req = "SELECT type_activité.nom,count(*)\n" +
                "FROM `activité`\n" +
                "INNER JOIN type_activité ON activité.`id_Type` = type_activité.`id_Type`\n" +
                "WHERE login=?\n" +
                "GROUP BY type_activité.nom";
        ResultSet result = get_Donnees(req,donnees);

        try {

            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            int nblignes = getNbacti(param);
            txt = new String[nblignes][nbCols];

            boolean encore = result.next();

            for (int j = 1; j <= nblignes; j++){
                for (int i = 1; i <= nbCols; i++)
                    txt[j-1][i-1]=(result.getString(i));
                result.next();
            }
            result.close();

            return txt;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Récupération du nombre d'activité pratiquée par semaine en moyenne
     * @return String nombre d'activité moyenne par semaine
     */
    private static String getMoyenneActivityPerWeek(String[] param){
        String[] donnees = {param[0],param[0]};
        String req = "SELECT ROUND(count(*)/(\n" +
                "SELECT DATEDIFF(NOW(),MIN(date_Activité))/7\n" +
                "FROM `activité`\n" +
                "WHERE login=?) , 2)\n" +
                "FROM `activité`\n" +
                "WHERE login=?";
        ResultSet result = get_Donnees(req,donnees);
        String txt = "";

        try {
            ResultSetMetaData rsmd = result.getMetaData();
            int nbCols = rsmd.getColumnCount();
            boolean encore = result.next();

            while (encore) {
                for (int i = 1; i <= nbCols; i++)
                    txt+=(result.getString(i));
                encore = result.next();
            }
            result.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return txt;
    }


    /**
     * Fonction d'appel SQL - GETTER
     * @param req : la requete
     * @param param : les elements qui contributs a la requete
     * @return un set de resultat formé au cas par cas
     */
    public static ResultSet get_Donnees(String req, String[] param) {
        Connection con = null;
        ResultSet result = null;


        // chargement du pilote
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("error : connection");
            System.err.println(e);
        }
        //connection a la base de données

        try {
            String DBurl = "jdbc:mysql://localhost:3306/alpha";
            con = DriverManager.getConnection(DBurl,"root","");
        } catch (SQLException e) {
            System.err.println("Connection à la base de données impossible");
        }



        try {
            PreparedStatement stmt = con.prepareStatement(req);
            for(int i=0; i< param.length;i++)
                stmt.setString((i+1), param[i]);

            //Statement stmt = con.createStatement();
            result = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println(e);
            System.err.println("Anomalie lors de l'execution de la requête");
            //System.exit(99);
        }

        return result;


    }

    /*
    *
    * LES SETTERS
    *
     */

    /**
     * Creation d'un compte utilisateur
     * param username String nom de l'utilisateur
     * param pwd String mot de passe hashé de l'utilisateur
     * param country String pays
     * param region String region
     * param city String ville
     * param weight Float poids
     * param birthdate String date de naissance
     * param size Float taille
     * param sexe String sexe
     * param level String niveau
     * @return 1 si création OK sinon 0 et -1 si probleme
     */
    private static boolean set_createAccount(String[] param) {
        int maj = 0;
        String[] donnees = {param[2],param[3],param[4],param[4]};
        System.out.println("row maj :"+ Arrays.toString(donnees));
        String req = "INSERT INTO `adresse` (`pays`,`region`,`ville`)\n" +
                "SELECT ?,?,? \n" +
                "WHERE NOT EXISTS (\n" +
                "    SELECT * FROM `adresse` WHERE `ville` =? LIMIT 1\n" +
                ");\n";
        set_Donnees(req,donnees);

        String[] donnees2 = {param[0],param[1],param[5],param[6],param[7],param[8],param[9],param[4],param[0] };
        req = "INSERT INTO `utilisateur` (`login`,`mdp`,`poids`,`age`,`taille`,`sexe`,`niveau`,`id_Adresse`)\n" +
                "SELECT ?,?,?,?,?,?,?,(SELECT `id_Adresse` FROM `adresse` WHERE `ville`=?)\n" +
                "WHERE NOT EXISTS (\n" +
                "    SELECT * FROM `utilisateur` WHERE `login` =? LIMIT 1\n" +
                ");";
        maj = set_Donnees(req,donnees2);

        System.out.println("row maj :"+maj);
        return (maj>0);

    }


    /**
     * Ajout d'une activité dans la base de données
     * param log user
     * param activity String activité
     * param distanceSpinner Float distance
     * param heureSpinner int Heure
     * param minuteSpinner int Minute
     * param secondeSpinner int Seconde
     * param textComment String commentaire
     * return true si ajout OK sinon false
     */
    private static boolean set_addActivity(String[] param){
        String[] donnees = {param[5],param[4],param[2],param[3],param[1],param[0]};
        int maj = 0;
        String req = "INSERT INTO `activité` (`description`,`date_Activité`,`nb_Km`,`temps`,`id_Type`,`login`)\n" +
                "SELECT ?,?,?,?,\n" +
                "(SELECT `id_Type` FROM `type_activité` WHERE `nom`=?),?";
        maj = set_Donnees(req,donnees);

        System.out.println("row maj :"+maj);
        return (maj>0);
    }


    /**
     * Modification des données d'un utilisateur
     * param pwd String mot de passe hashé
     * param country String pays
     * param region String région
     * param city String ville
     * param weight int poids
     * param height int taille
     * param sexe String sexe
     * param level String niveau
     * @return true si modification OK sinon false
     */
    private static boolean set_data(String[] param) {
        int maj = 0;
        String adr;
        String[] donnees = {param[6], param[7], param[8], param[8]};
        String req2;

        System.out.println("row maj :" + Arrays.toString(donnees));
        String req = "INSERT INTO `adresse` (`pays`,`region`,`ville`)\n" +
                "SELECT ?,?,? \n" +
                "WHERE NOT EXISTS (\n" +
                "    SELECT * FROM `adresse` WHERE `ville` =? LIMIT 1\n" +
                ");\n";
        set_Donnees(req, donnees);

        String[] donnees2 = {param[1], param[2], param[3], param[4], param[5], param[8], param[0]};
            req2 = "UPDATE `utilisateur` SET `mdp` = ?, `poids` = ?, `taille` = ?, `sexe` = ?, `niveau` = ?,\n" +
                    "`id_Adresse` = (SELECT id_Adresse FROM `adresse` WHERE `ville`= ?) \n" +
                    "WHERE `utilisateur`.`login` = ?";
            maj = set_Donnees(req2, donnees2);


        System.out.println("row maj :"+maj);
        return (maj>0);

    }


    /**
     * Fonction d'appel SQL - SETTER
     * @param req : la requete
     * @param param : les elements qui contributs a la requete
     * @return un int en fonction de la situation
     */
    private static int set_Donnees(String req,String[] param) {
        Connection con = null;

        // chargement du pilote
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("error : connection");
            System.err.println(e);
        }

        //connection a la base de données
        try {
            String DBurl = "jdbc:mysql://localhost:3306/alpha";
            con = DriverManager.getConnection(DBurl,"root","");
        } catch (SQLException e) {
            System.err.println("Connection à la base de données impossible");
        }

        try {
            //Statement stmt = con.createStatement();
            PreparedStatement preparedStmt = con.prepareStatement(req);
            for(int i=0; i< param.length;i++)
                preparedStmt.setString((i+1), param[i]);

            return(preparedStmt.executeUpdate());

        } catch (SQLException e) {
            System.err.println(e);
            System.err.println("Anomalie lors de l'execution de la requête");
        }
        return -1;

    }

}