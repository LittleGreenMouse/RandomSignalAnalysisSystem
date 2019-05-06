import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;
import java.io.IOException;

public class AsyncConsumer {

    public static void main(String[] args) {
        // Message broker address tcp://ip:port
        String brokerURL = "tcp://localhost:61616";
        ConnectionFactory factory = null;
        Connection connection = null;
        Session session = null;
        Topic topic = null;
        MessageConsumer consumer = null;
        MyListener listener = null;

        try {
            factory = new ActiveMQConnectionFactory(brokerURL);
            connection = factory.createConnection();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = session.createTopic("MyTopic");
            consumer = session.createConsumer(topic);

            listener = new MyListener();
            consumer.setMessageListener(listener);

            connection.start();

            System.out.println("Enter to exit.");
            System.in.read();
        } catch (JMSException e) {
            System.out.println("JMS: " + e);
        } catch (IOException e) {
            System.out.println("IO: " + e);
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                System.out.println("JMS: " + e);
            }
        }
    }
}
