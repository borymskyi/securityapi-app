package com.securityapi.controller;

import com.securityapi.config.security.jwt.AuthenticationManagerFilter;
import com.securityapi.dto.*;
import com.securityapi.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManagerFilter authenticationManagerFilter;
    private final PersonService personService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> registration(@RequestBody SignupRequestDTO signupRequestDTO) {
        try {
            personService.saveProfile(signupRequestDTO);
            return ResponseEntity.ok(new MessageResponseDTO("User CREATED"));
        } catch (RuntimeException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateProfile(@RequestBody AuthenticationRequestDTO authRequest) {
        try {
            Authentication auth = authenticationManagerFilter.authenticateRequest(authRequest);
            JwtResponseDTO jwtResponse = authenticationManagerFilter.handlingSuccessfulAuthentication(auth);
            return ResponseEntity.ok(jwtResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(String.format("The requested email %s was not found.", authRequest.getEmail()));
        }
    }
}
