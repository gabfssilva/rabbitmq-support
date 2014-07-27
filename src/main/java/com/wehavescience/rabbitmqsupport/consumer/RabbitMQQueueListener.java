package com.wehavescience.rabbitmqsupport.consumer;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public interface RabbitMQQueueListener<T> {
    void onMessage(T message) throws RuntimeException;
}
