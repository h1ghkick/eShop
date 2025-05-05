package entities;
import java.util.ArrayList;
import java.util.List;

public class Warenkorb {
    private int anzahl;


    private List<Artikel> artikelListe;

    public Warenkorb() {
        artikelListe = new ArrayList<>();
    }

    public void artikelHinzufuegen(Artikel artikel) {
        artikelListe.add(artikel);
    }

    public void warenkorbLeeren() {
        artikelListe.clear();
    }

    public void erhoeheAnzahl(int menge) {
        this.anzahl += menge;
    }

    public void zeigeWarenkorb() {
        for (Artikel artikel : artikelListe) {
            System.out.println(artikel);
        }
    }

    public int artikelAnzahl(Artikel artikel) {
        int counter = 0;
        for (Artikel a : artikelListe) {
            if (a.equals(artikel)) {
                counter++;
            }
        }
        return counter;
    }

    // Artikel mehrfach hinzufüge
    public void mehrfachArtikel(Artikel artikel ){
        if (artikelListe.contains(artikel)) {
            for (Artikel a : artikelListe) {
                if (a.equals(artikel)) {
                    artikel.erhoeheAnzahl();
                    return;
                }
            }
        } else {
            er
            artikelListe.add(artikel);
        }
    }


    //Stückzahl wenigermachen
    public void stueckzahl(){

    }

    //komplette Stückzahl leeren
--    public void stueckzahlLeeren(Artikel artikel){
        if (artikelListe)
    }

    //kaufen

    //rechnung mit datum Preisen und Kundeninfo

    //

}
