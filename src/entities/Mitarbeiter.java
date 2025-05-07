package entities;

public class Mitarbeiter extends User implements Registrieren{

    public Mitarbeiter( String firstName, String lastName, int userNr) {
        super(firstName, lastName, userNr);
    }

    public void registrieren(Mitarbeiter mitarbeiter) {


    }


}
