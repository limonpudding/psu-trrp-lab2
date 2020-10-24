package ru.psu.martyshenko.trrp.lab2.producer.app;

import ru.psu.martyshenko.trrp.lab2.fb.tables.pojos.PsuCourses;
import ru.psu.martyshenko.trrp.lab2.producer.sender.MQSender;
import ru.psu.martyshenko.trrp.lab2.producer.service.PsuCoursesService;

import javax.crypto.Cipher;
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
        String desPasswd = "TRRPPASSWD";
        try {
            // Взять открытый ключ Consumer
            ServerSocket server = new ServerSocket(60000);
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
