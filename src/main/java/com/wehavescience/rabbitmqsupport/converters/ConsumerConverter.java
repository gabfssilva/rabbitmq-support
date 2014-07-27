package com.wehavescience.rabbitmqsupport.converters;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public interface ConsumerConverter<T> {
    T convert(byte[] body);
}
