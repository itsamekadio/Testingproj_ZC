package com.cryptochecker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class WebDataUnitTest {

    private WebData webData;
    
    @Mock
    private FileInputStream mockFileInputStream;
    
    @Mock
    private ObjectInputStream mockObjectInputStream;

    @Before
    public void setUp() throws Exception {
        System.setProperty("java.awt.headless", "true");
        MockitoAnnotations.openMocks(this);
        
        Main.frame = mock(javax.swing.JFrame.class);
        
        Main.gui = new Main();
        try { Main.gui.debug = new Debug(); } catch (java.awt.HeadlessException e) { Main.gui.debug = null; }
        Main.theme = new Main.Theme(Main.themes.LIGHT);
        Main.currency = "USD";
        Main.currencyChar = "$";
        
        webData = new WebData();
        if (webData.coin == null) {
            webData.coin = new ArrayList<>();
        }
        if (webData.global_data == null) {
            webData.global_data = webData.new Global_Data();
        }
    }

    @Test
    public void testGetCoin_ShouldReturnNewCoinInstance() {
        WebData.Coin coin = webData.getCoin();
        
        assertNotNull(coin);
        assertTrue(coin instanceof WebData.Coin);
    }

    @Test
    public void testCoinTrimPrice_ValueGreaterThanOne() {
        WebData.Coin coin = webData.new Coin();
        
        String result = coin.trimPrice(100.5678);
        
        assertEquals("100.57", result);
    }

    @Test
    public void testCoinTrimPrice_ValueBetweenPointOneAndOne() {
        WebData.Coin coin = webData.new Coin();
        
        String result = coin.trimPrice(0.5678);
        
        assertEquals("0.568", result);
    }

    @Test
    public void testCoinTrimPrice_ValueBetweenPointZeroOneAndPointOne() {
        WebData.Coin coin = webData.new Coin();
        
        String result = coin.trimPrice(0.05678);
        
        assertEquals("0.0568", result);
    }

    @Test
    public void testCoinTrimPrice_ValueBetweenPointZeroZeroOneAndPointZeroOne() {
        WebData.Coin coin = webData.new Coin();
        
        String result = coin.trimPrice(0.005678);
        
        assertEquals("0.00568", result);
    }

    @Test
    public void testCoinTrimPrice_ValueBetweenPointZeroZeroZeroOneAndPointZeroZeroOne() {
        WebData.Coin coin = webData.new Coin();
        
        String result = coin.trimPrice(0.0005678);
        
        assertEquals("0.000568", result);
    }

    @Test
    public void testCoinTrimPrice_VerySmallValue() {
        WebData.Coin coin = webData.new Coin();
        
        String result = coin.trimPrice(0.00005678);
        
        assertTrue(result.startsWith("0.00005678"));
    }

    @Test
    public void testCoinTrimPrice_Zero() {
        WebData.Coin coin = webData.new Coin();
        
        String result = coin.trimPrice(0.0);
        
        assertEquals("0", result);
    }

    @Test
    public void testCoinGetInfo_ReturnsFormattedString() {
        WebData.Coin coin = webData.new Coin();
        coin.rank = 1;
        coin.id = "bitcoin";
        coin.name = "Bitcoin";
        coin.symbol = "BTC";
        coin.price = 45000.50;
        coin.market_cap = 850000000000.0;
        coin._24h_volume = 30000000000.0;
        coin.available_supply = 19000000.0;
        coin.total_supply = 21000000.0;
        coin.max_supply = 21000000.0;
        coin.percent_change_1h = 0.5;
        coin.percent_change_24h = 2.3;
        coin.percent_change_7d = -1.2;
        coin.last_updated = "2024-01-01";
        
        String result = coin.getInfo();
        
        assertTrue(result.contains("Rank: 1"));
        assertTrue(result.contains("ID: bitcoin"));
        assertTrue(result.contains("Name: Bitcoin"));
        assertTrue(result.contains("Symbol: BTC"));
        assertTrue(result.contains("Price USD:"));
        assertTrue(result.contains("Market Cap:"));
        assertTrue(result.contains("Last Updated: 2024-01-01"));
    }

    @Test
    public void testCoinGetPortfolio_ReturnsFormattedString() {
        WebData.Coin coin = webData.new Coin();
        coin.rank = 1;
        coin.id = "bitcoin";
        coin.name = "Bitcoin";
        coin.symbol = "BTC";
        coin.price = 45000.50;
        coin.portfolio_amount = 2.5;
        coin.portfolio_value = 112501.25;
        coin.portfolio_gains = 12501.25;
        coin.portfolio_currency = "USD";
        coin.portfolio_price_start = 40000.0;
        coin.portfolio_value_start = 100000.0;
        
        String result = coin.getPortfolio();
        
        assertTrue(result.contains("Portfolio Amount:"));
        assertTrue(result.contains("Portfolio Value:"));
        assertTrue(result.contains("Portfolio Gains:"));
        assertTrue(result.contains("Portfolio Currency: USD"));
        assertTrue(result.contains("Portfolio Price Start:"));
    }

    @Test
    public void testCoinToString_ReturnsName() {
        WebData.Coin coin = webData.new Coin();
        coin.name = "Bitcoin";
        
        String result = coin.toString();
        
        assertEquals("Bitcoin", result);
    }

    @Test
    public void testCoinCopy_SuccessfulClone() throws CloneNotSupportedException {
        WebData.Coin coin = webData.new Coin();
        coin.name = "Bitcoin";
        coin.price = 45000.0;
        
        Object copiedObj = coin.copy();
        
        assertNotNull(copiedObj);
        assertTrue(copiedObj instanceof WebData.Coin);
        WebData.Coin copiedCoin = (WebData.Coin) copiedObj;
        assertEquals("Bitcoin", copiedCoin.name);
        assertEquals(45000.0, copiedCoin.price, 0.001);
    }

    @Test
    public void testGlobalDataToString_ReturnsFormattedString() {
        WebData.Global_Data globalData = webData.new Global_Data();
        globalData.total_market_cap = 2000000000000L;
        globalData.total_24h_volume = 100000000000L;
        globalData.bitcoin_percentage_of_market_cap = 45.5;
        globalData.active_currencies = 5000;
        globalData.active_assets = 8000;
        globalData.active_markets = 25000;
        globalData.last_updated = 1640000000L;
        
        String result = globalData.toString();
        
        assertTrue(result.contains("Total Market Cap:"));
        assertTrue(result.contains("Total 24 Hour Volume:"));
        assertTrue(result.contains("Bitcoin Dominance: 45.5%"));
        assertTrue(result.contains("Active Currencies: 5000"));
        assertTrue(result.contains("Active Assets: 8000"));
        assertTrue(result.contains("Active Markets: 25000"));
        assertTrue(result.contains("Last Updated: 1640000000"));
    }

    @Test
    public void testWebDataConstructor_InitializesCollections() throws Exception {
        WebData newWebData = new WebData();
        
        assertNotNull(newWebData);
        assertNotNull(newWebData.portfolio_names);
        assertEquals(0, newWebData.portfolio_nr);
    }

    @Test
    public void testCoinList_IsNotNull() {
        assertNotNull(webData.coin);
    }

    @Test
    public void testGlobalData_IsNotNull() {
        assertNotNull(webData.global_data);
    }

    @Test
    public void testPortfolioNames_IsNotNull() {
        assertNotNull(webData.portfolio_names);
    }

    @Test
    public void testPortfolioNumber_DefaultValue() {
        assertEquals(0, webData.portfolio_nr);
    }

    @Test
    public void testCoinTrimPrice_NegativeValue() {
        WebData.Coin coin = webData.new Coin();
        
        String result = coin.trimPrice(-100.5678);
        
        assertTrue(result.contains("-"));
    }

    @Test
    public void testCoinTrimPrice_VeryLargeValue() {
        WebData.Coin coin = webData.new Coin();
        
        String result = coin.trimPrice(999999999.99);
        
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }

    @Test
    public void testCoinFields_InitialValues() {
        WebData.Coin coin = webData.new Coin();
        
        assertEquals(0.0, coin.price, 0.0);
        assertEquals(0.0, coin.portfolio_amount, 0.0);
        assertEquals(0.0, coin.portfolio_value, 0.0);
        assertEquals(0.0, coin.portfolio_gains, 0.0);
    }

    @Test
    public void testCoinSetValues_StoresCorrectly() {
        WebData.Coin coin = webData.new Coin();
        coin.id = "ethereum";
        coin.name = "Ethereum";
        coin.symbol = "ETH";
        coin.price = 3000.0;
        coin.rank = 2;
        
        assertEquals("ethereum", coin.id);
        assertEquals("Ethereum", coin.name);
        assertEquals("ETH", coin.symbol);
        assertEquals(3000.0, coin.price, 0.0);
        assertEquals(2, coin.rank);
    }

    @Test
    public void testGlobalDataFields_InitialValues() {
        WebData.Global_Data globalData = webData.new Global_Data();
        
        assertEquals(0L, globalData.total_market_cap);
        assertEquals(0L, globalData.total_24h_volume);
        assertEquals(0.0, globalData.bitcoin_percentage_of_market_cap, 0.0);
        assertEquals(0, globalData.active_currencies);
    }

    @Test
    public void testCoinTrimPrice_EdgeCase_ExactlyOne() {
        WebData.Coin coin = webData.new Coin();
        
        String result = coin.trimPrice(1.0);
        
        assertEquals("1", result);
    }

    @Test
    public void testCoinTrimPrice_EdgeCase_ExactlyPointOne() {
        WebData.Coin coin = webData.new Coin();
        
        String result = coin.trimPrice(0.1);
        
        assertEquals("0.1", result);
    }

    @Test
    public void testCoinTrimPrice_EdgeCase_ExactlyPointZeroOne() {
        WebData.Coin coin = webData.new Coin();
        
        String result = coin.trimPrice(0.01);
        
        assertEquals("0.01", result);
    }

    @Test
    public void testCoinGetInfo_WithNullValues() {
        WebData.Coin coin = webData.new Coin();
        
        String result = coin.getInfo();
        
        assertNotNull(result);
        assertTrue(result.contains("Rank:"));
        assertTrue(result.contains("Name:"));
    }

    @Test
    public void testMultipleCoins_InArrayList() {
        webData.coin.clear();
        WebData.Coin coin1 = webData.new Coin();
        coin1.name = "Bitcoin";
        WebData.Coin coin2 = webData.new Coin();
        coin2.name = "Ethereum";
        
        webData.coin.add(coin1);
        webData.coin.add(coin2);
        
        assertEquals(2, webData.coin.size());
        assertEquals("Bitcoin", webData.coin.get(0).name);
        assertEquals("Ethereum", webData.coin.get(1).name);
    }

    @Test
    public void testPortfolioNames_AddAndRetrieve() {
        webData.portfolio_names.add("My Portfolio");
        webData.portfolio_names.add("Trading Portfolio");
        
        assertEquals(2, webData.portfolio_names.size());
        assertEquals("My Portfolio", webData.portfolio_names.get(0));
        assertEquals("Trading Portfolio", webData.portfolio_names.get(1));
    }

    @Test
    public void testCoinPortfolioCalculations_BasicArithmetic() {
        WebData.Coin coin = webData.new Coin();
        coin.price = 1000.0;
        coin.portfolio_amount = 5.0;
        coin.portfolio_value = coin.price * coin.portfolio_amount;
        
        assertEquals(5000.0, coin.portfolio_value, 0.01);
    }
}

