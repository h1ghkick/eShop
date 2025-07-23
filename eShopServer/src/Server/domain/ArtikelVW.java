package Server.domain;

import Server.Persistence.FilePersistenceManager;
import Server.Persistence.PersistenceManager;
import entities.*;
import entities.MassengutArtikel;
import exception.VielFaches;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArtikelVW {
    // Liste aller vorhandenen Artikel im Lager
    private List<Artikel> artikelBestand = new ArrayList<>();
    // Liste aller Ein-/Auslagerungsereignisse
    private List<Ereignis> ereignisse = new ArrayList<>();
    private PersistenceManager pm = new FilePersistenceManager();

    // Lädt Artikel aus Datei
    public synchronized void liesDaten(String datei) throws IOException {
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

    public synchronized void schreibeDaten(String datei) throws IOException  {
        // PersistenzManager für Schreibvorgänge öffnen
        pm.openForWriting(datei);

        for (Artikel artikel: artikelBestand) {
            pm.speicherArtikel(artikel);
        }

        // Persistenz-Schnittstelle wieder schließen
        pm.close();
    }

    // Artikel ins Lager einfügen (neu oder Bestand erhöhen)
<<<<<<< HEAD
    public void artikelEinfuegen(Artikel artikel, int menge, String benutzerEmail){
        if (artikel instanceof MassengutArtikel) {
            MassengutArtikel mArtikel = (MassengutArtikel) artikel;
            if (menge % mArtikel.getPackungsgroesse() != 0) {
                throw new VielFaches("Menge muss ein Vielfaches der Packungsgröße sein!");            }
        }

        for (Artikel a : artikelBestand) {
=======
    public synchronized void artikelEinfuegen(Artikel artikel, int menge, String benutzerEmail){
         for (Artikel a : artikelBestand) {
>>>>>>> main
            if (a.equals(artikel)) {
                a.setArtikelAnzahl(a.getArtikelAnzahl() + menge); //menge erhöhen
                logEreignis(a, menge, "Einlagerung", benutzerEmail);
                return;
            }
        }
        // Wenn Artikel noch nicht vorhanden:
        artikel.setArtikelAnzahl(menge);
        artikelBestand.add(artikel);
        logEreignis(artikel, menge, "Einlagerung", benutzerEmail);
    }

    /**
     * Lagert Artikel aus dem Bestand aus (z. B. beim Kauf).
     * Gibt true zurück, wenn erfolgreich, false wenn nicht genug da.
     */
<<<<<<< HEAD
    public boolean artikelAuslagern(Artikel artikel, int menge, String benutzerEmail) {
        if (artikel instanceof MassengutArtikel) {
            MassengutArtikel mArtikel = (MassengutArtikel) artikel;
            if (menge % mArtikel.getPackungsgroesse() != 0) {
                throw new VielFaches("Menge muss ein Vielfaches der Packungsgröße sein!");            }
        }

=======
    public synchronized boolean artikelAuslagern(Artikel artikel, int menge, String benutzerEmail) {
>>>>>>> main
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
    private synchronized void logEreignis(Artikel artikel, int menge, String aktion, String benutzerEmail) {
        Ereignis e = new Ereignis(LocalDate.now().getDayOfYear(), artikel, menge, aktion, benutzerEmail);
        ereignisse.add(e);
    }

    // Sucht Artikel anhand der Bezeichnung (exakter Treffer)
    public synchronized Artikel artikelDa(String artikelBezeichnung) {
        for (Artikel a : artikelBestand) {
            if (a.getArtikelBezeichnung().equals(artikelBezeichnung)) {
                return a;
            }
        }
        return null;
    }

    // Sucht alle Artikel, die den Suchbegriff enthalten (case-insensitive)
    public synchronized List<Artikel> sucheArtikel(String suchbegriff) {
        List<Artikel> treffer = new ArrayList<>();

        for (Artikel artikel : artikelBestand) {
            if (artikel.getArtikelBezeichnung().toLowerCase().contains(suchbegriff.toLowerCase())) {
                treffer.add(artikel);
            }
        }
        return treffer;
    }

    // Gibt gesamte Artikelliste zurück
    public synchronized List<Artikel> getAlleArtikel() {
        return new ArrayList<>(artikelBestand);
    }

    // Gibt alle protokollierten Ein-/Auslagerungen zurück
    public synchronized List<Ereignis> getEreignisse() {
        return new ArrayList<>(ereignisse);
    }
}
