package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

public class Rechnung implements Serializable {
    private LocalDate kaufDatum;

    public Rechnung() {
        kaufDatum = LocalDate.now();
    }

    // Einfache Methode, um die Rechnung zu stellen und im CUI anzuzeigen
    public void rechnungStellen(Kunde kunde, Map<Artikel, Integer> artikelListe) {
        double gesamtpreis = 0.0;

        // Rechnungskopf mit Kundendaten und Datum
        System.out.println("====== RECHNUNG ======");
        System.out.println("Kunde: " + kunde.getFirstName() + " " + kunde.getLastName());
        System.out.println("Adresse: " + kunde.getAdresse());
        System.out.println("Datum: " + kaufDatum);
        System.out.println();
        System.out.println("Gekaufte Artikel:");

        // Artikel und deren Preise ausgeben
        for (Map.Entry<Artikel, Integer> entry : artikelListe.entrySet()) {
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();
            double preis = artikel.getPreis();
            double artikelGesamt = preis * menge;
            gesamtpreis += artikelGesamt;

            // Ausgabe der Artikelinformationen
            System.out.println(artikel.getArtikelBezeichnung() + " - Anzahl: " + menge + " - Preis pro Stück: " + preis + " - Gesamt: " + artikelGesamt);
        }

        // Gesamtsumme der Rechnung
        System.out.println();
        System.out.println("Gesamtpreis: " + gesamtpreis);
    }
}

