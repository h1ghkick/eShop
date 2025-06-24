package exception;

public class ArtikelExisitiertBereitsException extends Exception {
    public ArtikelExisitiertBereitsException(String message) {
        super("Artikel existiert schon: " + message);
    }
}
