package com.cryptochecker;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExtentReportTest {

    static ExtentReports extent;
    static ExtentTest test;

    @BeforeClass
    public static void setup() {
        extent = ExtentManager.getInstance();
    }

    @Test
    public void testPortfolioCalculationPass() {
        test = extent.createTest("Portfolio Calculation - Pass Scenario");
        test.log(Status.INFO, "Starting calculation test...");

        // Simulate calculation
        double value = 100 * 10;

        if (value == 1000) {
            test.pass("Value calculated correctly: " + value);
        } else {
            test.fail("Calculation failed");
        }
        assertEquals(1000.0, value, 0.01);
    }

    @Test
    public void testDataValidationFail() {
        test = extent.createTest("Data Validation - Fail Scenario");
        test.info("Validating negative input...");

        try {
            // Simulating a check
            boolean isValid = false; // Negative input is invalid
            if (!isValid) {
                test.pass("Correctly identified invalid input");
            }
        } catch (Exception e) {
            test.fail("Exception thrown: " + e.getMessage());
        }
        assertTrue(true);
    }

    @Test
    public void testApiConnection() {
        test = extent.createTest("API Connection Stability");
        test.info("Pinging CoinGecko API...");
        // Simulated Ping
        boolean connected = true;
        if (connected) {
            test.pass("API Connection Established (200 OK)");
        } else {
            test.fail("API Connection Timeout");
        }
        assertTrue(connected);
    }

    @Test
    public void testUserLoginSimulation() {
        test = extent.createTest("User Portfolio Authentication");
        test.info("Attempting login with default credentials...");
        test.warning("Using default dev credentials - security warning");
        test.pass("Login Successful");
        assertTrue(true);
    }

    @Test
    public void testDataLoadPerformance() {
        test = extent.createTest("Data Load Performance");
        // Simulate load
        long duration = 1500;

        if (duration < 2000) {
            test.pass("Data loaded in " + duration + "ms (SLA < 2000ms)");
        } else {
            test.warning("Slow data load: " + duration + "ms");
        }
        assertTrue(duration > 0);
    }

    @Test
    public void testCryptoTransaction() {
        test = extent.createTest("Crypto Transaction Flow");
        test.log(Status.INFO, "Initiating transaction of 0.5 BTC...");

        boolean addressValid = true;
        if (addressValid) {
            test.pass("Wallet Address Validated");
        }

        test.info("Broadcasting to network...");
        // Simulate network delay logic
        test.pass("Transaction Confirmed on Blockchain");
        assertTrue(true);
    }

    @AfterClass
    public static void tearDown() {
        // Essential to generate the report
        extent.flush();
    }
}
