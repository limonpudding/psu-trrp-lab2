package ru.psu.martyshenko.trrp.lab2.app;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Configuration {

    @JsonProperty
    private String ip;

    @JsonProperty
    private int socketPort;

    @JsonProperty
    private int jmsBrokerPort;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getSocketPort() {
        return socketPort;
    }

    public void setSocketPort(int socketPort) {
        this.socketPort = socketPort;
    }

    public int getJmsBrokerPort() {
        return jmsBrokerPort;
    }

    public void setJmsBrokerPort(int jmsBrokerPort) {
        this.jmsBrokerPort = jmsBrokerPort;
    }
}
