package com.securityapi.exception;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */
public class CallTemplateNotFoundException extends RuntimeException {
    public CallTemplateNotFoundException(String message) {
        super(message);
    }
}
