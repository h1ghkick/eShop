package domain;

import java.util.ArrayList;
import java.util.List;
import entities.Kunde;
import entities.User;

public class KundenVW {
    private List<Kunde> kundenListe = new ArrayList<>();

    public void einfuegenKunden(Kunde kunde) {
        kundenListe.add(kunde);
    }

    public boolean istRegistriert(String email) {
        for (Kunde k : kundenListe) {
            if (k.getMail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public List<Kunde> getAlleKunden() {
        return new ArrayList<>(kundenListe);
    }
}

