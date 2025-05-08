package domain;


import java.util.ArrayList;
import java.util.List;

import entities.Kunde;
import entities.User;


public class KundenVW {
    private ArrayList kunden = new ArrayList();
    private ArrayList email = new ArrayList();
    private ArrayList password = new ArrayList();

    public void einfuegenKunden(Kunde kunde) {
        kunden.add(kunde);
    }

    public void istRegistriert(String mail) {
        if(email.contains(mail)){
            return;
        }
    }



}


