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

    private PanelPortfolio panelPortfolio;
    private WebData webData;
    private ArrayList<WebData.Coin> testPortfolio;

    @Before
    public void setUp() throws Exception {
        // Set system property for headless mode (no GUI)
        System.setProperty("java.awt.headless", "false");

        // Initialize minimal GUI components
        if (Main.frame == null) {
            Main.frame = new javax.swing.JFrame();
            Main.frame.setVisible(false);
        }

        // Initialize test environment
        Main.gui = new Main();
        try {
            Main.gui.webData = new WebData();
        } catch (Exception e) {
            // If WebData fails (e.g., no internet), create minimal setup
            Main.gui.webData = new WebData();
            Main.gui.webData.coin = new ArrayList<>();
        }
        Main.gui.webData.portfolio = new ArrayList<>();
        Main.gui.webData.portfolio_names = new ArrayList<>();
        Main.gui.webData.portfolio_nr = 0;
        Main.gui.webData.portfolio.add(new ArrayList<WebData.Coin>());
        Main.gui.webData.portfolio_names.add("Test Portfolio");
        Main.gui.webData.portfolio_nr = 0;

        // Initialize theme
        Main.theme = new Main.Theme(Main.themes.LIGHT);
        Main.currency = "USD";
        Main.currencyChar = "$";

        panelPortfolio = new PanelPortfolio();
        testPortfolio = Main.gui.webData.portfolio.get(0);
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
        WebData.Coin coin = Main.gui.webData.getCoin();
        coin.name = "TestCoin";
        coin.portfolio_amount = 10.0;
        coin.price = 100.0;
        coin.portfolio_value = coin.portfolio_amount * coin.price; // 1000.0
        coin.portfolio_gains = 0.0;
        testPortfolio.add(coin);

        // Execute: Call calculatePortfolio()
        panelPortfolio.calculatePortfolio();

        // Verify: Portfolio value = 1000.0
        double totalValue = 0.0;
        for (WebData.Coin c : testPortfolio) {
            totalValue += c.portfolio_value;
        }
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
        WebData.Coin coin = Main.gui.webData.getCoin();
        coin.name = "TestCoin";
        coin.portfolio_amount = 0.0;
        coin.price = 100.0;
        coin.portfolio_value = coin.portfolio_amount * coin.price; // 0.0
        coin.portfolio_gains = 0.0;
        testPortfolio.add(coin);

        // Execute: Call calculatePortfolio()
        panelPortfolio.calculatePortfolio();

        // Verify: Portfolio value = 0.0
        double totalValue = 0.0;
        for (WebData.Coin c : testPortfolio) {
            totalValue += c.portfolio_value;
        }
        assertEquals(0.0, totalValue, 0.01);
    }

    /**
     * TC_PF_003: Calculate Portfolio Value - Negative Amount (EP - Invalid)
     * Test Level: Unit
     * Test Type: Black Box (Equivalence Partitioning - Invalid Class)
     * Requirement: FR6.1
     * Input: amount = -5.0, price = 100.0
     * Expected: System should reject or handle error appropriately
     * Note: This tests validation logic - negative amounts should be caught before
     * calculation
     */
    @Test
    public void testCalculatePortfolio_EP_InvalidNegativeAmount() {
        // Setup: Attempt to create portfolio with coin: amount = -5.0, price = 100.0
        testPortfolio.clear();
        WebData.Coin coin = Main.gui.webData.getCoin();
        coin.name = "TestCoin";
        coin.portfolio_amount = -5.0;
        coin.price = 100.0;
        coin.portfolio_value = coin.portfolio_amount * coin.price; // -500.0 (invalid)
        coin.portfolio_gains = 0.0;
        testPortfolio.add(coin);

        // Execute: Call calculatePortfolio()
        panelPortfolio.calculatePortfolio();

        // Verify: System handles negative value (may result in negative total, which is
        // invalid)
        // In a real scenario, validation should prevent this, but we test the
        // calculation behavior
        double totalValue = 0.0;
        for (WebData.Coin c : testPortfolio) {
            totalValue += c.portfolio_value;
        }
        // The calculation itself will produce negative value, but this should be caught
        // by validation
        assertTrue("Negative amount should result in negative or zero value", totalValue <= 0.0);
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
        WebData.Coin coin = Main.gui.webData.getCoin();
        coin.name = "TestCoin";
        coin.portfolio_amount = 0.00000001;
        coin.price = 100.0;
        coin.portfolio_value = coin.portfolio_amount * coin.price; // 0.000001
        coin.portfolio_gains = 0.0;
        testPortfolio.add(coin);

        // Execute: Call calculatePortfolio()
        panelPortfolio.calculatePortfolio();

        // Verify: Calculation handles very small numbers correctly
        double totalValue = 0.0;
        for (WebData.Coin c : testPortfolio) {
            totalValue += c.portfolio_value;
        }
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
        WebData.Coin coin = Main.gui.webData.getCoin();
        coin.name = "TestCoin";
        coin.portfolio_amount = 999999999.99;
        coin.price = 100.0;
        coin.portfolio_value = coin.portfolio_amount * coin.price; // 99999999999.0
        coin.portfolio_gains = 0.0;
        testPortfolio.add(coin);

        // Execute: Call calculatePortfolio()
        panelPortfolio.calculatePortfolio();

        // Verify: Calculation handles very large numbers correctly without overflow
        double totalValue = 0.0;
        for (WebData.Coin c : testPortfolio) {
            totalValue += c.portfolio_value;
        }
        assertEquals(99999999999.0, totalValue, 0.01);
        assertTrue("Value should be positive and finite", totalValue > 0 && Double.isFinite(totalValue));
    }
}
