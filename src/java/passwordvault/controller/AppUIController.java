package java.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class AppUIController {
    @FXML
    private StackPane stackPane;

    private VaultUIController vaultUIController;
    private RegisterLoginPanelController registerLoginPanelController;

    @FXML
    public void initialize() {
        try {
            FXMLLoader vaultLoader = new FXMLLoader(getClass().getResource("/fxml/VaultUI.fxml"));
            StackPane vaultUI = null;
            try {
                vaultUI = vaultLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            vaultUIController = vaultLoader.getController();

            FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("/fxml/RegisterLoginPanel.fxml"));
            StackPane registerLoginPanel = registerLoader.load();
            registerLoginPanelController = registerLoader.getController();
            registerLoginPanelController.setAppUIController(this);

            stackPane.getChildren().addAll(registerLoginPanel, vaultUI);

            showRegisterLoginPanel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showVault() {
        registerLoginPanelController.getRoot().setVisible(false);
        vaultUIController.getRoot().setVisible(true);
    }

    public void showRegisterLoginPanel() {
        registerLoginPanelController.getRoot().setVisible(true);
        vaultUIController.getRoot().setVisible(false);
    }
}
