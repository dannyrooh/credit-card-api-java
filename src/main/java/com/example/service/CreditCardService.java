package com.example.service;

import com.example.util.CreditCardValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CreditCardService {

    private final CreditCardValidator creditCardValidator;

    @Autowired
    public CreditCardService(CreditCardValidator creditCardValidator) {
        this.creditCardValidator = creditCardValidator;
    }

    public Map<String, Object> validateCreditCard(String cardNumber) {
        String cleanedCardNumber = creditCardValidator.cleanCardNumber(cardNumber);
        boolean isValid = creditCardValidator.isValid(cleanedCardNumber);
        String cardBrand = creditCardValidator.getCardBrand(cleanedCardNumber);

        Map<String, Object> response = new HashMap<>();
        response.put("isValid", isValid);
        response.put("cardBrand", cardBrand);

        return response;
    }
}