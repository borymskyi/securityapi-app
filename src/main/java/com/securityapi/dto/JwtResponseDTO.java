package com.securityapi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponseDTO {
    String token;
    String type;
    Long id;
    String email;
    List<String> roles;
}
