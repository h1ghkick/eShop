package persistence;

import java.io.IOException;

public interface PersistenceManager {

    public void openForReading(String datequelle) throws IOException;

    public void openForWriting(String datequelle) throws IOException;

    public boolean close();

}

