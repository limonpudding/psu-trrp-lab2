package ru.psu.martyshenko.trrp.lab2.consumer.app;

import javax.crypto.Cipher;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class AppConsumer {

    public static void main(String[] args) {
        String passwd = getKey();
        startActiveMQReceiver(passwd);
    }

    public static void startActiveMQReceiver(String passwd) {
        Thread thread = new Thread(new MQReceiver(passwd));
        thread.setDaemon(false);
        thread.start();
    }

    private static String getKey() {
        try {
            // Отправляю публичный ключ RSA
            Cipher cipher = Cipher.getInstance("RSA");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair kp = keyGen.genKeyPair();
            Socket client = new Socket("127.0.0.1", 60000);
            ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
            os.writeObject(kp.getPublic());
            os.flush();

            // Принять зашифрованное сообщение
            InputStream is = client.getInputStream();
            byte[] desPasswdEncrypted = new byte[256];
            is.read(desPasswdEncrypted);

            // Применить закрытый ключ для дешифрования сообщения:
            cipher.init(Cipher.DECRYPT_MODE, kp.getPrivate());
            byte[] desPasswdDecrypted = cipher.doFinal(desPasswdEncrypted);

            os.close();
            is.close();
            client.close();
            return new String(desPasswdDecrypted, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
