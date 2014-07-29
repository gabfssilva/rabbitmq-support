package com.wehavescience.rabbitmqsupport.factory;

import com.rabbitmq.client.Connection;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.io.IOException;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public class RabbitMQConnectionPool {
    private GenericObjectPool<Connection> pool;

    public RabbitMQConnectionPool(RabbitMQClientFactory rabbitMQClientFactory) {
        pool = new GenericObjectPool<Connection>(new ConnectionPooledFactory(rabbitMQClientFactory));

        pool.setMaxIdle(5);
        pool.setMinIdle(5);
        pool.setMaxTotal(20);
    }

    public Connection connection() throws IOException {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            throw new IOException();
        }
    }

    public void giveItBack(Connection connection) {
        pool.returnObject(connection);
    }
}
