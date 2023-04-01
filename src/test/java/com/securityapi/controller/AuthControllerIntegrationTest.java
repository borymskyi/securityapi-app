package com.securityapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securityapi.domain.enums.ERole;
import com.securityapi.dto.AuthenticationRequestDTO;
import com.securityapi.dto.SignupRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

class AuthControllerIntegrationTest extends BaseIntegrationTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_registrationNewPerson_shouldReturnHttpStatusCREATED() throws Exception {
        SignupRequestDTO signupRequestDTO = new SignupRequestDTO("new@gmail.com", "qwerty");

        var requestBuilder = post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestDTO));

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isCreated()
                );
    }

    @Test
    void test_registrationExistEmail_shouldReturnResponseErrorAndHttpStatusCONFLICT() throws Exception {
        SignupRequestDTO signupRequestDTO = new SignupRequestDTO("manager@gmail.com", "qwerty");

        var requestBuilder = post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestDTO));

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isConflict(),
                        jsonPath("$.status").exists(),
                        jsonPath("$.errorMessage").exists(),
                        jsonPath("$.time").exists()
                );
    }

    @Test
    void test_registrationPersonWithIllegalData_shouldReturnResponseErrorAndHttpStatusBAD_REQUEST() throws Exception {
        SignupRequestDTO signupRequestDTO = new SignupRequestDTO("", "qwerty");

        var requestBuilder = post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestDTO));

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.status").exists(),
                        jsonPath("$.errorMessage").exists(),
                        jsonPath("$.time").exists()
                );
    }

    @Test
    void test_authenticateExistingPerson_ShouldReturnToken() throws Exception {
        AuthenticationRequestDTO preparedRequest = new AuthenticationRequestDTO("manager@gmail.com", "qwerty");

        var requestBuilder = post("/api/v1/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(preparedRequest));

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.token").isNotEmpty(),
                        jsonPath("$.type").value("Bearer"),
                        jsonPath("$.email").value("manager@gmail.com"),
                        jsonPath("$.roles", hasItem(ERole.ROLE_MANAGER.toString()))
                );
    }

    @Test
    void test_authenticateWrongPerson_ReturnNotFoundMessage() throws Exception {
        AuthenticationRequestDTO preparedRequest = new AuthenticationRequestDTO("wrong@gmail.com", "qwerty");

        var requestBuilder = post("/api/v1/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(preparedRequest));

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.status").exists(),
                        jsonPath("$.errorMessage").exists(),
                        jsonPath("$.time").exists()
                );
    }

}