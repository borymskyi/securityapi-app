package com.securityapi.service;

import com.securityapi.domain.CallTemplate;
import com.securityapi.domain.Reason;
import org.springframework.stereotype.Service;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Service
public interface ReasonService {

    Reason createReason(String reason, CallTemplate callTemplate);

    Reason findReasonById(Long topicId);
}
