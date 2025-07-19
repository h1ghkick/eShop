package exception;

public class EmailExistiertSchon extends Exception {

    public EmailExistiertSchon() {
        super("Email existiert schon!");
    }
}
