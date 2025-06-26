package ui.gui;

import Persistence.FilePersistenceManager;
import domain.EShop;
import entities.Artikel;
import entities.Kunde;
import entities.Mitarbeiter;
import ui.gui.gui.Panels.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ShopClientGUI extends JFrame
        implements SearchArtikelPanel.SearchResultListener, AddArtikelPanel.AddArtikelListener {

    private EShop eshop;
    private SearchArtikelPanel searchPanel;
    private AddArtikelPanel addPanel;
    private ArtikelTablePanel artikelPanel;

    public ShopClientGUI(String titel) {
        super(titel);

        try {
            FilePersistenceManager fpm = new FilePersistenceManager();
            eshop = new EShop("Kunde.txt", "Artikel.txt", "Mitarbeiter.txt");

            // Zeige Login-Dialog
            StartPanel loginDialog = new StartPanel(this, eshop);
            loginDialog.setVisible(true);

            Object benutzer = loginDialog.getBenutzer();

            if (benutzer == null) {
                System.exit(0); // Wenn Fenster geschlossen oder Login fehlgeschlagen
            }

            // GUI je nach Rolle starten
            if (benutzer instanceof Kunde) {
                initializeKundenUI();
            } else if (benutzer instanceof Mitarbeiter) {
                initializeMitarbeiterUI();
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler beim Laden der Daten.", "Fehler", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void initializeKundenUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        searchPanel = new SearchArtikelPanel(eshop, this);
        List<Artikel> artikel = eshop.getArtikelBestand();
        artikelPanel = new ArtikelTablePanel(artikel);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(artikelPanel), BorderLayout.CENTER);

        setSize(640, 480);
        setVisible(true);
    }

    private void initializeMitarbeiterUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        searchPanel = new SearchArtikelPanel(eshop, this);
        addPanel = new AddArtikelPanel(eshop, this);
        List<Artikel> artikel = eshop.getArtikelBestand();
        artikelPanel = new ArtikelTablePanel(artikel);

        add(searchPanel, BorderLayout.NORTH);
        add(addPanel, BorderLayout.WEST);
        add(new JScrollPane(artikelPanel), BorderLayout.CENTER);

        setSize(800, 500);
        setVisible(true);
    }

    @Override
    public void onArtikelAdded(Artikel artikel) {
        // 1) Tabelle aktualisieren
        artikelPanel.updateArtikel(eshop.getArtikelBestand());

        // 2) Änderungen sofort persistieren
        try {
            eshop.speicherOption();
        } catch (IOException e) {
            // Fehlerbehandlung: dem User Bescheid geben
            JOptionPane.showMessageDialog(this,
                    "Fehler beim Speichern der Daten:\n" + e.getMessage(),
                    "Speicherfehler", JOptionPane.ERROR_MESSAGE);
        }
    }
    @Override
    public void onSearchResult(List<Artikel> artikels) {
        artikelPanel.updateArtikel(artikels);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShopClientGUI("ESHOP")); // LAMBDA EXPRESSION
    }
}
