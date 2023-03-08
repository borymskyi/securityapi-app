package com.securityapi.service.impl;

import com.securityapi.domain.ERole;
import com.securityapi.domain.Person;
import com.securityapi.domain.Role;
import com.securityapi.dto.SignupRequestDTO;
import com.securityapi.repository.PersonRepository;
import com.securityapi.service.PersonService;
import com.securityapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
                    return new UsernameNotFoundException("User Not Found with email: " + email);
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

                personRepository.save(person);
            } else {
                log.warn("Passed email for sign-up already exists in db: " + signupRequestDTO.getEmail());
                throw new IllegalArgumentException("Error: Email is exist");
            }
        } else {
            log.warn("Incorrect data was sent for sigh-up: " + signupRequestDTO.getEmail() + " " + signupRequestDTO.getPassword());
            throw new IllegalArgumentException("Error: Entered incorrect data");
        }
    }
}
