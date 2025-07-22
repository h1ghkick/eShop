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



    // Liest einen Artikel aus der Datei ( Anzahl, Nummer, Name, Preis, Verfügbarkeit)
    public Artikel ladeArtikel() throws IOException {
        //Int Artikelanzahl
        String artikelAnzahl = liesZeile();
        if(artikelAnzahl ==null){
            return null;
        }
        int artikelAnzahlToString = Integer.parseInt(artikelAnzahl);

        //Int Artikelnummer
        String artikelNummer = liesZeile();
        if (artikelNummer == null) {
            return null;
        }
        int artikelNummerToString = Integer.parseInt(artikelNummer);

        //String Artikelbezeichnung einlesen
        String artikelBezeichnung = liesZeile();
        if (artikelBezeichnung == null) {
            // keine Daten mehr vorhanden
            return null;
        }


        //Int Artikel Preis
        String artikelPreis = liesZeile();
        if (artikelPreis == null) {
            // keine Daten mehr vorhanden
            return null;
        }
        double artikelPreisToString = Double.parseDouble(artikelPreis);

        // ArtikelVrerfuegbar?
        String artikelVerfuegbar = liesZeile();
        // Codierung des Ausleihstatus in boolean umwandeln
        boolean verfuegbar = artikelVerfuegbar.equals("t") ? true : false;

            // neues Buch-Objekt anlegen und zurückgeben
        return new Artikel(artikelAnzahlToString,artikelNummerToString,artikelBezeichnung,artikelPreisToString,verfuegbar);
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
        // Titel, Nummer und Verfügbarkeit schreiben
        schreibeZeile(String.valueOf(artikel.getArtikelAnzahl()));
        schreibeZeile(String.valueOf(artikel.getArtikelNummer()));
        schreibeZeile(artikel.getArtikelBezeichnung());
        schreibeZeile(String.valueOf(artikel.getPreis()));
        String verfuegbar = artikel.getArtikelVerfuegbar() ? "t" : "f";
        schreibeZeile(verfuegbar);
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
