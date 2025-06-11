package exception;

import entities.Artikel;

public class ArtikelExistiertSchon extends Exception {


    public ArtikelExistiertSchon(Artikel artikel, String zusatzMsg) {
        super("Artikel mit Bezeichnung " + artikel.getArtikelBezeichnung() + " und Artikelnummer " + artikel.getArtikelNummer()
                + " existiert bereits" + zusatzMsg);
    }


}

