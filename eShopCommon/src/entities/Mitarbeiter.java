package entities;

import java.io.Serializable;


public class Mitarbeiter extends User implements Serializable {

    public Mitarbeiter( String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    /**
     * @param firstName
     * @param lastName
     * @param email
     *
     * Methode, sodass Mitarbeiter auch andere Mitarbeiter hinzufügen kann.
     */

    public Mitarbeiter mitarbeiterAufnehmen(String firstName, String lastName, String email, String password) {
        return new Mitarbeiter(firstName, lastName, email, password);
    }
    @Override
    public String toString() {
        return "Mitarbeiter: " + getFirstName() + " " + getLastName() + ", " + getMail();
    }

}
