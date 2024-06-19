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
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppUI.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Password Vault Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
