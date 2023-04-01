package com.securityapi.service.impl;

import com.securityapi.domain.CallTemplate;
import com.securityapi.domain.Consumer;
import com.securityapi.domain.Reason;
import com.securityapi.domain.enums.EConsumerStatus;
import com.securityapi.dto.CallTemplateFromManagerDTO;
import com.securityapi.exception.CallTemplateNotFoundException;
import com.securityapi.repository.CallTemplateRepository;
import com.securityapi.service.CallTemplateService;
import com.securityapi.service.ConsumerService;
import com.securityapi.service.PersonService;
import com.securityapi.service.ReasonService;
import io.jsonwebtoken.lang.Collections;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class CallTemplateServiceImpl implements CallTemplateService {

    CallTemplateRepository callTemplateRepository;
    PersonService personService;
    ReasonService reasonService;
    ConsumerService consumerService;

    @Transactional
    @Override
    public CallTemplate createNewCallTemplate(CallTemplateFromManagerDTO callTemplateFromManagerDTO, Long personId) {
        Set<Consumer> consumerSet = new HashSet<>(consumerService.findAllConsumerByStatuses(
                callTemplateFromManagerDTO.getStatuses().stream()
                        .map(statusConsumerDTO -> EConsumerStatus.valueOf(statusConsumerDTO.getStatus()))
                        .toList()
        ));
        List<Consumer> consumerList = new ArrayList<>(consumerSet);

        CallTemplate newCallTemplate = CallTemplate.builder()
                        .manager(personService.findPersonById(personId))
                        .build();
        newCallTemplate.addConsumers(consumerList);

        callTemplateRepository.save(newCallTemplate);

        List<Reason> reasonList = callTemplateFromManagerDTO.getReasons().stream()
                .map(reasonDTO -> reasonService.createReason(reasonDTO.getReason(), newCallTemplate))
                .toList();
        newCallTemplate.addTopics(reasonList);

        return callTemplateRepository.save(newCallTemplate);
    }

    @Transactional(readOnly = true)
    @Override
    public CallTemplate findCallTemplateById(Long id) {
        return callTemplateRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(String.format("CallTemplate with id:%s not found", id));
                    throw new CallTemplateNotFoundException(String.format("CallTemplate with id:%s not found", id));
                });
    }
}
