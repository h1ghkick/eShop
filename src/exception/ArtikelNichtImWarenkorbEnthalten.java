package exception;


public class ArtikelNichtImWarenkorbEnthalten extends Exception{
    public ArtikelNichtImWarenkorbEnthalten() {
        super("Der Artikel kann nicht in ihrem Warenkorb gefunden werden");
    }
}
