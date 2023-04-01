package com.securityapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({PersonNotFoundException.class, RoleNotFoundException.class, ConsumerNotFoundException.class,
            CallTemplateNotFoundException.class, TopicNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleNotFoundExceptions(RuntimeException exception) {
        log.warn(exception.getMessage(), exception);
        return ResponseError.builder()
                .status(HttpStatus.NOT_FOUND).errorMessage(List.of(exception.getMessage()))
                .build();
    }

    @ExceptionHandler({EmailExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleExistExceptions(RuntimeException exception) {
        log.warn(exception.getMessage(), exception);
        return ResponseError.builder()
                .status(HttpStatus.CONFLICT)
                .errorMessage(List.of(exception.getMessage()))
                .build();
    }

    @ExceptionHandler({InvalidDataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleInvalidDataExceptions(RuntimeException exception) {
        log.warn(exception.getMessage(), exception);
        return ResponseError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorMessage(List.of(exception.getMessage()))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleMethodArgNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorList = ex.getBindingResult().getFieldErrors().stream().map(fe -> fe.getDefaultMessage()).toList();
        log.warn("Errors: " + errorList);
        return ResponseError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorMessage(errorList)
                .build();
    }
}
