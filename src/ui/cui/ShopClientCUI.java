package ui.cui;

import entities.Artikel;
import entities.Kunde;
import entities.Mitarbeiter;

public class  ShopClientCUI {

  public ShopClientCUI() {

  }
  public static void main(String[] args) {
      Mitarbeiter uwe = new Mitarbeiter("Mitarbeiter", "Mitarbeiter", 1);
      System.out.println(uwe.toString());
      uwe.setfirstName("Fyn");
      System.out.println(uwe.getFirstName());
  }
}
