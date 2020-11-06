package ru.psu.martyshenko.trrp.lab2.app.util;

import java.io.*;

public class ObjectTransferPreparer {

    public static byte[] objectToByteArray(Object object) {
        byte[] byteObject = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();
            byteObject = bos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteObject;
    }

    public static Object byteArrayToObject(byte[] byteArray) {
        Object object = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
        ObjectInput in;
        try {
            in = new ObjectInputStream(bis);
            object = in.readObject();

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return object;
    }
}
