package com.cryptochecker;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Method;

/**
 * Black Box Test Suite for Currency Conversion
 * Test Cases: TC_CV_001, TC_CV_002, TC_CV_006
 * Based on Section 3.4.2 of TESTING_DOCUMENTATION.md
 */
public class CurrencyConversionBlackBoxTest {

    private PanelConverter panelConverter;
    private Method calculateCurrencyMethod;

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
            // If WebData fails to initialize (e.g., no internet), create minimal setup
            Main.gui.webData = new WebData();
            Main.gui.webData.coin = new java.util.ArrayList<>();
        }
        Main.theme = new Main.Theme(Main.themes.LIGHT);
        Main.currency = "USD";
        Main.currencyChar = "$";

        // Create panel converter
        panelConverter = new PanelConverter();

        // Use reflection to access private calculateCurrency method for testing
        calculateCurrencyMethod = PanelConverter.class.getDeclaredMethod("calculateCurrency", double.class);
        calculateCurrencyMethod.setAccessible(true);
    }

    /**
     * TC_CV_001: Convert Cryptocurrency to Fiat - Valid Conversion (EP)
     * Test Level: Unit
     * Test Type: Black Box (Equivalence Partitioning)
     * Requirement: FR3.2
     * Input: priceCurrency1 = 50000.0, priceCurrency2 = 0.0, buttonCurrency2 =
     * "USD", x = 1.0
     * Expected: returnValue = 50000.0
     */
    @Test
    public void testCalculateCurrency_EP_CryptoToFiat() throws Exception {
        // Setup: Set priceCurrency1 = 50000.0 (Bitcoin price in USD)
        // Set priceCurrency2 = 0.0, buttonCurrency2 = "USD"
        setPrivateField(panelConverter, "priceCurrency1", 50000.0);
        setPrivateField(panelConverter, "priceCurrency2", 0.0);
        javax.swing.JButton buttonCurrency2 = (javax.swing.JButton) getPrivateField(panelConverter, "buttonCurrency2");
        buttonCurrency2.setText("USD");

        // Execute: Call calculateCurrency(1.0)
        String result = (String) calculateCurrencyMethod.invoke(panelConverter, 1.0);

        // Verify: Result = 50000.0 (formatted)
        double resultValue = Double.parseDouble(result);
        assertEquals(50000.0, resultValue, 0.01);
    }

    /**
     * TC_CV_002: Convert Crypto to Crypto - Valid Conversion (EP)
     * Test Level: Unit
     * Test Type: Black Box (Equivalence Partitioning)
     * Requirement: FR3.1
     * Input: priceCurrency1 = 50000.0 (Bitcoin), priceCurrency2 = 3000.0
     * (Ethereum), x = 1.0
     * Expected: returnValue = 16.666... (50000/3000)
     */
    @Test
    public void testCalculateCurrency_EP_CryptoToCrypto() throws Exception {
        // Setup: Set priceCurrency1 = 50000.0 (Bitcoin)
        // Set priceCurrency2 = 3000.0 (Ethereum)
        setPrivateField(panelConverter, "priceCurrency1", 50000.0);
        setPrivateField(panelConverter, "priceCurrency2", 3000.0);
        javax.swing.JButton buttonCurrency2 = (javax.swing.JButton) getPrivateField(panelConverter, "buttonCurrency2");
        buttonCurrency2.setText("Ethereum");

        // Execute: Call calculateCurrency(1.0)
        String result = (String) calculateCurrencyMethod.invoke(panelConverter, 1.0);

        // Verify: Result = 16.666... (50000/3000)
        double resultValue = Double.parseDouble(result);
        double expected = 50000.0 / 3000.0; // 16.666...
        assertEquals(expected, resultValue, 0.01);
    }

    /**
     * TC_CV_006: Convert Boundary Value - Minimum Positive (BVA)
     * Test Level: Unit
     * Test Type: Black Box (Boundary Value Analysis)
     * Requirement: FR3.1
     * Input: priceCurrency1 = 50000.0, priceCurrency2 = 3000.0, x = 0.00000001
     * Expected: Result calculated and formatted correctly
     */
    @Test
    public void testCalculateCurrency_BVA_MinimumPositiveInput() throws Exception {
        // Setup: Set priceCurrency1 = 50000.0, priceCurrency2 = 3000.0
        setPrivateField(panelConverter, "priceCurrency1", 50000.0);
        setPrivateField(panelConverter, "priceCurrency2", 3000.0);
        javax.swing.JButton buttonCurrency2 = (javax.swing.JButton) getPrivateField(panelConverter, "buttonCurrency2");
        buttonCurrency2.setText("Ethereum");

        // Execute: Call calculateCurrency(0.00000001)
        String result = (String) calculateCurrencyMethod.invoke(panelConverter, 0.00000001);

        // Verify: Result calculated and formatted correctly
        double resultValue = Double.parseDouble(result);
        double expected = (50000.0 / 3000.0) * 0.00000001;
        assertEquals(expected, resultValue, 0.0000000001);
        assertTrue("Result should be positive and very small", resultValue > 0 && resultValue < 0.0001);
    }

    // Helper methods to access private fields using reflection
    private void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    private Object getPrivateField(Object obj, String fieldName) throws Exception {
        java.lang.reflect.Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
}
