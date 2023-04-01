package com.securityapi.service;

import com.securityapi.domain.Consumer;
import com.securityapi.domain.enums.EConsumerStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Service
public interface ConsumerService {
    Consumer findConsumerByStatus(EConsumerStatus consumerStatus);

    List<Consumer> findAllConsumerByStatuses(List<EConsumerStatus> statusList);
}
