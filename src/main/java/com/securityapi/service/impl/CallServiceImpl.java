package com.securityapi.service.impl;

import com.securityapi.domain.Call;
import com.securityapi.domain.enums.EConsumerStatus;
import com.securityapi.dto.CallFromOperatorDTO;
import com.securityapi.repository.CallRepository;
import com.securityapi.service.CallService;
import com.securityapi.service.ConsumerService;
import com.securityapi.service.ReasonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class CallServiceImpl implements CallService {

    ConsumerService consumerService;
    ReasonService reasonService;
    CallRepository callRepository;

    @Transactional
    @Override
    public Call saveCallByStatusAndTopicId(CallFromOperatorDTO callFromOperatorDTO) {
        Call newCall = Call.builder()
                .consumer(consumerService.findConsumerByStatus(EConsumerStatus.valueOf(callFromOperatorDTO.getConsumerStatus())))
                .reason(reasonService.findReasonById(callFromOperatorDTO.getReasonId()))
                .build();

        return callRepository.save(newCall);
    }

    @Transactional
    @Override
    public List<Call> getAllCallByIdCallTemplate(Long templateId) {
        log.info("***getting all call by id call_template with id: " + templateId);
        return callRepository.findAllByReason_CallTemplate_Id(templateId);
    }
}
