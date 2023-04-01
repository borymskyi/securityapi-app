package com.securityapi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusConsumerFromManagerDTO {

    @NotBlank(message = "Consumer should not be null or empty")
    @Size(max = 50, message = "Consumer must not consist of more than 50 characters")
    String status;
}
