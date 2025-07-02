package domain;

import entities.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import exception.*;


/**
 * Der E-Shop verhält sich genauso wie die Bibliothek!!!Nachgucken!!!
 */
public class EShop {
    private KundenVW kundenVW;
    private MitarbeiterVW mitarbeiterVW;
    private ArtikelVW artikelVW;
    private Warenkorb warenkorb;
    private String dateiKunden = "";
    private String dateiArtikel = "";
    private String dateiMitarbeiter = "";

    private User aktuellerUser;

    public EShop(String dateiKunden, String dateiArtikel, String dateiMitarbeiter) throws IOException {
        kundenVW = new KundenVW();
        mitarbeiterVW = new MitarbeiterVW();
        artikelVW = new ArtikelVW();
        warenkorb = new Warenkorb();

        this.dateiKunden = dateiKunden;
        kundenVW.liesDaten(dateiKunden);

        this.dateiArtikel = dateiArtikel;
        artikelVW.liesDaten(dateiArtikel);

        this.dateiMitarbeiter = dateiMitarbeiter;
        mitarbeiterVW.liesDaten(dateiMitarbeiter);
    }

    public EShop() {

    }

    public User einloggen(String email, String password) throws LoginException {
        for (Kunde k : kundenVW.getAlleKunden()) {
            if (k.getMail().equalsIgnoreCase(email)) {
                if (k.getPassword().equals(password)) {
                    this.aktuellerUser = k;      // ← merkt sich den Kunden
                    return k;
                } else {
                    throw new LoginException(k, "Falsches Passwort.");
                }
            }
        }
        for (Mitarbeiter m : mitarbeiterVW.getAlleMitarbeiter()) {
            if (m.getMail().equalsIgnoreCase(email)) {
                if (m.getPassword().equals(password)) {
                    this.aktuellerUser = m;      // ← merkt sich den Mitarbeiter
                    return m;
                } else {
                    throw new LoginException(m, "Falsches Passwort.");
                }
            }
        }
        throw new LoginException(null, "E-Mail nicht gefunden.");
    }



    public boolean istRegistriert(String email) {
       return kundenVW.istRegistriert(email);
    }

    public User getAktuellerUser() {
        return aktuellerUser;
    }
    public void Kaufen(Warenkorb warenkorb,String email) throws WarenkorbIstLeer {
        Map<Artikel, Integer> liste = warenkorb.listeAusgeben();
       if(liste == null || liste.isEmpty()) {
           throw new WarenkorbIstLeer();
       }
        for(Artikel key : liste.keySet()) {
            int menge = liste.get(key);
            artikelVW.artikelAuslagern(key, menge, email);
        }
        List<Kunde> kundenListe = kundenVW.getAlleKunden();
        for(Kunde key : kundenListe){
            key.getMail();
            if(email.equals(key.getMail())){
                Rechnung rechnung = new Rechnung();
                rechnung.rechnungStellen(key,liste);
                break;
            }
        }
        warenkorb.warenkorbLeeren();
    }

    public boolean artikelAuslagern(Artikel key, int menge, String email){
        return artikelVW.artikelAuslagern(key,menge,email);
    }

    public Artikel artikelDa(String artikelBezeichnung) {
        return artikelVW.artikelDa(artikelBezeichnung);
    }

    public List<Artikel> sucheArtikel(String artikelBezeichnung) {
        return artikelVW.sucheArtikel(artikelBezeichnung);
    }

    public void einfuegenMitarbeiter(Mitarbeiter mitarbeiter) {
        mitarbeiterVW.einfuegenMitarbeiter(mitarbeiter);
    }

    public void einfuegenKunden(Kunde kunde) {
        kundenVW.einfuegenKunden(kunde);
    }

    public void artikelEinfuegen(Artikel artikel, int menge) {
        String email = aktuellerUser.getMail();
        System.out.println("DEBUG: EShop.artikelEinfuegen von User = " + email);
        artikelVW.artikelEinfuegen(artikel, menge, email);
    }

    public List<Artikel> getArtikelBestand() {
        return artikelVW.getAlleArtikel();
    }

    public void artikelHinzufuegen(Artikel artikel, int menge) {
        warenkorb.artikelHinzufuegen(artikel, menge);
    }

    public Map<Artikel, Integer> listeAusgeben() {
        return warenkorb.listeAusgeben();
    }

    public Warenkorb getWarenkorb() {
        return this.warenkorb;
    }

    public void warenkorbLeeren() {
        this.warenkorb.warenkorbLeeren();
    }

    public void speicherOption () throws IOException {
        artikelVW.schreibeDaten(dateiArtikel);
        mitarbeiterVW.schreibeDaten(dateiMitarbeiter);
        kundenVW.schreibeDaten(dateiKunden);
    }

    public void gueltigesPasswort(String passwort) throws PasswortZuSchwach {
        if (passwort.length() < 8 || !passwort.matches(".*[A-Z].*") || !passwort.matches(".*[0-9].*"))
            throw new PasswortZuSchwach("Mind. 8 Zeichen, bestehend aus mind. einem Großbuchstaben und einer Zahl.");
    }


    public void gueltigePostleitzahl (String postleitzahl) throws PostleitzahlZuSchwach {
        if(!postleitzahl.matches("\\d{5}"))
            throw new PostleitzahlZuSchwach("Mindestens 5 Zahlen.");
    }


}


