package UniversalRegistry;

import client.ConsumerMessageListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.util.ThreadPoolUtils;

import javax.jms.*;
import java.net.URI;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by user on 05/05/16.
 */
public class URegistryImpl extends UnicastRemoteObject implements URegistry  {

    private Map<String, Object> table;
    private  Map<String, Integer> popularKey;
    private Topic topic ;
    private Session session;

    public URegistryImpl() throws RemoteException {
        table= new LinkedHashMap<>();
        popularKey= new LinkedHashMap<>();
    }

    public URegistryImpl(int portNb) throws RemoteException {
        super(portNb);
        table= new LinkedHashMap<>();
        popularKey= new LinkedHashMap<>();
    }

    /**
     * Puts the object with the corresponding key. The key should not already exist.
     * @param key
     * @param object
     * @throws AlreadyBoundException
     */
    @Override
    public void bind(String key, Object object) throws AlreadyBoundException {
        if(table.containsKey(key))
            throw new AlreadyBoundException();
        table.put(key, object);
    }

    /**
     * Puts the object with the corresponding key. If already exists, removes the old one to write a new one.
     * @param key
     * @param object
     */
    @Override
    public void rebind(String key, Object object) {
        table.put(key,object);
    }

    /**
     *
     * @param key
     * @return the object corresponding to the given key
     */
    @Override
    public Object get(String key) {
        if (popularKey.containsKey(key)) {
            int coeff = popularKey.get(key);
            popularKey.replace(key, coeff, ++coeff);
            System.out.println("Modif"+coeff);
        }
        else popularKey.put(key, 0);
        return table.get(key);
    }

    /**
     * @return the list of keys in the table
     */
    @Override
    public List<String> list() {
        return new ArrayList<>(table.keySet());
    }

    public List<Object> getLastObjects(int until){
        List<Object> res= new ArrayList<>();
        int i=0;
        for (String s: table.keySet()) {
            if (i++<until)
                res.add(table.get(s));
        }
        return res;
    }

    public List<String> getLastKeys(int until){
        List<String> res= new ArrayList<>();
        int i=0;
        for (String s: table.keySet()) {
            if (i++<until)
                res.add(s);
        }
        return res;
    }

    //TODO A revoir (ne cherche pas dans les N derniers)
    public List<String> getPopularKey(int until){
        if (popularKey.isEmpty()) return null;
        List<String> res= new ArrayList<>();
        int i=0;
        for (String key: table.keySet()) {
            System.out.println("-->"+key+ popularKey.get(key));
            if (i++<until && popularKey.get(key)!=null && popularKey.get(key)>1)
                res.add(key);
            System.out.println("taille"+ res.size());
        }
        return res;
    }

    @Override
    public void iniConnection() {
        BrokerService broker = null;
        try {
            broker = BrokerFactory.createBroker(new URI("broker:(tcp://localhost:61616)"));
            broker.start();
            Connection connection = null;
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            topic = session.createTopic("customerTopic");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void suscribe(String clientName) {
        MessageConsumer consumer = null;
        try {
            consumer = session.createConsumer(topic);
            consumer.setMessageListener(new ConsumerMessageListener(clientName));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void publish(String content){
        Message msg = null;
        try {
            msg = session.createTextMessage(content);
            MessageProducer producer = session.createProducer(topic);
            System.out.println("Sending text '" + content + "'");
            producer.send(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
