import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class SyncConsumer {

    public static void main(String[] args) {
        // Message broker address tcp://ip:port
        String brokerURL = "tcp://localhost:61616";

        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
            Connection connection = factory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("TEST");
            MessageConsumer consumer = session.createConsumer(destination);

            // Receive message from message queue
            TextMessage message = (TextMessage) consumer.receive(10000);
            System.out.println("Received a message: " + message.getText());

            connection.close();
        } catch (JMSException e) {
            System.out.println("JMS: " + e);
        }
    }
}
