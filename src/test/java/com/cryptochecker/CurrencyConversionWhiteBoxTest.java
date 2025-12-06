package com.cryptochecker;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import javax.swing.JButton;

/**
 * White Box Test Suite for Currency Conversion
 * Test Cases: TC_CV_003 to TC_CV_005
 * Focus: Path Coverage
 * Based on Section 3.4.2 of TESTING_DOCUMENTATION.md
 */
public class CurrencyConversionWhiteBoxTest {

    private PanelConverter panelConverter;
    private Method calculateCurrencyMethod;

    @Before
    public void setUp() throws Exception {
        // Set system property for headless mode
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
            Main.gui.webData = new WebData();
            Main.gui.webData.coin = new java.util.ArrayList<>();
        }
        Main.theme = new Main.Theme(Main.themes.LIGHT);
        Main.currency = "USD";
        Main.currencyChar = "$";

        panelConverter = new PanelConverter();

        // Use reflection to access private calculateCurrency method
        calculateCurrencyMethod = PanelConverter.class.getDeclaredMethod("calculateCurrency", double.class);
        calculateCurrencyMethod.setAccessible(true);
    }

    /**
     * TC_CV_003: Convert with Zero Price - Error Handling (White Box - Path
     * Coverage)
     * Requirement: FR3.1, FR6.2
     * Objective: Test path when priceCurrency1 is zero
     */
    @Test
    public void testCalculateCurrency_Path_ZeroPrice() throws Exception {
        // Setup: priceCurrency1 = 0.0
        setPrivateField(panelConverter, "priceCurrency1", 0.0);
        setPrivateField(panelConverter, "priceCurrency2", 3000.0);
        JButton buttonCurrency2 = (JButton) getPrivateField(panelConverter, "buttonCurrency2");
        buttonCurrency2.setText("Ethereum");

        // Execute
        String result = (String) calculateCurrencyMethod.invoke(panelConverter, 1.0);

        // Verify: Should return "0"
        assertEquals("0", result);
    }

    /**
     * TC_CV_004: Format Large Value (> 1) - White Box Path Coverage
     * Requirement: FR3.3
     * Objective: Test formatting path for values > 1
     */
    @Test
    public void testCalculateCurrency_Path_FormatLargeValue() throws Exception {
        // Setup: Result should be 100.5
        // price1 = 100.5, price2 = 1.0
        setPrivateField(panelConverter, "priceCurrency1", 100.5);
        setPrivateField(panelConverter, "priceCurrency2", 1.0);
        JButton buttonCurrency2 = (JButton) getPrivateField(panelConverter, "buttonCurrency2");
        buttonCurrency2.setText("USD");

        // Execute
        String result = (String) calculateCurrencyMethod.invoke(panelConverter, 1.0);

        // Verify: Should be formatted with 2 decimal places (df1)
        // 100.5 -> "100.5" (or "100,5" depending on locale, but DecimalFormat usually
        // uses dot in Java unless specified)
        // The code uses new DecimalFormat("#.##") which uses default locale.
        // We check if it contains the number.
        assertTrue(result.contains("100.5") || result.contains("100,5"));
    }

    /**
     * TC_CV_005: Format Small Value (< 0.0001) - White Box Path Coverage
     * Requirement: FR3.3
     * Objective: Test formatting path for very small values
     */
    @Test
    public void testCalculateCurrency_Path_FormatSmallValue() throws Exception {
        // Setup: Result should be 0.00005
        // price1 = 0.00005, price2 = 1.0
        setPrivateField(panelConverter, "priceCurrency1", 0.00005);
        setPrivateField(panelConverter, "priceCurrency2", 1.0);
        JButton buttonCurrency2 = (JButton) getPrivateField(panelConverter, "buttonCurrency2");
        buttonCurrency2.setText("USD");

        // Execute
        String result = (String) calculateCurrencyMethod.invoke(panelConverter, 1.0);

        // Verify: Should be formatted with many decimal places (df6)
        assertTrue(result.contains("0.00005") || result.contains("0,00005"));
    }

    // Helper methods
    private void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    private Object getPrivateField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
}
