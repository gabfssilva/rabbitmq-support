package com.wehavescience.rabbitmqsupport.cdi.annotations;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
@Target({TYPE, METHOD, FIELD, PARAMETER})
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface RabbitMQContext {
    String username() default "guest";
    String password() default "guest";
    String virtualhost() default "/";
    String urls() default "localhost:5672"; //if you have a cluster, you should separate each url by ";"
}
