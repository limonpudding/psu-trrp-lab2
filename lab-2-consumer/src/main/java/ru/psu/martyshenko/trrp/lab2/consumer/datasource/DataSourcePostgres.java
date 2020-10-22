package ru.psu.martyshenko.trrp.lab2.consumer.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourcePostgres {

    private static final String url = "jdbc:postgresql://127.0.0.1:5432/psu-courses?currentSchema=psu-courses&charSet=utf8";
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
                Connection connection = DriverManager.getConnection(url, user, password);
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
