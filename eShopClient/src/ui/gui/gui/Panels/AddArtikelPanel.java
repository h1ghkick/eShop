package ui.gui.gui.Panels;

import entities.EShopRemote;
import entities.Artikel;

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
    private final JButton addButton;

    public AddArtikelPanel(EShopRemote eshop, AddArtikelListener listener) {
        this.eshop    = eshop;
        this.listener = listener;

        // Felder anlegen
        titelField  = new JTextField(20);
        nummerField = new JTextField(20);
        preisField  = new JTextField(20);
        mengeField  = new JTextField(20);
        addButton   = new JButton("Hinzufügen");

        initLayout();
        initEvents();
    }

    private void initLayout() {
        setBorder(BorderFactory.createTitledBorder("Artikel einfügen"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill   = GridBagConstraints.HORIZONTAL;

        // Zeile 0: Titel
        c.gridx=0; c.gridy=0; c.weightx=0; add(new JLabel("Titel:"), c);
        c.gridx=1; c.weightx=1; add(titelField, c);

        // Zeile 1: Nummer
        c.gridx=0; c.gridy=1; c.weightx=0; add(new JLabel("Artikelnummer:"), c);
        c.gridx=1; c.weightx=1; add(nummerField, c);

        // Zeile 2: Preis
        c.gridx=0; c.gridy=2; c.weightx=0; add(new JLabel("Preis:"), c);
        c.gridx=1; c.weightx=1; add(preisField, c);

        // Zeile 3: Menge
        c.gridx=0; c.gridy=3; c.weightx=0; add(new JLabel("Menge:"), c);
        c.gridx=1; c.weightx=1; add(mengeField, c);

        // Zeile 4: Button
        c.gridx=0; c.gridy=4; c.gridwidth=2;
        c.fill = GridBagConstraints.NONE; c.anchor = GridBagConstraints.CENTER;
        add(addButton, c);
    }

    private void initEvents() {
        addButton.addActionListener(e -> {
            String t = titelField.getText().trim();
            String n = nummerField.getText().trim();
            String p = preisField.getText().trim();
            String m = mengeField.getText().trim();

            if (t.isEmpty() || n.isEmpty() || p.isEmpty() || m.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Bitte alle Felder ausfüllen.",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int nummer = Integer.parseInt(n);
                double preis  = Double.parseDouble(p);
                int menge  = Integer.parseInt(m);

                // Domäne aufrufen
                Artikel artikel = new Artikel(menge, nummer, t, preis);
                eshop.artikelEinfuegen(artikel, menge);

                // UI zurücksetzen
                titelField .setText("");
                nummerField.setText("");
                preisField .setText("");
                mengeField .setText("");

                // Tabelle aktualisieren
                listener.onArtikelAdded(artikel);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Nummer, Preis und Menge müssen Zahlen sein.",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
