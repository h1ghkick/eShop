package ui.gui.gui.Panels;

import domain.EShop;
import exception.LoginException;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JDialog {

    private final JPanel mainPanel;
    private final JPanel registerPanel;
    private boolean registerVisible = false;

    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JTextField nameField;
    private final JPasswordField confirmPasswordField;

    private final EShop eshop;
    private Object benutzer;

    public StartPanel(Frame parent, EShop eshop) {
        super(parent, "Login", true);
        this.eshop = eshop;

        // Layout
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // E-Mail
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("E-Mail:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        emailField = new JTextField(20);
        mainPanel.add(emailField, gbc);

        // Passwort
        gbc.gridx = 0; gbc.gridy++; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Passwort:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        // Login
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        mainPanel.add(loginButton, gbc);

        // Toggle Registrierung
        gbc.gridy++;
        JButton registerToggleButton = new JButton("Registrieren");
        mainPanel.add(registerToggleButton, gbc);

        // Registrieren-Panel
        registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBorder(BorderFactory.createTitledBorder("Registrieren"));
        registerPanel.setVisible(false);
        registerPanel.setBackground(Color.WHITE);

        GridBagConstraints rbc = new GridBagConstraints();
        rbc.insets = new Insets(5, 5, 5, 5);

        // Name
        rbc.gridx = 0; rbc.gridy = 0; rbc.anchor = GridBagConstraints.EAST;
        registerPanel.add(new JLabel("Name:"), rbc);
        rbc.gridx = 1; rbc.fill = GridBagConstraints.HORIZONTAL;
        nameField = new JTextField(20);
        registerPanel.add(nameField, rbc);

        // Passwort bestätigen
        rbc.gridx = 0; rbc.gridy++; rbc.fill = GridBagConstraints.NONE;
        registerPanel.add(new JLabel("Passwort bestätigen:"), rbc);
        rbc.gridx = 1; rbc.fill = GridBagConstraints.HORIZONTAL;
        confirmPasswordField = new JPasswordField(20);
        registerPanel.add(confirmPasswordField, rbc);

        // Abschließen
        rbc.gridy++; rbc.gridx = 0; rbc.gridwidth = 2; rbc.anchor = GridBagConstraints.CENTER;
        JButton registerButton = new JButton("Registrieren abschließen");
        registerPanel.add(registerButton, rbc);

        gbc.gridy++; gbc.gridwidth = 2;
        mainPanel.add(registerPanel, gbc);

        // Events
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String passwort = new String(passwordField.getPassword()).trim();

            try {
                benutzer = eshop.einloggen(email, passwort);
                dispose();
            } catch (LoginException ex) {
                JOptionPane.showMessageDialog(this, "Login fehlgeschlagen: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerToggleButton.addActionListener(e -> {
            registerVisible = !registerVisible;
            registerPanel.setVisible(registerVisible);
            pack();
        });

        registerButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String pw1 = new String(passwordField.getPassword()).trim();
            String pw2 = new String(confirmPasswordField.getPassword()).trim();

            if (name.isEmpty() || email.isEmpty() || pw1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bitte alle Felder ausfüllen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!pw1.equals(pw2)) {
                JOptionPane.showMessageDialog(this, "Passwörter stimmen nicht überein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // TODO: Registrierung in EShop
            JOptionPane.showMessageDialog(this, "Registrierung abgeschlossen! Bitte einloggen.", "Info", JOptionPane.INFORMATION_MESSAGE);
            registerPanel.setVisible(false);
            pack();
        });

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(parent);
    }

    public Object getBenutzer() {
        return benutzer;
    }
}
