package com.wehavescience.rabbitmqsupport.factory;

import com.rabbitmq.client.Connection;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public class ConnectionPooledFactory implements PooledObjectFactory<Connection> {
    private RabbitMQClientFactory factory;

    public ConnectionPooledFactory(RabbitMQClientFactory rabbitMQClientFactory) {
        this.factory = rabbitMQClientFactory;
    }

    @Override
    public PooledObject<Connection> makeObject() throws Exception {
        return new DefaultPooledObject<Connection>(factory.connection());
    }

    @Override
    public void destroyObject(PooledObject<Connection> connectionPooledObject) throws Exception {
        connectionPooledObject.getObject().close();
    }

    @Override
    public boolean validateObject(PooledObject<Connection> connectionPooledObject) {
        return connectionPooledObject.getObject().isOpen();
    }

    @Override
    public void activateObject(PooledObject<Connection> connectionPooledObject) throws Exception {
    }

    @Override
    public void passivateObject(PooledObject<Connection> connectionPooledObject) throws Exception {
    }

}
