package com.securityapi.exception;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
