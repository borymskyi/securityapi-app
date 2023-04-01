package com.securityapi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallTemplateFromManagerDTO {

    @Size(min = 1, message = "Should be more than 1")
    List<StatusConsumerFromManagerDTO> statuses;

    @Size(min = 1, message = "Should be more than 1")
    @UniqueElements(message = "Reasons should be uniq")
    List<ReasonFromManagerDTO> reasons;
}
