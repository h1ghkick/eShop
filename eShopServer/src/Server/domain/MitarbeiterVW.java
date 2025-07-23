package Server.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Server.Persistence.FilePersistenceManager;
import Server.Persistence.PersistenceManager;
import entities.Mitarbeiter;

public class MitarbeiterVW {
    private List<Mitarbeiter> mitarbeiterListe = new ArrayList<>();
    private PersistenceManager pm = new FilePersistenceManager();

    public void liesDaten(String datei) throws IOException {
        // PersistenzManager für Lesevorgänge öffnen
        pm.openForReading(datei);

        Mitarbeiter einMitarbeiter;
        do {
            // Buch-Objekt einlesen
            einMitarbeiter = pm.ladeMitarbeiter();
            if (einMitarbeiter != null) {
                // Buch in Liste einfügen
                einfuegenMitarbeiter(einMitarbeiter);
            }
        } while (einMitarbeiter != null);

        // Persistenz-Schnittstelle wieder schließen
        pm.close();
    }

    public synchronized void schreibeDaten(String datei) throws IOException  {
        // PersistenzManager für Schreibvorgänge öffnen
        pm.openForWriting(datei);

        for (Mitarbeiter mitarbeiter: mitarbeiterListe) {
            pm.speicherMitarbeiter(mitarbeiter);
        }

        // Persistenz-Schnittstelle wieder schließen
        pm.close();
    }

    public synchronized void einfuegenMitarbeiter(Mitarbeiter arbeiter) {
        mitarbeiterListe.add(arbeiter);
    }


    public synchronized boolean istRegistriert(String email) {
        for (Mitarbeiter m : mitarbeiterListe) {
            if (m.getMail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public synchronized List<Mitarbeiter> getAlleMitarbeiter() {
        return new ArrayList<>(mitarbeiterListe);
    }
}
