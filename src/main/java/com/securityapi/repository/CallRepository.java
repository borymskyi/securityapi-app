package com.securityapi.repository;

import com.securityapi.domain.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {

    @Query("SELECT c FROM Call c JOIN FETCH c.consumer cons JOIN FETCH c.reason r WHERE r.callTemplate.id = :templateId")
    List<Call> findAllByReason_CallTemplate_Id(@Param("templateId") Long templateId);
}
