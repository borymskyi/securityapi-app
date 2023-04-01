package com.securityapi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailAndRoleNamesPersonDTO {

    @Email(message = "Email should be valid")
    @Min(value = 5, message = "Email must have more then 5 characters")
    @Max(value = 50, message = "Email must have less then 50 characters")
    String email;

    @NotNull
    @Pattern(regexp = "/\\s*(ROLE_OPERATOR|ROLE_MANAGER)\\S*/gi", message = "Role should be: ROLE_OPERATOR or ROLE_MANAGER")
    String role;
}
