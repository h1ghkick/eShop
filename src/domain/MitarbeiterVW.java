package domain;

import java.util.ArrayList;
import java.util.List;
import entities.User;

import entities.Mitarbeiter;

public class MitarbeiterVW {
    private List<Mitarbeiter> mitarbeiterListe = new ArrayList<>();

    public void einfuegenMitarbeiter(Mitarbeiter arbeiter) {
        mitarbeiterListe.add(arbeiter);
    }


    public boolean istRegistriert(String email) {
        for (Mitarbeiter m : mitarbeiterListe) {
            if (m.getMail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public List<Mitarbeiter> getAlleMitarbeiter() {
        return new ArrayList<>(mitarbeiterListe);
    }
}
