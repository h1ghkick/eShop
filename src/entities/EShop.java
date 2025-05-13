package entities;

import domain.KundenVW;
import domain.MitarbeiterVW;
import domain.ArtikelVW;
import entities.Kunde;
import entities.User;
import java.util.HashMap;
import java.util.Map;
import entities.Warenkorb;
import entities.Rechnung;
import entities.Artikel;
import exception.LoginException;


/**
 * Der E-Shop verhält sich genauso wie die Bibliothek!!!Nachgucken!!!
 */
public class EShop {
    private KundenVW kundenVW;
    private MitarbeiterVW mitarbeiterVW;
    private ArtikelVW artikelVW;

    public EShop(String datei)  {
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

    public boolean registrieren(User user){
        if (user instanceof Kunde) {
            for (Kunde k : kundenVW.getAlleKunden()) {
                if (k.getMail().equalsIgnoreCase(user.getMail())) {
                    return false;
                }
            }
        }
        kundenVW.einfuegenKunden((Kunde) user);
        return true;
    }

    public void Kaufen(){


    }
}
