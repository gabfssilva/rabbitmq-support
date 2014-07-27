package com.wehavescience.rabbitmqsupport.converters;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public interface ProducerConverter<T> {
    byte [] convert(T body);
}
