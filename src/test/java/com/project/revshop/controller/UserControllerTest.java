package com.project.revshop.controller;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.revshop.entity.UserModel;
import com.project.revshop.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testProcessRegistrationSuccess() throws Exception {
        UserModel user = new UserModel();
        user.setUserMobileNumber("9123456789");
        user.setUserName("Test User");
        user.setUserPassword("Password123");
        user.setUserEmail("test@example.com");
        user.setUserAddress("123 Test St");
        user.setUserRole("USER");

        when(userService.saveUser(any(UserModel.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/register") // Replace with your actual endpoint
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }


    @Test
    public void testProcessRegistrationValidationError() throws Exception {
        // Test case for validation errors
    }

    // Add more test cases as needed
}

