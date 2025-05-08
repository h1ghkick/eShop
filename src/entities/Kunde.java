package entities;

public class Kunde extends User {
    private String strasse;
    private String wohnort;
    private int postleitzahl;

    /**
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param status
     * @param strasse
     * @param wohnort
     * @param postleitzahl
     *
     * Konstruktor für Kunde
     */
    public Kunde(String firstName, String lastName, String email, boolean status, String strasse, String wohnort, int postleitzahl) {
        super(firstName, lastName, email, status);
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


}
