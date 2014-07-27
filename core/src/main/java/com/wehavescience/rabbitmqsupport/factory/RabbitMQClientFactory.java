package com.wehavescience.rabbitmqsupport.factory;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.wehavescience.rabbitmqsupport.configurations.RabbitMQConfiguration;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public class RabbitMQClientFactory {
    private RabbitMQConfiguration configuration;

    public RabbitMQClientFactory(RabbitMQConfiguration configuration) {
        this.configuration = configuration;
    }

    public Connection connection() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername(configuration.username());
        factory.setPassword(configuration.password());
        factory.setVirtualHost(configuration.virtualhost());

        String[] urls = configuration.connectionUrl().split(";");

        List<Address> addresses = new LinkedList<Address>();

        for (String url : urls) {
            String[] urlInf = url.split(":");
            String hostname = urlInf[0];
            int port = parseInt(urlInf[1]);
            addresses.add(new Address(hostname, port));
        }

        return factory.newConnection(addresses.toArray(new Address[addresses.size()]));
    }

    public Channel channel() throws IOException {
        return connection().createChannel();
    }
}
