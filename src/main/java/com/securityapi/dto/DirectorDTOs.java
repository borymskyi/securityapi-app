package com.securityapi.dto;

import com.securityapi.config.security.jwt.UserDetailsImpl;
import com.securityapi.domain.Person;

public interface DirectorDTOs {
    PersonDTO fromUserDetailsImplToPersonDTO(UserDetailsImpl userDetails);

    PersonDTO fromPersonToPersonDTO(Person person);
}
