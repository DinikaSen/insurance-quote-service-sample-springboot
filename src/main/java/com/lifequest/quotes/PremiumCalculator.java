package com.lifequest.quotes;
import com.lifequest.quotes.model.Quote;

public class PremiumCalculator {

    public static double calculatePremium(Quote quote) {
        double baseRate = 0;
        double ageMultiplier = 1.0;
        double stateModifier = 0;

        switch (quote.getProductName()) {
            case "Health":
                baseRate = 50;
                if (quote.getAge() > 45) ageMultiplier = 1.2;
                if ("CA".equalsIgnoreCase(quote.getState())) stateModifier = 10;
                break;

            case "Auto":
                baseRate = 30;
                if (quote.getAge() < 25) ageMultiplier = 1.1;
                if ("NY".equalsIgnoreCase(quote.getState())) stateModifier = 5;
                break;

            case "Life":
                baseRate = 40;
                if (quote.getAge() > 60) ageMultiplier = 1.3;
                if ("FL".equalsIgnoreCase(quote.getState())) stateModifier = 15;
                break;

            case "Annuities":
                baseRate = 60;
                // No modifiers
                break;

            default:
                baseRate = 50; // fallback
        }

        return baseRate * ageMultiplier + stateModifier;
    }
}
