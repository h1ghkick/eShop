package entities;

public class Kunde extends User {
    private String strasse;
    private String wohnort;
    private int postleitzahl;

    //Konstruktor
    public Kunde(int userNr, String firstName, String lastName, String strasse, String wohnort, int postleitzahl) {
        super(firstName, lastName, userNr);
        this.strasse = strasse;
        this.wohnort = wohnort;
        this.postleitzahl = postleitzahl;
    }

    public void registrieren() {

    }



    public void setHausnummer(int hausnummer) { this.hausnummer = hausnummer;}

    public void setStrasse(String strasse) { this.strasse = strasse; }

    public void setWohnort(String wohnort) { this.wohnort = wohnort; }

    public void setPostleitzahl(int postleitzahl) { this.postleitzahl = postleitzahl; }

    public String getAdresse() {

        return hausnummer + " " + strasse + " " + wohnort + " " + postleitzahl;

    }


}
