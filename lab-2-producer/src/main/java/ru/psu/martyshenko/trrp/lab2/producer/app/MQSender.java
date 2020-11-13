package ru.psu.martyshenko.trrp.lab2.producer.app;

import org.apache.activemq.ActiveMQConnectionFactory;
import ru.psu.martyshenko.trrp.lab2.app.util.ObjectTransferPreparer;
import ru.psu.martyshenko.trrp.lab2.app.util.TripleDES;
import ru.psu.martyshenko.trrp.lab2.fb.tables.pojos.PsuCourses;
import ru.psu.martyshenko.trrp.lab2.app.Configuration;

import javax.jms.*;

public class MQSender {

    public void sendRow(PsuCourses psuCourses, String passwd) {
        Configuration configuration = ConfigurationHolder.getInstance().getConfiguration();
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://"+configuration.getSocketIp()+":" + configuration.getJmsPort());

        // Create a Connection
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue("messages");

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            byte[] psuCoursesBytes = ObjectTransferPreparer.objectToByteArray(psuCourses);
            printBytes("Исходная последовательность для отправки:", psuCoursesBytes);
            byte[] psuCoursesBytesEncoded = TripleDES.encrypt(psuCoursesBytes, passwd);
            printBytes("Зашифрованная последовательность для отправки:", psuCoursesBytesEncoded);
            BytesMessage message = session.createBytesMessage();
            message.writeBytes(psuCoursesBytesEncoded);

            // Tell the producer to send the message
            System.out.println("Информация отправлена в ActiveMQ очередь!");
            producer.send(message);

            // Clean up
            session.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printBytes(String description, byte[] input) {
        System.out.println(description);
        for (byte b:input) {
            System.out.print(String.format("%02x", b));
        }
        System.out.println();
    }
}
