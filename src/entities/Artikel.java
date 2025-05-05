package entities;

public class Artikel {
    private int artikelAnzahl;
    private int artikelNummer;
    private String artikelBezeichnung;
    private boolean artikelVerfuegbar;

    public Artikel(int artikelAnzahl,int artikelNummer, String artikelBezeichnung, boolean artikelVerfuegbar){
        this.artikelVerfuegbar =artikelVerfuegbar;
        this.artikelAnzahl = artikelAnzahl;
        this.artikelNummer = artikelNummer;
        this.artikelBezeichnung = artikelBezeichnung;
    }
    public Artikel(int artikelAnzahl,int artikelNummer, String artikelBezeichnung){
        this(artikelAnzahl,artikelNummer,artikelBezeichnung,true);
    }

    public String toString(){
        String verfuegbarkeit = artikelVerfuegbar ? "verfuegbar" : "ausgeliehen";
        return ("NR: " + artikelNummer + "|| Titel: " + artikelBezeichnung + "|| Anzahl: " + artikelAnzahl + "|| " +verfuegbarkeit);
    }

    public boolean equals(Object andererArtikel){
        if(andererArtikel instanceof Artikel){
            return((this.artikelNummer == ((Artikel) andererArtikel).artikelNummer)
                    && (this.artikelBezeichnung.equals(((Artikel) andererArtikel).artikelBezeichnung)));

        } else {
            return  false;
        }
    }




    public int getArtikelAnzahl(){
        return artikelAnzahl;
    }
    public int getArtikelNummer(){
        return artikelNummer;
    }
    public String getArtikelBezeichnung(){
        return artikelBezeichnung;
    }
    public boolean getArtikelVerfuegbar(){
        return artikelVerfuegbar;
    }
}
