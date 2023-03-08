package com.securityapi.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Data
public class AuthenticationRequestDTO {
    @Email(message = "Email should be valid")
    @Size(max = 50)
    @NotNull
    private String email;

    @Size(max = 500)
    @NotNull
    private String password;
}
