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
    private final JCheckBox massengutCheckbox;
    private final JTextField packungsgroesseField;
    private final JLabel packungsgroesseLabel;
    private final JButton addButton;

    public AddArtikelPanel(EShopRemote eshop, AddArtikelListener listener) {
        this.eshop = eshop;
        this.listener = listener;

        titelField = new JTextField(20);
        nummerField = new JTextField(20);
        preisField = new JTextField(20);
        mengeField = new JTextField(20);
        massengutCheckbox = new JCheckBox("Massengutartikel?");
        packungsgroesseField = new JTextField(20);
        packungsgroesseLabel = new JLabel("Packungsgröße:");
        addButton = new JButton("Hinzufügen");

        initLayout();
        initEvents();
    }

    private void initLayout() {
        setBorder(BorderFactory.createTitledBorder("Artikel einfügen"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Zeile 0
        c.gridx = 0; c.gridy = 0; c.weightx = 0; add(new JLabel("Titel:"), c);
        c.gridx = 1; c.weightx = 1; add(titelField, c);

        // Zeile 1
        c.gridx = 0; c.gridy = 1; c.weightx = 0; add(new JLabel("Artikelnummer:"), c);
        c.gridx = 1; add(nummerField, c);

        // Zeile 2
        c.gridx = 0; c.gridy = 2; add(new JLabel("Preis:"), c);
        c.gridx = 1; add(preisField, c);

        // Zeile 3
        c.gridx = 0; c.gridy = 3; add(new JLabel("Menge:"), c);
        c.gridx = 1; add(mengeField, c);

        // Zeile 4: Checkbox
        c.gridx = 0; c.gridy = 4; c.gridwidth = 2;
        add(massengutCheckbox, c);

        // Zeile 5: Packungsgroesse (nur sichtbar bei Checkbox)
        c.gridy = 5; c.gridwidth = 1;
        c.gridx = 0; add(packungsgroesseLabel, c);
        c.gridx = 1; add(packungsgroesseField, c);

        // Zeile 6: Button
        c.gridx = 0; c.gridy = 6; c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        add(addButton, c);

        // Anfangszustand
        packungsgroesseField.setVisible(false);
        packungsgroesseLabel.setVisible(false);
    }

    private void initEvents() {
        massengutCheckbox.addActionListener(e -> {
            boolean sichtbar = massengutCheckbox.isSelected();
            packungsgroesseField.setVisible(sichtbar);
            packungsgroesseLabel.setVisible(sichtbar);
            revalidate();
            repaint();
        });

        addButton.addActionListener(e -> {
            String titel = titelField.getText().trim();
            String nummerStr = nummerField.getText().trim();
            String preisStr = preisField.getText().trim();
            String mengeStr = mengeField.getText().trim();

            if (titel.isEmpty() || nummerStr.isEmpty() || preisStr.isEmpty() || mengeStr.isEmpty()) {
                showError("Bitte alle Felder ausfüllen.");
                return;
            }

            try {
                int nummer = Integer.parseInt(nummerStr);
                double preis = Double.parseDouble(preisStr);
                int menge = Integer.parseInt(mengeStr);

                Artikel artikel;

                if (massengutCheckbox.isSelected()) {
                    String packungStr = packungsgroesseField.getText().trim();
                    if (packungStr.isEmpty()) {
                        showError("Bitte Packungsgröße eingeben.");
                        return;
                    }
                    int packungsgroesse = Integer.parseInt(packungStr);
                    artikel = new MassengutArtikel(menge, nummer, titel, preis, packungsgroesse);
                } else {
                    artikel = new Artikel(menge, nummer, titel, preis);
                }

                eshop.artikelEinfuegen(artikel, menge);

                // Zurücksetzen
                titelField.setText("");
                nummerField.setText("");
                preisField.setText("");
                mengeField.setText("");
                packungsgroesseField.setText("");
                massengutCheckbox.setSelected(false);
                packungsgroesseField.setVisible(false);
                packungsgroesseLabel.setVisible(false);

                listener.onArtikelAdded(artikel);

            } catch (NumberFormatException ex) {
                showError("Artikelnummer, Preis, Menge und ggf. Packungsgröße müssen Zahlen sein.");
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Fehler", JOptionPane.ERROR_MESSAGE);
    }
}
