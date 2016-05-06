package client;

import UniversalRegistry.URegistry;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        try {
            URegistry reg= (URegistry) Naming.lookup("rmi://localhost/registry");
            reg.rebind("Vieux", new Integer(100));
            reg.rebind("Entier1", new Integer(1));
            reg.rebind("Entier2", new Integer(2));
            reg.rebind("Entier3", new Integer(3));
            reg.rebind("Entier4", new Integer(4));
            reg.rebind("Entier5", new Integer(5));
            reg.get("Vieux");
            reg.get("Vieux");
            reg.get("Vieux");
            reg.get("Vieux");
            reg.get("Entier1");
            reg.get("Entier3");
            reg.get("Entier1");
            reg.get("Entier3");
            reg.get("Entier1");
            reg.get("Entier1");
            List<String> res= reg.getPopularKey(2);
            for (String s: res)
                System.out.println(s);
            /*reg.iniConnection();
            reg.suscribe("Bob");
            reg.publish("Coucou!!");
            reg.suscribe("Marie");
            reg.publish("Hello!!");
            reg.closeConnection();*/
            /*TopicConnection connection= reg.suscribe();
            TopicSession topicSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            TopicSubscriber topicSubscriber = topicSession.createSubscriber(connection);
            topicConn.start();*/
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
