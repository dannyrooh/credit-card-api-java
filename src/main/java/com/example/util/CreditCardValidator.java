package com.example.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class CreditCardValidator {

    CreditCardValidator() {
    }

    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("\\D");

    private static final Map<String, String> CARD_BRANDS = new HashMap<>();

    static {
        CARD_BRANDS.put("^4\\d{12}(\\d{3})?$", "Visa");
        CARD_BRANDS.put("^(5[1-5][0-9]{14}|2(2[2-9][0-9]{12}|[3-6][0-9]{13}|7[01][0-9]{12}|720[0-9]{12}))$", "MasterCard");
        CARD_BRANDS.put("3[47][0-9]{13}$", "American Express");
        CARD_BRANDS.put("^(6011\\d{12}|622(12[6-9]|1[3-9]\\d|[2-8]\\d{2}|9[01]\\d|92[0-5])\\d{10}|64[4-9]\\d{13}|65\\d{14})$", "Discover");
        CARD_BRANDS.put("^(36|38|39|30[0-5])[0-9]{11,12}$", "Diners Club");
        CARD_BRANDS.put("^(352[89]|35[3-8]\\d)\\d{12}$", "JCB");
        CARD_BRANDS.put("^(4011|4312|4389|4514|4576|5041|5066|5090|6277|6362|6363|6500|6504|6505|6516|6550)\\d{12}$", "ELO");
        CARD_BRANDS.put("^(606282|3841)\\d{10,17}$", "Hipercard");
        CARD_BRANDS.put("^8699\\d{11}$", "Voyager");
        CARD_BRANDS.put("^(2014|2149)\\d{11}$", "Enroute");
        CARD_BRANDS.put("^50\\d{14}$", "Aura");
    }

    public String cleanCardNumber(String cardNumber) {
        if (cardNumber == null) {
            throw new IllegalArgumentException("Card number cannot be null");
        }
        return CARD_NUMBER_PATTERN.matcher(cardNumber).replaceAll("");
    }

    public boolean isValidCardNumber(String cardNumber) {
        String cleanedNumber = cleanCardNumber(cardNumber);
        return isValid(cleanedNumber);
    }

    public boolean isValid(String cardNumber) {

        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            return false;
        }

        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return sum % 10 == 0;
    }

    public String getCardBrand(String cardNumber) {
        String cleanedNumber = cleanCardNumber(cardNumber);

        for (Map.Entry<String, String> entry : CARD_BRANDS.entrySet()) {
            if (cleanedNumber.matches(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "Unknown";
    }
}
