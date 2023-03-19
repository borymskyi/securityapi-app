package com.securityapi.controller;

import com.securityapi.config.security.jwt.UserDetailsImpl;
import com.securityapi.dto.DirectorDTOs;
import com.securityapi.dto.PersonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@RestController
@RequestMapping("/api/v1/persons")
@RequiredArgsConstructor
public class PersonController {
    private final DirectorDTOs directorDTOs;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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
