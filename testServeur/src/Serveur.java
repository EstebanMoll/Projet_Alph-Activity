import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Serveur {
    private static final int port = 9999;
    private static ServerSocket s;
    private static final String SEPARATEUR = "/";


    public static void main(String[] args) throws Exception {
        s = new ServerSocket(port);
        appelServ();
    }


    public static void appelServ() throws Exception {

        System.out.println("Socket serveur: " + s);

        Socket soc = s.accept(); //bloquante en attendant l'acces

        System.out.println("Serveur a accepte connexion: " + soc);

        ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
        out.flush();

        ObjectInputStream in = new ObjectInputStream(soc.getInputStream());


        System.out.println("Serveur a cree les flux");

        Object objetRecu = in.readObject();
        String root = ""+ objetRecu;


        String demande = root.substring(0,root.indexOf('/'));   //la demande
        String param[] = root.substring(root.indexOf('/')+1).split(SEPARATEUR); //les parametres


        System.out.println("root :" + root);
        System.out.println("demande :" + demande);

        System.out.println("param :" );
        for (int i = 0; i < param.length; i++) {
            System.out.println(param[i]);
        }
        Object retour;


        switch(demande) {
            case "get.login":
                retour = get_login(param);
                break;
            case "set.createAccount":
                retour = set_createAccount(param);
                break;
            default:
                retour = "ERROR";
        }

        out.writeObject(retour);
        out.flush();
        System.out.println("     return : "+retour);



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
    private static boolean get_login(String[] param){ //test une connexion, return bool
        String req = "SELECT count(*) FROM `utilisateur` WHERE `login` =\""+param[0]+"\" AND`mdp`= \""+param[1]+"\"";
        ResultSet retour = get_Donnees(req);
        boolean result = false;
        try {
            if(retour.next())
                result = retour.getString(1).equals("1");

            retour.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            //System.exit(99);
        }
        return result;
    }





    public static ResultSet get_Donnees(String req) {
        System.out.println(req);
        Connection con = null;
        ResultSet result = null;
        String txt ="";


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
            Statement stmt = con.createStatement();
            result = stmt.executeQuery(req);
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
    private static boolean set_createAccount(String[] param) {
        int maj = 0;
        String req = "INSERT INTO `adresse` (`pays`,`region`,`ville`)\n" +
                "SELECT \""+param[2]+"\",\""+param[3]+"\",\""+param[4]+"\" \n" +
                "WHERE NOT EXISTS (\n" +
                "    SELECT * FROM `adresse` WHERE `ville` =\""+param[4]+"\" LIMIT 1\n" +
                ");\n";
        set_Donnees(req);


        req = "INSERT INTO `utilisateur` (`login`,`mdp`,`poids`,`age`,`taille`,`sexe`,`niveau`,`id_Adresse`)\n" +
                "SELECT \""+param[0]+"\",\""+param[1]+"\","+param[5]+","+param[6]+","+param[7]+","+param[8]+","+param[9]+",10\n" +
                "WHERE NOT EXISTS (\n" +
                "    SELECT * FROM `utilisateur` WHERE `login` =\""+param[0]+"\" LIMIT 1\n" +
                ");";
        maj = set_Donnees(req);

        System.out.println("row maj :"+maj);
        return (maj>0);

    }

    private static int set_Donnees(String req) {
        System.out.println(req);
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
            return(preparedStmt.executeUpdate());


        } catch (SQLException e) {
            System.err.println(e);
            System.err.println("Anomalie lors de l'execution de la requête");
            //System.exit(99);
        }
        return -1;

    }

}