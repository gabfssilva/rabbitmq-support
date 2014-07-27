package com.wehavescience.rabbitmqsupport.cdi.exceptions;

/**
 * @author Gabriel Francisco  <gabfssilva@gmail.com>
 */
public class NotSupportedException extends RuntimeException {
    public NotSupportedException() {
    }

    public NotSupportedException(String message) {
        super(message);
    }

    public NotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSupportedException(Throwable cause) {
        super(cause);
    }
}
