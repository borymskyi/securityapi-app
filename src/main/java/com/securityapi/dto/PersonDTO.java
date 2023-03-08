package com.securityapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Setter
@Getter
@AllArgsConstructor
@Builder
public class PersonDTO {
    private Long id;
    private String email;
    private List<String> roles;
}
