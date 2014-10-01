package com.wehavescience.rabbitmqsupport.consumer.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Gabriel Francisco <gabfssilva@gmail.com>
 */
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnCancelBySender {
}
