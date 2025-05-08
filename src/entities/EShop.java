package entities;

import domain.KundenVW;
import domain.MitarbeiterVW;
import domain.ArtikelVW;
import entities.Kunde;
import entities.User;

/**
 * Der E-Shop verhält sich genauso wie die Bibliothek !!!Nachgucken!!!
 */
public class EShop {
    private KundenVW kunden;
    private MitarbeiterVW mitarbeiter;
    private ArtikelVW artikel;
    private String datei = "";

    public EShop(String datei)  {
        kunden = new KundenVW();
        mitarbeiter = new MitarbeiterVW();
        artikel = new ArtikelVW();

    }

    /**
     *
     * @param user User der in VW aufgenommen werden soll
     * Prüfung ob Mitarbeiter oder Kunde
     *
     */
    public void registrieren(User user){
        if(user instanceof Mitarbeiter){
            Mitarbeiter arbeiter = (Mitarbeiter) user;
            mitarbeiter.einfuegenMitarbeiter(arbeiter);
        }
        else if (user instanceof Kunde){
            Kunde kunde = (Kunde) user;
            kunden.einfuegenKunden(kunde);
        }
    }
}
