import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class vaultUI extends JFrame {
    private JTextField siteField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextArea vaultTextArea;

    private Map<String, String> passwordVault;
    private static final String VAULT_FILE = "vault.dat";
    private static final String KEY_FILE = "key.dat";
    private SecretKey secretKey;

    public vaultUI() {
        // Load existing vault
        passwordVault = new HashMap<>();
        loadVault();
        loadKey();

        setTitle("Password Vault");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        panel.add(new JLabel("Site:"));
        siteField = new JTextField();
        panel.add(siteField);

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton addButton = new JButton("Add/Update");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrUpdatePassword();
            }
        });
        panel.add(addButton);

        JButton loadButton = new JButton("Load Vault");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadVault();
            }
        });
        panel.add(loadButton);

        vaultTextArea = new JTextArea();
        vaultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(vaultTextArea);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        displayVault();
    }

    private void addOrUpdatePassword() {
        String site = siteField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (site.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String encryptedPassword = encryptPassword(password);
        passwordVault.put(site, username + ":" + encryptedPassword);
        saveVault();
        displayVault();
    }

    private void displayVault() {
        vaultTextArea.setText("");
        for (Map.Entry<String, String> entry : passwordVault.entrySet()) {
            String site = entry.getKey();
            String[] parts = entry.getValue().split(":");
            String username = parts[0];
            String password = decryptPassword(parts[1]);

            vaultTextArea.append("Site: " + site + "\n");
            vaultTextArea.append("Username: " + username + "\n");
            vaultTextArea.append("Password: " + password + "\n\n");
        }
    }

    private void saveVault() {
        try (FileWriter writer = new FileWriter(VAULT_FILE)) {
            for (Map.Entry<String, String> entry : passwordVault.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving vault", "Error", JOptionPane.ERROR_MESSAGE);
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
                secretKey = new SecretKeySpec(keyBytes, "AES");
            } else {
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(128);
                secretKey = keyGen.generateKey();
                try (FileWriter writer = new FileWriter(KEY_FILE)) {
                    writer.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading encryption key", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String encryptPassword(String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error encrypting password", "Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Error decrypting password", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
