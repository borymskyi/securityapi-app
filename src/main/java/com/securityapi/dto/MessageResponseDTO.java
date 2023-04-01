package com.securityapi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponseDTO {
    String message;
}
