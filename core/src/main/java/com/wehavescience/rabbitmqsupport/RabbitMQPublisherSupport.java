package com.wehavescience.rabbitmqsupport;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wehavescience.rabbitmqsupport.converters.DefaultConverter;
import com.wehavescience.rabbitmqsupport.converters.ProducerConverter;
import com.wehavescience.rabbitmqsupport.factory.RabbitMQConnectionPool;

import java.io.IOException;

/**
 * @author Gabriel Francisco <cad_gfsilva@uolinc.com>
 */
public class RabbitMQPublisherSupport {
    private RabbitMQConnectionPool connectionPool;

    RabbitMQPublisherSupport(RabbitMQConnectionPool pool) {
        connectionPool = pool;
    }

    public <T> void publish(T object, String exchange) throws IOException {
        publish(object, exchange, new DefaultConverter<T>());
    }

    public <T> void publish(T object, String exchange, int delay) throws IOException {
        publish(object, exchange, new DefaultConverter<T>(), delay);
    }

    public <T> void publish(T object, String exchange, ProducerConverter<T> converter) throws IOException {
        byte[] bytes = converter.convert(object);
        publish(bytes, exchange, 0);
    }

    public <T> void publish(T object, String exchange, ProducerConverter<T> converter, int delay) throws IOException {
        byte[] bytes = converter.convert(object);
        publish(bytes, exchange, delay);
    }

    public <T> void publishToQueue(byte[] object, String queueName, int delay) throws IOException {
        Connection connection = connectionPool.connection();
        Channel channel = connection.createChannel();

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties();

        AMQP.BasicProperties build = basicProperties
                .builder()
                .expiration(String.valueOf(delay))
                .build();

        try {
            channel.basicPublish("", queueName, build, object);
        } finally {
            channel.close();
            connectionPool.giveItBack(connection);
        }
    }

    public <T> void publish(byte[] object, String exchange, int delay) throws IOException {
        Connection connection = connectionPool.connection();
        Channel channel = connection.createChannel();

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties();

        AMQP.BasicProperties build = basicProperties
                .builder()
                .expiration(String.valueOf(delay))
                .build();

        try {
            channel.basicPublish(exchange, "", build, object);
        } finally {
            if (channel.isOpen()) {
                channel.close();
            }

            connectionPool.giveItBack(connection);
        }
    }
}
