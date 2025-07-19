package ui.gui.models;

import entities.Artikel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WarenkorbTableModel extends AbstractTableModel {
    private final List<Map.Entry<Artikel,Integer>> entries;
    private final String[] columnNames = {
            "Nummer", "Bezeichnung", "Menge", "Einzelpreis", "Summe"
    };

    /**
     * @param waren Map aus Artikel → Menge, z.B. aus eshop.getWarenkorb().listeAusgeben()
     */
    public WarenkorbTableModel(Map<Artikel,Integer> waren) {
        // Kopie der Map-Einträge, um stabile Reihenfolge im Model zu haben
        this.entries = new ArrayList<>(waren.entrySet());
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
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Map.Entry<Artikel,Integer> entry = entries.get(rowIndex);
        Artikel art = entry.getKey();
        int menge   = entry.getValue();

        return switch (columnIndex) {
            case 0 -> art.getArtikelNummer();
            case 1 -> art.getArtikelBezeichnung();
            case 2 -> menge;
            case 3 -> art.getPreis();
            case 4 -> art.getPreis() * menge;
            default -> null;
        };
    }

    /**
     * Entfernt eine Zeile aus dem Model (z.B. bei Klick auf "Entfernen").
     */
    public void removeRow(int rowIndex) {
        entries.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    /**
     * Leert alle Zeilen im Model (z.B. nach abgeschlossenem Kauf).
     */
    public void removeAllRows() {
        if (!entries.isEmpty()) {
            int lastIndex = entries.size() - 1;
            entries.clear();
            fireTableRowsDeleted(0, lastIndex);
        }
    }
}
