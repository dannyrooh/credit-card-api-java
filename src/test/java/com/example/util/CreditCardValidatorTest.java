package com.example.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreditCardValidatorTest {

    private final CreditCardValidator validator = new CreditCardValidator();

    @Test
    void testCleanCardNumber() {
        assertEquals("4111111111111111", validator.cleanCardNumber("4111-1111-1111-1111"));
        assertEquals("4111111111111111", validator.cleanCardNumber("4111 1111 1111 1111"));
        assertThrows(IllegalArgumentException.class, () -> validator.cleanCardNumber(null));
    }

    @Test
    void testIsValidCardNumber() {
        assertTrue(validator.isValidCardNumber("4111111111111111"));
        assertFalse(validator.isValidCardNumber("4111111111111112"));
        assertFalse(validator.isValidCardNumber(null));
        assertFalse(validator.isValidCardNumber(""));
    }

    @Test
    void testGetCardBrand() {
        assertEquals("Visa", validator.getCardBrand("4111111111111111"));
        assertEquals("MasterCard", validator.getCardBrand("5500000000000004"));
        assertEquals("American Express", validator.getCardBrand("340000000000009"));
        assertEquals("Discover", validator.getCardBrand("6011000000000004"));
        assertEquals("Diners Club", validator.getCardBrand("30000000000004"));
        assertEquals("JCB", validator.getCardBrand("3528000000000007"));
        assertEquals("ELO", validator.getCardBrand("5066991111111118"));
        assertEquals("Hipercard", validator.getCardBrand("6062825624254001"));
        assertEquals("Voyager", validator.getCardBrand("869941234567890"));
        assertEquals("Enroute", validator.getCardBrand("201400000000009"));
        assertEquals("Aura", validator.getCardBrand("507860187000012798"));
        assertEquals("Unknown", validator.getCardBrand("1234567890123456"));
    }
}