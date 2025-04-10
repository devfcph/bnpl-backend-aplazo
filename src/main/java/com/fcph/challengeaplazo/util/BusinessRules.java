package com.fcph.challengeaplazo.util;

import com.fcph.challengeaplazo.exception.BusinessRuleException;

public class BusinessRules {
    public static Double assignCreditLimitByAge(int age) {
        if (age < 18 || age > 65) {
            throw new BusinessRuleException("Client not eligible due to age restrictions");
        } else if (age <= 25) {
            return 3000.0;
        } else if (age <= 30) {
            return 5000.0;
        } else {
            return 8000.0;
        }
    }
}
