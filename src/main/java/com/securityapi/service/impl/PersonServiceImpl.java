package com.securityapi.service.impl;

import com.securityapi.domain.enums.ERole;
import com.securityapi.domain.Person;
import com.securityapi.domain.Role;
import com.securityapi.dto.EmailAndRoleNamesPersonDTO;
import com.securityapi.dto.SignupRequestDTO;
import com.securityapi.exception.EmailExistException;
import com.securityapi.exception.PersonNotFoundException;
import com.securityapi.exception.InvalidDataException;
import com.securityapi.repository.PersonRepository;
import com.securityapi.service.PersonService;
import com.securityapi.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    PersonRepository personRepository;
    RoleService roleService;
    PasswordEncoder passwordEncoder;

    @Override
    public Person findPersonByEmail(String email) {
        return personRepository.findByEmailFetchRoles(email)
                .orElseThrow(() -> {
                    log.warn("Failed to find a person by email: " + email);
                    return new PersonNotFoundException("Person not found with email: " + email);
                });
    }

    @Override
    public Person findPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Failed to find a person by id: " + id);
                    return new PersonNotFoundException("Person not found with id: " + id);
                });
    }

    @Transactional
    @Override
    public void saveProfile(SignupRequestDTO signupRequestDTO) {
        if (hasText(signupRequestDTO.getEmail()) && hasText(signupRequestDTO.getPassword())) {
            if (!personRepository.existsByEmail(signupRequestDTO.getEmail())) {

                Role role = roleService.findRoleByRoleName(ERole.ROLE_OPERATOR);
                Person person = Person.builder()
                        .email(signupRequestDTO.getEmail())
                        .password(passwordEncoder.encode(signupRequestDTO.getPassword()))
                        .build();
                person.addRole(role);

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
    public Person addRoleForPersonByEmail(EmailAndRoleNamesPersonDTO rolesForEmail) {
        Role requestRole = roleService.findRoleByRoleName(ERole.valueOf(rolesForEmail.getRole().toUpperCase()));
        Person personByEmail = findPersonByEmail(rolesForEmail.getEmail());

        personByEmail.addRole(requestRole);

        return personRepository.save(personByEmail);
    }
}

