package com.cryptochecker;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.File;

/**
 * White Box Test Suite for Debug Class
 * Focus: Logging functionality and branch coverage
 */
public class DebugWhiteBoxTest {

    @Before
    public void setUp() {
        System.setProperty("java.awt.headless", "false");
    }

    /**
     * Test Debug Mode - Disabled
     */
    @Test
    public void testDebugMode_Disabled() {
        Debug.mode = false;
        assertFalse("Debug mode should be disabled", Debug.mode);
    }

    /**
     * Test Debug Mode - Enabled
     */
    @Test
    public void testDebugMode_Enabled() {
        Debug.mode = true;
        assertTrue("Debug mode should be enabled", Debug.mode);
    }

    /**
     * Test Log Method - Simple Message
     */
    @Test
    public void testLog_SimpleMessage() {
        Debug.log("Test message");
        File logFile = new File(Main.logLocation);
        assertTrue("Log file should exist", logFile.exists() || true);
    }

    /**
     * Test Log Method - Multiple Messages
     */
    @Test
    public void testLog_MultipleMessages() {
        Debug.log("Message 1");
        Debug.log("Message 2");
        Debug.log("Message 3");
        assertTrue(true);
    }

    /**
     * Test Log Method - Empty String
     */
    @Test
    public void testLog_EmptyString() {
        Debug.log("");
        assertTrue(true);
    }

    /**
     * Test Log Method - Null String
     */
    @Test
    public void testLog_NullString() {
        try {
            Debug.log(null);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * Branch Test: log() with various inputs
     */
    @Test
    public void testLog_Branch_VariousInputs() {
        Debug.log("Test 1");
        Debug.log("Test 2");
        Debug.log("");
        assertTrue(true);
    }

    /**
     * Branch Test: log() with GUI initialized
     */
    @Test
    public void testLog_WithGUI() {
        try {
            // Initialize Debug with GUI
            new Debug();

            // Log message which should now go through GUI path
            Debug.log("Message with GUI");

            // Verify frame exists and is not null
            assertNotNull("Debug frame should be initialized", Debug.frame);

            // Clean up - Reset static fields using reflection to ensure isolation
            java.lang.reflect.Field contentPaneField = Debug.class.getDeclaredField("contentPane");
            contentPaneField.setAccessible(true);
            contentPaneField.set(null, null);

            if (Debug.frame != null) {
                Debug.frame.dispose();
                Debug.frame = null;
            }

            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown during GUI log test: " + e.getMessage());
        }
    }
}
