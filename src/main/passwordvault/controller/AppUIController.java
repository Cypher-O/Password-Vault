package main.passwordvault.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AppUIController {

    @FXML
    private AnchorPane anchorPane;

    private VaultUIController vaultUIController;
    private RegisterLoginPanelController registerLoginPanelController;

    @FXML
    public void initialize() {
        try {
            // Load VaultUI.fxml
            FXMLLoader vaultLoader = new FXMLLoader(getClass().getResource("/fxml/VaultUI.fxml"));
            VBox vaultUI = vaultLoader.load();
            vaultUIController = vaultLoader.getController();

            // Load RegisterLoginPanel.fxml
            FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("/fxml/RegisterLoginPanel.fxml"));
            VBox registerLoginPanel = registerLoader.load();
            registerLoginPanelController = registerLoader.getController();
            registerLoginPanelController.setAppUIController(this);

            // Initialize stackPane after FXML loading
            if (anchorPane != null) {
                anchorPane.getChildren().add(registerLoginPanel);
            } else {
                throw new IllegalStateException("FXML file is not configured correctly. Check the 'stackPane' element and @FXML annotation.");
            }

            // Show RegisterLoginPanel initially
            showRegisterLoginPanel();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML files.", e);
        }
    }

    @FXML
    public void showVault() {
        if (anchorPane != null) {
            anchorPane.getChildren().clear(); // Clear any existing content
            anchorPane.getChildren().add(vaultUIController.getRoot()); // Add VaultUI.fxml content
        } else {
            throw new IllegalStateException("FXML file is not configured correctly. Check the 'stackPane' element and @FXML annotation.");
        }
    }

    @FXML
    public void showRegisterLoginPanel() {
        if (anchorPane != null) {
            anchorPane.getChildren().clear(); // Clear any existing content
            anchorPane.getChildren().add(registerLoginPanelController.getRoot()); // Add RegisterLoginPanel.fxml content
        } else {
            throw new IllegalStateException("FXML file is not configured correctly. Check the 'stackPane' element and @FXML annotation.");
        }
    }
}



//public class AppUIController {
//    @FXML
//    private StackPane stackPane;
//
//    private VaultUIController vaultUIController;
//    private RegisterLoginPanelController registerLoginPanelController;
//
//    @FXML
//    public void initialize() {
//        try {
//            FXMLLoader vaultLoader = new FXMLLoader(getClass().getResource("fxml/VaultUI.fxml"));
//            StackPane vaultUI = null;
//            try {
//                vaultUI = vaultLoader.load();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            vaultUIController = vaultLoader.getController();
//
//            FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("fxml/RegisterLoginPanel.fxml"));
//            StackPane registerLoginPanel = registerLoader.load();
//            registerLoginPanelController = registerLoader.getController();
//            registerLoginPanelController.setAppUIController(this);
//
//            stackPane.getChildren().addAll(registerLoginPanel, vaultUI);
//
//            showRegisterLoginPanel();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void showVault() {
//        registerLoginPanelController.getRoot().setVisible(false);
//        vaultUIController.getRoot().setVisible(true);
//    }
//
//    public void showRegisterLoginPanel() {
//        registerLoginPanelController.getRoot().setVisible(true);
//        vaultUIController.getRoot().setVisible(false);
//    }
//}
