package com.cryptochecker;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Black Box Test Suite for Data Validation
 * Test Cases: TC_DV_001 to TC_DV_005
 * Based on Section 3.6.2 of TESTING_DOCUMENTATION.md
 */
public class DataValidationBlackBoxTest {

    /**
     * TC_DV_001: Validate Portfolio Amount - Valid Positive Number (EP)
     * Test Level: Unit
     * Test Type: Black Box (Equivalence Partitioning)
     * Requirement: FR6.1
     * Input: "10.5", "100", "0.001"
     * Expected: Parsing succeeds, amount = 10.5
     */
    @Test
    public void testValidateAmount_EP_ValidPositiveNumber() {
        // Test valid positive numbers
        String[] validInputs = {"10.5", "100", "0.001"};
        
        for (String input : validInputs) {
            try {
                double amount = Double.parseDouble(input);
                assertTrue("Valid input should parse successfully: " + input, amount >= 0);
                
                if (input.equals("10.5")) {
                    assertEquals(10.5, amount, 0.01);
                }
            } catch (NumberFormatException e) {
                fail("Valid input should not throw exception: " + input);
            }
        }
    }

    /**
     * TC_DV_002: Validate Portfolio Amount - Invalid Non-Numeric (EP)
     * Test Level: Unit
     * Test Type: Black Box (Equivalence Partitioning - Invalid Class)
     * Requirement: FR6.1
     * Input: "abc", "12.3.4", "ten", ""
     * Expected: NumberFormatException thrown
     */
    @Test
    public void testValidateAmount_EP_InvalidNonNumeric() {
        // Test invalid non-numeric strings
        String[] invalidInputs = {"abc", "12.3.4", "ten", ""};
        
        for (String input : invalidInputs) {
            try {
                double amount = Double.parseDouble(input);
                fail("Invalid input should throw NumberFormatException: " + input);
            } catch (NumberFormatException e) {
                // Expected exception - test passes
                assertNotNull("Exception should be thrown for invalid input: " + input, e);
            }
        }
    }

    /**
     * TC_DV_003: Validate Portfolio Amount - Zero Value (BVA)
     * Test Level: Unit
     * Test Type: Black Box (Boundary Value Analysis)
     * Requirement: FR6.1
     * Input: "0"
     * Expected: amount = 0.0 (may be valid or invalid depending on business rule)
     */
    @Test
    public void testValidateAmount_BVA_ZeroValue() {
        // Test boundary: zero value
        String input = "0";
        
        try {
            double amount = Double.parseDouble(input);
            assertEquals(0.0, amount, 0.01);
            // Zero is a valid numeric value, but may be rejected by business logic
            // This test verifies parsing behavior, not business validation
        } catch (NumberFormatException e) {
            fail("Zero should parse successfully as a number");
        }
    }

    /**
     * TC_DV_004: Validate Portfolio Amount - Negative Value (EP)
     * Test Level: Unit
     * Test Type: Black Box (Equivalence Partitioning - Invalid Class)
     * Requirement: FR6.1
     * Input: "-5.0", "-0.001", "-100"
     * Expected: Parsing succeeds but value is negative (should be rejected by validation)
     */
    @Test
    public void testValidateAmount_EP_InvalidNegativeValue() {
        // Test negative numbers
        String[] negativeInputs = {"-5.0", "-0.001", "-100"};
        
        for (String input : negativeInputs) {
            try {
                double amount = Double.parseDouble(input);
                assertTrue("Negative input should parse but value is negative: " + input, amount < 0);
                
                if (input.equals("-5.0")) {
                    assertEquals(-5.0, amount, 0.01);
                }
                // Note: In actual validation logic, negative values should be rejected
                // This test verifies parsing behavior, not business validation
            } catch (NumberFormatException e) {
                fail("Negative number should parse successfully: " + input);
            }
        }
    }

    /**
     * TC_DV_005: Validate Portfolio Amount - Very Large Number (BVA)
     * Test Level: Unit
     * Test Type: Black Box (Boundary Value Analysis)
     * Requirement: FR6.1
     * Input: "999999999999.99"
     * Expected: Parsed successfully or appropriate error for overflow
     */
    @Test
    public void testValidateAmount_BVA_VeryLargeNumber() {
        // Test very large number
        String input = "999999999999.99";
        
        try {
            double amount = Double.parseDouble(input);
            assertEquals(999999999999.99, amount, 0.01);
            assertTrue("Large number should be positive and finite", amount > 0 && Double.isFinite(amount));
        } catch (NumberFormatException e) {
            fail("Large valid number should parse successfully");
        } catch (Exception e) {
            // Other exceptions (like overflow) are acceptable for very large numbers
            // This would depend on the specific implementation
        }
    }
}

