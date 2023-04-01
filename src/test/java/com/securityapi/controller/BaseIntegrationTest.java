package com.securityapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author Dmitrii Borymskyi
 * @version 1.0
 */

@ActiveProfiles("test-containers-flyway")
@Testcontainers
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@SpringBootTest
public abstract class BaseIntegrationTest {
}
