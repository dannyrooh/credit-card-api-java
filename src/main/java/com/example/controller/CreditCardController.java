package com.example.controller;

import com.example.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credit-card")
public class CreditCardController {

    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateCreditCard(@RequestBody String cardNumber) {
        String cleanedCardNumber = cardNumber.replaceAll("\\D", "");
        var validationResult = creditCardService.validateCreditCard(cleanedCardNumber);
        
        if ((boolean) validationResult.get("isValid")) {
            return ResponseEntity.ok(validationResult);
        } else {
            return ResponseEntity.badRequest().body(validationResult);
        }
    }
}