package ui.gui.gui.Panels;

import entities.EShopRemote;
import entities.Mitarbeiter;
import exception.PasswortZuSchwach;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;

public class EinfuegenMitarbeiterDialog extends JDialog {
    public EinfuegenMitarbeiterDialog(JFrame parent, EShopRemote eshop) {
        super(parent, "Mitarbeiter:in anlegen", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Labels und Felder
        JLabel firstNameLabel = new JLabel("Vorname:");
        JTextField firstNameField = new JTextField(20);

        JLabel lastNameLabel = new JLabel("Nachname:");
        JTextField lastNameField = new JTextField(20);

        JLabel emailLabel = new JLabel("E-Mail:");
        JTextField emailField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Passwort:");
        JPasswordField passwordField = new JPasswordField(20);

        // Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(firstNameLabel, gbc);
        gbc.gridx = 1;
        add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(lastNameLabel, gbc);
        gbc.gridx = 1;
        add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(emailLabel, gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Speichern");
        JButton cancelBtn = new JButton("Abbrechen");
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(buttonPanel, gbc);

        saveBtn.addActionListener(e -> {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Bitte alle Felder ausfüllen.",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Passwort-Prüfung wie beim Kunden
                eshop.gueltigesPasswort(password);

                Mitarbeiter mitarbeiter = new Mitarbeiter(firstName, lastName, email, password);
                eshop.einfuegenMitarbeiter(mitarbeiter);

                eshop.speicherOption();

                JOptionPane.showMessageDialog(this,
                        "Mitarbeiter:in erfolgreich angelegt.",
                        "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                dispose();

            } catch (PasswortZuSchwach ex) {
                JOptionPane.showMessageDialog(this,
                        "Ungültiges Passwort: " + ex.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(this,
                        "Fehler bei der Verbindung:\n" + ex.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Fehler beim Speichern:\n" + ex.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }
}
