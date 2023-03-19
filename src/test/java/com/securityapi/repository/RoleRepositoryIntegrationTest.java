package com.securityapi.repository;

import com.securityapi.domain.ERole;
import com.securityapi.domain.Person;
import com.securityapi.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Sql("/sql/datasql.sql")
@ActiveProfiles("test-containers-flyway")
@Testcontainers
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RoleRepositoryIntegrationTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PersonRepository personRepository;

    @Test
    void test_findRoleByName() {
        Role actualRole = roleRepository.findRoleByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new IllegalArgumentException("Error: illegal argument"));

        assertEquals(actualRole.getName(), ERole.ROLE_ADMIN);
    }

    @Test
    void test_addRoleToPerson(){
        Person initPerson = personRepository.findByEmailFetchRoles("user@gmail.com")
                .orElseThrow(() -> new UsernameNotFoundException("Error: person not found"));
        Role role = roleRepository.findRoleByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new IllegalArgumentException("Error: illegal argument"));

        initPerson.addRole(role);
        personRepository.save(initPerson);

        Person actualPerson = personRepository.findByEmailFetchRoles("user@gmail.com")
                .orElseThrow(() -> new UsernameNotFoundException("Error: person not found"));

        assertThat(
                actualPerson.getRoles().stream().map(r -> r.getName().toString()).toList(),
                is(initPerson.getRoles().stream().map(r -> r.getName().toString()).toList())
        );
    }
}