package ui.cui;

import Persistence.FilePersistenceManager;
import entities.Artikel;
import entities.Kunde;
import entities.Mitarbeiter;
import entities.Warenkorb;
import domain.EShop;
import exception.LoginException;
import entities.User;
import exception.PasswortZuSchwach;
import exception.PostleitzahlZuSchwach;
import exception.WarenkorbIstLeer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class  ShopClientCUI {
  private EShop eshop;
  private BufferedReader reader;


  public ShopClientCUI(String kundenVW, String artikelVW, String mitarbeiterVW) throws IOException {
    eshop = new EShop(kundenVW, artikelVW, mitarbeiterVW);
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
    try {
      String email;
      // E-Mail prüfen
      do {
        emailFenster();
        email = liesEingabe();
        if (eshop.istRegistriert(email)) {
          System.out.println("Email bereits vergeben, bitte andere Email angeben.");
        }
      } while (eshop.istRegistriert(email));

      // Passwort prüfen
      String passwort = null;
      boolean passwortGueltig = false;
      while (!passwortGueltig) {
        try {
          passwordFenster();
          passwort = liesEingabe();
          eshop.gueltigesPasswort(passwort); // wirft PasswortZuSchwach
          passwortGueltig = true;
        } catch (PasswortZuSchwach e) {
          System.out.println("Ungültiges Passwort: " + e.getMessage());
        }
      }

      System.out.println("Vorname: ");
      String firstName = liesEingabe();

      System.out.println("Nachname: ");
      String lastName = liesEingabe();

      System.out.println("Wohnort: ");
      String wohnort = liesEingabe();

      // Postleitzahl prüfen
      int postleitzahl = 0;
      boolean plzGueltig = false;
      while (!plzGueltig) {
        try {
          System.out.println("Postleitzahl: ");
          String plzEingabe = liesEingabe();
          eshop.gueltigePostleitzahl(plzEingabe); // wirft PostleitzahlZuSchwach
          postleitzahl = Integer.parseInt(plzEingabe);
          plzGueltig = true;
        } catch (PostleitzahlZuSchwach e) {
          System.out.println("Ungültige Postleitzahl: " + e.getMessage());
        } catch (NumberFormatException e) {
          System.out.println("Bitte nur Zahlen für die PLZ eingeben.");
        }
      }

      System.out.println("Strasse: ");
      String strasse = liesEingabe();

      // Kunde erzeugen und speichern
      Kunde kunde = new Kunde(firstName, lastName, email, passwort, strasse, wohnort, postleitzahl);
      eshop.einfuegenKunden(kunde);
      System.out.println("Registrierung erfolgreich!");

    } catch (IOException e) {
      System.out.println("Ein Fehler beim Einlesen ist aufgetreten: " + e.getMessage());
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
    System.out.println("Bestand ansehen: 'c'");
    System.out.println("Mitarbeiter registrieren: 'd'");
    System.out.println("Speichern: 'e'");
  }

  private void gibKundenMenue () {
    System.out.println("Willkommen im Kundenmenü");
    System.out.println("Alle Artikel anschauen: 'a'");
    System.out.println("Artikel den Warenkorb hinzufügen : 'b'");
    System.out.println("Warenkorb ausgeben: 'c'");
    System.out.println("Artikel kaufen: 'd'");
    System.out.println("Warenkorb leeren: 'e'");
  }

  private void gibMenueAus(User user) {
    String input;
    try {
        if(user == null) {
          System.out.println("Bitte mit richtigen Daten anmelden oder neu registrieren.");
          return;
        }
      if(user instanceof Mitarbeiter) {
        gibMitarbeiterMenue();
        input = liesEingabe();
        verarbeiteEingabeMitarbeiter(input ,user.getMail());
      } else if (user instanceof Kunde) {
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
          while(eshop.artikelDa(artikelname) == null) {
            System.out.println("Artikelname nicht gefunden.");
            System.out.println("Artikelname: ");
            artikelname = liesEingabe();
          }
          Artikel artikel = eshop.artikelDa(artikelname);
          System.out.println("Menge: ");
          menge = Integer.parseInt(liesEingabe());
          eshop.artikelAuslagern(artikel, menge, email);
        }
        case "c" -> {
          System.out.println(eshop.getArtikelBestand());
        }
        case "d" -> {
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
        case "e" -> {
          eshop.speicherOption();
          System.out.println("Datei wurde gespeichert!");
        }
      }


  }

  private void verarbeiteEingabeKunde(String line, String email) throws IOException, WarenkorbIstLeer {
    String artikelName;
    int menge;
    Warenkorb warenkorb;

    switch (line) {
      case "a" -> {
        System.out.println(eshop.getArtikelBestand());
      }
      case "b" -> {
        System.out.println("Artikelname: ");
        artikelName = liesEingabe();
        System.out.println("Menge: ");
        menge = Integer.parseInt(liesEingabe());
        eshop.artikelHinzufuegen(eshop.artikelDa(artikelName), menge);
      }
      case "c" -> {
        System.out.println(eshop.listeAusgeben());
      }
      case "d" -> {
        System.out.println("Kaufen wird erzeugt.");
        eshop.Kaufen(eshop.getWarenkorb(), email);
      }
      case "e" -> {
        eshop.warenkorbLeeren();
      }
    }
  }
//Todo: Verstehen wie diese Methode funktioniert Yunus und Naufal
private void run() {

  boolean programmLaeuft = true;

  while (programmLaeuft) {
    User aktuellerUser = null;

    // Login-/Registrierungsschleife
    while (aktuellerUser == null) {
      wilkommenFenster();
      try {
        String eingabe = liesEingabe();
        switch (eingabe) {
          case "a" -> aktuellerUser = einloggen();
          case "b" -> {
            registrieren();
          }
          case "q" -> {
            programmLaeuft = false;
            System.out.println("Programm wird beendet.");
            return;
          }
          default -> System.out.println("Ungültige Eingabe.");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // Hauptmenü-Schleife für eingeloggten Benutzer
    boolean benutzerAngemeldet = true;
    while (benutzerAngemeldet) {
      try {
        if (aktuellerUser instanceof Mitarbeiter) {
          gibMitarbeiterMenue();
          System.out.println("Zurück zum Login mit 'logout', Programm beenden mit 'q'");
          String input = liesEingabe();
          switch (input) {
            case "logout" -> benutzerAngemeldet = false;
            case "q" -> {
              programmLaeuft = false;
              return;
            }
            default -> verarbeiteEingabeMitarbeiter(input, aktuellerUser.getMail());
          }
        } else if (aktuellerUser instanceof Kunde) {
          gibKundenMenue();
          System.out.println("Zurück zum Login mit 'logout', Programm beenden mit 'q'");
          String input = liesEingabe();
          switch (input) {
            case "logout" -> {
              benutzerAngemeldet = false;
              aktuellerUser = null;
            }
            case "q" -> {
              programmLaeuft = false;
              return;
            }
            default -> verarbeiteEingabeKunde(input, aktuellerUser.getMail());
          }
        }
      } catch (IOException | WarenkorbIstLeer e) {
        e.printStackTrace();
      }
    }
  }
}


  public static void main(String[] args) {

    ShopClientCUI cui;
    try {
      cui = new ShopClientCUI("Kunde.txt", "Artikel.txt", "Mitarbeiter.txt");
      cui.run();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}


