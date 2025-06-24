package exception;

public class PostleitzahlZuSchwach extends RuntimeException {
    public PostleitzahlZuSchwach(String message) {
        super(message);
    }
}
