package ru.psu.martyshenko.trrp.lab2.app;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Configuration {

    @JsonProperty
    private String socketIp;

    @JsonProperty
    private int socketPort;

    @JsonProperty
    private String jmsIp;

    @JsonProperty
    private int jmsPort;

    @JsonProperty
    private String dataBaseIp;

    @JsonProperty
    private int dataBasePort;

    public String getSocketIp() {
        return socketIp;
    }

    public void setSocketIp(String socketIp) {
        this.socketIp = socketIp;
    }

    public int getSocketPort() {
        return socketPort;
    }

    public void setSocketPort(int socketPort) {
        this.socketPort = socketPort;
    }

    public String getJmsIp() {
        return jmsIp;
    }

    public void setJmsIp(String jmsIp) {
        this.jmsIp = jmsIp;
    }

    public int getJmsPort() {
        return jmsPort;
    }

    public void setJmsPort(int jmsPort) {
        this.jmsPort = jmsPort;
    }

    public String getDataBaseIp() {
        return dataBaseIp;
    }

    public void setDataBaseIp(String dataBaseIp) {
        this.dataBaseIp = dataBaseIp;
    }

    public int getDataBasePort() {
        return dataBasePort;
    }

    public void setDataBasePort(int dataBasePort) {
        this.dataBasePort = dataBasePort;
    }
}
