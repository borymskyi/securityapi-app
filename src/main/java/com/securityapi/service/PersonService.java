package com.securityapi.service;

import com.securityapi.domain.Person;
import com.securityapi.dto.EmailAndRoleNamesPersonDTO;
import com.securityapi.dto.SignupRequestDTO;
import org.springframework.stereotype.Service;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Service
public interface PersonService {

    Person findPersonByEmail(String email);

    Person findPersonById(Long id);

    void saveProfile(SignupRequestDTO signupRequestDTO);

    Person addRoleForPersonByEmail(EmailAndRoleNamesPersonDTO rolesForEmail);
}
