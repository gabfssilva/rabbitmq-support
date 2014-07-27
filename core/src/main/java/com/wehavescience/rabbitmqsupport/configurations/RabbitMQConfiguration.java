package com.wehavescience.rabbitmqsupport.configurations;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public class RabbitMQConfiguration {
    private String username;
    private String password;
    private String virtualhost;
    private String urls;

    public RabbitMQConfiguration(String username, String password, String virtualhost, String urls) {
        this.username = username;
        this.password = password;
        this.virtualhost = virtualhost;
        this.urls = urls;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String virtualhost() {
        return virtualhost;
    }

    public String connectionUrl() {
        return urls;
    }
}
