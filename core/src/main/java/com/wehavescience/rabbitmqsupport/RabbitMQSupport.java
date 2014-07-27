package com.wehavescience.rabbitmqsupport;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wehavescience.rabbitmqsupport.configurations.RabbitMQConfiguration;
import com.wehavescience.rabbitmqsupport.consumer.DefaultConsumer;
import com.wehavescience.rabbitmqsupport.converters.DefaultConverter;
import com.wehavescience.rabbitmqsupport.converters.ProducerConverter;
import com.wehavescience.rabbitmqsupport.factory.RabbitMQClientFactory;
import com.wehavescience.rabbitmqsupport.register.AsyncConsumerRegister;
import com.wehavescience.rabbitmqsupport.consumer.RabbitMQQueueListener;

import java.io.IOException;
import java.util.List;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public class RabbitMQSupport {
    private RabbitMQClientFactory factory;
    private RabbitMQConfiguration configuration;

    public RabbitMQSupport(RabbitMQConfiguration configuration) throws IOException {
        factory = new RabbitMQClientFactory(configuration);
        this.configuration = configuration;
    }

    public void register(RabbitMQQueueListener... listeners) throws IOException {
        for (RabbitMQQueueListener rabbitMQListener : listeners) {
            new DefaultConsumer(new AsyncConsumerRegister(factory), rabbitMQListener);
        }
    }

    public void register(List<RabbitMQQueueListener> listeners) throws IOException {
        for (RabbitMQQueueListener rabbitMQListener : listeners) {
            new DefaultConsumer(new AsyncConsumerRegister(factory), rabbitMQListener);
        }
    }

    public <T> void publish(T object, String queueName) throws IOException {
        publish(object, queueName, new DefaultConverter<T>());
    }

    public <T> void publish(T object, String queueName, ProducerConverter<T> converter) throws IOException {
        byte[] bytes = converter.convert(object);
        publish(bytes, queueName);
    }

    public <T> void publish(byte [] object, String queueName) throws IOException {
        Channel channel = factory.channel();
        channel.basicPublish("", queueName, null, object);
    }
}
