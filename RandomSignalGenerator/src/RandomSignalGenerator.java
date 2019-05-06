import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import java.util.Random;

public class RandomSignalGenerator {

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
     */
    public RandomSignalGenerator() {
        try {
            factory = new ActiveMQConnectionFactory(brokerURL);
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = session.createTopic("Signal");
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
     * Send given signal to topic
     * @param message signal
     */
    public void sendSignal(String message) {
        try {
            Message m = session.createTextMessage(message);
            producer.send(m);
            System.out.println("Sent a signal: " + message);
        } catch (JMSException e) {
            System.out.println("JMS: " + e);
        }
    }

    public static void main(String[] args) {
        RandomSignalGenerator generator = new RandomSignalGenerator();
        Random random = new Random();

        // mean of normal distribution
        double mean = 10;
        // variance of normal distribution
        double variance = 4;

        try {
            // generate random signal and send it to topic
            while (true) {
                double temp = Math.sqrt(variance) * random.nextGaussian() + mean;
                generator.sendSignal(String.valueOf(temp));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e);
        } finally {
            generator.close();
        }
    }
}
