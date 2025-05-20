package ui.cui;

import domain.ArtikelVW;
import domain.KundenVW;
import domain.MitarbeiterVW;
import entities.Artikel;
import entities.Kunde;
import entities.Mitarbeiter;
import entities.Rechnung;
import entities.Warenkorb;
import entities.EShop;
import exception.LoginException;
import entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class  ShopClientCUI {
  private EShop eshop;
  private BufferedReader reader;


  public ShopClientCUI() throws IOException {
    eshop = new EShop();

    reader = new BufferedReader(new InputStreamReader(System.in));
  }

  private void wilkommenFenster() {
    System.out.println("Willkommen in unserem EShop");
    System.out.println("Login: 'a'");
    System.out.println("Registrieren: 'b'");
  }

  private User einloggen() throws IOException {
      emailFenster();
      User user = null;
      try {
          String email = liesEingabe();
          passwordFenster();
          String passwort = liesEingabe();
          try {
              user = eshop.einloggen(email, passwort);
          } catch (LoginException e) {
              e.printStackTrace();
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
      return user;
  }

  private void registrieren() {
    emailFenster();
    try {
      String email = liesEingabe();
      while (eshop.istRegistriert(email)) {
        System.out.println("Email bereits vergeben, bitte andere Email angeben.");
        email = liesEingabe();
      }
      passwordFenster();
      String passwort = liesEingabe();
      System.out.println("Vorname: ");
      String firstName = liesEingabe();
      System.out.println("Nachname: ");
      String lastName = liesEingabe();
      System.out.println("Wohnort: ");
      String wohnort = liesEingabe();
      System.out.println("Postleitzahl: ");
      int postleitzahl = Integer.parseInt(liesEingabe());
      System.out.println("Strasse: ");
      String strasse = liesEingabe();
      Kunde kunde = new Kunde(firstName, lastName, email, passwort, strasse, wohnort, postleitzahl);
      eshop.einfuegenKunden(kunde);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void emailFenster() {
    System.out.println("E-mail: ");
  }

  private void passwordFenster() {
    System.out.println("Passwort: ");
  }

  private String liesEingabe () throws IOException {

    return reader.readLine();

  }
  private void gibMitarbeiterMenue () {
    System.out.println("Willkommen im Mitarbeitermenü");
    System.out.println("Artikel hinzufügen : 'a'");
    System.out.println("Bestand verändern : 'b'");
    System.out.println("Mitarbeiter registrieren: 'c'");
    System.out.println("Menü verlassen : 'e'");
  }

  private void gibKundenMenue () {
    System.out.println("Willkommen im Kundenmenü");
    System.out.println("Alle Artikel anschauen: 'a'");
    System.out.println("Artikel kaufen : 'b'");
    System.out.println("Warenkorb ausgeben: 'c'");
    System.out.println(" 'd'");

  }

  private void gibMenueAus(User user) {
    try {
       String input;
      if(user instanceof Mitarbeiter) {
        gibMitarbeiterMenue();
        input = liesEingabe();
        verarbeiteEingabeMitarbeiter(input ,user.getMail());
      } else {
        gibKundenMenue();
        input = liesEingabe();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  private void verarbeiteEingabeMitarbeiter(String line, String email) throws IOException {
    String artikelname;
    int menge;
    double preis;
    int artikelNummer;
    String passwort;
    String vorname;
    String nachname;

    switch (line) {
      case "a" -> {
        System.out.println("Artikelname: ");
        artikelname = liesEingabe();
        System.out.println("Menge: ");
        menge = Integer.parseInt(liesEingabe());
        System.out.println("Preis: ");
        preis = Double.parseDouble(liesEingabe());
        System.out.println("Artikelnummer: ");
        artikelNummer = Integer.parseInt(liesEingabe());
        Artikel artikel = new Artikel(menge, artikelNummer, artikelname, preis, true);
        eshop.artikelEinfuegen(artikel, menge, email);
        System.out.println("Artikel" + artikelname + " wurde hinzugefügt.");
      }
      case "b" -> {
        System.out.println("Artikelname: ");
        artikelname = liesEingabe();
        Artikel artikel = eshop.artikelDa(artikelname);
        System.out.println("Menge: ");
        menge = Integer.parseInt(liesEingabe());
        eshop.artikelAuslagern(artikel, menge, email);
        System.out.println("Artikel: " + artikelname +" wurde " + menge + " mal " + " ausgelagert.");
      }
      case "c" -> {
        System.out.println("Email: ");
        email = liesEingabe();
        System.out.println("Passwort: ");
        passwort = liesEingabe();
        System.out.println("Vorname: ");
        vorname = liesEingabe();
        System.out.println("Nachname: ");
        nachname = liesEingabe();
        Mitarbeiter mitarbeiter = new Mitarbeiter(email, passwort, vorname, nachname);
        eshop.einfuegenMitarbeiter(mitarbeiter);
      }
    }

  }

  private void verarbeiteEingabeKunde() throws LoginException {
  }
//Todo: Verstehen wie diese Methode funktioniert Yunus und Naufal
  private void run() {
    String email = "";
    String password = "";
    String input = "";
    Mitarbeiter w = new Mitarbeiter("Admin","Franz", "franz.hans@gmail.com", "123abc");
    eshop.einfuegenMitarbeiter(w);
    do {
      wilkommenFenster();
      try {
        input = liesEingabe();
        switch (input) {
          case "a" -> {
            gibMenueAus(einloggen());
          }
          case "b" -> {
            registrieren();
          }
        }

      } catch (IOException e) {
        e.printStackTrace();

      }
    }  while (!input.equals("q"));
  }

  public static void main(String[] args) {
    ShopClientCUI cui;
    try {
      cui = new ShopClientCUI();
      cui.run();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
