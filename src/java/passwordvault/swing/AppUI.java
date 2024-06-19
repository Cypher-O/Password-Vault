//import javax.swing.*;
//import java.awt.*;
//
//public class AppUI extends JFrame {
//    private CardLayout cardLayout;
//    private JPanel cardPanel;
//    private RegisterLoginPanel registerLoginPanel;
//    private VaultUI vaultUI;
//
//    public AppUI() {
//        setTitle("Password Vault Application");
//        setSize(600, 500);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        cardLayout = new CardLayout();
//        cardPanel = new JPanel(cardLayout);
//
//        registerLoginPanel = new RegisterLoginPanel(this);
//        vaultUI = new VaultUI();
//
//        cardPanel.add(registerLoginPanel, "RegisterLogin");
//        cardPanel.add(vaultUI, "Vault");
//
//        add(cardPanel);
//        cardLayout.show(cardPanel, "RegisterLogin");
//    }
//
//    public void showVault() {
//        cardLayout.show(cardPanel, "Vault");
//    }
//}



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AppUI extends Application {
    private StackPane stackPane;
    private VaultUI vaultUI;
    private RegisterLoginPanel registerLoginPanel;

    @Override
    public void start(Stage primaryStage) {
        stackPane = new StackPane();

        registerLoginPanel = new RegisterLoginPanel(this);
        vaultUI = new VaultUI();

        stackPane.getChildren().addAll(registerLoginPanel, vaultUI);

        Scene scene = new Scene(stackPane, 600, 500);
        primaryStage.setTitle("Password Vault Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        showRegisterLoginPanel();
    }

    public void showVault() {
        registerLoginPanel.setVisible(false);
        vaultUI.setVisible(true);
    }

    public void showRegisterLoginPanel() {
        registerLoginPanel.setVisible(true);
        vaultUI.setVisible(false);
    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}
