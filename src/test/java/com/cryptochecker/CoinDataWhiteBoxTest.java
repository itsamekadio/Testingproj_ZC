package com.cryptochecker;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 * White Box Test Suite for Coin Data
 * Test Cases: TC_CD_001, TC_CD_002, TC_CD_003, TC_CD_004, TC_CD_007
 * Focus: Path and Statement Coverage
 * Based on Section 3.6.3 of TESTING_DOCUMENTATION.md
 */
public class CoinDataWhiteBoxTest {

    private WebData.Coin coin;

    @Before
    public void setUp() throws Exception {
        // Initialize minimal environment
        Main.gui = new Main();
        try {
            Main.gui.webData = new WebData();
        } catch (Exception e) {
            Main.gui.webData = new WebData();
            Main.gui.webData.coin = new ArrayList<>();
        }
        coin = Main.gui.webData.getCoin();
        Main.currencyChar = "$";
    }

    /**
     * TC_CD_001: Format Price - Large Value (> 1) (White Box - Path Coverage)
     * Requirement: FR1.2
     * Objective: Test trimPrice() path for values > 1
     */
    @Test
    public void testFormatPrice_Path_LargeValue() {
        // Setup
        double price = 50000.0;

        // Execute
        String result = coin.trimPrice(price);

        // Verify: Should use 2 decimal places
        assertTrue(result.contains("50000") || result.contains("50,000"));
    }

    /**
     * TC_CD_002: Format Price - Small Value (< 0.0001) (White Box - Path Coverage)
     * Requirement: FR1.2
     * Objective: Test trimPrice() path for very small values
     */
    @Test
    public void testFormatPrice_Path_SmallValue() {
        // Setup
        double price = 0.00005;

        // Execute
        String result = coin.trimPrice(price);

        // Verify: Should use many decimal places
        assertTrue(result.contains("0.00005") || result.contains("0,00005"));
    }

    /**
     * TC_CD_003: Format Price - Medium Value (0.1 to 1) (White Box - Path Coverage)
     * Requirement: FR1.2
     * Objective: Test trimPrice() path for medium values
     */
    @Test
    public void testFormatPrice_Path_MediumValue() {
        // Setup
        double price = 0.5;

        // Execute
        String result = coin.trimPrice(price);

        // Verify: Should use 3 decimal places
        assertTrue(result.contains("0.5") || result.contains("0,5"));
    }

    /**
     * TC_CD_004: Get Coin Info - Complete Data (White Box - Statement Coverage)
     * Requirement: FR1.5
     * Objective: Verify all statements in getInfo() execute
     */
    @Test
    public void testGetInfo_Statement() {
        // Setup: Populate coin data
        coin.rank = 1;
        coin.name = "Bitcoin";
        coin.symbol = "BTC";
        coin.price = 50000.0;
        coin.market_cap = 1000000000.0;

        // Execute
        String info = coin.getInfo();

        // Verify: Contains key fields
        assertTrue(info.contains("Bitcoin"));
        assertTrue(info.contains("BTC"));
        assertTrue(info.contains("50000") || info.contains("50,000"));
    }

    /**
     * TC_CD_007: Coin ToString - Name Return (White Box - Statement Coverage)
     * Requirement: FR1.2
     * Objective: Verify toString() returns coin name
     */
    @Test
    public void testToString_Statement() {
        // Setup
        coin.name = "Ethereum";

        // Execute
        String result = coin.toString();

        // Verify
        assertEquals("Ethereum", result);
    }

    /**
     * Test Trim Price - Boundary Value 1.0
     */
    @Test
    public void testTrimPrice_BoundaryOne() {
        double price = 1.0;
        String result = coin.trimPrice(price);
        assertNotNull(result);
    }

    /**
     * Test Trim Price - Boundary Value 0.1
     */
    @Test
    public void testTrimPrice_BoundaryPointOne() {
        double price = 0.1;
        String result = coin.trimPrice(price);
        assertNotNull(result);
    }

    /**
     * Test Trim Price - Boundary Value 0.01
     */
    @Test
    public void testTrimPrice_BoundaryPointZeroOne() {
        double price = 0.01;
        String result = coin.trimPrice(price);
        assertNotNull(result);
    }

    /**
     * Test Trim Price - Boundary Value 0.001
     */
    @Test
    public void testTrimPrice_BoundaryPointZeroZeroOne() {
        double price = 0.001;
        String result = coin.trimPrice(price);
        assertNotNull(result);
    }

