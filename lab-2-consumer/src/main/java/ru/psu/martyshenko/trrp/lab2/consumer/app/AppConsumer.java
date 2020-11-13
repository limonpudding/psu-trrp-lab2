package ru.psu.martyshenko.trrp.lab2.consumer.app;

import ru.psu.martyshenko.trrp.lab2.app.Configuration;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
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
        Configuration configuration = ConfigurationHolder.getInstance().getConfiguration();
        try {
            // Отправляю публичный ключ RSA
            Cipher cipher = Cipher.getInstance("RSA");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair kp = keyGen.genKeyPair();
            Socket client = new Socket(configuration.getSocketIp(), configuration.getSocketPort());
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

            MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");
            sha1Digest.update(desPasswdEncrypted, 0, desPasswdEncrypted.length);
            String sha1 = DatatypeConverter.printHexBinary(sha1Digest.digest());
            System.out.println("SHA1 от зашифрованного с помощью RSA ключа симметричного шифрования, полученного от Producer:");
            System.out.println(sha1);

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
