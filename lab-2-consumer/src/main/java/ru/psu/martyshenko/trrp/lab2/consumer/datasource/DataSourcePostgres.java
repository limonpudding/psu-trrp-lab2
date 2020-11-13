package ru.psu.martyshenko.trrp.lab2.consumer.datasource;

import ru.psu.martyshenko.trrp.lab2.app.Configuration;
import ru.psu.martyshenko.trrp.lab2.consumer.app.ConfigurationHolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourcePostgres {

    private static final String url = "jdbc:postgresql://%s:%d/psu-courses?currentSchema=psu-courses&charSet=utf8";
    private static final String user = "admin";
    private static final String password = "admin";

    private static DataSourcePostgres instance;
    private Connection connection;

    private DataSourcePostgres(Connection connection) {
        this.connection = connection;
    }

    public static DataSourcePostgres getInstance() {
        if (instance == null) {
            try {
                DriverManager.registerDriver(new org.postgresql.Driver());
                Configuration configuration = ConfigurationHolder.getInstance().getConfiguration();
                String ip = configuration.getDataBaseIp();
                int port = configuration.getDataBasePort();
                Connection connection = DriverManager.getConnection(String.format(url, ip, port), user, password);
                instance = new DataSourcePostgres(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
