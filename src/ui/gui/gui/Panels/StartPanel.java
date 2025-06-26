package ui.gui.gui.Panels;

import domain.EShop;
import exception.LoginException;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JDialog {

    private JPanel mainPanel;
    private JPanel registerPanel;
    private boolean registerVisible = false;

    private JButton loginButton;
    private JButton registerToggleButton;
    private JButton registerButton;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JPasswordField confirmPasswordField;

    private EShop eshop;
    private String rolle;

    public StartPanel(Frame parent) {
        super(parent, "Login", true); // Modal
        eshop = new EShop(); // ggf. Konstruktor mit Dateinamen verwenden
        setupUI();
        setupEvent();
    }

    public String getRolle() {
        return rolle;
    }

    public void setupUI() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("E-Mail:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(250, 35));
        mainPanel.add(emailField, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Passwort:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(250, 35));
        mainPanel.add(passwordField, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(250, 40));
        mainPanel.add(loginButton, gbc);


        gbc.gridy++;
        registerToggleButton = new JButton("Registrieren");
        registerToggleButton.setPreferredSize(new Dimension(250, 40));
        mainPanel.add(registerToggleButton, gbc);


        registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBorder(BorderFactory.createTitledBorder("Registrieren"));
        registerPanel.setVisible(false);
        registerPanel.setBackground(Color.WHITE);
        GridBagConstraints rbc = new GridBagConstraints();
        rbc.insets = new Insets(5, 5, 5, 5);

        // Name
        rbc.gridx = 0;
        rbc.gridy = 0;
        rbc.anchor = GridBagConstraints.EAST;
        registerPanel.add(new JLabel("Name:"), rbc);

        rbc.gridx = 1;
        rbc.fill = GridBagConstraints.HORIZONTAL;
        rbc.weightx = 1.0;
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(250, 30));
        registerPanel.add(nameField, rbc);

        // Passwort bestätigen
        rbc.gridx = 0;
        rbc.gridy++;
        rbc.weightx = 0;
        rbc.fill = GridBagConstraints.NONE;
        registerPanel.add(new JLabel("Passwort bestätigen:"), rbc);

        rbc.gridx = 1;
        rbc.fill = GridBagConstraints.HORIZONTAL;
        rbc.weightx = 1.0;
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(250, 30));
        registerPanel.add(confirmPasswordField, rbc);

        // Registrieren abschließen
        rbc.gridy++;
        rbc.gridx = 0;
        rbc.gridwidth = 2;
        rbc.anchor = GridBagConstraints.CENTER;
        registerButton = new JButton("Registrieren abschließen");
        registerButton.setPreferredSize(new Dimension(250, 40));
        registerPanel.add(registerButton, rbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        mainPanel.add(registerPanel, gbc);

        setContentPane(mainPanel);
        getContentPane().setBackground(Color.WHITE);
        pack();
        setLocationRelativeTo(getParent());
    }

    public void setupEvent() {
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String passwort = new String(passwordField.getPassword()).trim();

            try {
                rolle = eshop.einloggen(email, passwort);
                dispose();
            } catch (LoginException ex) {
                JOptionPane.showMessageDialog(this, "Login fehlgeschlagen: " + ex.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerToggleButton.addActionListener(e -> {
            registerVisible = !registerVisible;
            registerPanel.setVisible(registerVisible);
            pack();
        });

        registerButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Registrierung abgeschlossen!", "Info", JOptionPane.INFORMATION_MESSAGE);
            registerPanel.setVisible(false);
            pack();
        });
    }
}
