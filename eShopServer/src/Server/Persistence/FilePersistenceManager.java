package Server.Persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import entities.*;


/**
 * @author teschke
 *
 * Realisierung einer Schnittstelle zur persistenten Speicherung von
 * Daten in Dateien.
 *
 */
public class FilePersistenceManager implements PersistenceManager {

    private BufferedReader reader = null; //liest textzeiel aus Datei
    private PrintWriter writer = null; //schreibt Textzeilen in Datei

    //Öffnet Datei zum Schreiben
    public void openForReading(String datei) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(datei));
    }
    // Schließt Reader und Writer (wenn offen)
    public void openForWriting(String datei) throws IOException {
        writer = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(datei)));
    }

    public boolean close() {
        if (writer != null)
            writer.close();

        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                return false;
            }
        }

        return true;
    }



    @Override
    public Artikel ladeArtikel() throws IOException {
        String typ = liesZeile(); // artikel oder massengut
        if (typ == null) return null;

        String artikelAnzahl = liesZeile();
        if (artikelAnzahl == null) return null;
        int anzahl = Integer.parseInt(artikelAnzahl);

        String artikelNummer = liesZeile();
        if (artikelNummer == null) return null;
        int nummer = Integer.parseInt(artikelNummer);

        String bezeichnung = liesZeile();
        if (bezeichnung == null) return null;

        String preis = liesZeile();
        if (preis == null) return null;
        double preisWert = Double.parseDouble(preis);

        String verf = liesZeile();
        if (verf == null) return null;
        boolean verfuegbar = verf.equals("t");

//        String artikelTyp = liesZeile();
//        if (artikelTyp == null) return null;

        if (typ.equals("massengut")) {
            String packung = liesZeile();
            if (packung == null) return null;
            int packungsgroesse = Integer.parseInt(packung);

            MassengutArtikel mArtikel = new MassengutArtikel(anzahl, nummer, bezeichnung, preisWert, packungsgroesse);
            mArtikel.setArtikelVerfuegbar(verfuegbar);
            return mArtikel;
        } else {
            return new Artikel(anzahl, nummer, bezeichnung, preisWert, verfuegbar);
        }
    }



   @Override
    public Kunde ladeKunde() throws IOException {
        //String firstName
        String firstName = liesZeile();
        if(firstName ==null){
            return null;
        }

        //String lastName
        String lastName = liesZeile();
        if (lastName == null) {
            return null;
        }

        //String email
        String email = liesZeile();
        if (email == null) {
            // keine Daten mehr vorhanden
            return null;
        }

        //String password
        String password = liesZeile();
        if (password == null) {
            // keine Daten mehr vorhanden
            return null;
        }

        //String Strasse
        String strasse = liesZeile();
        if (strasse == null) {
            // keine Daten mehr vorhanden
            return null;
        }
        //String Wohnort
        String wohnort = liesZeile();
        if (wohnort == null) {
            // keine Daten mehr vorhanden
            return null;
        }
        //Int postleitzahl
        String postleitzahl = liesZeile();
        if (postleitzahl == null) {
            return null;
        }
        int postleitzahlToString = Integer.parseInt(postleitzahl);

        // neues Objekt anlegen und zurückgeben
        return new Kunde(firstName,lastName,email,password,strasse,wohnort,postleitzahlToString);
    }

    @Override
    public Mitarbeiter ladeMitarbeiter() throws IOException {
        //String firstName
        String firstName = liesZeile();
        if(firstName ==null){
            return null;
        }

        //String lastName
        String lastName = liesZeile();
        if (lastName == null) {
            return null;
        }

        //String email
        String email = liesZeile();
        if (email == null) {
            // keine Daten mehr vorhanden
            return null;
        }

        //String password
        String password = liesZeile();
        if (password == null) {
            // keine Daten mehr vorhanden
            return null;
        }

        // neues Objekt anlegen und zurückgeben
        return new Mitarbeiter(firstName,lastName,email,password);
    }

    //Schreibt in die Datei
    @Override
    public boolean speicherArtikel(Artikel artikel) throws IOException {
        if (artikel instanceof MassengutArtikel) {
            schreibeZeile("massengut");
        } else {
            schreibeZeile("artikel");
        }

        schreibeZeile(String.valueOf(artikel.getArtikelAnzahl()));
        schreibeZeile(String.valueOf(artikel.getArtikelNummer()));
        schreibeZeile(artikel.getArtikelBezeichnung());
        schreibeZeile(String.valueOf(artikel.getPreis()));
        String verfuegbar = artikel.getArtikelVerfuegbar() ? "t" : "f";
        schreibeZeile(verfuegbar);

        // Speichere zusätzlich Packungsgröße bei Massengut
        if (artikel instanceof MassengutArtikel) {
            schreibeZeile(String.valueOf(((MassengutArtikel) artikel).getPackungsgroesse()));
        }

        return true;
    }


    //Schreibt in die Datei
    @Override
    public boolean speicherKunde(Kunde kunde) throws IOException {
        // Titel, Nummer und Verfügbarkeit schreiben
        schreibeZeile(String.valueOf(kunde.getFirstName()));
        schreibeZeile(String.valueOf(kunde.getLastName()));
        schreibeZeile(String.valueOf(kunde.getMail()));
        schreibeZeile(String.valueOf(kunde.getPassword()));
        schreibeZeile(String.valueOf(kunde.getStrasse()));
        schreibeZeile(String.valueOf(kunde.getWohnort()));
        schreibeZeile(String.valueOf(kunde.getPostleitzahl()));
        return  true;
    }

    //schreibt in die Datei
    public boolean speicherMitarbeiter(Mitarbeiter mitarbeiter) throws IOException{
        schreibeZeile(String.valueOf(mitarbeiter.getFirstName()));
        schreibeZeile(String.valueOf(mitarbeiter.getLastName()));
        schreibeZeile(String.valueOf(mitarbeiter.getMail()));
        schreibeZeile(String.valueOf(mitarbeiter.getPassword()));
        return  true;
    }


    // Liest eine Zeile aus der Datei
    private String liesZeile() throws IOException {
        if (reader != null)
            return reader.readLine();
        else
            return "";
    }


    // Schreibt eine Zeile in die Datei (wenn Writer vorhanden)
    private void schreibeZeile(String daten) {
        if (writer != null)
            writer.println(daten);
    }
}
