package com.securityapi.controller;

import com.securityapi.config.security.jwt.UserDetailsImpl;
import com.securityapi.dto.PersonDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> currentPersonData() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();

            PersonDTO personDTO = PersonDTO.builder()
                    .id(userDetails.getId())
                    .email(userDetails.getEmail())
                    .roles(userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                    .build();

            return ResponseEntity.ok().body(personDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
