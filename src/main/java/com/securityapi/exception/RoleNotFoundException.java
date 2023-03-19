package com.securityapi.exception;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
