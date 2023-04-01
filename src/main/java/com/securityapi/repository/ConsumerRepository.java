package com.securityapi.repository;

import com.securityapi.domain.Consumer;
import com.securityapi.domain.enums.EConsumerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    Optional<Consumer> findByStatus(EConsumerStatus status);
}