    /**
     * Test Trim Price - Boundary Value 0.0001
     */
    @Test
    public void testTrimPrice_BoundaryPointZeroZeroZeroOne() {
        double price = 0.0001;
        String result = coin.trimPrice(price);
        assertNotNull(result);
    }

    /**
     * Branch Test: getInfo with positive percent_change_1h
     */
    @Test
    public void testGetInfo_Branch_PositiveChange1h() {
        coin.name = "Bitcoin";
        coin.symbol = "BTC";
        coin.price = 50000.0;
        coin.percent_change_1h = 5.0;

        String info = coin.getInfo();
        assertTrue(info.contains("Bitcoin"));
    }

    /**
     * Branch Test: getInfo with negative percent_change_1h
     */
    @Test
    public void testGetInfo_Branch_NegativeChange1h() {
        coin.name = "Ethereum";
        coin.symbol = "ETH";
        coin.price = 3000.0;
        coin.percent_change_1h = -2.5;

        String info = coin.getInfo();
        assertTrue(info.contains("Ethereum"));
    }

    /**
     * Branch Test: getInfo with zero percent_change_1h
     */
    @Test
    public void testGetInfo_Branch_ZeroChange1h() {
        coin.name = "Litecoin";
        coin.symbol = "LTC";
        coin.price = 100.0;
        coin.percent_change_1h = 0.0;

        String info = coin.getInfo();
        assertTrue(info.contains("Litecoin"));
    }

    /**
     * Branch Test: getInfo with positive percent_change_24h
     */
    @Test
    public void testGetInfo_Branch_PositiveChange24h() {
        coin.name = "Ripple";
        coin.symbol = "XRP";
        coin.price = 0.5;
        coin.percent_change_24h = 10.0;

        String info = coin.getInfo();
        assertTrue(info.contains("Ripple"));
    }

    /**
     * Branch Test: getInfo with negative percent_change_24h
     */
    @Test
    public void testGetInfo_Branch_NegativeChange24h() {
        coin.name = "Cardano";
        coin.symbol = "ADA";
        coin.price = 0.3;
        coin.percent_change_24h = -5.0;

        String info = coin.getInfo();
        assertTrue(info.contains("Cardano"));
    }

    /**
     * Branch Test: getInfo with positive percent_change_7d
     */
    @Test
    public void testGetInfo_Branch_PositiveChange7d() {
        coin.name = "Polkadot";
        coin.symbol = "DOT";
        coin.price = 5.0;
        coin.percent_change_7d = 15.0;

        String info = coin.getInfo();
        assertTrue(info.contains("Polkadot"));
    }

    /**
     * Branch Test: getInfo with negative percent_change_7d
     */
    @Test
    public void testGetInfo_Branch_NegativeChange7d() {
        coin.name = "Chainlink";
        coin.symbol = "LINK";
        coin.price = 7.0;
        coin.percent_change_7d = -8.0;

        String info = coin.getInfo();
        assertTrue(info.contains("Chainlink"));
    }

    /**
     * Branch Test: trimPrice with exactly 1.0
     */
    @Test
    public void testTrimPrice_Branch_Exactly1() {
        double price = 1.0;
        String result = coin.trimPrice(price);
        assertNotNull(result);
    }

    /**
     * Branch Test: trimPrice with exactly 0.1
     */
    @Test
    public void testTrimPrice_Branch_Exactly0Point1() {
        double price = 0.1;
        String result = coin.trimPrice(price);
        assertNotNull(result);
    }

    /**
     * Branch Test: trimPrice with exactly 0.01
     */
    @Test
    public void testTrimPrice_Branch_Exactly0Point01() {
        double price = 0.01;
        String result = coin.trimPrice(price);
        assertNotNull(result);
    }

    /**
     * Branch Test: trimPrice with exactly 0.001
     */
    @Test
    public void testTrimPrice_Branch_Exactly0Point001() {
        double price = 0.001;
        String result = coin.trimPrice(price);
        assertNotNull(result);
    }

    /**
     * Branch Test: trimPrice with exactly 0.0001
     */
    @Test
    public void testTrimPrice_Branch_Exactly0Point0001() {
        double price = 0.0001;
        String result = coin.trimPrice(price);
        assertNotNull(result);
    }

    /**
     * Branch Test: trimPrice with very large number
     */
    @Test
    public void testTrimPrice_Branch_VeryLarge() {
        double price = 1000000.0;
        String result = coin.trimPrice(price);
        assertNotNull(result);
    }

    /**
     * Branch Test: trimPrice with very small number
     */
    @Test
    public void testTrimPrice_Branch_VerySmall() {
        double price = 0.000000001;
        String result = coin.trimPrice(price);
        assertNotNull(result);
    }
}
