package com.example.controller;

import com.example.service.CreditCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

 class CreditCardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreditCardService creditCardService;

    @InjectMocks
    private CreditCardController creditCardController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(creditCardController).build();
    }

    @Test
    void testValidateCreditCard_ValidCard() throws Exception {
        Map<String, Object> validationResult = new HashMap<>();
        validationResult.put("isValid", true);
        validationResult.put("message", "Valid card");

        when(creditCardService.validateCreditCard(anyString())).thenReturn(validationResult);

        mockMvc.perform(post("/api/credit-card/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cardNumber\":\"1234567890123456\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"isValid\":true,\"message\":\"Valid card\"}"));
    }

    @Test
    void testValidateCreditCard_InvalidCard() throws Exception {
        Map<String, Object> validationResult = new HashMap<>();
        validationResult.put("isValid", false);
        validationResult.put("message", "Invalid card");

        when(creditCardService.validateCreditCard(anyString())).thenReturn(validationResult);

        mockMvc.perform(post("/api/credit-card/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cardNumber\":\"1234567890123456\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"isValid\":false,\"message\":\"Invalid card\"}"));
    }
}