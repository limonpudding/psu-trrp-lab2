package ru.psu.martyshenko.trrp.lab2.consumer.app;

import org.apache.activemq.ActiveMQConnectionFactory;
import ru.psu.martyshenko.trrp.lab2.app.Configuration;
import ru.psu.martyshenko.trrp.lab2.app.ConfigurationHolder;
import ru.psu.martyshenko.trrp.lab2.app.util.ObjectTransferPreparer;
import ru.psu.martyshenko.trrp.lab2.app.util.TripleDES;
import ru.psu.martyshenko.trrp.lab2.fb.tables.pojos.PsuCourses;

import javax.jms.*;

public class MQReceiver implements Runnable, ExceptionListener {

    private String passwd;

    MQReceiver (String passwd) {
        this.passwd = passwd;
    }

    public void run() {
        Configuration configuration = ConfigurationHolder.getInstance().getConfiguration();
        while (true) {
            try {
                // Create a ConnectionFactory
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://"+configuration.getIp()+":"+configuration.getJmsBrokerPort());

                // Create a Connection
                Connection connection = connectionFactory.createConnection();
                connection.start();

                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue("messages");

                // Create a MessageConsumer from the Session to the Topic or Queue
                MessageConsumer consumer = session.createConsumer(destination);

                // Wait for a message
                Message message = consumer.receive();

                if (message instanceof BytesMessage) {
                    BytesMessage bytesMessage = (BytesMessage) message;
                    byte[] psuCoursesBytesEncoded = new byte[(int) bytesMessage.getBodyLength()];
                    bytesMessage.readBytes(psuCoursesBytesEncoded);
                    printBytes("Зашифрованная полученная последовательность:", psuCoursesBytesEncoded);
                    byte[] psuCoursesBytesDecoded = TripleDES.decrypt(psuCoursesBytesEncoded, passwd);
                    printBytes("Дешифрованная полученная последовательность:", psuCoursesBytesDecoded);

                    PsuCourses psuCourses = (PsuCourses) ObjectTransferPreparer.byteArrayToObject(psuCoursesBytesDecoded);
                    TablesConverter tablesConverter = new TablesConverter();
                    tablesConverter.convert(psuCourses);
                    System.out.println("Информация из ActiveMQ успешно сохранена в БД!");
                } else {
                    System.out.println("Received: " + message);
                }

                consumer.close();
                session.close();
                connection.close();
            } catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onException(JMSException ex) {
        ex.printStackTrace();
    }

    private static void printBytes(String description, byte[] input) {
        System.out.println(description);
        for (byte b:input) {
            System.out.print(String.format("%02x", b));
        }
        System.out.println();
    }
}