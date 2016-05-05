package client;

import UniversalRegistry.URegistry;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

public class Client {
    public static void main(String[] args) {
        try {
            URegistry reg= (URegistry) Naming.lookup("rmi://localhost/registry");
            reg.bind("Entier", new Integer(3));
            List<String> s= reg.list();
            System.out.println(s.contains("Entier"));
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
