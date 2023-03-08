package com.securityapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Getter
@Setter
@AllArgsConstructor
public class RolesForEmailDTO {
    private String email;
    private List<String> roles;
}
