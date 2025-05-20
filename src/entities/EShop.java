package entities;

import domain.KundenVW;
import domain.MitarbeiterVW;
import domain.ArtikelVW;

import java.util.List;
import java.util.Map;

import exception.LoginException;


/**
 * Der E-Shop verhält sich genauso wie die Bibliothek!!!Nachgucken!!!
 */
public class EShop {
    private KundenVW kundenVW;
    private MitarbeiterVW mitarbeiterVW;
    private ArtikelVW artikelVW;



    public EShop()  {
        this.kundenVW = new KundenVW();
        this.mitarbeiterVW = new MitarbeiterVW();
        this.artikelVW = new ArtikelVW();

    }


    public User einloggen(String email, String password) throws LoginException {
        for (Kunde k : kundenVW.getAlleKunden() ) {
            if(k.getMail().equalsIgnoreCase(email) && k.getPassword().equalsIgnoreCase(password)) {
                return k;
            } else {
                throw new LoginException(k, "Falsches Passwort.");
            }
        }

        for (Mitarbeiter m : mitarbeiterVW.getAlleMitarbeiter()) {
            if(m.getMail().equalsIgnoreCase(email) && m.getPassword().equalsIgnoreCase(password)) {
                return m;
            } else {
                throw new LoginException(m, "Falsches Passwort.");
            }
        }
        throw new LoginException(null, "E-Mail nicht gefunden.");
    }

    public boolean istRegistriert(String email){
       return kundenVW.istRegistriert(email);
    }

    public void Kaufen(Warenkorb warenkorb,String email){
        Map<Artikel, Integer> liste = warenkorb.listeAusgeben();
        for(Artikel key : liste.keySet()){
            int menge = liste.get(key);
            artikelVW.artikelAuslagern(key,menge,email);
        }
        List<Kunde> kundenListe = kundenVW.getAlleKunden();
        for(Kunde key : kundenListe){
            key.getMail();
            if(email.equals(key.getMail())){
                Rechnung rechnung = new Rechnung();
                rechnung.rechnungStellen(key,liste);
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
    public void einfuegenMitarbeiter(Mitarbeiter mitarbeiter) {
        mitarbeiterVW.einfuegenMitarbeiter(mitarbeiter);
    }
    public void einfuegenKunden(Kunde kunde) {
        kundenVW.einfuegenKunden(kunde);
    }
    public void artikelEinfuegen(Artikel artikel, int menge, String benutzerEmail) {
        artikelVW.artikelEinfuegen(artikel, menge, benutzerEmail);
    }

}


