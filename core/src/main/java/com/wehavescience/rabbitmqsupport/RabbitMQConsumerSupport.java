package com.wehavescience.rabbitmqsupport;

import com.wehavescience.rabbitmqsupport.consumer.DefaultConsumer;
import com.wehavescience.rabbitmqsupport.factory.RabbitMQClientFactory;
import com.wehavescience.rabbitmqsupport.factory.RabbitMQConnectionPool;
import com.wehavescience.rabbitmqsupport.register.AsyncConsumerRegister;

import java.util.List;

/**
 * @author Gabriel Francisco <cad_gfsilva@uolinc.com>
 */
public class RabbitMQConsumerSupport {
    private RabbitMQClientFactory factory;
    private RabbitMQConnectionPool connectionPool;

    RabbitMQConsumerSupport(RabbitMQClientFactory factory, RabbitMQConnectionPool connectionPool) {
        this.factory = factory;
        this.connectionPool = connectionPool;
    }

    public void register(Object... listeners) {
        for (Object rabbitMQListener : listeners) {
//            new DefaultConsumer(new RabbitMQPublisherSupport(connectionPool), new AsyncConsumerRegister(factory), rabbitMQListener);
        }
    }

    public void register(List<Object> listeners) {
        for (Object rabbitMQListener : listeners) {
//            new DefaultConsumer(new RabbitMQPublisherSupport(connectionPool), new AsyncConsumerRegister(factory), rabbitMQListener);
        }
    }
}
