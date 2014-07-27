package com.wehavescience.rabbitmqsupport.cdi.annotations;

import javax.enterprise.util.Nonbinding;
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
public @interface Consumers {
    @Nonbinding String packagePrefixScan() default "";
}
