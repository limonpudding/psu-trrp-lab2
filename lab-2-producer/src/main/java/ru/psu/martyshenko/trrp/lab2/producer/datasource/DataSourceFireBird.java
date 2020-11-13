package ru.psu.martyshenko.trrp.lab2.producer.datasource;

import ru.psu.martyshenko.trrp.lab2.app.Configuration;
import ru.psu.martyshenko.trrp.lab2.producer.app.ConfigurationHolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceFireBird {

    private static final String url = "jdbc:firebirdsql:%s/%d:/db/test.fdb?charSet=utf8";
    private static final String user = "sysdba";
    private static final String password = "masterkey";

    private static DataSourceFireBird instance;
    private Connection connection;

    private DataSourceFireBird(Connection connection) {
        this.connection = connection;
    }

    public static DataSourceFireBird getInstance() {
        if (instance == null) {
            try {
                DriverManager.registerDriver(new org.firebirdsql.jdbc.FBDriver());
                Configuration configuration = ConfigurationHolder.getInstance().getConfiguration();
                String ip = configuration.getDataBaseIp();
                int port = configuration.getDataBasePort();
                Connection connection = DriverManager.getConnection(String.format(url, ip, port), user, password);
                instance = new DataSourceFireBird(connection);
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
