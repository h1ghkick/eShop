package ui.gui.models;

import entities.Artikel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ArtikelTabelModel extends AbstractTableModel {

    private List<Artikel> artikel;
    private String[] spaltenNamen = { "Nummer","Titel", "Preis" };

    public ArtikelTabelModel(List<Artikel> aktuelleArtikel) {
        super();
        // Ich erstelle eine Kopie der Bücherliste,
        // damit beim Aktualisieren (siehe Methode setBooks())
        // keine unerwarteten Seiteneffekte entstehen.
        artikel = new ArrayList<>(aktuelleArtikel);
    }

    public void setArtikel(List<Artikel> aktuelleArtikel) {
        artikel.clear();
        artikel.addAll(aktuelleArtikel);
        // JTable benachrichtigen, dass Daten geändert wurden.
        // (Löst neues Zeichnen aus.)
        fireTableDataChanged();
    }


    /*
     * Ab hier überschriebene Methoden mit Informationen,
     * die eine JTable von jedem TableModel erwartet:
     * - Anzahl der Zeilen
     * - Anzahl der Spalten
     * - Benennung der Spalten
     * - Wert einer Zelle in Zeile / Spalte
     */

    @Override
    public int getRowCount() {
        return artikel.size();
    }

    @Override
    public int getColumnCount() {
        return spaltenNamen.length;
    }

    @Override
    public String getColumnName(int col) {
        return spaltenNamen[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Artikel gewaehlterArtikel = artikel.get(row);
        return switch (col) {
            case 0 -> gewaehlterArtikel.getArtikelNummer();
            case 1 -> gewaehlterArtikel.getArtikelBezeichnung();
            case 2 -> gewaehlterArtikel.getPreis();
            default -> null;
        };
    }

    /** Liefert das Artikel-Objekt in der gegebenen Zeile */
    public Artikel getArtikelAt(int row) {
        return artikel.get(row);
    }
}

