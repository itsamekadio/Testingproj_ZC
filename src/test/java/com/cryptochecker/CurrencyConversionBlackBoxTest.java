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

    private CurrencyConversionService currencyConversionService;

    @Before
    public void setUp() {
        currencyConversionService = new CurrencyConversionService();
    }

    /**
     * TC_CV_001: Convert Cryptocurrency to Fiat - Valid Conversion (EP)
     * Test Level: Unit
     * Test Type: Black Box (Equivalence Partitioning)
     * Requirement: FR3.2
     * Input: priceCurrency1 = 50000.0, priceCurrency2 = 0.0, buttonCurrency2 = "USD", x = 1.0
     * Expected: returnValue = 50000.0
     */
    @Test
    public void testCalculateCurrency_EP_CryptoToFiat() throws Exception {
        double result = currencyConversionService.calculate(50000.0, 0.0, 1.0, true);
        assertEquals(50000.0, result, 0.01);
    }

    /**
     * TC_CV_002: Convert Crypto to Crypto - Valid Conversion (EP)
     * Test Level: Unit
     * Test Type: Black Box (Equivalence Partitioning)
     * Requirement: FR3.1
     * Input: priceCurrency1 = 50000.0 (Bitcoin), priceCurrency2 = 3000.0 (Ethereum), x = 1.0
     * Expected: returnValue = 16.666... (50000/3000)
     */
    @Test
    public void testCalculateCurrency_EP_CryptoToCrypto() throws Exception {
        double result = currencyConversionService.calculate(50000.0, 3000.0, 1.0, false);
        double expected = 50000.0 / 3000.0; // 16.666...
        assertEquals(expected, result, 0.01);
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
        double result = currencyConversionService.calculate(50000.0, 3000.0, 0.00000001, false);
        double expected = (50000.0 / 3000.0) * 0.00000001;
        assertEquals(expected, result, 0.0000000001);
        assertTrue("Result should be positive and very small", result > 0 && result < 0.0001);
    }
}

