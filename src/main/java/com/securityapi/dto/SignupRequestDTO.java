package com.securityapi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
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
public class SignupRequestDTO {

    @NotNull
    @Size(max = 50)
    @Email
    String email;

    @NotNull
    @Size(max = 500)
    String password;
}
