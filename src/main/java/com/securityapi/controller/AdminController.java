package com.securityapi.controller;

import com.securityapi.domain.Person;
import com.securityapi.dto.DirectorDTOs;
import com.securityapi.dto.EmailPersonAndRoleNamesDTO;
import com.securityapi.dto.PersonDTO;
import com.securityapi.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final PersonService personService;
    private final DirectorDTOs directorDTOs;

    @PutMapping("/roles/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addRoleByEmail(@RequestBody EmailPersonAndRoleNamesDTO rolesForEmail) {
        try {
            Person requestPerson = personService.addRoleForPersonByEmail(rolesForEmail);
            PersonDTO responsePerson = directorDTOs.fromPersonToPersonDTO(requestPerson);
            return ResponseEntity.ok().body(responsePerson);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
