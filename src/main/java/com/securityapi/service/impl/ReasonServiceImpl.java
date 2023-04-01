package com.securityapi.service.impl;

import com.securityapi.domain.CallTemplate;
import com.securityapi.domain.Reason;
import com.securityapi.exception.TopicNotFoundException;
import com.securityapi.repository.ReasonRepository;
import com.securityapi.service.ReasonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class ReasonServiceImpl implements ReasonService {

    ReasonRepository reasonRepository;

    @Override
    public Reason createReason(String reason, CallTemplate callTemplate) {
        Reason newReason = Reason.builder()
                .reasonMessage(reason)
                .build();
        newReason.addCallTemplate(callTemplate);

        return reasonRepository.save(newReason);
    }

    @Override
    public Reason findReasonById(Long reasonId) {
        return reasonRepository.findById(reasonId)
                .orElseThrow(() -> {
                    log.error(String.format("Topic with id: %s not found", reasonId));
                    throw new TopicNotFoundException(String.format("Topic with id: %s not found", reasonId));
                });
    }
}
