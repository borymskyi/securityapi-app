package com.securityapi.exception;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}
