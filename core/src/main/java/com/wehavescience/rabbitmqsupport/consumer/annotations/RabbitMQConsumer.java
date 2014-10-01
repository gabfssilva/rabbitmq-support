package com.wehavescience.rabbitmqsupport.consumer.annotations;

import com.wehavescience.rabbitmqsupport.converters.ConsumerConverter;
import com.wehavescience.rabbitmqsupport.converters.DefaultConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * This annotations represents a RabbitMQ queue consumer. <br/>
 * If you annotate a class with this annotations, you must also implements the RabbitMQQueueListener interface.
 *
 * @author Gabriel Francisco <gabfssilva@gmail.com>
 */
@Target({TYPE, METHOD, FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RabbitMQConsumer {
    /**
     * Queue name
     *
     * @return
     */
    String queue();

    /**
     * Exchange name
     *
     * @return
     */
    String exchange() default "";

    /**
     * RabbitMQ stores the messages as byte arrays. You should implement your own converter. If you don't, we implement one for you.
     *
     * @return
     */
    Class<? extends ConsumerConverter> converterClass() default DefaultConverter.class;

    /**
     * If RabbitMQSupport cannot connect to RabbitMQ, it will retry until it does. <br/>
     * -1 disables auto retry.
     *
     * @return
     */
    int retryInterval() default 20000;
}
