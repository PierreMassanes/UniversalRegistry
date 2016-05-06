package server;

import UniversalRegistry.URegistryImpl;
import classserver.ClassFileServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
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
            System.out.println("Lauching the server...");
            if(System.getSecurityManager() == null){
                System.setSecurityManager(new java.rmi.RMISecurityManager());
            }
            System.out.println(" > Security manager is set");

            LocateRegistry.getRegistry(1099);
            URegistryImpl reg = new URegistryImpl();
            Naming.rebind("registry", reg);
            System.out.println(" > UniversalRegistry registered on the RMIRegistry");

            System.out.println("Server launched !");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
