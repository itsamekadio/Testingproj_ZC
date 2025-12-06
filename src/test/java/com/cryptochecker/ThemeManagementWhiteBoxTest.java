package com.cryptochecker;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;

/**
 * White Box Test Suite for Theme Management
 * Test Cases: TC_TH_001 to TC_TH_006
 * Focus: Branch, Statement, and Condition Coverage
 * Based on Section 3.6.1 of TESTING_DOCUMENTATION.md
 */
public class ThemeManagementWhiteBoxTest {

    private Main.Theme theme;

    @Before
    public void setUp() throws Exception {
        // Initialize theme with LIGHT mode
        theme = new Main.Theme(Main.themes.LIGHT);
    }

    /**
     * TC_TH_001: Switch Theme - Light to Dark (White Box - Branch Coverage)
     * Requirement: FR4.1
     * Objective: Test branch for LIGHT -> DARK theme transition
     */
    @Test
    public void testSwitchTheme_Branch_LightToDark() {
        // Setup: Ensure start state is LIGHT
        theme.change(Main.themes.LIGHT);
        assertEquals(Color.WHITE, theme.background);

        // Execute: Switch to DARK
        theme.change(Main.themes.DARK);

        // Verify: Colors updated to DARK mode values
        // Background: new Color(15, 15, 15)
        // Foreground: Color.WHITE
        assertEquals(new Color(15, 15, 15), theme.background);
        assertEquals(Color.WHITE, theme.foreground);
        assertEquals(Main.themes.DARK, theme.currentTheme);
    }

    /**
     * TC_TH_002: Switch Theme - Dark to Custom (White Box - Branch Coverage)
     * Requirement: FR4.1
     * Objective: Test branch for DARK -> CUSTOM theme transition
     */
    @Test
    public void testSwitchTheme_Branch_DarkToCustom() {
        // Setup: Start in DARK mode
        theme.change(Main.themes.DARK);

        // Set custom colors
        Color customBg = new Color(10, 10, 10);
        Color customFg = new Color(20, 20, 20);
        theme.customBackground = customBg;
        theme.customForeground = customFg;

        // Execute: Switch to CUSTOM
        theme.change(Main.themes.CUSTOM);

        // Verify: Colors updated to custom values
        assertEquals(customBg, theme.background);
        assertEquals(customFg, theme.foreground);
        assertEquals(Main.themes.CUSTOM, theme.currentTheme);
    }

    /**
     * TC_TH_003: Switch Theme - Custom to Light (White Box - Branch Coverage)
     * Requirement: FR4.1
     * Objective: Test branch for CUSTOM -> LIGHT theme transition
     */
    @Test
    public void testSwitchTheme_Branch_CustomToLight() {
        // Setup: Start in CUSTOM mode
        theme.change(Main.themes.CUSTOM);

        // Execute: Switch to LIGHT
        theme.change(Main.themes.LIGHT);

        // Verify: Colors updated to LIGHT mode values
        assertEquals(Color.WHITE, theme.background);
        assertEquals(Color.BLACK, theme.foreground);
        // Verify: Colors reset to defaults
        // Default custom background is Black (0,0,0)
        // Default custom foreground is Light Green (14, 255, 0)
        assertEquals(new Color(0, 0, 0), theme.customBackground);
        assertEquals(new Color(14, 255, 0), theme.customForeground);
    }

    /**
     * TC_TH_006: Custom Theme - All Color Properties (White Box - Condition
     * Coverage)
     * Requirement: FR4.2
     * Objective: Test condition `currentTheme == CUSTOM` for all color properties
     */
    @Test
    public void testCustomTheme_Condition_AllProperties() {
        // Setup: Set all custom colors
        Color cBg = new Color(1, 1, 1);
        Color cFg = new Color(2, 2, 2);
        Color cGr = new Color(3, 3, 3);
        Color cRd = new Color(4, 4, 4);
        Color cSl = new Color(5, 5, 5);
        Color cEb = new Color(6, 6, 6);

        theme.customBackground = cBg;
        theme.customForeground = cFg;
        theme.customGreen = cGr;
        theme.customRed = cRd;
        theme.customSelection = cSl;
        theme.customEmptyBackground = cEb;

        // Execute: Switch to CUSTOM and update
        theme.change(Main.themes.CUSTOM);

        // Verify: All 6 properties use custom values
        assertEquals(cBg, theme.background);
        assertEquals(cFg, theme.foreground);
        assertEquals(cGr, theme.green);
        assertEquals(cRd, theme.red);
        assertEquals(cSl, theme.selection);
        assertEquals(cEb, theme.emptyBackground);
    }
}
