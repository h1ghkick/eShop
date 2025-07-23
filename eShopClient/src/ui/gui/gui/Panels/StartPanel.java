package ui.gui.gui.Panels;

import entities.EShopRemote;
import entities.Kunde;
import exception.LoginException;
import exception.PasswortZuSchwach;
import exception.PostleitzahlZuSchwach;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;

public class StartPanel extends JDialog {

    private final JTextField emailField      = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);

    private final JPanel registerPanel;
    private final JTextField vornameField    = new JTextField(20);
    private final JTextField nachnameField   = new JTextField(20);
    private final JPasswordField confirmField = new JPasswordField(20);

    // NEU
    private final JTextField strasseField    = new JTextField(20);
    private final JTextField wohnortField    = new JTextField(20);
    private final JTextField plzField        = new JTextField(20);

    private final EShopRemote eshop;
    private Object benutzer;

    public StartPanel(Frame parent, EShopRemote eshop) {
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
        registerPanel = new JPanel(new GridLayout(7, 2, 5, 5)); // 7 Zeilen (6 Felder + ConfirmPW)
        registerPanel.setBorder(BorderFactory.createTitledBorder("Registrieren"));
        registerPanel.add(new JLabel("Vorname:"));
        registerPanel.add(vornameField);
        registerPanel.add(new JLabel("Nachname:"));
        registerPanel.add(nachnameField);
        registerPanel.add(new JLabel("Straße:"));
        registerPanel.add(strasseField);
        registerPanel.add(new JLabel("Wohnort:"));
        registerPanel.add(wohnortField);
        registerPanel.add(new JLabel("PLZ:"));
        registerPanel.add(plzField);
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
            } catch (LoginException | RemoteException ex) {
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
            String nachname = nachnameField.getText().trim();
            String email = emailField.getText().trim();
            String pw1 = new String(passwordField.getPassword()).trim();
            String pw2 = new String(confirmField.getPassword()).trim();
            String strasse = strasseField.getText().trim();
            String wohnort = wohnortField.getText().trim();
            String plz = plzField.getText().trim();

            // Pflichtfelder checken
            if (vorname.isEmpty() || nachname.isEmpty() || email.isEmpty() || pw1.isEmpty()
                    || strasse.isEmpty() || wohnort.isEmpty() || plz.isEmpty()) {
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

            int plzNumber;
            try {
                plzNumber = Integer.parseInt(plz);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Bitte gib eine gültige fünfstellige Zahl als Postleitzahl ein.",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                eshop.gueltigesPasswort(pw1);       // Prüfung auf Passwort
                eshop.gueltigePostleitzahl(plz);    // Prüfung auf Postleitzahl

                Kunde neu = new Kunde(vorname, nachname, email, pw1, strasse, wohnort, plzNumber);
                eshop.einfuegenKunden(neu);
                eshop.speicherOption();

                JOptionPane.showMessageDialog(this,
                        "Registrierung abgeschlossen! Bitte einloggen.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);

                // Felder zurücksetzen
                vornameField.setText("");
                nachnameField.setText("");
                passwordField.setText("");
                confirmField.setText("");
                strasseField.setText("");
                wohnortField.setText("");
                plzField.setText("");

                registerPanel.setVisible(false);
                registerBtn.setVisible(false);
                loginBtn.setVisible(true);
                toggleBtn.setVisible(true);
                pack();
            } catch (PasswortZuSchwach ex) {
                JOptionPane.showMessageDialog(this,
                        "Ungültiges Passwort: " + ex.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (PostleitzahlZuSchwach ex) {
                JOptionPane.showMessageDialog(this,
                        "Ungültige Postleitzahl: " + ex.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
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
