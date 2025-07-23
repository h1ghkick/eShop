package ui.gui.gui.Panels;

import entities.*;
import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

public class AddArtikelPanel extends JPanel {
    public interface AddArtikelListener {
        void onArtikelAdded(Artikel artikel) throws RemoteException;
    }

    private final EShopRemote eshop;
    private final AddArtikelListener listener;

    private final JTextField titelField;
    private final JTextField nummerField;
    private final JTextField preisField;
    private final JTextField mengeField;
    private final JTextField packungsgroesseField; // NEU
    private final JButton addButton;

    public AddArtikelPanel(EShopRemote eshop, AddArtikelListener listener) {
        this.eshop    = eshop;
        this.listener = listener;

        titelField          = new JTextField(20);
        nummerField         = new JTextField(20);
        preisField          = new JTextField(20);
        mengeField          = new JTextField(20);
        packungsgroesseField = new JTextField(20); // NEU
        addButton           = new JButton("Hinzufügen");

        initLayout();
        initEvents();
    }

    private void initLayout() {
        setBorder(BorderFactory.createTitledBorder("Artikel einfügen"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill   = GridBagConstraints.HORIZONTAL;

        // Titel
        c.gridx=0; c.gridy=0; c.weightx=0; add(new JLabel("Titel:"), c);
        c.gridx=1; c.weightx=1; add(titelField, c);

        // Nummer
        c.gridx=0; c.gridy=1; c.weightx=0; add(new JLabel("Artikelnummer:"), c);
        c.gridx=1; c.weightx=1; add(nummerField, c);

        // Preis
        c.gridx=0; c.gridy=2; c.weightx=0; add(new JLabel("Preis:"), c);
        c.gridx=1; c.weightx=1; add(preisField, c);

        // Menge
        c.gridx=0; c.gridy=3; c.weightx=0; add(new JLabel("Menge:"), c);
        c.gridx=1; c.weightx=1; add(mengeField, c);

        // Packungsgroesse (optional)
        c.gridx=0; c.gridy=4; c.weightx=0; add(new JLabel("Packungsgröße (optional):"), c);
        c.gridx=1; c.weightx=1; add(packungsgroesseField, c);

        // Button
        c.gridx=0; c.gridy=5; c.gridwidth=2;
        c.fill = GridBagConstraints.NONE; c.anchor = GridBagConstraints.CENTER;
        add(addButton, c);
    }

    private void initEvents() {
        addButton.addActionListener(e -> {
            String titel  = titelField.getText().trim();
            String nummer = nummerField.getText().trim();
            String preis  = preisField.getText().trim();
            String menge  = mengeField.getText().trim();
            String packung = packungsgroesseField.getText().trim();

            if (titel.isEmpty() || nummer.isEmpty() || preis.isEmpty() || menge.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Bitte alle Pflichtfelder ausfüllen.",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int artikelnummer = Integer.parseInt(nummer);
                double artikelpreis = Double.parseDouble(preis);
                int artikelmenge = Integer.parseInt(menge);

                Artikel artikel;

                // Prüfen, ob Packungsgröße gesetzt → MassengutArtikel
                if (!packung.isEmpty()) {
                    int packungsgroesse = Integer.parseInt(packung);
                    artikel = new MassengutArtikel(artikelmenge, artikelnummer, titel, artikelpreis, packungsgroesse);
                } else {
                    artikel = new Artikel(artikelmenge, artikelnummer, titel, artikelpreis);
                }

                eshop.artikelEinfuegen(artikel, artikelmenge);

                // UI reset
                titelField.setText("");
                nummerField.setText("");
                preisField.setText("");
                mengeField.setText("");
                packungsgroesseField.setText("");

                listener.onArtikelAdded(artikel);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Bitte gültige Zahlen eingeben (Nummer, Preis, Menge, Packung).",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
