package ru.psu.martyshenko.trrp.lab2.producer.app;

import ru.psu.martyshenko.trrp.lab2.app.Configuration;
import ru.psu.martyshenko.trrp.lab2.fb.tables.pojos.PsuCourses;
import ru.psu.martyshenko.trrp.lab2.producer.service.PsuCoursesService;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

public class AppProducer {

    public static void main(String[] args) {

        String passwd = publishKey();

        PsuCoursesService psuCoursesService = new PsuCoursesService();
        MQSender sender = new MQSender();
        for (PsuCourses psuCourses:psuCoursesService.getAllCourses()) {
            sender.sendRow(psuCourses, passwd);
        }
    }

    private static String publishKey() {
        Configuration configuration = ConfigurationHolder.getInstance().getConfiguration();
        String desPasswd = "TRRPPASSWD";
        try {
            // Взять открытый ключ Consumer
            ServerSocket server = new ServerSocket(configuration.getSocketPort());
            Socket communication = server.accept();
            ObjectInputStream is = new ObjectInputStream(communication.getInputStream());
            OutputStream os = communication.getOutputStream();
            PublicKey consumerPubKey = (PublicKey) is.readObject();

            // Зашифровать сообщение с использованием открытого ключа Consumer:
            Cipher cipher = Cipher.getInstance("RSA");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            cipher.init(Cipher.ENCRYPT_MODE, consumerPubKey);
            byte[] desPasswdEncrypted = cipher.doFinal(desPasswd.getBytes());

            // Отправляю зашифрованный ключ DES
            os.write(desPasswdEncrypted);
            os.flush();

            MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");
            sha1Digest.update(desPasswdEncrypted, 0, desPasswdEncrypted.length);
            String sha1 = DatatypeConverter.printHexBinary(sha1Digest.digest());
            System.out.println("SHA1 от зашифрованного с помощью RSA ключа симметричного шифрования:");
            System.out.println(sha1);

            communication.close();
            server.close();
            is.close();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return desPasswd;
    }
}
