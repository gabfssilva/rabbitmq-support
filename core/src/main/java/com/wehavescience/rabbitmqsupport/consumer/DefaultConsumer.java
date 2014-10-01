package com.wehavescience.rabbitmqsupport.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.wehavescience.rabbitmqsupport.consumer.annotations.*;
import com.wehavescience.rabbitmqsupport.register.ConsumerRegister;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.wehavescience.rabbitmqsupport.util.RabbitMQClientHelper.converterInstance;
import static com.wehavescience.rabbitmqsupport.util.RabbitMQClientHelper.fetchListenerInf;


/**
 * @author Gabriel Francisco <gabfssilva@gmail.com>
 */
public class DefaultConsumer<T> implements Consumer {
    private ConsumerRegister asyncConsumerRegister;
    private Map<Class<? extends Annotation>, Method> annotations;
    private Object rabbitMQListener;

    public DefaultConsumer(ConsumerRegister asyncConsumerRegister, Object rabbitMQListener) throws IOException {
        this.asyncConsumerRegister = asyncConsumerRegister;
        this.rabbitMQListener = rabbitMQListener;
        this.annotations = loadMethods(rabbitMQListener.getClass());
        this.asyncConsumerRegister.register(this);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        Method method = annotations.get(OnMessage.class);

        if (method != null) {
            try {
                method.invoke(rabbitMQListener, converterInstance(rabbitMQListener()).convert(body));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
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

        Method method = annotations.get(AfterRegistered.class);

        if (method != null) {
            try {
                method.invoke(rabbitMQListener, consumerTag);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(builder.toString());
    }

    @Override
    public void handleCancelOk(String consumerTag) {
        System.out.println("handleCancelOk");

        Method method = annotations.get(OnCancelBySender.class);

        if (method != null) {
            try {
                method.invoke(rabbitMQListener, consumerTag);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {
        System.out.println("handleCancel");

        Method method = annotations.get(OnCancel.class);

        if (method != null) {
            try {
                method.invoke(rabbitMQListener, consumerTag);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        try {
            asyncConsumerRegister.register(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Method method = annotations.get(BeforeShutdown.class);

        if (method != null) {
            try {
                method.invoke(rabbitMQListener, consumerTag, sig);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void handleRecoverOk(String consumerTag) {
        System.out.println("handleRecoverOk");

        Method method = annotations.get(OnRecover.class);

        if (method != null) {
            try {
                method.invoke(rabbitMQListener, consumerTag);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Object rabbitMQListener() {
        return rabbitMQListener;
    }

    private Map<Class<? extends Annotation>, Method> loadMethods(Class listenerClazz) {
        annotations = new HashMap<Class<? extends Annotation>, Method>();

        for (Method method : rabbitMQListener.getClass().getDeclaredMethods()) {
            if (hasAnnotation(method, OnMessage.class)) {
                annotations.put(OnMessage.class, method);
                continue;
            }

            if (hasAnnotation(method, AfterRegistered.class)) {
                annotations.put(AfterRegistered.class, method);
                continue;
            }

            if (hasAnnotation(method, BeforeShutdown.class)) {
                annotations.put(BeforeShutdown.class, method);
                continue;
            }

            if (hasAnnotation(method, OnCancel.class)) {
                annotations.put(OnCancel.class, method);
                continue;
            }

            if (hasAnnotation(method, OnCancelBySender.class)) {
                annotations.put(OnCancelBySender.class, method);
                continue;
            }

            if (hasAnnotation(method, OnRecover.class)) {
                annotations.put(OnRecover.class, method);
                continue;
            }
        }

        return annotations;
    }

    private boolean hasAnnotation(Method method, Class<? extends Annotation> annotation) {
        return method.getAnnotation(annotation) != null;
    }
}
