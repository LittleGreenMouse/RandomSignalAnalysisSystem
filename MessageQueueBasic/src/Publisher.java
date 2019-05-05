import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class Publisher {

    // Message broker address tcp://ip:port
    private final String brokerURL = "tcp://localhost:61616";
    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;

    /**
     * Constructor
     * brokerURL -> ConnectionFactory -> Connection -> Session -> MessageProducer
     */
    public Publisher() {
        try {
            factory = new ActiveMQConnectionFactory(brokerURL);
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(null);
        } catch (JMSException e) {
            System.out.println("JMS: " + e);
        }
    }

    /**
     * Close connection to message broker
     */
    public void close() {
        try {
            if(connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            System.out.println("JMS: " + e);
        }
    }

    /**
     * Send given string message to given message queue
     * @param queueName the aim message queue
     * @param message message
     */
    public void sendStringMessage(String queueName, String message) {
        try {
            Destination destination = session.createQueue(queueName);
            Message m = session.createTextMessage(message);
            producer.send(destination, m);
            System.out.println("Sent a message: " + message);
        } catch (JMSException e) {
            System.out.println("JMS: " + e);
        }
    }

    public static void main(String[] args) {
        Publisher publisher = new Publisher();
        publisher.sendStringMessage("TEST", "Hello world!");
        publisher.close();
    }
}
