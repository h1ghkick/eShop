package ui.cui;

import entities.Artikel;
import domain.EShop;

import ui.cui.gui.Panels.*;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Alternative Version der Bibliotheks-GUI, bei der die im Layout vorgesehenen Bereich für das
 * - Hinzufügen neuer Bücher,
 * - Suchen nach Büchern und
 * - Ausgeben von Büchern
 * nicht mehr innerhalb dieser Klasse definiert werden, sondern in Klassen im Unterpaket "panels".
 * Jede der genannten Aufgaben wird dort in einer Unterklasse von Panel implementiert;
 * hier werden nur noch Objekte der Unterklassen erzeugt und in das Layout des Frames eingefügt.
 * Die Kommunikation zwischen den Panels und dem (zentralen) Frame erfolgt dabei über Interfaces,
 * die in den Panel-Unterklassen definiert und in der Frame-Unterklasse implementiert werden.
 *
 * @author thorsten
 *
 */
public class ShopClientGUI extends JFrame
        implements SearchArtikelPanel.SearchResultListener, AddArtikelPanel.AddBookListener {

    private EShop eshop;

    private SearchArtikelPanel searchPanel;
    private AddArtikelPanel addPanel;
    //	private BooksListPanel booksPanel;
    private ArtikelTablePanel artikelPanel;

    public ShopClientGUI(String titel) {
        super(titel);

        try {
            eshop = new EShop("Kunde.txt","Artikel.txt", "Mitarbeiter.txt");
            initialize();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void initialize() {

        // Menü definieren
        JMenuBar menuBar = new ShopMenueBar(eshop);
        setJMenuBar(menuBar);

        // Klick auf Kreuz / roten Kreis (Fenster schließen) behandeln lassen:
        // A) Mittels Default Close Operation
//		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // B) Mittels WindowAdapter (für Sicherheitsabfrage)
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowClosingListener());

        // Layout des Frames: BorderLayout
        this.setLayout(new BorderLayout());

        // North
        searchPanel = new SearchArtikelPanel(eshop, this);

        // West
        addPanel = new AddArtikelPanel(eshop, this);

        // Center
        java.util.List<Artikel> buecher = eshop.getArtikelBestand();
        // (wahlweise Anzeige als Liste oder Tabelle)
//		booksPanel = new BooksListPanel(buecher);
        artikelPanel = new ArtikelTablePanel(buecher);
        JScrollPane scrollPane = new JScrollPane(artikelPanel);

        // "Zusammenbau" in BorderLayout des Frames
        this.add(searchPanel, BorderLayout.NORTH);
        this.add(addPanel, BorderLayout.WEST);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setSize(640, 480);
        this.setVisible(true);
    }

    /*
     * (non-Javadoc)
     *
     * Listener, der Benachrichtungen erhält, wenn im AddBookPanel ein Buch eingefügt wurde.
     * (Als Reaktion soll die Bücherliste aktualisiert werden.)
     * @see bib.local.ui.gui.panels.AddBookPanel.AddBookListener#onBookAdded(bib.local.entities.Buch)
     */
    @Override
    public void onArtikelAdded(Artikel artikel) {
        // Ich lade hier einfach alle Bücher neu und lasse sie anzeigen
        java.util.List<Artikel> artikels = eshop.getArtikelBestand();
        artikelPanel.updateArtikel(artikels);
    }

    /*
     * (non-Javadoc)
     *
     * Listener, der Benachrichtungen erhält, wenn das SearchBooksPanel ein Suchergebnis bereitstellen möchte.
     * (Als Reaktion soll die Bücherliste aktualisiert werden.)
     * @see bib.local.ui.gui.swing.panels.SearchBooksPanel.SearchResultListener#onSearchResult(java.util.List)
     */
    @Override
    public void onSearchResult(java.util.List<Artikel> artikels) {
        artikelPanel.updateArtikel(artikels);
    }


    public static void main(String[] args) {
        // Start der Anwendung (per anonymer Klasse)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ShopClientGUI gui = new ShopClientGUI("ESHOP");
            }
        });

//		// Start der Anwendung (per Lambda-Expression)
//		SwingUtilities.invokeLater(() -> { BibGuiMitKomponenten gui = new BibGuiMitKomponenten("Bibliothek"); });
    }
}