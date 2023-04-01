package com.securityapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securityapi.domain.enums.EConsumerStatus;
import com.securityapi.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

class PanelControllerIntegrationTest extends BaseIntegrationTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Transactional
    void createNewTemplate_shouldCreateTemplateAndReturnStatusCREATED() throws Exception {
        var callTemplateFromManagerDTO = new CallTemplateFromManagerDTO(
                List.of(new StatusConsumerFromManagerDTO("INDIVIDUAL_ENTITY"),
                        new StatusConsumerFromManagerDTO("LEGAL_ENTITY")
                ),
                List.of(new ReasonFromManagerDTO("Reason 1"),
                        new ReasonFromManagerDTO("Reason 2"),
                        new ReasonFromManagerDTO("Reason 3"),
                        new ReasonFromManagerDTO("Reason 4"),
                        new ReasonFromManagerDTO("Reason 5"),
                        new ReasonFromManagerDTO("Reason 6"),
                        new ReasonFromManagerDTO("Reason 7"),
                        new ReasonFromManagerDTO("Reason 8"),
                        new ReasonFromManagerDTO("Reason 9"),
                        new ReasonFromManagerDTO("Reason 10")
                )
        );

        var requestBuilder = post("/api/v1/calls/templates")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + obtainAccessToken("manager@gmail.com", "qwerty"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(callTemplateFromManagerDTO));

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").exists(),
                        jsonPath("$.manager").exists(),
                        jsonPath("$.statusList", hasSize(2)),
                        jsonPath("$.reasonList[*].reasonMessage").exists()
                );
    }

    @Test
    @Transactional
    void createWrongNewTemplateWithDoubleConsumerStatus_shouldCreateCallTemplateWithUniqueConsumers() throws Exception {
        var callTemplateFromManagerDTO = new CallTemplateFromManagerDTO(
                List.of(new StatusConsumerFromManagerDTO("INDIVIDUAL_ENTITY"),
                        new StatusConsumerFromManagerDTO("INDIVIDUAL_ENTITY"),
                        new StatusConsumerFromManagerDTO("LEGAL_ENTITY")
                ),
                List.of(new ReasonFromManagerDTO("Reason 1"),
                        new ReasonFromManagerDTO("Reason 2"),
                        new ReasonFromManagerDTO("Reason 3"),
                        new ReasonFromManagerDTO("Reason 4"),
                        new ReasonFromManagerDTO("Reason 5"),
                        new ReasonFromManagerDTO("Reason 6"),
                        new ReasonFromManagerDTO("Reason 7"),
                        new ReasonFromManagerDTO("Reason 8"),
                        new ReasonFromManagerDTO("Reason 9"),
                        new ReasonFromManagerDTO("Reason 10")
                )
        );

        var requestBuilder = post("/api/v1/calls/templates")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + obtainAccessToken("manager@gmail.com", "qwerty"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(callTemplateFromManagerDTO));

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").exists(),
                        jsonPath("$.manager").exists(),
                        jsonPath("$.statusList", hasSize(2)),
                        jsonPath("$.reasonList").exists()
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