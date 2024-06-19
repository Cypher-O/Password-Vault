package main.passwordvault.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RegisterLoginPanelController {

    private AppUIController appUIController;

    @FXML
    private StackPane root;

//    @FXML
//    private VBox root;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button toggleButton;

    private boolean isPasswordVisible = false;
    private static final Map<String, String> userDatabase = new HashMap<>();

    @FXML
    private void initialize() {
        // Initialization code if necessary
    }

    @FXML
    private void registerUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all fields");
            return;
        }
        if (userDatabase.containsKey(username)) {
            showAlert(Alert.AlertType.ERROR, "User already exists");
            return;
        }
        userDatabase.put(username, Base64.getEncoder().encodeToString(password.getBytes()));
        showAlert(Alert.AlertType.INFORMATION, "Registration successful");
    }

    @FXML
    private void loginUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all fields");
            return;
        }
        if (userDatabase.containsKey(username)) {
            String storedPassword = new String(Base64.getDecoder().decode(userDatabase.get(username)));
            if (password.equals(storedPassword)) {
                showAlert(Alert.AlertType.INFORMATION, "Login successful");
                // appUI.showVault(); // Integrate this with your AppUI logic
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid password");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "User does not exist");
        }
    }

    @FXML
    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;
        passwordField.setPromptText(passwordField.getText());
        passwordField.clear();
        passwordField.setPromptText(isPasswordVisible ? passwordField.getPromptText() : null);
        toggleButton.setText(isPasswordVisible ? "Hide" : "Show");
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }


    public void setAppUIController(AppUIController appUIController) {
        this.appUIController = appUIController;
    }

    public StackPane getRoot() {
        return root;
    }
}
