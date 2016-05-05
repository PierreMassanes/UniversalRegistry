package server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by user on 05/05/16.
 */
public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            String url = "rmi://"+ InetAddress.getLocalHost().getHostAddress();

            URegistryImpl reg = new URegistryImpl();

            Naming.rebind("registry", reg);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
