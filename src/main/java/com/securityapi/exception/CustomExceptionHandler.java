package com.securityapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({EmailNotFoundException.class, RoleNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleNotFoundExceptions(RuntimeException exception) {
        log.warn(exception.getMessage(), exception);
        return new ResponseError(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({EmailExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleExistExceptions(RuntimeException exception) {
        log.warn(exception.getMessage(), exception);
        return new ResponseError(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler({InvalidDataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleInvalidDataExceptions(RuntimeException exception) {
        log.warn(exception.getMessage(), exception);
        return new ResponseError(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
