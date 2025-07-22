package Server.Persistence;

import entities.*;
import java.io.IOException;

public interface PersistenceManager {

    /**
     * Öffnet die Datei zum Lesen.
     * @param datenquelle Dateiname oder Pfad
     */
    public void openForReading(String datenquelle) throws IOException;

    /**
     * Öffnet die Datei zum Schreiben (überschreibt).
     * @param datenquelle Dateiname oder Pfad
     */
    public void openForWriting(String datenquelle) throws IOException;

    /**
     * Schließt Reader und Writer, wenn sie verwendet wurden.
     * @return true, wenn Schließen erfolgreich
     */
    public boolean close();

    /**
     * Liest einen Artikel aus der Datei (z. B. 5 Zeilen).
     * @return Artikel-Objekt oder null am Ende
     */
    public Artikel ladeArtikel() throws IOException;

    /**
     * Speichert einen Artikel in die Datei (z. B. als 5 Zeilen).
     * @param artikel Artikel, der gespeichert werden soll
     * @return true bei Erfolg
     */
    public boolean speicherArtikel(Artikel artikel) throws IOException;

    /**
     * Liest einen Kunden aus der Datei (z. B. 7 Zeilen).
     * @return Kunde-Objekt oder null am Ende
     */
    public Kunde ladeKunde() throws IOException;

    /**
     * Speichert einen Kunden in die Datei.
     * @param kunde Kunde, der gespeichert werden soll
     * @return true bei Erfolg
     */
    public boolean speicherKunde(Kunde kunde) throws IOException;

    /**
     * Liest einen Mitarbeiter aus der Datei.
     * @return Mitarbeiter-Objekt oder null am Ende
     */
    public Mitarbeiter ladeMitarbeiter() throws IOException;

    /**
     * Speichert einen Mitarbeiter in die Datei.
     * @param mitarbeiter Mitarbeiter, der gespeichert werden soll
     * @return true bei Erfolg
     */
    public boolean speicherMitarbeiter(Mitarbeiter mitarbeiter) throws IOException;
}
