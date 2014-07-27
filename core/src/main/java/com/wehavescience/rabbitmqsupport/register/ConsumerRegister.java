package com.wehavescience.rabbitmqsupport.register;

import com.wehavescience.rabbitmqsupport.consumer.DefaultConsumer;
import com.wehavescience.rabbitmqsupport.factory.RabbitMQClientFactory;

import java.io.IOException;

import static com.wehavescience.rabbitmqsupport.util.RabbitMQClientHelper.fetchListenerInf;


/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public class ConsumerRegister {
    private RabbitMQClientFactory rabbitMQClientFactory;

    public ConsumerRegister(RabbitMQClientFactory rabbitMQClientFactory) {
        this.rabbitMQClientFactory = rabbitMQClientFactory;
    }

    public void register(DefaultConsumer consumer) throws IOException {
        String consumerName = fetchListenerInf(consumer.rabbitMQListener()).queue();
        rabbitMQClientFactory.connection().createChannel().basicConsume(consumerName, true, consumer);
    }
}
