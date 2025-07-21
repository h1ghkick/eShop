package exception;

public class MengeNichtVerfuegbar extends Exception {

    public MengeNichtVerfuegbar() {
        super("Zu viele Artikel verlangt – Bestand reicht nicht aus.");
    }

    public MengeNichtVerfuegbar(String message) {
        super(message); // benutzerdefinierte Nachricht
    }
}
