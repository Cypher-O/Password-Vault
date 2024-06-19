//import javax.swing.*;
////
////public class Main {
////    public static void main(String[] args) {
////        System.out.println("Hello world!");
////        SwingUtilities.invokeLater(() -> {
//////            VaultUI vaultUI = new VaultUI();
//////            vaultUI.setVisible(true);
////            AppUI appUI = new AppUI();
////            appUI.setVisible(true);
////        });
////    }
////}

//public class Main {
//    public static void main(String[] args) {
//        AppUI.main(args);
//    }
//}


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/AppUI.fxml"));
            primaryStage.setTitle("Password Vault");
            primaryStage.setScene(new Scene(root));

            // Load application icon
            primaryStage.getIcons().add(new Image("/resources/images/privacy_lock.png"));

//            primaryStage.setMinWidth(600);
//            primaryStage.setMinHeight(400);
            primaryStage.setWidth(600);
            primaryStage.setHeight(500);
            primaryStage.show();

            // Center the stage on screen after it's shown
            primaryStage.centerOnScreen();

            // Center the stage more precisely using Screen bounds
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
            primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);

        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
