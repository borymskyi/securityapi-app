package com.securityapi.controller;

import com.securityapi.domain.ERole;
import com.securityapi.domain.Person;
import com.securityapi.domain.Role;
import com.securityapi.dto.PersonDTO;
import com.securityapi.dto.RolesForEmailDTO;
import com.securityapi.repository.PersonRepository;
import com.securityapi.service.PersonService;
import com.securityapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final RoleService roleService;
    private final PersonService personService;
    private final PersonRepository personRepository;

    @PutMapping("/roles/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addRoleForEmail(@RequestBody RolesForEmailDTO rolesForEmail) {
        try {
            List<Role> requestRoles = rolesForEmail.getRoles().stream()
                    .map(String::toUpperCase)
                    .map(stringRole -> roleService.findRoleByRoleName(ERole.valueOf(stringRole)))
                    .collect(Collectors.toList());
            Person requestPerson = personService.findPersonByEmail(rolesForEmail.getEmail());
            requestPerson.addRoles(requestRoles);
            personRepository.save(requestPerson);

            PersonDTO responsePerson = PersonDTO.builder()
                    .id(requestPerson.getId())
                    .email(requestPerson.getEmail())
                    .roles(requestPerson.getRoles().stream()
                            .map(role -> role.getName().name())
                            .collect(Collectors.toList()))
                    .build();

            return ResponseEntity.ok().body(responsePerson);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
