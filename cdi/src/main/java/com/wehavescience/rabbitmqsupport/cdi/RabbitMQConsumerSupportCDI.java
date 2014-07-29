package com.wehavescience.rabbitmqsupport.cdi;

import com.wehavescience.rabbitmqsupport.RabbitMQSupport;
import com.wehavescience.rabbitmqsupport.cdi.annotations.Consumers;
import com.wehavescience.rabbitmqsupport.cdi.exceptions.NotSupportedException;
import com.wehavescience.rabbitmqsupport.configurations.RabbitMQConfiguration;
import com.wehavescience.rabbitmqsupport.consumer.RabbitMQQueueListener;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public class RabbitMQConsumerSupportCDI {
    @Inject
    @Consumers
    private List<RabbitMQQueueListener> listeners;

    private RabbitMQSupport rabbitMQSupport;

    public RabbitMQConsumerSupportCDI(RabbitMQSupport rabbitMQSupport){
        this.rabbitMQSupport = rabbitMQSupport;
    }

    public RabbitMQConsumerSupportCDI(RabbitMQConfiguration rabbitMQConfiguration, @Consumers List<RabbitMQQueueListener> listeners) throws IOException {
        rabbitMQSupport = new RabbitMQSupport(rabbitMQConfiguration);
        this.listeners = listeners;
    }

    public void register() throws IOException {
        if (listeners == null) {
            throw new NotSupportedException("The listeners are null. Maybe you didn't create your class injecting it");
        }
        rabbitMQSupport.register(listeners);
    }
}
