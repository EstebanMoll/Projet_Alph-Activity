import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Serveur {
    public static final int port = 9999;
    public static ServerSocket s;
    public static TestJDBC1 bdd;


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

        System.out.println("Serveur recoit: " + root);
        String demande = root;
        String retour;

        retour = get_stat(0);
/*
        switch(demande) {
            case "get/stat_Sport":
                retour = get_stat(0);
                break;
            default:
                retour = "ERROR";
        }
*/
        out.writeObject(retour);
        out.flush();
        System.out.println("Serveur: donnees emises");



        in.close();
        out.close();
        // soc.close();
        appelServ();
    }

    private static String get_stat(int id_Sport){ //return moyennes dist,tmps,vit de tout les users d'un sport donné
        String t = "SELECT AVG(nb_Km) as \"Moyenne_dist\",AVG(temps)/60 as \"Moyenne_tmps\",AVG(nb_Km)/(AVG(temps)/(60*60)) as \"Moyenne vit\" " +
                "FROM `activité` " +
                "WHERE id_Type=5";
        return bdd.test(t);
    }
}