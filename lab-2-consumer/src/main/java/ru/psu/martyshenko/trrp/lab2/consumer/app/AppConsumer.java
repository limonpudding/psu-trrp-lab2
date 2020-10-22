package ru.psu.martyshenko.trrp.lab2.consumer.app;

public class AppConsumer {

    public static void main(String[] args) {
        startActiveMQReceiver();
    }

    public static void startActiveMQReceiver() {
        Thread thread = new Thread(new ActiveMQReceiver());
        thread.setDaemon(false);
        thread.start();
    }
}
