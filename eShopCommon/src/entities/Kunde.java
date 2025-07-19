package entities;

import java.io.Serializable;

public class Kunde extends User implements Serializable {
    private String strasse;
    private String wohnort;
    private int postleitzahl;

    /**
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param strasse
     * @param wohnort
     * @param postleitzahl
     *
     * Konstruktor für Kunde
     */
    public Kunde(String firstName, String lastName, String email, String password, String strasse, String wohnort, int postleitzahl) {
        super(firstName, lastName, email, password);
        this.strasse = strasse;
        this.wohnort = wohnort;
        this.postleitzahl = postleitzahl;
    }


    /**
     *
     * @param strasse
     *
     * Setter, Getter, nochmal andere als in der User Klasse
     */
    public void setStrasse(String strasse) { this.strasse = strasse; }

    public void setWohnort(String wohnort) { this.wohnort = wohnort; }

    public void setPostleitzahl(int postleitzahl) { this.postleitzahl = postleitzahl; }

    public String getAdresse() {
        return strasse + " " + wohnort + " " + postleitzahl;
    }

    public int getPostleitzahl() {
        return postleitzahl;
    }
    public String getWohnort() {
        return wohnort;
    }
    public String getStrasse() {
        return strasse;
    }
    @Override
    public String toString() {
        return String.format("Kunde: %s %s\nEmail: %s\nAdresse: %s",
                getFirstName(), getLastName(), getMail(), getAdresse());
    }




}
