package com.project.revshop.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.revshop.entity.UserModel;
import com.project.revshop.service.UserService;
import com.project.revshop.repository.UserRepository;
import com.project.revshop.entity.SellerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserControllerTest {

    private MockMvc mockMvc;
       
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private ObjectMapper objectMapper;

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

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();  // Set up MockMvc manually
    }

    // Test GET method to show the registration form
    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/api/v1/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("Register"));
    }

    // Test POST method for a successful user registration (non-seller)
    @Test
    public void testProcessRegistration_NonSeller() throws Exception {
        UserModel user = new UserModel();
        user.setUserRole("user");

        // Mock the saveUser method
        when(userService.saveUser(any(UserModel.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/register")
                .flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/v1/login"));

        // Verify that saveUser was called once
        verify(userService, times(1)).saveUser(any(UserModel.class));
    }

    // Test POST method for a successful seller registration
    @Test
    public void testProcessRegistration_Seller() throws Exception {
        UserModel user = new UserModel();
        user.setUserRole("seller");
        SellerModel seller = new SellerModel();
        user.setSellermodel(seller);

        // Mock saveUser and saveSeller methods
        when(userService.saveUser(any(UserModel.class))).thenReturn(user);
        when(userService.saveSeller(any(SellerModel.class))).thenReturn(seller);

        mockMvc.perform(post("/api/v1/register")
                .flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/v1/login"));

        // Verify that both saveUser and saveSeller were called once
        verify(userService, times(1)).saveUser(any(UserModel.class));
        verify(userService, times(1)).saveSeller(any(SellerModel.class));
    }

    // Test POST method for missing seller details
    @Test
    public void testProcessRegistration_MissingSellerDetails() throws Exception {
        UserModel user = new UserModel();
        user.setUserRole("seller");
        user.setSellermodel(null); // Seller details are missing

        mockMvc.perform(post("/api/v1/register")
                .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("Register"));

        // Verify that saveSeller was not called
        verify(userService, times(0)).saveSeller(any(SellerModel.class));
    }
}

