package ru.psu.martyshenko.trrp.lab2.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

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
            configuration.setIp("localhost");
            configuration.setSocketPort(60000);
            configuration.setJmsBrokerPort(61616);
        }
        System.out.println("Загружена конфигурация:");
        System.out.print("IP: ");
        System.out.println(configuration.getIp());
        System.out.print("Порт для сокетов: ");
        System.out.println(configuration.getSocketPort());
        System.out.print("Порт для очередей: ");
        System.out.println(configuration.getJmsBrokerPort());
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
