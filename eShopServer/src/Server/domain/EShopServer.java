package Server.domain;

import entities.EShopRemote;

import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class EShopServer {

    public EShopServer() {
        EShopRemote shop = null;
        String serviceName = "EShopService";
        Registry registry = null;

        try {
            shop = new EShop("Kunde.txt", "Artikel.txt", "Mitarbeiter.txt");
            registry = LocateRegistry.getRegistry();
            registry.rebind(serviceName, shop);
            System.out.println("Lokales Registry-Objekt gefunden.");
            System.out.println("eShop-Server läuft...");
        } catch (ConnectException ce) {
            System.out.println("Registry läuft nicht. Starte eigene Registry...");
            try {
                registry = LocateRegistry.createRegistry(1099);
                System.out.println("Registry erzeugt.");
                registry.rebind(serviceName, shop);
                System.out.println("eShop-Server läuft...");
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } catch (RemoteException e1) {
            System.out.println(e1.getMessage());
            e1.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new EShopServer();
    }
}
