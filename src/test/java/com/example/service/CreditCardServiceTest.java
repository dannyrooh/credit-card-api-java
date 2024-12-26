package com.example.service;

import com.example.util.CreditCardValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CreditCardServiceTest {

    @Mock
    private CreditCardValidator creditCardValidator;

    @InjectMocks
    private CreditCardService creditCardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa validação de cartão válido")
    void testValidateCreditCard_ValidCard() {
        String cardNumber = "4111111111111111";
        String cleanedCardNumber = "4111111111111111";
        when(creditCardValidator.cleanCardNumber(cardNumber)).thenReturn(cleanedCardNumber);
        when(creditCardValidator.isValid(cleanedCardNumber)).thenReturn(true);
        when(creditCardValidator.getCardBrand(cleanedCardNumber)).thenReturn("VISA");

        Map<String, Object> response = creditCardService.validateCreditCard(cardNumber);

        assertEquals(true, response.get("isValid"));
        assertEquals("VISA", response.get("brand"));
    }

    @Test
    @DisplayName("Testa validação de cartão inválido")
    void testValidateCreditCard_InvalidCard() {
        String cardNumber = "1234567890123456";
        String cleanedCardNumber = "1234567890123456";
        when(creditCardValidator.cleanCardNumber(cardNumber)).thenReturn(cleanedCardNumber);
        when(creditCardValidator.isValid(cleanedCardNumber)).thenReturn(false);

        Map<String, Object> response = creditCardService.validateCreditCard(cardNumber);

        assertEquals(false, response.get("isValid"));
        assertEquals("Unknown", response.get("brand"));
    }
}
