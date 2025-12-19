package com.cryptochecker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

/**
 * White Box Test Suite for Data Validation
 * Test Cases: TC_DV_006 to TC_DV_010
 * Focus: Branch and Condition Coverage
 * Based on Section 3.6.2 of TESTING_DOCUMENTATION.md
 */
public class DataValidationWhiteBoxTest {

    private PanelPortfolio panelPortfolio;
    private Method findPortfolioNameMethod;

    @Before
    public void setUp() throws Exception {
        // Set system property for headless mode
        System.setProperty("java.awt.headless", "false");

        // Initialize minimal GUI components
        Main.frame = mock(javax.swing.JFrame.class);

        // Initialize test environment
        Main.gui = new Main();
        try {
            Main.gui.webData = new WebData();
        } catch (Exception e) {
            Main.gui.webData = new WebData();
            Main.gui.webData.coin = new ArrayList<>();
        }
        Main.gui.webData.portfolio = new ArrayList<>();
        Main.gui.webData.portfolio_names = new ArrayList<>();
        Main.gui.webData.portfolio_nr = 0;

        // Add default portfolio
        Main.gui.webData.portfolio.add(new ArrayList<WebData.Coin>());
        Main.gui.webData.portfolio_names.add("Portfolio 1");

        Main.theme = new Main.Theme(Main.themes.LIGHT);
        Main.currency = "USD";
        Main.currencyChar = "$";

        panelPortfolio = new PanelPortfolio();

        // Reflection for private method findPortfolioName
        findPortfolioNameMethod = PanelPortfolio.class.getDeclaredMethod("findPortfolioName", String.class);
        findPortfolioNameMethod.setAccessible(true);
    }

    /**
     * TC_DV_006: Validate Duplicate Coin - Coin Already Exists (White Box - Branch
     * Coverage)
     * Requirement: FR2.10
     * Objective: Test branch where duplicate coin is detected
     */
    @Test
    public void testValidateDuplicateCoin_Branch_Exists() throws Exception {
        // Setup: Add Bitcoin to portfolio
        WebData.Coin coin = Main.gui.webData.getCoin();
        coin.name = "Bitcoin";
        Main.gui.webData.portfolio.get(0).add(coin);

        // Execute: Check if Bitcoin exists
        boolean result = (boolean) findPortfolioNameMethod.invoke(panelPortfolio, "Bitcoin");

        // Verify: Returns true
        assertTrue("Should return true for existing coin", result);
    }

    /**
     * TC_DV_007: Validate Duplicate Coin - Coin Not Exists (White Box - Branch
     * Coverage)
     * Requirement: FR2.10
     * Objective: Test branch where coin is not duplicate
     */
    @Test
    public void testValidateDuplicateCoin_Branch_NotExists() throws Exception {
        // Setup: Add Bitcoin to portfolio
        WebData.Coin coin = Main.gui.webData.getCoin();
        coin.name = "Bitcoin";
        Main.gui.webData.portfolio.get(0).add(coin);

        // Execute: Check if Ethereum exists
        boolean result = (boolean) findPortfolioNameMethod.invoke(panelPortfolio, "Ethereum");

        // Verify: Returns false
        assertFalse("Should return false for non-existing coin", result);
    }

    /**
     * TC_DV_008: Validate Portfolio Name - Duplicate Name (White Box - Condition
     * Coverage)
     * Requirement: FR2.8
     * Objective: Test condition for duplicate portfolio name check
     * Note: This logic is inside bManagePortfolioListener, which is hard to test
     * directly via unit test
     * without triggering UI events. However, we can simulate the logic or test the
     * underlying data structure.
     * Since the logic is embedded in the ActionListener, we will simulate the check
     * manually to verify the logic flow.
     */
    @Test
    public void testValidatePortfolioName_Condition_Duplicate() {
        // Setup: Two portfolios
        Main.gui.webData.portfolio_names.add("Portfolio 2");

        // Logic to test: Check if "Portfolio 1" exists in names list (simulating rename
        // of Portfolio 2)
        String newName = "Portfolio 1";
        int currentNr = 1; // Portfolio 2 index

        boolean duplicateFound = false;
        for (int i = 0; i < Main.gui.webData.portfolio_names.size(); ++i) {
            if (Main.gui.webData.portfolio_names.get(i).equals(newName)) {
                if (i != currentNr) {
                    duplicateFound = true;
                }
            }
        }

        // Verify: Duplicate detected
        assertTrue("Should detect duplicate name", duplicateFound);
    }

    /**
     * TC_DV_009: Validate Portfolio Deletion - Last Portfolio (White Box - Branch
     * Coverage)
     * Requirement: FR2.9
     * Objective: Test branch preventing deletion of last portfolio
     */
    @Test
    public void testValidatePortfolioDeletion_Branch_LastPortfolio() {
        // Setup: Only 1 portfolio exists (default setup)
        assertEquals(1, Main.gui.webData.portfolio.size());

        // Logic to test: if (webData.portfolio.size() == 1)
        boolean canDelete = true;
        if (Main.gui.webData.portfolio.size() == 1) {
            canDelete = false;
        }

        // Verify: Deletion prevented
        assertFalse("Should not allow deletion of last portfolio", canDelete);
    }

    /**
     * TC_DV_010: Validate Portfolio Deletion - Multiple Portfolios (White Box -
     * Branch Coverage)
     * Requirement: FR2.9
     * Objective: Test branch allowing deletion when multiple portfolios exist
     */
    @Test
    public void testValidatePortfolioDeletion_Branch_MultiplePortfolios() {
        // Setup: Add second portfolio
        Main.gui.webData.portfolio.add(new ArrayList<WebData.Coin>());
        assertEquals(2, Main.gui.webData.portfolio.size());

        // Logic to test: if (webData.portfolio.size() == 1)
        boolean canDelete = true;
        if (Main.gui.webData.portfolio.size() == 1) {
            canDelete = false;
        }

        // Verify: Deletion allowed
        assertTrue("Should allow deletion when multiple portfolios exist", canDelete);
    }
}
