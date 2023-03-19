package com.securityapi.service.impl;

import com.securityapi.domain.ERole;
import com.securityapi.domain.Person;
import com.securityapi.domain.Role;
import com.securityapi.dto.EmailPersonAndRoleNamesDTO;
import com.securityapi.dto.SignupRequestDTO;
import com.securityapi.exception.EmailExistException;
import com.securityapi.exception.EmailNotFoundException;
import com.securityapi.exception.InvalidDataException;
import com.securityapi.repository.PersonRepository;
import com.securityapi.service.PersonService;
import com.securityapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Person findPersonByEmail(String email) {
        return personRepository.findByEmailFetchRoles(email)
                .orElseThrow(() -> {
                    log.warn("Failed to find a person by email: " + email);
                    return new EmailNotFoundException("Person not found with email: " + email);
                });
    }

    public void saveProfile(SignupRequestDTO signupRequestDTO) {
        if (hasText(signupRequestDTO.getEmail()) && hasText(signupRequestDTO.getPassword())) {
            if (!personRepository.existsByEmail(signupRequestDTO.getEmail())) {

                Role role = roleService.findRoleByRoleName(ERole.ROLE_USER);
                Person person = Person.builder()
                        .email(signupRequestDTO.getEmail())
                        .password(passwordEncoder.encode(signupRequestDTO.getPassword()))
                        .roles(List.of(role))
                        .build();

                log.info(format("Trying to save a new Person: %s, %s, %s",
                                person.getEmail(), person.getPassword(), person.getRoles()));
                personRepository.save(person);

            } else {
                log.warn("Passed email for sign-up already exists in DB: " + signupRequestDTO.getEmail());
                throw new EmailExistException(
                        format("Passed email=%s for sign-up already exists in DB", signupRequestDTO.getEmail()));
            }
        } else {
            log.warn("Invalid data was sent for sigh-up: " + signupRequestDTO.getEmail() + " " + signupRequestDTO.getPassword());
            throw new InvalidDataException(
                    format("Passed invalid data: %s %s", signupRequestDTO.getEmail(), signupRequestDTO.getPassword()));
        }
    }

    @Override
    public Person addRoleForPersonByEmail(EmailPersonAndRoleNamesDTO rolesForEmail) {
        List<Role> requestRoles = rolesForEmail.getRoles().stream()
                .map(String::toUpperCase)
                .map(stringRole -> roleService.findRoleByRoleName(ERole.valueOf(stringRole)))
                .collect(Collectors.toList());

        Person personByEmail = findPersonByEmail(rolesForEmail.getEmail());

        personByEmail.addRoles(requestRoles);

        return personRepository.save(personByEmail);
    }
}
