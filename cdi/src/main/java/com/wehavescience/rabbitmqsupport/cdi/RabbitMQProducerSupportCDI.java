package com.wehavescience.rabbitmqsupport.cdi;

import com.wehavescience.rabbitmqsupport.RabbitMQSupport;
import com.wehavescience.rabbitmqsupport.configurations.RabbitMQConfiguration;
import com.wehavescience.rabbitmqsupport.converters.ProducerConverter;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public class RabbitMQProducerSupportCDI {
    private RabbitMQSupport rabbitMQSupport;

    public RabbitMQProducerSupportCDI(RabbitMQSupport rabbitMQSupport){
        this.rabbitMQSupport = rabbitMQSupport;
    }

    public RabbitMQProducerSupportCDI(RabbitMQConfiguration rabbitMQConfiguration) throws IOException {
        this.rabbitMQSupport = new RabbitMQSupport(rabbitMQConfiguration);
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
