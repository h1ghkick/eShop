package ui.gui.models;

import entities.Artikel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ArtikelTabelModel extends AbstractTableModel {

    private List<Artikel> artikel;
    private String[] spaltenNamen = { "Nummer","Titel", "Preis", "Menge" };

    public ArtikelTabelModel(List<Artikel> aktuelleArtikel) {
        super();
        artikel = new ArrayList<>();
        setArtikel(aktuelleArtikel); // Dadurch ist auch beim Start alles sortiert!
    }


    public void setArtikel(List<Artikel> aktuelleArtikel) {
        artikel.clear();
        aktuelleArtikel.stream()
                .sorted((a, b) -> a.getArtikelBezeichnung().trim().compareToIgnoreCase(b.getArtikelBezeichnung().trim()))
                .forEach(artikel::add);
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
            case 3 -> gewaehlterArtikel.getArtikelAnzahl();
            default -> null;
        };
    }

    /** Liefert das Artikel-Objekt in der gegebenen Zeile */
    public Artikel getArtikelAt(int row) {
        return artikel.get(row);
    }
}

