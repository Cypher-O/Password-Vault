//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//
//public class VaultUI extends JFrame {
//    private JTextField siteField;
//    private JTextField usernameField;
//    private JPasswordField passwordField;
//    private JTextArea vaultTextArea;
//
//    private Map<String, String> passwordVault;
//    private static final String VAULT_FILE = "vault.dat";
//    private static final String KEY_FILE = "key.dat";
//    private SecretKey secretKey;
//
//    public VaultUI() {
//        setTitle("Password Vault");
//        setSize(500, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(6, 2));
//
//        panel.add(new JLabel("Site:"));
//        siteField = new JTextField();
//        panel.add(siteField);
//
//        panel.add(new JLabel("Username:"));
//        usernameField = new JTextField();
//        panel.add(usernameField);
//
//        panel.add(new JLabel("Password:"));
//        passwordField = new JPasswordField();
//        panel.add(passwordField);
//
//        JButton addButton = new JButton("Add/Update");
//        addButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                addOrUpdatePassword();
//            }
//        });
//        panel.add(addButton);
//
//        JButton loadButton = new JButton("Load Vault");
//        loadButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                loadVault();
//            }
//        });
//        panel.add(loadButton);
//
//        vaultTextArea = new JTextArea();
//        vaultTextArea.setEditable(false);
//        JScrollPane scrollPane = new JScrollPane(vaultTextArea);
//
//        add(panel, BorderLayout.NORTH);
//        add(scrollPane, BorderLayout.CENTER);
//
//        // Initialize password vault and load existing vault
//        passwordVault = new HashMap<>();
//        loadVault();
//        loadKey();
//
//        // Display vault contents after UI components are fully initialized
//        SwingUtilities.invokeLater(() -> displayVault());
//    }
//
//    private void addOrUpdatePassword() {
//        String site = siteField.getText();
//        String username = usernameField.getText();
//        String password = new String(passwordField.getPassword());
//
//        if (site.isEmpty() || username.isEmpty() || password.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        String encryptedPassword = encryptPassword(password);
//        passwordVault.put(site, username + ":" + encryptedPassword);
//        saveVault();
//        displayVault();
//    }
//
//    private void displayVault() {
//        vaultTextArea.setText("");
//        for (Map.Entry<String, String> entry : passwordVault.entrySet()) {
//            String site = entry.getKey();
//            String[] parts = entry.getValue().split(":");
//            String username = parts[0];
//            String password = decryptPassword(parts[1]);
//
//            vaultTextArea.append("Site: " + site + "\n");
//            vaultTextArea.append("Username: " + username + "\n");
//            vaultTextArea.append("Password: " + password + "\n\n");
//        }
//    }
//
//    private void saveVault() {
//        try (FileWriter writer = new FileWriter(VAULT_FILE)) {
//            for (Map.Entry<String, String> entry : passwordVault.entrySet()) {
//                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
//            }
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "Error saving vault", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void loadVault() {
//        passwordVault.clear();
//        try {
//            Files.lines(Paths.get(VAULT_FILE)).forEach(line -> {
//                String[] parts = line.split("=");
//                if (parts.length == 2) {
//                    passwordVault.put(parts[0], parts[1]);
//                }
//            });
//        } catch (IOException e) {
//            // File might not exist on first run, which is fine
//        }
//        displayVault();
//    }
//
//    private void loadKey() {
//        try {
//            if (new File(KEY_FILE).exists()) {
//                byte[] keyBytes = Files.readAllBytes(Paths.get(KEY_FILE));
//                secretKey = new SecretKeySpec(keyBytes, "AES");
//            } else {
//                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//                keyGen.init(128);
//                secretKey = keyGen.generateKey();
//                try (FileWriter writer = new FileWriter(KEY_FILE)) {
//                    writer.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
//                }
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error loading encryption key", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private String encryptPassword(String password) {
//        try {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
//            return Base64.getEncoder().encodeToString(encryptedBytes);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error encrypting password", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//        return null;
//    }
//
//    private String decryptPassword(String encryptedPassword) {
//        try {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
//            return new String(decryptedBytes);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error decrypting password", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//        return null;
//    }
//}



//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//public class VaultUI extends JPanel {
//    private JTextField siteField;
//    private JTextField usernameField;
//    private JPasswordField passwordField;
//    private JTextArea vaultTextArea;
//    private JButton toggleButton;
//    private boolean isPasswordVisible;
//
//    private Map<String, String> passwordVault;
//    private static final String VAULT_FILE = "vault.dat";
//    private static final String KEY_FILE = "key.dat";
//    private SecretKey secretKey;
//
//    public VaultUI() {
//        setLayout(new BorderLayout());
//
//        // Load key first to avoid null pointer issues
//        loadKey();
//
//        // Initialize components
//        passwordVault = new HashMap<>();
//        siteField = new JTextField();
//        usernameField = new JTextField();
//        passwordField = new JPasswordField();
//        vaultTextArea = new JTextArea();
//        toggleButton = new JButton("Show");
//        isPasswordVisible = false;
//
//        // Load existing vault
//        loadVault();
//
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(6, 2));
//
//        panel.add(new JLabel("Site:"));
//        panel.add(siteField);
//
//        panel.add(new JLabel("Username:"));
//        panel.add(usernameField);
//
//        panel.add(new JLabel("Password:"));
//        panel.add(passwordField);
//
//        JButton addButton = new JButton("Add/Update");
//        addButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                addOrUpdatePassword();
//            }
//        });
//        panel.add(addButton);
//
//        JButton loadButton = new JButton("Load Vault");
//        loadButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                loadVault();
//            }
//        });
//        panel.add(loadButton);
//
//        vaultTextArea.setEditable(false);
//        JScrollPane scrollPane = new JScrollPane(vaultTextArea);
//
//        add(panel, BorderLayout.NORTH);
//        add(scrollPane, BorderLayout.CENTER);
//        add(toggleButton, BorderLayout.SOUTH);
//
//        toggleButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                togglePasswordVisibility();
//            }
//        });
//
//        displayVault();
//    }
//
//    private void addOrUpdatePassword() {
//        String site = siteField.getText();
//        String username = usernameField.getText();
//        String password = new String(passwordField.getPassword());
//
//        if (site.isEmpty() || username.isEmpty() || password.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        String encryptedPassword = encryptPassword(password);
//        passwordVault.put(site, username + ":" + encryptedPassword);
//        saveVault();
//        displayVault();
//    }
//
//    private void displayVault() {
//        if (passwordVault.isEmpty()) {
//            vaultTextArea.setText("Nothing to show here.");
//        } else {
//            vaultTextArea.setText("");
//            for (Map.Entry<String, String> entry : passwordVault.entrySet()) {
//                String site = entry.getKey();
//                String[] parts = entry.getValue().split(":");
//                String username = parts[0];
//                String password = decryptPassword(parts[1]);
//
//                vaultTextArea.append("Site: " + site + "\n");
//                vaultTextArea.append("Username: " + username + "\n");
//                vaultTextArea.append("Password: " + password + "\n\n");
//            }
//        }
//    }
//
//    private void saveVault() {
//        try (FileWriter writer = new FileWriter(VAULT_FILE)) {
//            for (Map.Entry<String, String> entry : passwordVault.entrySet()) {
//                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
//            }
//        } catch (IOException e) {
////            JOptionPane.show
//            JOptionPane.showMessageDialog(this, "Error saving vault", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void loadVault() {
//        passwordVault.clear();
//        try {
//            Files.lines(Paths.get(VAULT_FILE)).forEach(line -> {
//                String[] parts = line.split("=");
//                if (parts.length == 2) {
//                    passwordVault.put(parts[0], parts[1]);
//                }
//            });
//        } catch (IOException e) {
//            // File might not exist on first run, which is fine
//        }
//        displayVault();
//    }
//
//    private void loadKey() {
//        try {
//            if (new File(KEY_FILE).exists()) {
//                byte[] keyBytes = Files.readAllBytes(Paths.get(KEY_FILE));
//                secretKey = new SecretKeySpec(Base64.getDecoder().decode(keyBytes), "AES");
//            } else {
//                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//                keyGen.init(128);
//                secretKey = keyGen.generateKey();
//                try (FileWriter writer = new FileWriter(KEY_FILE)) {
//                    writer.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
//                }
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error loading encryption key", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private String encryptPassword(String password) {
//        try {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
//            return Base64.getEncoder().encodeToString(encryptedBytes);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error encrypting password", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//        return null;
//    }
//
//    private String decryptPassword(String encryptedPassword) {
//        try {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
//            return new String(decryptedBytes);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error decrypting password", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//        return null;
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


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

