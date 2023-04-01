package com.securityapi.repository;

import com.securityapi.domain.Reason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Repository
public interface ReasonRepository extends JpaRepository<Reason, Long> {
}
