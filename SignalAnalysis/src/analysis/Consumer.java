package analysis;

import org.apache.activemq.ActiveMQConnectionFactory;
import view.MainController;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;

public class Consumer {

    private final String brokerURL;
    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private Topic topic;
    private MessageConsumer consumer;
    private MyListener listener;

    private MainController mainController;

    /**
     * Constructor
     * brokerURL -> ConnectionFactory -> Connection -> Session -> Topic and MessageConsumer
     * @param url Message broker address
     */
    public Consumer(String url, MainController mainController) {
        brokerURL = url;
        this.mainController = mainController;
        try {
            factory = new ActiveMQConnectionFactory(brokerURL);
            connection = factory.createConnection();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = session.createTopic("Signal");
            consumer = session.createConsumer(topic);

            listener = new MyListener(mainController);
            consumer.setMessageListener(listener);

            connection.start();
        } catch (JMSException e) {
            System.out.println("JMS: " + e);
        }
    }

    /**
     * Close connection
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
}
