package com.securityapi.dto.impl;

import com.securityapi.config.security.jwt.UserDetailsImpl;
import com.securityapi.domain.Person;
import com.securityapi.dto.DirectorDTOs;
import com.securityapi.dto.PersonDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DirectorDTOsImpl implements DirectorDTOs {

    @Override
    public PersonDTO fromUserDetailsImplToPersonDTO(UserDetailsImpl userDetails) {
        return PersonDTO.builder()
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .roles(userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public PersonDTO fromPersonToPersonDTO(Person person) {
        return PersonDTO.builder()
                .id(person.getId())
                .email(person.getEmail())
                .roles(person.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toList()))
                .build();
    }
}
