package com.wehavescience.rabbitmqsupport.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.wehavescience.rabbitmqsupport.register.ConsumerRegister;

import java.io.IOException;

import static com.wehavescience.rabbitmqsupport.util.RabbitMQClientHelper.fetchListenerInf;
import static com.wehavescience.rabbitmqsupport.util.RabbitMQClientHelper.converterInstance;


/**
 * @author Gabriel Francisco <gabfssilva@gmail.com>
 */
public class DefaultConsumer<T> implements Consumer {
    private ConsumerRegister asyncConsumerRegister;
    private RabbitMQQueueListener<T> rabbitMQListener;

    public DefaultConsumer(ConsumerRegister asyncConsumerRegister, RabbitMQQueueListener<T> rabbitMQListener) throws IOException {
        this.asyncConsumerRegister = asyncConsumerRegister;
        this.rabbitMQListener = rabbitMQListener;
        this.asyncConsumerRegister.register(this);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        rabbitMQListener.onMessage((T) converterInstance(rabbitMQListener()).convert(body));
    }

    @Override
    public void handleConsumeOk(String consumerTag) {
        StringBuilder builder = new StringBuilder();

        builder
                .append("Class ")
                .append(rabbitMQListener().getClass().getSimpleName())
                .append(" listening to ")
                .append(fetchListenerInf(rabbitMQListener()).queue())
                .append(" queue");

        System.out.println(builder.toString());
    }

    @Override
    public void handleCancelOk(String consumerTag) {
        System.out.println("handleCancelOk");
    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {
        System.out.println("handleCancel");
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        try {
            asyncConsumerRegister.register(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleRecoverOk(String consumerTag) {
        System.out.println("handleRecoverOk");
    }

    public RabbitMQQueueListener<T> rabbitMQListener() {
        return rabbitMQListener;
    }
}
