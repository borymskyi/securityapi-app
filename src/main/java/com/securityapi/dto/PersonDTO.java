package com.securityapi.dto;

import lombok.*;

import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Data
@Builder
public class PersonDTO {
    private Long id;
    private String email;
    private List<String> roles;
}