public class VaultUI extends VBox {
    private TextField siteField;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextArea vaultTextArea;
    private Button toggleButton;
    private boolean isPasswordVisible = false;

    private Map<String, String> passwordVault;
    private static final String VAULT_FILE = "vault.dat";
    private static final String KEY_FILE = "key.dat";
    private SecretKey secretKey;

    public VaultUI() {
        passwordVault = new HashMap<>();
        loadKey();

        siteField = new TextField();
        usernameField = new TextField();
        passwordField = new PasswordField();
        vaultTextArea = new TextArea();
        vaultTextArea.setEditable(false);
        toggleButton = new Button("Show Passwords");

        loadVault();
        displayVault();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(5);
        grid.setHgap(5);

        grid.add(new Label("Site:"), 0, 0);
        grid.add(siteField, 1, 0);

        grid.add(new Label("Username:"), 0, 1);
        grid.add(usernameField, 1, 1);

        grid.add(new Label("Password:"), 0, 2);
        grid.add(passwordField, 1, 2);

        Button addButton = new Button("Add/Update");
        addButton.setOnAction(e -> addOrUpdatePassword());
        grid.add(addButton, 0, 3);

        Button loadButton = new Button("Load Vault");
        loadButton.setOnAction(e -> loadVault());
        grid.add(loadButton, 1, 3);

        getChildren().addAll(grid, vaultTextArea, toggleButton);

        toggleButton.setOnAction(e -> togglePasswordVisibility());
    }

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
}



