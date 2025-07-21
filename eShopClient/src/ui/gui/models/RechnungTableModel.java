package ui.gui.models;

import entities.Artikel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RechnungTableModel extends AbstractTableModel {
    private final List<Map.Entry<Artikel, Integer>> entries;

    private final String[] columnNames = {
            "Nummer", "Bezeichnung", "Menge", "Einzelpreis", "Summe"
    };

    public RechnungTableModel(Map<Artikel, Integer> artikel) {
        this.entries = new ArrayList<>(artikel.entrySet());
    }

    @Override
    public int getRowCount() {
        return entries.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Artikel artikel = entries.get(row).getKey();
        int menge = entries.get(row).getValue();

        return switch (col) {
            case 0 -> artikel.getArtikelNummer();
            case 1 -> artikel.getArtikelBezeichnung();
            case 2 -> menge;
            case 3 -> artikel.getPreis();
            case 4 -> artikel.getPreis() * menge;
            default -> null;
        };
    }
}
