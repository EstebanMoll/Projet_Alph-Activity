import java.sql.*;

public class TestJDBC1 {


    public static String test(String req) {
        //System.out.println("OK");
        Connection con = null;
        ResultSet résultats = null;
        String requete = "";
        String txt ="";


        // chargement du pilote
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("error : connection");
            System.err.println(e);
            //System.exit(99);
        }

        //connection a la base de données

        try {
            String DBurl = "jdbc:mysql://localhost:3306/alpha";
            con = DriverManager.getConnection(DBurl,"root","");
        } catch (SQLException e) {
            System.err.println("Connection à la base de données impossible");
            //System.exit(99);
        }


        //creation et execution de la requete
        requete = "SELECT AVG(nb_Km) as \"Moyenne_dist\",AVG(temps)/60 as \"Moyenne_tmps\",AVG(nb_Km)/(AVG(temps)/(60*60)) as \"Moyenne vit\"\n" +
                "FROM `activité`  \n" +
                "WHERE id_Type=5";

        try {
            Statement stmt = con.createStatement();
            résultats = stmt.executeQuery(req);
        } catch (SQLException e) {
            System.err.println("Anomalie lors de l'execution de la requête");
            //System.exit(99);
        }


        //parcours des données retournées
        try {

            ResultSetMetaData rsmd = résultats.getMetaData();
            int nbCols = rsmd.getColumnCount();
            boolean encore = résultats.next();

            while (encore) {
                for (int i = 1; i <= nbCols; i++)
                    txt+=(résultats.getString(i) + ";");
                encore = résultats.next();
            }

            résultats.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            //System.exit(99);
        }

        return txt;
    }

}