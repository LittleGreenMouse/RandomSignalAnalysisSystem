package AsyncConsumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("Received a message: " + ((TextMessage)message).getText());
        } catch (JMSException e) {
            System.out.println("JMS: " + e);
        }
    }
}
