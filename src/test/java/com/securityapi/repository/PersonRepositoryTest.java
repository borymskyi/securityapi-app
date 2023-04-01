package com.securityapi.repository;

import com.securityapi.domain.enums.ERole;
import com.securityapi.domain.Person;
import com.securityapi.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ActiveProfiles("test-containers-flyway")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void test_saveProfile() {
        List<Role> roles = List.of(
                roleRepository.findRoleByName(ERole.ROLE_MANAGER)
                        .orElseThrow(() -> {
                            log.error("Error: role not found!");
                            return new IllegalArgumentException("error");
                        }));

        Person expectedPerson = Person.builder()
                .email("test@gmail.com")
                .password("$2a$10$ssYfD8xm/j.H70cCYiPLLebAI0G6qCd7JCWDw3zPO9.FWjv33i57C")
                .build();
        expectedPerson.addRoles(roles);

        personRepository.save(expectedPerson);

        Person actualPerson = personRepository.findByEmailFetchRoles("test@gmail.com")
                .orElseThrow(() -> new UsernameNotFoundException("Error: Person not found!"));
        assertEquals(expectedPerson, actualPerson);
    }
}