//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//public class VaultUI extends JFrame {
//    private JTextField siteField;
//    private JTextField usernameField;
//    private JPasswordField passwordField;
//    private JTextArea vaultTextArea;
//
//    private Map<String, String> passwordVault;
//    private static final String VAULT_FILE = "vault.dat";
//    private static final String KEY_FILE = "key.dat";
//    private SecretKey secretKey;
//
//    public VaultUI() {
//        // Load key first to avoid null pointer issues
//        loadKey();
//
//        // Initialize components
//        passwordVault = new HashMap<>();
//        siteField = new JTextField();
//        usernameField = new JTextField();
//        passwordField = new JPasswordField();
//        vaultTextArea = new JTextArea();
//
//        // Load existing vault
//        loadVault();
//
//        setTitle("Password Vault");
//        setSize(500, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(6, 2));
//
//        panel.add(new JLabel("Site:"));
//        panel.add(siteField);
//
//        panel.add(new JLabel("Username:"));
//        panel.add(usernameField);
//
//        panel.add(new JLabel("Password:"));
//        panel.add(passwordField);
//
//        JButton addButton = new JButton("Add/Update");
//        addButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                addOrUpdatePassword();
//            }
//        });
//        panel.add(addButton);
//
//        JButton loadButton = new JButton("Load Vault");
//        loadButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                loadVault();
//            }
//        });
//        panel.add(loadButton);
//
//        vaultTextArea.setEditable(false);
//        JScrollPane scrollPane = new JScrollPane(vaultTextArea);
//
//        add(panel, BorderLayout.NORTH);
//        add(scrollPane, BorderLayout.CENTER);
//
//        displayVault();
//    }
//
//    private void addOrUpdatePassword() {
//        String site = siteField.getText();
//        String username = usernameField.getText();
//        String password = new String(passwordField.getPassword());
//
//        if (site.isEmpty() || username.isEmpty() || password.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        String encryptedPassword = encryptPassword(password);
//        passwordVault.put(site, username + ":" + encryptedPassword);
//        saveVault();
//        displayVault();
//    }
//
//    private void displayVault() {
//        if (passwordVault.isEmpty()) {
//            vaultTextArea.setText("Nothing to show here.");
//        } else {
//            vaultTextArea.setText("");
//            for (Map.Entry<String, String> entry : passwordVault.entrySet()) {
//                String site = entry.getKey();
//                String[] parts = entry.getValue().split(":");
//                String username = parts[0];
//                String password = decryptPassword(parts[1]);
//
//                vaultTextArea.append("Site: " + site + "\n");
//                vaultTextArea.append("Username: " + username + "\n");
//                vaultTextArea.append("Password: " + password + "\n\n");
//            }
//        }
//    }
//
//    private void saveVault() {
//        try (FileWriter writer = new FileWriter(VAULT_FILE)) {
//            for (Map.Entry<String, String> entry : passwordVault.entrySet()) {
//                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
//            }
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "Error saving vault", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void loadVault() {
//        passwordVault.clear();
//        try {
//            Files.lines(Paths.get(VAULT_FILE)).forEach(line -> {
//                String[] parts = line.split("=");
//                if (parts.length == 2) {
//                    passwordVault.put(parts[0], parts[1]);
//                }
//            });
//        } catch (IOException e) {
//            // File might not exist on first run, which is fine
//        }
//        displayVault();
//    }
//
//    private void loadKey() {
//        try {
//            if (new File(KEY_FILE).exists()) {
//                byte[] keyBytes = Files.readAllBytes(Paths.get(KEY_FILE));
//                secretKey = new SecretKeySpec(Base64.getDecoder().decode(keyBytes), "AES");
//            } else {
//                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//                keyGen.init(128);
//                secretKey = keyGen.generateKey();
//                try (FileWriter writer = new FileWriter(KEY_FILE)) {
//                    writer.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
//                }
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error loading encryption key", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private String encryptPassword(String password) {
//        try {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
//            return Base64.getEncoder().encodeToString(encryptedBytes);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error encrypting password", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//        return null;
//    }
//
//    private String decryptPassword(String encryptedPassword) {
//        try {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
//            return new String(decryptedBytes);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error decrypting password", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//        return null;
//    }
//}
