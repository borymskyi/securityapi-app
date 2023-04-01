package com.securityapi.exception;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String message) {
        super(message);
    }
}
