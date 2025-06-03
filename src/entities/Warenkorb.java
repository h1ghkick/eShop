package entities;
import entities.Rechnung;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import entities.Warenkorb;
import entities.Rechnung;

public class Warenkorb {
    private Map<Artikel, Integer> artikelListe;


    public Warenkorb() {
        artikelListe = new HashMap<>();
    }

    //Artikel mit einer bestimmten Stückzahl in den Warenkorb einfügen

    public void artikelHinzufuegen(Artikel artikel, int menge) {
        //entweder die aktuelle menge nehmen oder er nimmt defaultValue = 0;
        int aktuelleMenge = artikelListe.getOrDefault(artikel, 0);
        //addieren
        artikelListe.put(artikel, aktuelleMenge + menge);
    }

    //Warenkorb komplett Leeren
    public void warenkorbLeeren() {
        artikelListe.clear();
    }

    //Liste ausgeben
    public Map<Artikel, Integer> listeAusgeben() {
        return artikelListe;
    }


    //Anzahlverrringern
    public String artikelEntfernen(Artikel artikel, int menge) {
        if (artikelListe.containsKey(artikel)) {
            //Aktuelle menge herausfinden
            int aktuelleMenge = artikelListe.get(artikel);
            // neue menge berechnen
            int neueMenge = aktuelleMenge - menge;
            if (neueMenge > 0) {
                artikelListe.put(artikel, neueMenge); // neue Menge setzen
            } else {
                artikelListe.remove(artikel); // ganz entfernen, wenn Menge 0 oder kleiner
            }
        } else {
            return "Artikel ist nicht im Warenkorb.";
        }
        return "";
    }


    //Todo: komplette Stückzahl leeren methode
    public String artikelKomplettEntfernen(Artikel artikel) {
        if (artikelListe.containsKey(artikel)) {
            artikelListe.remove(artikel); // entfernt den Artikel aus der Map
        } else {
            return "Artikel ist nicht mehr im Warenkorb.";
        }
        return "";
    }
    }







