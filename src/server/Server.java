package server;

import UniversalRegistry.URegistryImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by user on 05/05/16.
 */
public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("Launching the server...");
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
        }
    }
}

//-Djava.rmi.server.useCodebaseOnly=false -Djava.rmi.server.codebase="http://b036:1098/