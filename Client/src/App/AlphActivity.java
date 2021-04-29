package App;

import Model.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlphActivity extends Application {

   public static Client client = new Client();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
        primaryStage.setTitle("Alph'Activity");
        primaryStage.getIcons().add(new Image(AlphActivity.class.getResourceAsStream("/ressources/Logo.png")));
        Scene scene = new Scene(root, 350, 540);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
