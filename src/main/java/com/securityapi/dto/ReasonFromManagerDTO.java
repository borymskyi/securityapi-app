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
public class ReasonFromManagerDTO {

    @NotBlank(message = "Reason should not be null or empty")
    @Size(max = 200, message = "Reason must not consist of more than 200 characters")
    String reason;
}
