package entities;

import exception.LoginException;
import exception.PasswortZuSchwach;
import exception.PostleitzahlZuSchwach;
import exception.WarenkorbIstLeer;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface EShopRemote extends Remote {

    public User einloggen(String email, String password) throws RemoteException, LoginException;
    public boolean istRegistriert(String email) throws RemoteException;
    public User getAktuellerUser() throws RemoteException;
    public void Kaufen(Warenkorb warenkorb,String email) throws WarenkorbIstLeer, RemoteException;
    public boolean artikelAuslagern(Artikel key, int menge, String email) throws RemoteException;
    public Artikel artikelDa(String artikelBezeichnung) throws RemoteException;
    public List<Artikel> sucheArtikel(String artikelBezeichnung) throws RemoteException;
    public void einfuegenMitarbeiter(Mitarbeiter mitarbeiter) throws RemoteException;
    public void einfuegenKunden(Kunde kunde) throws RemoteException;
    public void artikelEinfuegen(Artikel artikel, int menge) throws RemoteException;
    public List<Artikel> getArtikelBestand() throws RemoteException;
    public void artikelHinzufuegen(Artikel artikel, int menge) throws RemoteException;
    public Map<Artikel, Integer> listeAusgeben() throws RemoteException;
    public Warenkorb getWarenkorb() throws RemoteException;
    public void warenkorbLeeren() throws RemoteException;
    public void speicherOption () throws IOException, RemoteException;
    public void gueltigesPasswort(String passwort) throws PasswortZuSchwach, RemoteException;
    public void gueltigePostleitzahl (String postleitzahl) throws PostleitzahlZuSchwach, RemoteException;

}
