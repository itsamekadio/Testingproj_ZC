package com.cryptochecker;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * White Box Test Suite for Menu Class
 * Focus: GUI Initialization and Button Actions
 * Coverage: All navigation button visibility states
 */
public class MenuWhiteBoxTest {

    @Before
    public void setUp() {
        System.setProperty("java.awt.headless", "false");

        try {
            // Initialize frame first
            if (Main.frame == null) {
                Main.frame = new JFrame();
                Main.frame.setVisible(false);
            }

            // Initialize Main and Debug
            Main.gui = new Main();
            Main.gui.debug = new Debug();

            // Initialize Theme
            Main.theme = new Main.Theme(Main.themes.LIGHT);
            Main.currency = "USD";
            Main.currencyChar = "$";

            // Initialize WebData (handle potential network issues)
            try {
                Main.gui.webData = new WebData();
            } catch (Exception e) {
                Main.gui.webData = new WebData();
                Main.gui.webData.coin = new ArrayList<>();
                Main.gui.webData.global_data = Main.gui.webData.new Global_Data();
            }

            // Ensure portfolio fields are initialized (required by PanelPortfolio)
            if (Main.gui.webData.portfolio == null) {
                Main.gui.webData.portfolio = new ArrayList<>();
                Main.gui.webData.portfolio.add(new ArrayList<>());
            }
            if (Main.gui.webData.portfolio_names == null) {
                Main.gui.webData.portfolio_names = new ArrayList<>();
                Main.gui.webData.portfolio_names.add("Portfolio 1");
            }

            // Initialize Panels required by Menu listeners
            Main.gui.panelCoin = new PanelCoin();
            Main.gui.panelPortfolio = new PanelPortfolio();
            Main.gui.panelConverter = new PanelConverter();
            Main.gui.panelSettings = new PanelSettings();

            // Initialize Menu
            Main.gui.menu = new Menu();

        } catch (Exception e) {
            System.err.println("Setup failed with exception: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            fail("Setup failed: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

    @Test
    public void testMenuInitialization() {
        assertNotNull("Menu object should be created", Main.gui.menu);
        assertNotNull("Menu panel should be created", Main.gui.menu.panel);
        assertTrue("Menu panel should have components", Main.gui.menu.panel.getComponentCount() > 0);
    }

    @Test
    public void testNavigationButtons_Exist() {
        // Verify all buttons exist
        JButton bCoin = findButton("Coin Data");
        JButton bPortfolio = findButton("Portfolio");
        JButton bConverter = findButton("Converter");
        JButton bSettings = findButton("Settings");

        assertNotNull("Coin Data button not found", bCoin);
        assertNotNull("Portfolio button not found", bPortfolio);
        assertNotNull("Converter button not found", bConverter);
        assertNotNull("Settings button not found", bSettings);
    }

    @Test
    public void testCoinButton_AllVisibilityStates() {
        JButton bCoin = findButton("Coin Data");
        assertNotNull("Coin Data button not found", bCoin);

        // Click Coin Data button
        bCoin.doClick();

        // Verify ALL 4 panel visibility states
        assertTrue("PanelCoin should be visible", Main.gui.panelCoin.panel.isVisible());
        assertFalse("PanelPortfolio should be hidden", Main.gui.panelPortfolio.panel.isVisible());
        assertFalse("PanelConverter should be hidden", Main.gui.panelConverter.panel.isVisible());
        assertFalse("PanelSettings should be hidden", Main.gui.panelSettings.panel.isVisible());
    }

    @Test
    public void testPortfolioButton_AllVisibilityStates() {
        JButton bPortfolio = findButton("Portfolio");
        assertNotNull("Portfolio button not found", bPortfolio);

        // Click Portfolio button
        bPortfolio.doClick();

        // Verify ALL 4 panel visibility states
        assertFalse("PanelCoin should be hidden", Main.gui.panelCoin.panel.isVisible());
        assertTrue("PanelPortfolio should be visible", Main.gui.panelPortfolio.panel.isVisible());
        assertFalse("PanelConverter should be hidden", Main.gui.panelConverter.panel.isVisible());
        assertFalse("PanelSettings should be hidden", Main.gui.panelSettings.panel.isVisible());
    }

    @Test
    public void testConverterButton_AllVisibilityStates() {
        JButton bConverter = findButton("Converter");
        assertNotNull("Converter button not found", bConverter);

        // Click Converter button
        bConverter.doClick();

        // Verify ALL 4 panel visibility states
        assertFalse("PanelCoin should be hidden", Main.gui.panelCoin.panel.isVisible());
        assertFalse("PanelPortfolio should be hidden", Main.gui.panelPortfolio.panel.isVisible());
        assertTrue("PanelConverter should be visible", Main.gui.panelConverter.panel.isVisible());
        assertFalse("PanelSettings should be hidden", Main.gui.panelSettings.panel.isVisible());
    }

    @Test
    public void testSettingsButton_AllVisibilityStates() {
        JButton bSettings = findButton("Settings");
        assertNotNull("Settings button not found", bSettings);

        // Click Settings button
        bSettings.doClick();

        // Verify ALL 4 panel visibility states
        assertFalse("PanelCoin should be hidden", Main.gui.panelCoin.panel.isVisible());
        assertFalse("PanelPortfolio should be hidden", Main.gui.panelPortfolio.panel.isVisible());
        assertFalse("PanelConverter should be hidden", Main.gui.panelConverter.panel.isVisible());
        assertTrue("PanelSettings should be visible", Main.gui.panelSettings.panel.isVisible());
    }

    @Test
    public void testNavigationSequence() {
        // Test switching between panels in sequence
        JButton bCoin = findButton("Coin Data");
        JButton bPortfolio = findButton("Portfolio");
        JButton bConverter = findButton("Converter");
        JButton bSettings = findButton("Settings");

        // Start with Coin
        bCoin.doClick();
        assertTrue("PanelCoin should be visible", Main.gui.panelCoin.panel.isVisible());

        // Switch to Portfolio
        bPortfolio.doClick();
        assertFalse("PanelCoin should now be hidden", Main.gui.panelCoin.panel.isVisible());
        assertTrue("PanelPortfolio should be visible", Main.gui.panelPortfolio.panel.isVisible());

        // Switch to Converter
        bConverter.doClick();
        assertFalse("PanelPortfolio should now be hidden", Main.gui.panelPortfolio.panel.isVisible());
        assertTrue("PanelConverter should be visible", Main.gui.panelConverter.panel.isVisible());

        // Switch to Settings
        bSettings.doClick();
        assertFalse("PanelConverter should now be hidden", Main.gui.panelConverter.panel.isVisible());
        assertTrue("PanelSettings should be visible", Main.gui.panelSettings.panel.isVisible());
    }

    private JButton findButton(String text) {
        return findButtonInContainer(Main.gui.menu.panel, text);
    }

    private JButton findButtonInContainer(Container container, String text) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                if (text.equals(btn.getText())) {
                    return btn;
                }
            } else if (comp instanceof Container) {
                JButton found = findButtonInContainer((Container) comp, text);
                if (found != null)
                    return found;
            }
        }
        return null;
    }
}
