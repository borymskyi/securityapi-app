package com.securityapi.service.impl;

import com.securityapi.domain.Consumer;
import com.securityapi.domain.enums.EConsumerStatus;
import com.securityapi.exception.ConsumerNotFoundException;
import com.securityapi.repository.ConsumerRepository;
import com.securityapi.service.ConsumerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class ConsumerServiceImpl implements ConsumerService {

    ConsumerRepository consumerRepository;

    @Override
    public Consumer findConsumerByStatus(EConsumerStatus consumerStatus) {
        return consumerRepository.findByStatus(consumerStatus)
                .orElseThrow(() -> {
                    log.error(String.format("Consumer with status: %s is not exist", consumerStatus));
                    throw new ConsumerNotFoundException(String.format("Consumer with status: %s is not exist", consumerStatus));
                });
    }

    @Override
    public List<Consumer> findAllConsumerByStatuses(List<EConsumerStatus> statusList) {
        List<Consumer> consumerList = statusList.stream()
                .map(this::findConsumerByStatus)
                .toList();

        return consumerList;
    }
}
