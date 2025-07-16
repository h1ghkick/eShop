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
    private ArtikelTablePanel artikelPanel;

    // Bottom‐Panel Komponenten
    private JTextField mengeField;
    private JButton toCartBtn;
    private JButton cartBtn;

    public ShopClientGUI(String titel) {
        super(titel);
        try {
            new FilePersistenceManager(); // nicht weiter genutzt
            eshop = new EShop("Kunde.txt", "Artikel.txt", "Mitarbeiter.txt");
            StartPanel loginDialog = new StartPanel(this, eshop);
            loginDialog.setVisible(true);
            Object benutzer = loginDialog.getBenutzer();
            if (benutzer == null) System.exit(0);

            if (benutzer instanceof Kunde) {
                initializeKundenUI();
            } else if (benutzer instanceof Mitarbeiter) {
                initializeMitarbeiterUI();
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Fehler beim Laden der Daten.",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    // ---------------- Kunden‐UI ----------------

    private void initializeKundenUI() {
        setupMainFrame();
        addSearchPanel();
        addArtikelTable();
        addBottomPanel();
        setupListeners();
        showFrame();
    }

    private void setupMainFrame() {
        addWindowListener(new WindowClosingListener());
        setLayout(new BorderLayout());
        setSize(640, 480);
    }

    private void addSearchPanel() {
        searchPanel = new SearchArtikelPanel(eshop, this);
        add(searchPanel, BorderLayout.NORTH);
    }

    private void addArtikelTable() {
        List<Artikel> liste = eshop.getArtikelBestand();
        artikelPanel = new ArtikelTablePanel(liste);
        add(new JScrollPane(artikelPanel), BorderLayout.CENTER);
    }

    private void addBottomPanel() {
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bottom.add(new JLabel("Menge:"));
        mengeField = new JTextField(5);
        bottom.add(mengeField);

        toCartBtn = new JButton("In den Warenkorb");
        cartBtn   = new JButton("Warenkorb öffnen");
        bottom.add(toCartBtn);
        bottom.add(cartBtn);

        add(bottom, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        toCartBtn.addActionListener(e -> addToCart());
        cartBtn .addActionListener(e -> openCartDialog());
    }

    private void showFrame() {
        setVisible(true);
    }

    private void addToCart() {
        int row = artikelPanel.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Bitte erst einen Artikel auswählen.",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int menge = Integer.parseInt(mengeField.getText().trim());
            if (menge <= 0) throw new NumberFormatException();

            Artikel art = ((ui.gui.models.ArtikelTabelModel) artikelPanel.getModel()).getArtikelAt(row);

            eshop.artikelHinzufuegen(art, menge);
            JOptionPane.showMessageDialog(this,
                    menge + "× \"" + art.getArtikelBezeichnung() + "\" zum Warenkorb hinzugefügt.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            mengeField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Bitte eine gültige positive Zahl als Menge eingeben.",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openCartDialog() {
        JDialog dlg = new JDialog(this, "Ihr Warenkorb", true);
        dlg.getContentPane().add(new WarenkorbPanel(eshop));
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);

        // Nach Schließen evtl. Tabelle neu laden
        artikelPanel.updateArtikel(eshop.getArtikelBestand());
    }

    // ---------------- Mitarbeiter‐UI ----------------

    private void initializeMitarbeiterUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        searchPanel = new SearchArtikelPanel(eshop, this);
        AddArtikelPanel addPanel = new AddArtikelPanel(eshop, this);
        List<Artikel> artikel = eshop.getArtikelBestand();
        artikelPanel = new ArtikelTablePanel(artikel);


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.NORTH);


        JButton createEmployeeBtn = new JButton("Mitarbeiter:in anlegen");
        topPanel.add(createEmployeeBtn, BorderLayout.SOUTH);


        createEmployeeBtn.addActionListener(e -> openCreateEmployeeDialog());

        // Statt searchPanel jetzt topPanel einfügen
        add(topPanel, BorderLayout.NORTH);
        add(addPanel, BorderLayout.WEST);
        add(new JScrollPane(artikelPanel), BorderLayout.CENTER);

        setSize(800, 500);
        setVisible(true);
    }


    // ---------------- Interface‐Methoden ----------------

    @Override
    public void onArtikelAdded(Artikel artikel) {
        artikelPanel.updateArtikel(eshop.getArtikelBestand());
        try {
            eshop.speicherOption();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Fehler beim Speichern der Daten:\n" + e.getMessage(),
                    "Speicherfehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void onSearchResult(List<Artikel> artikels) {
        artikelPanel.updateArtikel(artikels);
    }

    private void openCreateEmployeeDialog() {
        new EinfuegenMitarbeiterDialog(this, eshop).setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShopClientGUI("ESHOP"));
    }
}
