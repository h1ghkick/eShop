package domain;


import java.util.ArrayList;
import java.util.List;

import entities.Mitarbeiter;
import entities.User;


public class MitarbeiterVW {
    private ArrayList mitarbeiter = new ArrayList();
    private ArrayList email = new ArrayList();
    private ArrayList password = new ArrayList();

    public void einfuegenMitarbeiter(Mitarbeiter arbeiter) {
        mitarbeiter.add(arbeiter);
    }


    public void istRegistriert(String mail){
        if(email.contains(mail)){
            return;
        }
    }


}

