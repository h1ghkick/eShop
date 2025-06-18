package ui.Cui;

import Persistence.FilePersistenceManager;
import entities.Artikel;
import entities.Kunde;
import entities.Mitarbeiter;
import entities.Warenkorb;
import domain.EShop;
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
        System.out.println("Email: ");
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
    System.out.println("Bestand ansehen: 'c'");
    System.out.println("Mitarbeiter registrieren: 'd'");
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
      }


  }

  private void verarbeiteEingabeKunde(String line, String email) throws IOException {
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
  Mitarbeiter admin = new Mitarbeiter("Admin", "Franz", "admin@admin", "123abc");
  eshop.einfuegenMitarbeiter(admin);

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
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}


  public static void main(String[] args) {
    FilePersistenceManager pm = new FilePersistenceManager();

    try {
      // === MITARBEITER TESTEN ===
      Mitarbeiter m1 = new Mitarbeiter("Max", "Muster", "max@muster.de", "pass123");

      pm.openForWriting("Mitarbeiter.txt");
      pm.speicherMitarbeiter(m1);
      pm.close();

      pm.openForReading("Mitarbeiter.txt");
      Mitarbeiter geladenerMitarbeiter = pm.ladeMitarbeiter();
      pm.close();

      System.out.println("Geladener Mitarbeiter:");
      System.out.println(geladenerMitarbeiter);

      // === KUNDE TESTEN ===
      Kunde k1 = new Kunde("Anna", "Beispiel", "anna@example.com", "pass456", "Hauptstraße 1", "Berlin", 10115);

      pm.openForWriting("Kunde.txt");
      pm.speicherKunde(k1);
      pm.close();

      pm.openForReading("Kunde.txt");
      Kunde geladenerKunde = pm.ladeKunde();
      pm.close();

      System.out.println("\nGeladener Kunde:");
      System.out.println(geladenerKunde);

      // === ARTIKEL TESTEN ===
      Artikel a1 = new Artikel(5, 1234, "Bürostuhl", 89.99, true);

      pm.openForWriting("Artikel.txt");
      pm.speicherArtikel(a1);
      pm.close();

      pm.openForReading("Artikel.txt");
      Artikel geladenerArtikel = pm.ladeArtikel();
      pm.close();

      System.out.println("\nGeladener Artikel:");
      System.out.println(geladenerArtikel);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  }


