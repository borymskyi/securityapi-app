package com.securityapi.repository;

import com.securityapi.domain.ERole;
import com.securityapi.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select distinct p from Person p left join fetch p.roles where p.email = ?1")
    Optional<Person> findByEmailFetchRoles(String email);

    Boolean existsByEmail(String email);

    List<Person> findAllByRolesName(ERole roleName);
}
