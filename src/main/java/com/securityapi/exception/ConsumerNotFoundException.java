package com.securityapi.exception;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */
public class ConsumerNotFoundException extends RuntimeException {
    public ConsumerNotFoundException(String message) {
        super(message);
    }
}
