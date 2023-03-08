package com.securityapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Getter
@Setter
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private List<String> roles;

    public JwtResponseDTO(String token, Long id, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }
}
