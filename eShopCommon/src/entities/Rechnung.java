package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Rechnung implements Serializable {
    private LocalDate kaufDatum;

        private Kunde kunde;
        private Map<Artikel, Integer> artikelListe;
        private double gesamtpreis;

        public Rechnung() {
            this.kaufDatum = LocalDate.now();
        }

    public void rechnungStellen(Kunde kunde, Map<Artikel, Integer> artikelListe) {
        this.kunde = kunde;
        this.artikelListe = new HashMap<>(artikelListe); // ✅ Kopie machen!
        this.gesamtpreis = 0.0;

        for (Map.Entry<Artikel, Integer> entry : artikelListe.entrySet()) {
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();
            double artikelGesamt = artikel.getPreis() * menge;
            this.gesamtpreis += artikelGesamt;
        }
    }


    public Map<Artikel, Integer> getArtikelListe() {
            return artikelListe;
        }

        public double getGesamtpreis() {
            return gesamtpreis;
        }

        public Kunde getKunde() {
            return kunde;
        }

        public LocalDate getKaufDatum() {
            return kaufDatum;
        }
    }

