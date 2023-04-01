package com.securityapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallFromOperatorDTO {

    @NotBlank(message = "consumer_status should not be null or empty")
    @Size(max = 50, message = "consumer_status must not consist of more than 50 characters")
    @JsonProperty("consumer_status")
    String consumerStatus;

    @NotNull(message = "reason_id should not be null")
    @JsonProperty("reason_id")
    Long reasonId;
}
