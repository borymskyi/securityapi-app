package com.securityapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Data
@Builder
public class JwtResponseDTO {
    private String token;
    private String type;
    private Long id;
    private String email;
    private List<String> roles;
}
