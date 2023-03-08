package com.securityapi.repository;

import com.securityapi.domain.ERole;
import com.securityapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByName(ERole name);
}
