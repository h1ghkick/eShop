package entities;

public class Mitarbeiter extends User {

    public Mitarbeiter( String firstName, String lastName, String email, boolean status) {
        super(firstName, lastName, email, status);
    }

    /**
     * @param firstName
     * @param lastName
     * @param email
     * @param status
     *
     * Methode, sodass Mitarbeiter auch andere Mitarbeiter hinzufügen kann.
     */
    public void mitarbeiterAufnehmen(String firstName, String lastName, String email, boolean status) {
        Mitarbeiter arbeiter = new Mitarbeiter(firstName, lastName, email, false);
    }
}
