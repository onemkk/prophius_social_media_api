package com.prophius.controller;

import com.prophius.auth.CustomUserDetailsService;

import com.prophius.common.ResponseObject;
import com.prophius.dto.request.UserRegistrationRequest;
import com.prophius.dto.response.UserResponse;
import com.prophius.service.JwtService;
import com.prophius.service.UserService;
import com.prophius.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    private final TestUtil testUtil = new TestUtil();

    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Test
    void testUserRegistration() throws Exception {
        UserResponse response = testUtil.getUserResponse();
        UserRegistrationRequest request = testUtil.getUserRegistrationRequest();

        when(userService.registerUser(any(UserRegistrationRequest.class))).thenReturn(response);

        this.mockMvc.perform(post("/api/v1/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseObject.ResponseStatus.SUCCESSFUL.getValue()))
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.data.id").value(response.getId()))
                .andExpect(jsonPath("$.data.username").value(response.getUsername()))
                .andExpect(jsonPath("$.data.verified").value(response.isVerified()))
                .andExpect(jsonPath("$.data.roles[0].name").value(response.getRoles().get(0).getName().getValue()));
    }

    @Test
    void testUserRegistration_whenRequestHasEmailAsNull() throws Exception {
        UserRegistrationRequest request = testUtil.getUserRegistrationRequest();
        request.setEmail(null);

        this.mockMvc.perform(post("/api/v1/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(ResponseObject.ResponseStatus.FAILED.getValue()))
                .andExpect(jsonPath("$.message").value("Email is required"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void testGetAllUsers() throws Exception {
        UserResponse response = testUtil.getUserResponse();

        when(userService.getAllUsers(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(response)));

        this.mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseObject.ResponseStatus.SUCCESSFUL.getValue()))
                .andExpect(jsonPath("$.data.content[0].id").value(response.getId()))
                .andExpect(jsonPath("$.data.content[0].username").value(response.getUsername()))
                .andExpect(jsonPath("$.data.content[0].verified").value(response.isVerified()))
                .andExpect(jsonPath("$.data.content[0].roles[0].name").value(response.getRoles().get(0).getName().getValue()));
    }

}
