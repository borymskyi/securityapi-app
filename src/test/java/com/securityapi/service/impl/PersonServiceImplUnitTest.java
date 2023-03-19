package com.securityapi.service.impl;

import com.securityapi.domain.ERole;
import com.securityapi.domain.Person;
import com.securityapi.domain.Role;
import com.securityapi.dto.SignupRequestDTO;
import com.securityapi.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplUnitTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private RoleServiceImpl roleService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void test_findPersonByEmail_shouldCallRepository() {
        final Person expected = mock(Person.class);
        when(personRepository.findByEmailFetchRoles("admin@gmail.com")).thenReturn(Optional.of(expected));

        final Person actual = personService.findPersonByEmail("admin@gmail.com");
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void test_savePerson_shouldCallRepository() {
        final String expectedEmail = "test@gmail.com";
        final String expectedPassword = "qwerty";
        final String expectedEncodedPassword = "superEncodePasswordAWDAWD";
        final Role expectedRole = Role.builder().id(1L).name(ERole.ROLE_USER).build();

        final SignupRequestDTO signupRequestDTO = SignupRequestDTO.builder()
                .email(expectedEmail)
                .password(expectedPassword)
                .build();

        when(personRepository.existsByEmail(expectedEmail)).thenReturn(false);
        when(roleService.findRoleByRoleName(ERole.ROLE_USER)).thenReturn(expectedRole);
        when(passwordEncoder.encode(expectedPassword)).thenReturn(expectedEncodedPassword);

        personService.saveProfile(signupRequestDTO);

        verify(personRepository).existsByEmail(expectedEmail);
        verify(roleService).findRoleByRoleName(ERole.ROLE_USER);
        verify(personRepository).save(Mockito.argThat(argument ->
                argument.getEmail().equals(expectedEmail) &&
                        argument.getPassword().equals(expectedEncodedPassword)
                ));
    }
}