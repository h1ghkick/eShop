package ui.gui.gui.Panels;

import entities.Artikel;
import ui.gui.models.ArtikelTabelModel;

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
        ArtikelTabelModel tableModel = (ArtikelTabelModel) getModel();
        tableModel.setArtikel(artikel);
    }



}
