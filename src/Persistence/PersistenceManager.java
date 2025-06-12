package Persistence;

import java.io.IOException;

import entities.*;

/**
 * @author teschke
 *
 * Allgemeine Schnittstelle für den Zugriff auf ein Speichermedium
 * (z.B. Datei oder Datenbank) zum Ablegen von beispielsweise
 * Bücher- oder Kundendaten.
 *
 * Das Interface muss von Klassen implementiert werden, die eine
 * Persistenz-Schnittstelle realisieren wollen.
 */
public interface PersistenceManager {

    public void openForReading(String datenquelle) throws IOException;

    public void openForWriting(String datenquelle) throws IOException;

    public boolean close();

    /**
     * Methode zum Einlesen der Buchdaten aus einer externen Datenquelle.
     *
     * @return Buch-Objekt, wenn Einlesen erfolgreich, false null
     */

    /**
     * Methode zum Schreiben der Buchdaten in eine externe Datenquelle.
     *
     * @param artikel Buch-Objekt, das gespeichert werden soll
     * @return true, wenn Schreibvorgang erfolgreich, false sonst
     */

    public Artikel ladeArtikel() throws IOException;

    public boolean speicherArtikel(Artikel artikel) throws IOException;

    public Kunde ladeKunde() throws IOException;

    public boolean speicherKunde(Kunde kunde) throws IOException;

    public Mitarbeiter ladeMitarbeiter() throws  IOException;
    public boolean speicherMitarbeiter(Mitarbeiter mitarbeiter) throws  IOException;

}
