package com.securityapi.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Getter
public class ResponseError {

    private final HttpStatus status;

    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time = LocalDateTime.now();

    public ResponseError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
