package Server.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Server.Persistence.FilePersistenceManager;
import Server.Persistence.PersistenceManager;
import entities.Kunde;

public class KundenVW {
    private List<Kunde> kundenListe = new ArrayList<>();
    private PersistenceManager pm = new FilePersistenceManager();

    public void liesDaten(String datei) throws IOException {
        // PersistenzManager für Lesevorgänge öffnen
        pm.openForReading(datei);

        Kunde einKunde;
        do {
            // Buch-Objekt einlesen
            einKunde = pm.ladeKunde();
            if (einKunde != null) {
                // Buch in Liste einfügen
                einfuegenKunden(einKunde);
            }
        } while (einKunde != null);

        // Persistenz-Schnittstelle wieder schließen
        pm.close();
    }

    public synchronized void schreibeDaten(String datei) throws IOException  {
        // PersistenzManager für Schreibvorgänge öffnen
        pm.openForWriting(datei);

        for (Kunde kunde: kundenListe) {
            pm.speicherKunde(kunde);
        }

        // Persistenz-Schnittstelle wieder schließen
        pm.close();
    }

    public synchronized void einfuegenKunden(Kunde kunde) {
        kundenListe.add(kunde);
    }

    public synchronized boolean istRegistriert(String email) {
        for (Kunde k : kundenListe) {
            if (k.getMail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public synchronized List<Kunde> getAlleKunden() {
        return new ArrayList<>(kundenListe);
    }
}

