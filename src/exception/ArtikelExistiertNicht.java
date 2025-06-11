
package exception;

public class ArtikelExistiertNicht extends Exception {
    public ArtikelExistiertNicht(String bezeichnung) {
        super("Der gesuchte Artikel: " + bezeichnung + " kann nicht in unserem Bestand gefunden werden.");
    }
}
