package com.securityapi.configuration.security.jwt;

import com.securityapi.dto.AuthenticationRequestDTO;
import com.securityapi.dto.JwtResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationManagerFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public Authentication authenticateRequest(AuthenticationRequestDTO requestDto) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));
    }

    public JwtResponseDTO handlingSuccessfulAuthentication(Authentication auth) {
        SecurityContextHolder.getContext().setAuthentication(auth);

        UserDetailsImpl userDetails = (UserDetailsImpl)auth.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return JwtResponseDTO.builder()
                .token(jwt)
                .type("Bearer")
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }
}
