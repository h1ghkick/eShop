package entities;

import java.io.Serializable;

public class Ereignis implements Serializable {
    private int tagImJahr;
    private Artikel artikel;
    private int menge;
    private String aktion;
    private String benutzerEmail;

    public Ereignis(int tagImJahr, Artikel artikel, int menge, String aktion, String benutzerEmail) {
        this.tagImJahr = tagImJahr;
        this.artikel = artikel;
        this.menge = menge;
        this.aktion = aktion;
        this.benutzerEmail = benutzerEmail;
    }

    @Override
    public String toString() {
        return tagImJahr + " | " + aktion + " | " + menge + " x " + artikel.getArtikelBezeichnung() + " | durch: " + benutzerEmail;
    }

}
