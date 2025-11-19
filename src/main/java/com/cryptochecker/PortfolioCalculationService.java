package com.cryptochecker;

import java.util.List;

public class PortfolioCalculationService {

    public PortfolioSummary calculate(List<WebData.Coin> coins) {
        if (coins == null) {
            throw new IllegalArgumentException("Portfolio list cannot be null.");
        }

        double totalValue = 0.0;
        double totalGains = 0.0;

        for (WebData.Coin coin : coins) {
            if (coin == null) {
                continue;
            }

            if (coin.portfolio_amount < 0) {
                throw new IllegalArgumentException("Portfolio amount cannot be negative for " + coin.name);
            }

            totalValue += coin.portfolio_value;
            totalGains += coin.portfolio_gains;
        }

        return new PortfolioSummary(totalValue, totalGains);
    }

    public static class PortfolioSummary {
        private final double totalValue;
        private final double totalGains;

        public PortfolioSummary(double totalValue, double totalGains) {
            this.totalValue = totalValue;
            this.totalGains = totalGains;
        }

        public double getTotalValue() {
            return totalValue;
        }

        public double getTotalGains() {
            return totalGains;
        }
    }
}

