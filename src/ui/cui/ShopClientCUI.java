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

  private void loginFenster() {
    System.out.println("Willkommen in unserem EShop");
    System.out.println("Bitte melden Sie sich an.");
    System.out.println("-LOGIN-");
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
    System.out.println("Miarbeiter reegistrieren: 'c'");
  }

  private void gibKundenMenue () {
    System.out.println("Willkommen im Kundenmenü");
    System.out.println("Alle Artikel anschauen: 'a'");
    System.out.println("Artikel")
  }
  private void verarbeiteEingabeLogin(String line){


  }
//Todo: Verstehen wie diese Methode funktioniert Yunus und Naufal
  private void run() {
    String email = "";
    String password = "";
    String input = "";

/**
 * Zuerst wird ein loginFenster geschmissen, dann ein Email Fenster.
 * Danach die Email gelesen und gespeichert.
 * Danach das PasswordFenster geworfen und die Eingabe gespeichert.
 * Das ganze in die eshop.einloggen Methode eingefügt um zu entscheiden ob M oder K.
 */
    do {
      loginFenster();
      emailFenster();
      try {
        email = liesEingabe();
        passwordFenster();
        password = liesEingabe();
        try {
          User user = eshop.einloggen(email, password);
          if(user instanceof Mitarbeiter) {
            gibMitarbeiterMenue(); // SCHREIBEN!
            verarbeiteEingabeMitarbeiter();//Muss noch geschrieben werden...
          } else {
            gibKundenMenue(); // Schreiben!
            verarbeiteEingabeKunde();// Auch schreiben...
          }

        } catch (LoginException e) {
          e.printStackTrace();
        }
      } catch (IOException e) {
      e.printStackTrace();

      }
    }  while (!input.equals("q"));
  }
// Todo: verarbeiteEingabeMitarbeiter()
// Todo: verarbeiteEingabeKunde()
  public static void main(String[] args) {
    ShopClientCUI cui;
    Mitarbeiter w = new Mitarbeiter("Hans","Franz", "franz.hans@gmail.com", "123abc");
    try {
      cui = new ShopClientCUI();
      cui.run();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
