package java.passwordvault.swing;//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//public class RegisterLoginPanel extends JPanel {
//    private AppUI appUI;
//    private JTextField usernameField;
//    private JPasswordField passwordField;
//    private JButton toggleButton;
//    private boolean isPasswordVisible;
//
//    private static final Map<String, String> userDatabase = new HashMap<>();
//
//    public RegisterLoginPanel(AppUI appUI) {
//        this.appUI = appUI;
//        setLayout(new GridLayout(4, 2, 10, 10));
//
//        JLabel usernameLabel = new JLabel("Username:");
//        usernameField = new JTextField();
//        JLabel passwordLabel = new JLabel("Password:");
//        passwordField = new JPasswordField();
//        toggleButton = new JButton("Show");
//        isPasswordVisible = false;
//
//        JButton registerButton = new JButton("Register");
//        JButton loginButton = new JButton("Login");
//
//        add(usernameLabel);
//        add(usernameField);
//        add(passwordLabel);
//        add(passwordField);
//        add(new JLabel(""));
//        add(toggleButton);
//        add(registerButton);
//        add(loginButton);
//
//        registerButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                registerUser();
//            }
//        });
//
//        loginButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                loginUser();
//            }
//        });
//
//        toggleButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                togglePasswordVisibility();
//            }
//        });
//    }
//
//    private void registerUser() {
//        String username = usernameField.getText();
//        String password = new String(passwordField.getPassword());
//        if (username.isEmpty() || password.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        if (userDatabase.containsKey(username)) {
//            JOptionPane.showMessageDialog(this, "User already exists", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        userDatabase.put(username, Base64.getEncoder().encodeToString(password.getBytes()));
//        JOptionPane.showMessageDialog(this, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    private void loginUser() {
//        String username = usernameField.getText();
//        String password = new String(passwordField.getPassword());
//        if (username.isEmpty() || password.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        if (userDatabase.containsKey(username)) {
//            String storedPassword = new String(Base64.getDecoder().decode(userDatabase.get(username)));
//            if (password.equals(storedPassword)) {
//                JOptionPane.showMessageDialog(this, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
//                appUI.showVault();
//            } else {
//                JOptionPane.showMessageDialog(this, "Invalid password", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "User does not exist", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void togglePasswordVisibility() {
//        if (isPasswordVisible) {
//            passwordField.setEchoChar('*');
//            toggleButton.setText("Show");
//        } else {
//            passwordField.setEchoChar((char) 0);
//            toggleButton.setText("Hide");
//        }
//        isPasswordVisible = !isPasswordVisible;
//    }
//}


//
//import javafx.geometry.Insets;
//import javafx.scene.control.*;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;
//
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//public class RegisterLoginPanel extends VBox {
//    private AppUI appUI;
//    private TextField usernameField;
//    private PasswordField passwordField;
//    private Button toggleButton;
//    private boolean isPasswordVisible = false;
//
//    private static final Map<String, String> userDatabase = new HashMap<>();
//
//    public RegisterLoginPanel(AppUI appUI) {
//        this.appUI = appUI;
//        usernameField = new TextField();
//        passwordField = new PasswordField();
//        toggleButton = new Button("Show");
//
//        GridPane grid = new GridPane();
//        grid.setPadding(new Insets(10));
//        grid.setVgap(5);
//        grid.setHgap(5);
//
//        grid.add(new Label("Username:"), 0, 0);
//        grid.add(usernameField, 1, 0);
//
//        grid.add(new Label("Password:"), 0, 1);
//        grid.add(passwordField, 1, 1);
//
//        grid.add(new Label(""), 0, 2);
//        grid.add(toggleButton, 1, 2);
//
//        Button registerButton = new Button("Register");
//        Button loginButton = new Button("Login");
//
//        grid.add(registerButton, 0, 3);
//        grid.add(loginButton, 1, 3);
//
//        registerButton.setOnAction(e -> registerUser());
//        loginButton.setOnAction(e -> loginUser());
//        toggleButton.setOnAction(e -> togglePasswordVisibility());
//
//        getChildren().add(grid);
//    }
//
//    private void registerUser() {
//        String username = usernameField.getText();
//        String password = passwordField.getText();
//        if (username.isEmpty() || password.isEmpty()) {
//            showAlert(Alert.AlertType.ERROR, "Please fill all fields");
//            return;
//        }
//        if (userDatabase.containsKey(username)) {
//            showAlert(Alert.AlertType.ERROR, "User already exists");
//            return;
//        }
//        userDatabase.put(username, Base64.getEncoder().encodeToString(password.getBytes()));
//        showAlert(Alert.AlertType.INFORMATION, "Registration successful");
//    }
//
//    private void loginUser() {
//        String username = usernameField.getText();
//        String password = passwordField.getText();
//        if (username.isEmpty() || password.isEmpty()) {
//            showAlert(Alert.AlertType.ERROR, "Please fill all fields");
//            return;
//        }
//        if (userDatabase.containsKey(username)) {
//            String storedPassword = new String(Base64.getDecoder().decode(userDatabase.get(username)));
//            if (password.equals(storedPassword)) {
//                showAlert(Alert.AlertType.INFORMATION, "Login successful");
//                appUI.showVault();
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Invalid password");
//            }
//        } else {
//            showAlert(Alert.AlertType.ERROR, "User does not exist");
//        }
//    }
//
//    private void togglePasswordVisibility() {
//        isPasswordVisible = !isPasswordVisible;
//        passwordField.setPromptText(passwordField.getText());
//        passwordField.clear();
//        passwordField.setPromptText(isPasswordVisible ? passwordField.getPromptText() : null);
//        toggleButton.setText(isPasswordVisible ? "Hide" : "Show");
//    }
//
//    private void showAlert(Alert.AlertType alertType, String message) {
//        Alert alert = new Alert(alertType);
//        alert.setContentText(message);
//        alert.show();
//    }
//}
