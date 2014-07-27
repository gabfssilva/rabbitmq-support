package com.wehavescience.rabbitmqsupport.converters;

import static com.wehavescience.rabbitmqsupport.util.RabbitMQClientHelper.deserialize;
import static com.wehavescience.rabbitmqsupport.util.RabbitMQClientHelper.serialize;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public class DefaultConverter<T> implements ConsumerConverter<T>, ProducerConverter<T> {
    @Override
    public T convert(byte[] body) {
        return (T) deserialize(body);
    }

    @Override
    public byte[] convert(T body) {
        return serialize(body);
    }
}
