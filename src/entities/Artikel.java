package entities;

public class Artikel {
    private int artikelAnzahl;
    private int artikelNummer;
    private String artikelBezeichnung;
    private boolean artikelVerfuegbar;
    private double preis;

    // Neuer Konstruktor mit Preis
    public Artikel(int artikelAnzahl, int artikelNummer, String artikelBezeichnung, double preis, boolean artikelVerfuegbar){
        this.artikelVerfuegbar = artikelVerfuegbar;
        this.artikelAnzahl = artikelAnzahl;
        this.artikelNummer = artikelNummer;
        this.artikelBezeichnung = artikelBezeichnung;
        this.preis = preis;
    }

    // Überladener Konstruktor ohne Verfügbarkeitsangabe (Standard: true)
    public Artikel(int artikelAnzahl, int artikelNummer, String artikelBezeichnung, double preis){
        this(artikelAnzahl, artikelNummer, artikelBezeichnung, preis, true);
    }

    public Artikel(int artikelAnzahl, int artikelNummer, String titel, int preisAlsInt) {
        // delegiere an den echten Konstruktor mit double
        this(artikelAnzahl, artikelNummer, titel, (double) preisAlsInt);
    }


    @Override
    public String toString(){
        String verfuegbarkeit = artikelVerfuegbar ? "verfügbar" : "nicht verfügbar";
        return ("NR: " + artikelNummer + " || Titel: " + artikelBezeichnung + " || Anzahl: " + artikelAnzahl + " || Preis: " + preis + "€ || " + verfuegbarkeit + "\n");
    }


    @Override
    public boolean equals(Object andererArtikel){
        if(andererArtikel instanceof Artikel){
            return ((this.artikelNummer == ((Artikel) andererArtikel).artikelNummer)
                    && (this.artikelBezeichnung.equals(((Artikel) andererArtikel).artikelBezeichnung)));
        } else {
            return false;
        }
    }



    public double getPreis() {return preis;}
    public void setPreis(double preis) {this.preis = preis;}
    public int getArtikelAnzahl(){
        return artikelAnzahl;
    }
    public int getArtikelNummer(){
        return artikelNummer;
    }
    public String getArtikelBezeichnung(){return artikelBezeichnung;}
    public boolean getArtikelVerfuegbar(){
        return artikelVerfuegbar;
    }
    public void setArtikelVerfuegbar(boolean artikelVerfuegbar){
        this.artikelVerfuegbar = artikelVerfuegbar;
    }
    public void setArtikelAnzahl(int artikelAnzahl){
        this.artikelAnzahl = artikelAnzahl;
    }
}
