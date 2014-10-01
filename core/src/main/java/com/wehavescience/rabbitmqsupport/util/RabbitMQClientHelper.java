package com.wehavescience.rabbitmqsupport.util;

import com.wehavescience.rabbitmqsupport.consumer.annotations.RabbitMQConsumer;
import com.wehavescience.rabbitmqsupport.converters.ConsumerConverter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public class RabbitMQClientHelper {
    private static Map<Class, ConsumerConverter> classConverterMap;

    public static RabbitMQConsumer fetchListenerInf(Object rabbitMQQueueListener) {
        return rabbitMQQueueListener.getClass().getAnnotation(RabbitMQConsumer.class);
    }

    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(out);
            os.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static Object deserialize(byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(in);
            return is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> ConsumerConverter<T> converterInstance(Object o) {
        if (classConverterMap == null) {
            classConverterMap = new HashMap<Class, ConsumerConverter>();
        }

        RabbitMQConsumer annotation = o.getClass().getAnnotation(RabbitMQConsumer.class);
        Class<? extends ConsumerConverter> converterClass = annotation.converterClass();

        if (classConverterMap.containsKey(converterClass)) {
            return classConverterMap.get(converterClass);
        }

        try {
            classConverterMap.put(converterClass, converterClass.newInstance());
            return classConverterMap.get(converterClass);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
