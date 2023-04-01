package com.securityapi.controller;

import com.securityapi.configuration.security.jwt.UserDetailsImpl;
import com.securityapi.domain.Person;
import com.securityapi.dto.director.DirectorDTOs;
import com.securityapi.dto.EmailAndRoleNamesPersonDTO;
import com.securityapi.dto.PersonDTO;
import com.securityapi.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@RestController
@RequestMapping("/api/v1/persons")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    private final DirectorDTOs directorDTOs;

    @PutMapping("/roles")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> addRoleByEmail(@Valid @RequestBody EmailAndRoleNamesPersonDTO rolesForEmail) {
        try {
            Person requestPerson = personService.addRoleForPersonByEmail(rolesForEmail);
            PersonDTO responsePerson = directorDTOs.fromPersonToPersonDTO(requestPerson);
            return ResponseEntity.ok().body(responsePerson);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('OPERATOR') or hasRole('MANAGER')")
    public ResponseEntity<?> getCurrentPersonData() {
        try {
            UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            PersonDTO personDTO = directorDTOs.fromUserDetailsImplToPersonDTO(userDetailsImpl);
            return ResponseEntity.ok().body(personDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
