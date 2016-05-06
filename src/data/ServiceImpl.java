package data;

import client.ConsumerMessageListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;
import java.net.URI;

/**
 * Created by user on 05/05/16.
 */
public class ServiceImpl implements Service {
    private ReponseService reponseService;
    private Topic topic ;
    private Session session;

    @Override
    public String getInfo() {
        return reponseService.getInfo();
    }

    @Override
    public ReponseService accesService() {
        return reponseService;
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

    @Override
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

    @Override
    public void closeConnection(){
        try {
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
