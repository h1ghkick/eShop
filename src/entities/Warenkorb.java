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

    public List<Artikel> listeAusgeben(){
        return artikelListe;
    }



//    public int artikelAnzahl(Artikel artikel) {
//        int counter = 0;
//        for (Artikel a : artikelListe) {
//            if (a.equals(artikel)) {
//                counter++;
//            }
//        }
//        return counter;
//    }

    public void mehrfachArtikel(Artikel artikel, int anzahl) {
        for (int i = 0; i < anzahl; i++) {
            if (artikelListe.contains(artikel)){
                anzahl++;
            } else {
                System.out.println("Funktioniert nicht");

            }

        }
    }
}

//Todo: Stückzahl verringern methode


//Todo: komplette Stückzahl leeren methode

//Todo: kaufen methode

//Todo: rechnung mit datum Preisen und Kundeninfo methode



