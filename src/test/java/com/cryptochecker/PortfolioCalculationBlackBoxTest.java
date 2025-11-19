package com.cryptochecker;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 * Black Box Test Suite for Portfolio Calculation
 * Test Cases: TC_PF_001 to TC_PF_005
 * Based on Section 3.4.1 of TESTING_DOCUMENTATION.md
 */
public class PortfolioCalculationBlackBoxTest {

    private PortfolioCalculationService portfolioCalculationService;
    private WebData testWebData;
    private ArrayList<WebData.Coin> testPortfolio;

    @Before
    public void setUp() throws Exception {
        portfolioCalculationService = new PortfolioCalculationService();
        testWebData = new WebData(true);
        testPortfolio = new ArrayList<>();
    }

    private WebData.Coin createCoin(double amount, double price) {
        WebData.Coin coin = testWebData.getCoin();
        coin.name = "TestCoin";
        coin.portfolio_amount = amount;
        coin.price = price;
        coin.portfolio_price = price;
        coin.portfolio_value = amount * price;
        coin.portfolio_gains = 0.0;
        coin.portfolio_value_start = coin.portfolio_value;
        return coin;
    }

    /**
     * TC_PF_001: Calculate Portfolio Value - Valid Positive Amount (EP)
     * Test Level: Unit
     * Test Type: Black Box (Equivalence Partitioning)
     * Requirement: FR2.4
     * Input: amount = 10.0, price = 100.0
     * Expected: portfolio_value = 1000.0
     */
    @Test
    public void testCalculatePortfolio_EP_ValidPositiveAmount() {
        // Setup: Create portfolio with coin: amount = 10.0, price = 100.0
        testPortfolio.clear();
        testPortfolio.add(createCoin(10.0, 100.0));

        PortfolioCalculationService.PortfolioSummary summary = portfolioCalculationService.calculate(testPortfolio);
        double totalValue = summary.getTotalValue();
        assertEquals(1000.0, totalValue, 0.01);
    }

    /**
     * TC_PF_002: Calculate Portfolio Value - Zero Amount (BVA)
     * Test Level: Unit
     * Test Type: Black Box (Boundary Value Analysis)
     * Requirement: FR2.4, FR6.1
     * Input: amount = 0.0, price = 100.0
     * Expected: portfolio_value = 0.0
     */
    @Test
    public void testCalculatePortfolio_BVA_ZeroAmount() {
        // Setup: Create portfolio with coin: amount = 0.0, price = 100.0
        testPortfolio.clear();
        testPortfolio.clear();
        testPortfolio.add(createCoin(0.0, 100.0));

        PortfolioCalculationService.PortfolioSummary summary = portfolioCalculationService.calculate(testPortfolio);
        double totalValue = summary.getTotalValue();
        assertEquals(0.0, totalValue, 0.01);
    }

    /**
     * TC_PF_003: Calculate Portfolio Value - Negative Amount (EP - Invalid)
     * Test Level: Unit
     * Test Type: Black Box (Equivalence Partitioning - Invalid Class)
     * Requirement: FR6.1
     * Input: amount = -5.0, price = 100.0
     * Expected: System should reject or handle error appropriately
     * Note: This tests validation logic - negative amounts should be caught before calculation
     */
    @Test
    public void testCalculatePortfolio_EP_InvalidNegativeAmount() {
        // Setup: Attempt to create portfolio with coin: amount = -5.0, price = 100.0
        testPortfolio.clear();
        testPortfolio.clear();
        testPortfolio.add(createCoin(-5.0, 100.0));

        assertThrows(IllegalArgumentException.class, () -> portfolioCalculationService.calculate(testPortfolio));
    }

    /**
     * TC_PF_004: Calculate Portfolio Value - Very Small Amount (BVA)
     * Test Level: Unit
     * Test Type: Black Box (Boundary Value Analysis)
     * Requirement: FR2.4
     * Input: amount = 0.00000001, price = 100.0
     * Expected: portfolio_value = 0.000001 (or appropriate precision)
     */
    @Test
    public void testCalculatePortfolio_BVA_MinimumPositiveAmount() {
        // Setup: Create portfolio with coin: amount = 0.00000001, price = 100.0
        testPortfolio.clear();
        testPortfolio.clear();
        testPortfolio.add(createCoin(0.00000001, 100.0));

        PortfolioCalculationService.PortfolioSummary summary = portfolioCalculationService.calculate(testPortfolio);
        double totalValue = summary.getTotalValue();
        assertEquals(0.000001, totalValue, 0.0000001);
    }

    /**
     * TC_PF_005: Calculate Portfolio Value - Very Large Amount (BVA)
     * Test Level: Unit
     * Test Type: Black Box (Boundary Value Analysis)
     * Requirement: FR2.4
     * Input: amount = 999999999.99, price = 100.0
     * Expected: portfolio_value calculated correctly without overflow
     */
    @Test
    public void testCalculatePortfolio_BVA_MaximumAmount() {
        // Setup: Create portfolio with coin: amount = 999999999.99, price = 100.0
        testPortfolio.clear();
        testPortfolio.clear();
        testPortfolio.add(createCoin(999999999.99, 100.0));

        PortfolioCalculationService.PortfolioSummary summary = portfolioCalculationService.calculate(testPortfolio);
        double totalValue = summary.getTotalValue();
        assertEquals(99999999999.0, totalValue, 0.01);
        assertTrue("Value should be positive and finite", totalValue > 0 && Double.isFinite(totalValue));
    }
}

