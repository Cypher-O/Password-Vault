package java.passwordvault.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class VaultUIController {
    @FXML
    private VBox root;
    @FXML
    private TextField siteField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextArea vaultTextArea;
    @FXML
    private Button toggleButton;

    private boolean isPasswordVisible = false;
    private Map<String, String> passwordVault;
    private static final String VAULT_FILE = "vault.dat";
    private static final String KEY_FILE = "key.dat";
    private SecretKey secretKey;

    @FXML
    public void initialize() {
        passwordVault = new HashMap<>();
        loadKey();
        loadVault();
        displayVault();

        toggleButton.setOnAction(e -> togglePasswordVisibility());
    }

    @FXML
    private void addOrUpdatePassword() {
        String site = siteField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (site.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all fields");
            return;
        }

        String encryptedPassword = encryptPassword(password);
        passwordVault.put(site, username + ":" + encryptedPassword);
        saveVault();
        displayVault();
    }

    private void displayVault() {
        if (passwordVault.isEmpty()) {
            vaultTextArea.setText("Nothing to show here.");
        } else {
            vaultTextArea.setText("");
            for (Map.Entry<String, String> entry : passwordVault.entrySet()) {
                String site = entry.getKey();
                String[] parts = entry.getValue().split(":");
                String username = parts[0];
                String password = decryptPassword(parts[1]);

                vaultTextArea.appendText("Site: " + site + "\n");
                vaultTextArea.appendText("Username: " + username + "\n");
                vaultTextArea.appendText("Password: " + password + "\n\n");
            }
        }
    }

    private void saveVault() {
        try (FileWriter writer = new FileWriter(VAULT_FILE)) {
            for (Map.Entry<String, String> entry : passwordVault.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error saving vault");
        }
    }

    @FXML
    private void loadVault() {
        passwordVault.clear();
        try {
            Files.lines(Paths.get(VAULT_FILE)).forEach(line -> {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    passwordVault.put(parts[0], parts[1]);
                }
            });
        } catch (IOException e) {
            // File might not exist on first run, which is fine
        }
        displayVault();
    }

    private void loadKey() {
        try {
            if (new File(KEY_FILE).exists()) {
                byte[] keyBytes = Files.readAllBytes(Paths.get(KEY_FILE));
                secretKey = new SecretKeySpec(Base64.getDecoder().decode(keyBytes), "AES");
            } else {
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(128);
                secretKey = keyGen.generateKey();
                try (FileWriter writer = new FileWriter(KEY_FILE)) {
                    writer.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
                }
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error loading encryption key");
        }
    }

    private String encryptPassword(String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error encrypting password");
        }
        return null;
    }

    private String decryptPassword(String encryptedPassword) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decryptedBytes);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error decrypting password");
        }
        return null;
    }

    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;
        displayVault();
        toggleButton.setText(isPasswordVisible ? "Hide Passwords" : "Show Passwords");
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }

    public VBox getRoot() {
        return root;
    }
}
