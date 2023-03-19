package com.securityapi.controller;

import com.securityapi.config.security.jwt.AuthenticationManagerFilter;
import com.securityapi.dto.*;
import com.securityapi.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        personService.saveProfile(signupRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Person created");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateProfile(@RequestBody AuthenticationRequestDTO authRequest) {
        Authentication auth = authenticationManagerFilter.authenticateRequest(authRequest);
        JwtResponseDTO jwtResponse = authenticationManagerFilter.handlingSuccessfulAuthentication(auth);

        return ResponseEntity.ok(jwtResponse);
    }
}
