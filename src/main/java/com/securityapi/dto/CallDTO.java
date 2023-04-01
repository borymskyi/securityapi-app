package com.securityapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallDTO {

    @JsonProperty("call_id")
    Long callId;

    @JsonProperty("call_template_id")
    Long callTemplateId;

    @JsonProperty("reason_message")
    String reasonMessage;

    @JsonProperty("consumer_status")
    String consumerStatus;

    @JsonProperty("created_at")
    Instant createdAt;
}
