package ui.gui.gui.Panels;

import javax.swing.*;
import entities.Artikel;

// Wichtig: Das BooksListPanel _ist eine_ JList und damit eine Component;
// es kann daher in das Layout eines anderen Containers
// (in unserer Anwendung des Frames) eingefügt werden.
public class ArtikelListPanel extends JList<Artikel> {

    public ArtikelListPanel(java.util.List<Artikel> artikel) {
        super();

        // ListModel erzeugen ...
        DefaultListModel<Artikel> listModel = new DefaultListModel<>();
        // ... bei JList "anmelden" und ...
        setModel(listModel);
        // ... Daten an Model übergeben
        updateBooks(artikel);
    }

    public void updateBooks(java.util.List<Artikel> artikel) {

        // Sortierung (mit Lambda-Expression)
        artikel.sort((b1, b2) -> b1.getArtikelAnzahl() - b2.getArtikelAnzahl());	// Sortierung nach Nummer
//		artikel.sort((b1, b2) -> b1.getTitel().compareTo(b2.getTitel()));	// Sortierung nach Titel

        // ListModel von JList holen und ...
        DefaultListModel<Artikel> listModel = (DefaultListModel<Artikel>) getModel();

        // ... Inhalt aktualisieren
        listModel.clear();
        listModel.addAll(artikel);
    }
}
