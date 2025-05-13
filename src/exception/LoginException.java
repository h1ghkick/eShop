package exception;

import entities.User;

public class LoginException extends Exception {

    private User user;

    /**
     * Konstruktor für LoginException
     *
     * @param user Der betroffene Benutzer (kann null sein, wenn E-Mail nicht gefunden wurde)
     * @param zusatzMsg Zusätzlicher Text für die Fehlermeldung
     */
    public LoginException(User user, String zusatzMsg) {
        super("Login fehlgeschlagen: " + zusatzMsg);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
