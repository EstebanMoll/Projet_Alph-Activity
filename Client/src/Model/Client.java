package Model
;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
     * @return String de toutes les activités séparées par des points virgules
     */
    public String getActivities()
    {
        return "Course à pied;Marche;Vélo;VTT;Aviron";
    }
}