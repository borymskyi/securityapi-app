package com.securityapi.exception;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

public class EmailExistException extends RuntimeException {
    public EmailExistException(String message) {
        super(message);
    }
}
