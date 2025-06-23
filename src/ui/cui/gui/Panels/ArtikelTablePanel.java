package ui.cui.gui.Panels;

import entities.Artikel;
import ui.cui.models.ArtikelTabelModel;

import javax.swing.*;
import java.util.List;

public class ArtikelTablePanel extends JTable {

    public ArtikelTablePanel(List<Artikel> artikel) {
        super();

        // TableModel erzeugen und gewünschte Sortierung übergeben ...
        ArtikelTabelModel tableModel = new ArtikelTabelModel(artikel);

        // ... bei JTable "anmelden" und ...
        setModel(tableModel);
    }

    public void updateArtikel(List<Artikel> artikel) {
        // Sortierung (mit Lambda-Expression)

        artikel.sort((b1, b2) -> b1.getArtikelAnzahl() - b2.getArtikelAnzahl());	// Sortierung nach Nummer
		// artikel.sort((b1, b2) -> b1.getArtikelBezeichnung().compareTo(b2.getArtikelBezeichnung()));	// Sortierung nach Titel

        // TableModel von JTable holen und ...
        ArtikelTabelModel tableModel = (ArtikelTabelModel) getModel();
//		// ... Inhalt aktualisieren
        tableModel.setArtikel(artikel);
    }
}
