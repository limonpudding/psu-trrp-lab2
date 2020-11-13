package ru.psu.martyshenko.trrp.lab2.consumer.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ru.psu.martyshenko.trrp.lab2.app.Configuration;

import java.io.File;
import java.io.IOException;

public class ConfigurationHolder {

    private static ConfigurationHolder instance;

    private Configuration configuration = null;

    private ConfigurationHolder() {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            File configurationFile = new File("Configuration.yaml");
            configuration = mapper.readValue(configurationFile, Configuration.class);
        } catch (IOException e) {
            System.out.println("Не удалось загрузить конфигурацию из файла, будет загружена конфигурация по умолчанию.");
        }
        if (configuration == null) {

            // Если не удалось получить данные из файла, то используются параметры по умолчанию
            configuration = new Configuration();
            configuration.setSocketIp("127.0.0.1");
            configuration.setSocketPort(60000);
            configuration.setJmsIp("127.0.0.1");
            configuration.setJmsPort(61616);
            configuration.setDataBaseIp("127.0.0.1");
            configuration.setDataBasePort(5432);
        }
        System.out.println("Загружена конфигурация:");
        System.out.print("IP для сокетов: ");
        System.out.println(configuration.getSocketIp());
        System.out.print("Порт для сокетов: ");
        System.out.println(configuration.getSocketPort());
        System.out.print("IP для ActiveMQ: ");
        System.out.println(configuration.getJmsIp());
        System.out.print("Порт для очередей: ");
        System.out.println(configuration.getJmsPort());
        System.out.print("IP для БД: ");
        System.out.println(configuration.getDataBaseIp());
        System.out.print("Порт для БД: ");
        System.out.println(configuration.getDataBasePort());
    }

    public static ConfigurationHolder getInstance() {
        if (instance == null) {
            instance = new ConfigurationHolder();
        }
        return instance;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
