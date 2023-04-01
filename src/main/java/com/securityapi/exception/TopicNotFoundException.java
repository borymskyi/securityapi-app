package com.securityapi.exception;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */
public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(String message) {
        super(message);
    }
}
