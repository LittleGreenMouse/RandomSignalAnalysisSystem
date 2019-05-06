import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

public class Publisher {

    // Message broker address tcp://ip:port
    private final String brokerURL = "tcp://localhost:61616";
    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private Topic topic;

    /**
     * Constructor
     * brokerURL -> ConnectionFactory -> Connection -> Session -> Topic and MessageProducer
     * @param topicName name of topic
     */
    public Publisher(String topicName) {
        try {
            factory = new ActiveMQConnectionFactory(brokerURL);
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = session.createTopic(topicName);
            producer = session.createProducer(topic);
        } catch (JMSException e) {
            System.out.println("JMS: " + e);
        }
    }

    /**
     * Close connection
     */
    public void close() {
        try {
            if(connection != null){
                connection.close();
            }
        } catch (JMSException e) {
            System.out.println("JMS: " + e);
        }
    }

    /**
     * Send given string message to topic
     * @param message message
     */
    public void sendStringMessage(String message) {
        try {
            Message m = session.createTextMessage(message);
            producer.send(m);
            System.out.println("Sent a message: " + message);
        } catch (JMSException e) {
            System.out.println("JMS: " + e);
        }
    }

    public static void main(String[] args) {
        Publisher publisher = new Publisher("MyTopic");
        publisher.sendStringMessage("Hello world!");
        publisher.close();
    }
}
