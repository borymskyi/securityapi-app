package com.securityapi.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Data
@Builder
public class ResponseError {

    private final HttpStatus status;

    private final List<String> errorMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time = LocalDateTime.now();
}
