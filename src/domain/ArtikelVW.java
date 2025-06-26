package domain;

import Persistence.FilePersistenceManager;
import Persistence.PersistenceManager;
import entities.Artikel;
import entities.Ereignis;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArtikelVW {
    private List<Artikel> artikelBestand = new ArrayList<>();
    private List<Ereignis> ereignisse = new ArrayList<>();
    private PersistenceManager pm = new FilePersistenceManager();

    public void liesDaten(String datei) throws IOException {
        // PersistenzManager für Lesevorgänge öffnen
        pm.openForReading(datei);

        Artikel einArtikel;
        do {
            // Buch-Objekt einlesen
            einArtikel = pm.ladeArtikel();
            if (einArtikel != null) {
                // Buch in Liste einfügen
                artikelEinfuegen(einArtikel, einArtikel.getArtikelAnzahl(), einArtikel.getArtikelBezeichnung());
            }
        } while (einArtikel != null);

        // Persistenz-Schnittstelle wieder schließen
        pm.close();
    }

    public void schreibeDaten(String datei) throws IOException  {
        // PersistenzManager für Schreibvorgänge öffnen
        pm.openForWriting(datei);

        for (Artikel artikel: artikelBestand) {
            pm.speicherArtikel(artikel);
        }

        // Persistenz-Schnittstelle wieder schließen
        pm.close();
    }

    // Artikel ins Lager einfügen (neu oder Bestand erhöhen)
    public void artikelEinfuegen(Artikel artikel, int menge, String benutzerEmail){
         for (Artikel a : artikelBestand) {
            if (a.equals(artikel)) {
                a.setArtikelAnzahl(a.getArtikelAnzahl() + menge);
                logEreignis(a, menge, "Einlagerung", benutzerEmail);
                return;
            }
        }
        artikel.setArtikelAnzahl(menge);
        artikelBestand.add(artikel);
        logEreignis(artikel, menge, "Einlagerung", benutzerEmail);
    }

    // Artikelbestand verringern (z.B. beim Kauf)
    public boolean artikelAuslagern(Artikel artikel, int menge, String benutzerEmail) {
        for (Artikel a : artikelBestand) {
            if (a.equals(artikel)) {
                if (a.getArtikelAnzahl() >= menge) {
                    a.setArtikelAnzahl(a.getArtikelAnzahl() - menge);
                    if(a.getArtikelAnzahl() == 0){
                        a.setArtikelVerfuegbar(false);
                    }
                    logEreignis(a, menge, "Auslagerung", benutzerEmail);
                    return true;
                }
                return false;// Nicht genug Bestand
            }
        }
        return false; // Artikel nicht gefunden
    }

    // Hilfsmethode zum Protokollieren von Ein-/Auslagerungen
    private void logEreignis(Artikel artikel, int menge, String aktion, String benutzerEmail) {
        Ereignis e = new Ereignis(LocalDate.now().getDayOfYear(), artikel, menge, aktion, benutzerEmail);
        ereignisse.add(e);
    }
    public Artikel artikelDa(String artikelBezeichnung) {
        for (Artikel a : artikelBestand) {
            if (a.getArtikelBezeichnung().equals(artikelBezeichnung)) {
                return a;
            }
        }
        return null;
    }
    public List<Artikel> getAlleArtikel() {
        return artikelBestand;
    }

    public List<Ereignis> getEreignisse() {
        return ereignisse;
    }
}
