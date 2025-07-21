package ui.gui;

import entities.*;
import exception.MengeNichtVerfuegbar;
import exception.MengeNichtVerfuegbar;
import ui.gui.gui.Panels.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class ShopClientGUI extends JFrame
        implements SearchArtikelPanel.SearchResultListener, AddArtikelPanel.AddArtikelListener {

    private EShopRemote eshop;
    private SearchArtikelPanel searchPanel;
    private ArtikelTablePanel artikelPanel;

    // Bottom‐Panel Komponenten
    private JTextField mengeField;
    private JButton toCartBtn;
    private JButton cartBtn;

    private static int DEFAULT_PORT = 1099;

    public ShopClientGUI(String titel) {
        super(titel);
        try {
            eshop = (EShopRemote) Naming.lookup("rmi://localhost:1099/EShopService");
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
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    // ---------------- Kunden‐UI ----------------

    private void initializeKundenUI() throws RemoteException {
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

    private void addArtikelTable() throws RemoteException {
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
        cartBtn.addActionListener(e -> {
            try {
                openCartDialog();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex); // das ist ok, weil das nicht anders behandelbar ist
            }
        });
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

            try {
                eshop.artikelHinzufuegen(art, menge);
            } catch (MengeNichtVerfuegbar e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                return; // <- FEHLER: ABBRUCH!
            }

            // Nur wenn kein Fehler: Erfolgsmeldung
            JOptionPane.showMessageDialog(this,
                    menge + "× \"" + art.getArtikelBezeichnung() + "\" zum Warenkorb hinzugefügt.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            mengeField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Bitte eine gültige positive Zahl als Menge eingeben.",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    private void openCartDialog() throws RemoteException {
        JDialog dlg = new JDialog(this, "Ihr Warenkorb", true);
        dlg.getContentPane().add(new WarenkorbPanel(eshop));
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);

        // Nach Schließen evtl. Tabelle neu laden
        artikelPanel.updateArtikel(eshop.getArtikelBestand());
    }

    // ---------------- Mitarbeiter‐UI ----------------

    private void initializeMitarbeiterUI() throws RemoteException {
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
    public void onArtikelAdded(Artikel artikel) throws RemoteException {
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
