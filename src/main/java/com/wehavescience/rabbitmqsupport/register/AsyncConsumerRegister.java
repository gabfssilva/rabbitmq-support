package com.wehavescience.rabbitmqsupport.register;

import com.wehavescience.rabbitmqsupport.consumer.DefaultConsumer;
import com.wehavescience.rabbitmqsupport.consumer.annotations.RabbitMQConsumer;
import com.wehavescience.rabbitmqsupport.factory.RabbitMQClientFactory;

import java.io.IOException;

import static com.wehavescience.rabbitmqsupport.util.RabbitMQClientHelper.fetchListenerInf;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public class AsyncConsumerRegister extends ConsumerRegister {

    public AsyncConsumerRegister(RabbitMQClientFactory rabbitMQClientFactory) {
        super(rabbitMQClientFactory);
    }

    @Override
    public void register(final DefaultConsumer consumer) throws IOException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                RabbitMQConsumer rabbitMQConsumer = fetchListenerInf(consumer.rabbitMQListener());
                try {
                    syncRegister(consumer);
                } catch (Throwable t) {
                    System.out.println("Could not register a consumer to " + rabbitMQConsumer.queue());
                    System.out.println("Trying again later after " + rabbitMQConsumer.retryInterval() + " ms");
                    try {
                        Thread.sleep(rabbitMQConsumer.retryInterval());
                        register(consumer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void syncRegister(DefaultConsumer consumer) throws IOException {
        super.register(consumer);
    }
}
