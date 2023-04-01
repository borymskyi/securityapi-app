package com.securityapi.service;

import com.securityapi.domain.Call;
import com.securityapi.dto.CallFromOperatorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Service
public interface CallService {

    Call saveCallByStatusAndTopicId(CallFromOperatorDTO callFromOperatorDTO);

    List<Call> getAllCallByIdCallTemplate(Long templateId);
}
