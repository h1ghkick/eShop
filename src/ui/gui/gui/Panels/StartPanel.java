package ui.gui.gui.Panels;

import domain.EShop;
import entities.Kunde;
import exception.LoginException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class StartPanel extends JDialog {

    private final JTextField emailField      = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);

    private final JPanel registerPanel;
    private final JTextField vornameField    = new JTextField(20);
    private final JTextField nachnameField   = new JTextField(20);
    private final JPasswordField confirmField = new JPasswordField(20);

    private final EShop eshop;
    private Object benutzer;

    public StartPanel(Frame parent, EShop eshop) {
        super(parent, "Login / Registrierung", true);
        this.eshop = eshop;

        // Haupt-Container mit vertikalem Layout
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 1) Login-Form
        JPanel loginForm = new JPanel(new GridLayout(2, 2, 5, 5));
        loginForm.add(new JLabel("E-Mail:"));
        loginForm.add(emailField);
        loginForm.add(new JLabel("Passwort:"));
        loginForm.add(passwordField);
        content.add(loginForm);

        // 2) Login-Button
        JButton loginBtn = new JButton("Login");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(Box.createVerticalStrut(10));
        content.add(loginBtn);

        // 3) Toggle für Registrierung
        JButton toggleBtn = new JButton("Neu Registrieren");
        toggleBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(Box.createVerticalStrut(10));
        content.add(toggleBtn);

        // 4) Registrierungs-Panel (anfangs versteckt)
        registerPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        registerPanel.setBorder(BorderFactory.createTitledBorder("Registrieren"));
        registerPanel.add(new JLabel("Vorname:"));
        registerPanel.add(vornameField);
        registerPanel.add(new JLabel("Nachname:"));
        registerPanel.add(nachnameField);
        registerPanel.add(new JLabel("Passwort bestätigen:"));
        registerPanel.add(confirmField);
        registerPanel.setVisible(false);
        content.add(Box.createVerticalStrut(10));
        content.add(registerPanel);

        // 5) Abschließen-Button
        JButton registerBtn = new JButton("Registrierung abschließen");
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setVisible(false);
        content.add(Box.createVerticalStrut(10));
        content.add(registerBtn);

        // Dialog einrichten
        setContentPane(content);
        pack();
        setLocationRelativeTo(parent);

        // --- Event-Handler ---

        // Login
        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String pw    = new String(passwordField.getPassword()).trim();
            try {
                benutzer = eshop.einloggen(email, pw);
                dispose();
            } catch (LoginException ex) {
                JOptionPane.showMessageDialog(this,
                        "Login fehlgeschlagen: " + ex.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Registrierung ein-/ausblenden
        toggleBtn.addActionListener(e -> {
            boolean jetztRegistrieren = !registerPanel.isVisible();

            // Registrierungs-Form ein-/ausblenden
            registerPanel.setVisible(jetztRegistrieren);
            registerBtn.setVisible(jetztRegistrieren);

            // Login- und Toggle-Button genau entgegengesetzt
            loginBtn.setVisible(!jetztRegistrieren);
            toggleBtn.setVisible(!jetztRegistrieren);

            pack();
        });

        // Registrierung abschließen
        registerBtn.addActionListener(e -> {
            String vorname = vornameField.getText().trim();
            String nachname= nachnameField.getText().trim();
            String email   = emailField.getText().trim();
            String pw1     = new String(passwordField.getPassword()).trim();
            String pw2     = new String(confirmField.getPassword()).trim();

            // nur Vor­/Nachname, E-Mail und Passwort als Pflicht
            if (vorname.isEmpty() || nachname.isEmpty() || email.isEmpty() || pw1.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Bitte alle Felder ausfüllen.",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pw1.equals(pw2)) {
                JOptionPane.showMessageDialog(this,
                        "Passwörter stimmen nicht überein.",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kunde anlegen & speichern
            Kunde neu = new Kunde(
                    vorname, nachname,
                    email, pw1,
                    "", "", 0
            );
            try {
                eshop.einfuegenKunden(neu);
                eshop.speicherOption();
                JOptionPane.showMessageDialog(this,
                        "Registrierung abgeschlossen! Bitte einloggen.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                // Formular zurücksetzen
                vornameField.setText("");
                nachnameField.setText("");
                passwordField.setText("");
                confirmField.setText("");
                registerPanel.setVisible(false);
                registerBtn.setVisible(false);

                // Login- & "Neu registrieren"-Buttons wieder einblenden
                loginBtn.setVisible(true);
                toggleBtn.setVisible(true);
                pack();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Speichern fehlgeschlagen: " + ex.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public Object getBenutzer() {
        return benutzer;
    }
}
