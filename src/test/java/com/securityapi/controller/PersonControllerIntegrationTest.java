package com.securityapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securityapi.dto.AuthenticationRequestDTO;
import com.securityapi.dto.EmailAndRoleNamesPersonDTO;
import com.securityapi.dto.JwtResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

class PersonControllerIntegrationTest extends BaseIntegrationTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_passedWrongArgsForAddNewRoleForPerson_shouldReturnResponseErrorWithHttpStatusBAD_REQUEST() throws Exception {
        EmailAndRoleNamesPersonDTO roleForPersonRequest = new EmailAndRoleNamesPersonDTO("qwe", "ROLE_CLOWN");

        var requestBuilder = put("/api/v1/persons/roles")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + obtainAccessToken("manager@gmail.com", "qwerty"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleForPersonRequest));

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.status").exists(),
                        jsonPath("$.errorMessage").exists(),
                        jsonPath("$.time").exists()
                );
    }

    private String obtainAccessToken(String email, String password) throws Exception {
        objectMapper = new ObjectMapper();

        AuthenticationRequestDTO preparedRequest = new AuthenticationRequestDTO(email, password);

        var requestBuilder = post("/api/v1/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(preparedRequest));

        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        JwtResponseDTO jwtResponseDTO = objectMapper.readValue(result.getResponse().getContentAsString(), JwtResponseDTO.class);
        return jwtResponseDTO.getToken();
    }
}