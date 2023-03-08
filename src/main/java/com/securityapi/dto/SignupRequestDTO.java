package com.securityapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Getter
@Setter
@AllArgsConstructor
public class SignupRequestDTO {

    @NotNull
    @Size(max = 50)
    private String email;

    @NotNull
    @Size(max = 500)
    private String password;
}
