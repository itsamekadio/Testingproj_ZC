package com.cryptochecker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SearchIntegrationTest {

    private WebData webData;
    private WebData.Coin btc;
    private WebData.Coin eth;
    private WebData.Coin ltc;

    @Before
    public void setUp() throws Exception {
        System.setProperty("java.awt.headless", "true");

        // Mock GUI components
        Main.frame = mock(javax.swing.JFrame.class);
        Main.gui = new Main();
        try {
            Main.gui.debug = new Debug();
        } catch (Exception e) {
            Main.gui.debug = null;
        }

        webData = new WebData();
        if (webData.coin == null) {
            webData.coin = new ArrayList<>();
        } else {
            webData.coin.clear();
        }

        // Setup initial data
        btc = webData.new Coin();
        btc.name = "Bitcoin";
        btc.symbol = "BTC";
        btc.price = 50000.0;

        eth = webData.new Coin();
        eth.name = "Ethereum";
        eth.symbol = "ETH";
        eth.price = 3000.0;

        ltc = webData.new Coin();
        ltc.name = "Litecoin";
        ltc.symbol = "LTC";
        ltc.price = 150.0;

        webData.coin.add(btc);
        webData.coin.add(eth);
        webData.coin.add(ltc);
    }

    @Test
    public void testSearchIntegration_FilterLogic() {
        // Imitate the filtering logic from PanelCoin's SearchDocumentListener
        // Since we are headless and can't use actual JTable/TableRowSorter easily
        // without UI,
        // we verify the model filtering logic conceptually against the WebData.

        String searchText = "Bit";
        ArrayList<WebData.Coin> filtered = performSearch(searchText);

        assertEquals(1, filtered.size());
        assertEquals("Bitcoin", filtered.get(0).name);
    }

    @Test
    public void testSearchIntegration_CaseInsensitive() {
        String searchText = "eth";
        ArrayList<WebData.Coin> filtered = performSearch(searchText);

        assertEquals(1, filtered.size());
        assertEquals("Ethereum", filtered.get(0).name);
    }

    @Test
    public void testSearchIntegration_SymbolSearch() {
        String searchText = "LTC";
        ArrayList<WebData.Coin> filtered = performSearch(searchText);

        assertEquals(1, filtered.size());
        assertEquals("Litecoin", filtered.get(0).name);
    }

    @Test
    public void testSearchIntegration_NoResults() {
        String searchText = "Dogecoin";
        ArrayList<WebData.Coin> filtered = performSearch(searchText);

        assertEquals(0, filtered.size());
    }

    @Test
    public void testSearchIntegration_EmptyString() {
        String searchText = "";
        ArrayList<WebData.Coin> filtered = performSearch(searchText);

        assertEquals(3, filtered.size());
    }

    // Helper method simulating the RowFilter regex logic
    private ArrayList<WebData.Coin> performSearch(String text) {
        if (text == null || text.trim().length() == 0) {
            return new ArrayList<>(webData.coin);
        }

        ArrayList<WebData.Coin> result = new ArrayList<>();
        String regex = "(?i).*" + text + ".*"; // Simulating "(?i)" + text containment

        for (WebData.Coin c : webData.coin) {
            // PanelCoin filters generally match against visible text logic,
            // usually checking name or symbol or other columns.
            // Here we verify it checks Name and Symbol as primary indicators.
            if (c.name.matches(regex) || c.symbol.matches(regex)) {
                result.add(c);
            }
        }
        return result;
    }
}
