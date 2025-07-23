package entities;

public class MassengutArtikel extends Artikel {
    private int packungsgroesse; //  6er-Pack

    public MassengutArtikel(int artikelAnzahl, int artikelNummer, String artikelBezeichnung, double preis, int packungsgroesse) {
        super(artikelAnzahl, artikelNummer, artikelBezeichnung, preis);
        this.packungsgroesse = packungsgroesse;
        //Wichtig für die unterscheidung ob es Massengutartikel ist
        if (artikelAnzahl % packungsgroesse != 0)
            throw new IllegalArgumentException("Initiale Anzahl muss ein Vielfaches der Packungsgröße sein.");
    }

    public int getPackungsgroesse() {
        return packungsgroesse;
    }

    public void setPackungsgroesse(int packungsgroesse) {
        this.packungsgroesse = packungsgroesse;
    }

    @Override
    public void setArtikelAnzahl(int artikelAnzahl) {
        if (artikelAnzahl % packungsgroesse != 0) {
            throw new IllegalArgumentException("Die Anzahl muss ein Vielfaches der Packungsgröße sein.");
        }
        super.setArtikelAnzahl(artikelAnzahl);
    }

    // Hilfsmethode zum Hinzufügen in Packungseinheiten
    public void fuegePackungenHinzu(int anzahlPackungen) {
        super.setArtikelAnzahl(getArtikelAnzahl() + anzahlPackungen * packungsgroesse);
    }

    // Hilfsmethode zum Entfernen in Packungseinheiten
    public void entfernePackungen(int anzahlPackungen) {
        int neueAnzahl = getArtikelAnzahl() - anzahlPackungen * packungsgroesse;
        if (neueAnzahl < 0) throw new IllegalArgumentException("Nicht genügend Lagerbestand.");
        super.setArtikelAnzahl(neueAnzahl);
    }

    @Override
    public String toString() {
        return super.toString() + "(Massengutartikel – Packung zu " + packungsgroesse + ")";
    }


}