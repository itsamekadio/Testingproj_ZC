package com.cryptochecker;

public class CurrencyConversionService {

    public double calculate(double priceCurrency1, double priceCurrency2, double amount, boolean targetIsFiat) {
        if (targetIsFiat && priceCurrency2 == 0.0) {
            return priceCurrency1 * amount;
        }

        if (priceCurrency1 == 0.0 || priceCurrency2 == 0.0) {
            return 0.0;
        }

        return (priceCurrency1 / priceCurrency2) * amount;
    }
}

