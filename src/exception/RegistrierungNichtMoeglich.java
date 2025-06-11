package exception;

public class RegistrierungNichtMoeglich extends Exception {
    public RegistrierungNichtMoeglich() {
        super("Kundenregistrierung nicht möglich, bitte überprüfen sie ihre Eingaben.");
    }
}
