package com.securityapi.service;

import com.securityapi.domain.Person;
import com.securityapi.dto.SignupRequestDTO;
import org.springframework.stereotype.Service;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Service
public interface PersonService {

    Person findPersonByEmail(String email);

    void saveProfile(SignupRequestDTO signupRequestDTO);
}
