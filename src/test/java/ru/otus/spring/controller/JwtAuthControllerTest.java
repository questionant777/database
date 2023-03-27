package ru.otus.spring.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class JwtAuthControllerTest {

    public static final String TEST_USER = "user1";
    public static final String TEST_USER_PWD_OK = "1";
    public static final String TEST_USER_PWD_WRONG = "password_wrong";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void userOkAuthenticatedTest() throws Exception {

        String content = "{\"username\": \"" + TEST_USER + "\", \"password\": \"" + TEST_USER_PWD_OK + "\"}";

        mockMvc.perform(
                post("/auth")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void userNotAuthenticatedTest() throws Exception {

        String content = "{\"username\": \"" + TEST_USER + "\", \"password\": \"" + TEST_USER_PWD_WRONG + "\"}";

        mockMvc.perform(
                post("/auth")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.message").value("BadCredentialsException"));
    }

}