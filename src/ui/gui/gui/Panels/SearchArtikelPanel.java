package ui.gui.gui.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import entities.Artikel;
import domain.EShop;


public class SearchArtikelPanel extends JPanel {

    public interface SearchResultListener {
        void onSearchResult(List<Artikel> artikelList);
    }

    private EShop shop = null;
    private SearchResultListener searchListener = null;
    private JTextField searchTextField;
    private JButton searchButton = null;

    public SearchArtikelPanel(EShop eShop, SearchResultListener listener) {
        shop = new EShop();
        searchListener = listener;

        setupUI();

        setupEvents();
    }

    private void setupUI() {
        // GridBagLayout
        // (Hinweis: Das ist schon ein komplexerer LayoutManager, der mehr kann als hier gezeigt.
        //  Hervorzuheben ist hier die Idee, explizit Constraints (also Nebenbedindungen) für
        //  die Positionierung / Ausrichtung / Größe von GUI-Komponenten anzugeben.)
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;	// Zeile 0

        JLabel searchLabel = new JLabel("Suchbegriff:");
        c.gridx = 0;	// Spalte 0
        c.weightx = 0.2;	// 20% der gesamten Breite
        c.anchor = GridBagConstraints.EAST;
        gridBagLayout.setConstraints(searchLabel, c);
        this.add(searchLabel);

        searchTextField = new JTextField();
        searchTextField.setToolTipText("Suchbegriff eingeben.");
        c.gridx = 1;	// Spalte 1
        c.weightx = 0.6;	// 60% der gesamten Breite
        gridBagLayout.setConstraints(searchTextField, c);
        this.add(searchTextField);

        searchButton = new JButton("Such!");
        c.gridx = 2;	// Spalte 2
        c.weightx = 0.2;	// 20% der gesamten Breite
        gridBagLayout.setConstraints(searchButton, c);
        this.add(searchButton);

        // Rahmen definieren
        setBorder(BorderFactory.createTitledBorder("Suche"));
    }

    private void setupEvents() {
        searchButton.addActionListener(new SuchListener());
    }

    // Lokale Klasse für Reaktion auf Such-Klick
    class SuchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource().equals(searchButton)) {
                String suchbegriff = searchTextField.getText();
                List<Artikel> suchErgebnis;
                if (suchbegriff.isEmpty()) {
                    suchErgebnis = shop.getArtikelBestand();
                } else {
                    suchErgebnis = (List<Artikel>) shop.artikelDa(suchbegriff);
                    searchTextField.setText("");
                }
                // Listener benachrichtigen, damit er die Ausgabe aktualisieren kann
                searchListener.onSearchResult(suchErgebnis);
            }
        }
    }

}
