package com.wehavescience.rabbitmqsupport.consumer.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * @author Gabriel Francisco <gabfssilva@gmail.com>
 */
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnRecover {
    boolean retryOnException() default true;
    int maxRetries() default 50;
    int retryInterval() default 100000;
}
