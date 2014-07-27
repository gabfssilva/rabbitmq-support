package com.wehavescience.rabbitmqsupport.cdi;

import com.wehavescience.rabbitmqsupport.RabbitMQSupport;
import com.wehavescience.rabbitmqsupport.cdi.annotations.Consumers;
import com.wehavescience.rabbitmqsupport.consumer.RabbitMQQueueListener;
import com.wehavescience.rabbitmqsupport.converters.ProducerConverter;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public class RabbitMQSupportCDI{
    @Inject
    @Consumers
    private List<RabbitMQQueueListener> listeners;

    @Inject
    private RabbitMQSupport rabbitMQSupport;

    @Inject
    public RabbitMQSupportCDI(@Consumers List<RabbitMQQueueListener> listeners, RabbitMQSupport rabbitMQSupport) {
        this.listeners = listeners;
        this.rabbitMQSupport = rabbitMQSupport;
    }

    public void register() throws IOException {
        rabbitMQSupport.register(listeners);
    }

    public void register(RabbitMQQueueListener... listeners) throws IOException {
        rabbitMQSupport.register(listeners);
    }

    public void register(List<RabbitMQQueueListener> listeners) throws IOException {
        rabbitMQSupport.register(listeners);
    }

    public <T> void publish(T object, String queueName) throws IOException {
        rabbitMQSupport.publish(object, queueName);
    }

    public <T> void publish(byte[] object, String queueName) throws IOException {
        rabbitMQSupport.publish(object, queueName);
    }

    public <T> void publish(T object, String queueName, ProducerConverter<T> converter) throws IOException {
        rabbitMQSupport.publish(object, queueName, converter);
    }
}
