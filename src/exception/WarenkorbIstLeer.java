package exception;


public class WarenkorbIstLeer extends Exception{
    public WarenkorbIstLeer() {
        super("Ihr Warenkorb ist leer");
    }
}
