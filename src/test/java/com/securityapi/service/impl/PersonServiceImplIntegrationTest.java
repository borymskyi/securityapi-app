package com.securityapi.service.impl;

import com.securityapi.repository.PersonRepository;
import com.securityapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@ActiveProfiles("test-containers-flyway")
@Testcontainers
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PersonServiceImplIntegrationTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

   /* @Test
    void test_addExistRoleForPersonByExistEmail_shouldAddRoleAndSaveProfile() {
        EmailPersonAndRoleNamesDTO requestRoleForPerson = EmailPersonAndRoleNamesDTO.builder()
                .email("operator@gmail.com").role("ROLE_MANAGER").build();

        Person actualPerson = personService.addRoleForPersonByEmail(requestRoleForPerson);

        assertThat(actualPerson.getRoles(), hasSize(2));
        assertThat(
                actualPerson.getRoles().stream().map(r -> r.getName().toString()).collect(Collectors.toList()),
                containsInAnyOrder("ROLE_MANAGER", "ROLE_OPERATOR")
        );
    }*/

    /*@Test
    void test_addRoleForPersonByEmailThatAlreadyHasOne_shouldThrowInvalidDataException() {
        EmailPersonAndRoleNamesDTO requestRoleForPerson = EmailPersonAndRoleNamesDTO.builder()
                .email("operator@gmail.com").role("ROLE_OPERATOR").build();

        assertThrows(InvalidDataException.class, () -> personService.addRoleForPersonByEmail(requestRoleForPerson));
    }*/
}