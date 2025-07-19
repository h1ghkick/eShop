package Server.domain;

import entities.*;
import exception.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;


public class EShop extends UnicastRemoteObject implements EShopRemote {
    private static final long serialVersionUID = 1096295124934664424L;
    private KundenVW kundenVW;
    private MitarbeiterVW mitarbeiterVW;
    private ArtikelVW artikelVW;
    private Warenkorb warenkorb;
    private String dateiKunden = "";
    private String dateiArtikel = "";
    private String dateiMitarbeiter = "";
    private User aktuellerUser;

    public EShop(String dateiKunden, String dateiArtikel, String dateiMitarbeiter) throws IOException, RemoteException {
        super();
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


    public User einloggen(String email, String password) throws LoginException, RemoteException {
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



    public boolean istRegistriert(String email) throws RemoteException {
       return kundenVW.istRegistriert(email);
    }

    public User getAktuellerUser() throws RemoteException {
        return aktuellerUser;
    }
    public void Kaufen(Warenkorb warenkorb,String email) throws WarenkorbIstLeer , RemoteException {
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

    public boolean artikelAuslagern(Artikel key, int menge, String email) throws RemoteException {
        return artikelVW.artikelAuslagern(key,menge,email);
    }

    public Artikel artikelDa(String artikelBezeichnung) throws RemoteException {
        return artikelVW.artikelDa(artikelBezeichnung);
    }

    public List<Artikel> sucheArtikel(String artikelBezeichnung) throws RemoteException {
        return artikelVW.sucheArtikel(artikelBezeichnung);
    }

    public void einfuegenMitarbeiter(Mitarbeiter mitarbeiter) throws RemoteException {
        mitarbeiterVW.einfuegenMitarbeiter(mitarbeiter);
    }

    public void einfuegenKunden(Kunde kunde) throws RemoteException {
        kundenVW.einfuegenKunden(kunde);
    }

    public void artikelEinfuegen(Artikel artikel, int menge) throws RemoteException {
        String email = aktuellerUser.getMail();
        System.out.println("DEBUG: EShop.artikelEinfuegen von User = " + email);
        artikelVW.artikelEinfuegen(artikel, menge, email);
    }

    public List<Artikel> getArtikelBestand() throws RemoteException {
        return artikelVW.getAlleArtikel();
    }

    public void artikelHinzufuegen(Artikel artikel, int menge) throws RemoteException {
        warenkorb.artikelHinzufuegen(artikel, menge);
    }

    public Map<Artikel, Integer> listeAusgeben() throws RemoteException {
        return warenkorb.listeAusgeben();
    }

    public Warenkorb getWarenkorb() throws RemoteException {
        return this.warenkorb;
    }

    public void warenkorbLeeren() throws RemoteException {
        this.warenkorb.warenkorbLeeren();
    }

    public void speicherOption () throws IOException, RemoteException {
        artikelVW.schreibeDaten(dateiArtikel);
        mitarbeiterVW.schreibeDaten(dateiMitarbeiter);
        kundenVW.schreibeDaten(dateiKunden);
    }

    public void gueltigesPasswort(String passwort) throws RemoteException, PasswortZuSchwach {
        if (passwort.length() < 8 || !passwort.matches(".*[A-Z].*") || !passwort.matches(".*[0-9].*"))
            throw new PasswortZuSchwach("Mind. 8 Zeichen, bestehend aus mind. einem Großbuchstaben und einer Zahl.");
    }


    public void gueltigePostleitzahl (String postleitzahl) throws PostleitzahlZuSchwach, RemoteException {
        if(!postleitzahl.matches("\\d{5}"))
            throw new PostleitzahlZuSchwach("Mindestens 5 Zahlen.");
    }


}


