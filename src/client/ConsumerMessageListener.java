package client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.Serializable;

public class ConsumerMessageListener implements MessageListener, Serializable {
    private String consumerName;
    public ConsumerMessageListener(String consumerName) {
        this.consumerName = consumerName;
    }

    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println(consumerName + " received " + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
