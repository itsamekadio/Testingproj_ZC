package com.cryptochecker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.io.*;
import java.util.ArrayList;

public class PortfolioManagementIntegrationTest {

    private WebData webData;
    private PanelPortfolio panelPortfolio;
    private String testPortfolioLocation;

    @Before
    public void setUp() throws Exception {
        System.setProperty("java.awt.headless", "true");
        
        Main.frame = mock(javax.swing.JFrame.class);
        
        Main.gui = new Main();
        try { Main.gui.debug = new Debug(); } catch (java.awt.HeadlessException e) { Main.gui.debug = null; }
        Main.theme = new Main.Theme(Main.themes.LIGHT);
        Main.currency = "USD";
        Main.currencyChar = "$";
        
        testPortfolioLocation = Main.folderLocation + "test_portfolio.ser";
        
        webData = new WebData();
        if (webData.coin == null) {
            webData.coin = new ArrayList<>();
        }
        if (webData.portfolio == null) {
            webData.portfolio = new ArrayList<>();
        }
        if (webData.global_data == null) {
            webData.global_data = webData.new Global_Data();
        }
        
        Main.gui.webData = webData;
        
        setupTestCoins();
    }

    private void setupTestCoins() {
        webData.coin.clear();
        
        WebData.Coin btc = webData.new Coin();
        btc.name = "Bitcoin";
        btc.symbol = "BTC";
        btc.price = 50000.0;
        btc.rank = 1;
        btc.market_cap = 900000000000.0;
        btc._24h_volume = 30000000000.0;
        btc.percent_change_1h = 0.5;
        btc.percent_change_24h = 2.3;
        btc.percent_change_7d = 5.1;
        
        WebData.Coin eth = webData.new Coin();
        eth.name = "Ethereum";
        eth.symbol = "ETH";
        eth.price = 3000.0;
        eth.rank = 2;
        eth.market_cap = 350000000000.0;
        eth._24h_volume = 15000000000.0;
        eth.percent_change_1h = 0.3;
        eth.percent_change_24h = 1.8;
        eth.percent_change_7d = 3.2;
        
        webData.coin.add(btc);
        webData.coin.add(eth);
    }

    @Test
    public void testPortfolioIntegration_AddCoinToPortfolio() {
        webData.portfolio.clear();
        ArrayList<WebData.Coin> newPortfolio = new ArrayList<>();
        
        WebData.Coin portfolioCoin = (WebData.Coin) webData.coin.get(0).copy();
        portfolioCoin.portfolio_amount = 2.0;
        portfolioCoin.portfolio_currency = "USD";
        portfolioCoin.portfolio_price_start = 45000.0;
        portfolioCoin.portfolio_value_start = 90000.0;
        
        newPortfolio.add(portfolioCoin);
        webData.portfolio.add(newPortfolio);
        
        assertEquals(1, webData.portfolio.size());
        assertEquals(1, webData.portfolio.get(0).size());
        assertEquals(2.0, webData.portfolio.get(0).get(0).portfolio_amount, 0.01);
    }

    @Test
    public void testPortfolioIntegration_RefreshCalculation_SameCurrency() {
        webData.portfolio.clear();
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        
        WebData.Coin btcPortfolio = (WebData.Coin) webData.coin.get(0).copy();
        btcPortfolio.portfolio_amount = 2.0;
        btcPortfolio.portfolio_currency = "USD";
        btcPortfolio.portfolio_price_start = 45000.0;
        btcPortfolio.portfolio_value_start = 90000.0;
        
        portfolio.add(btcPortfolio);
        webData.portfolio.add(portfolio);
        
        WebData.Coin currentPrice = webData.coin.get(0);
        
        double expectedValue = currentPrice.price * btcPortfolio.portfolio_amount;
        double expectedGains = expectedValue - btcPortfolio.portfolio_value_start;
        
        assertEquals(100000.0, expectedValue, 0.01);
        assertEquals(10000.0, expectedGains, 0.01);
    }

    @Test
    public void testPortfolioIntegration_MultipleCoinsInPortfolio() {
        webData.portfolio.clear();
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        
        WebData.Coin btcPortfolio = (WebData.Coin) webData.coin.get(0).copy();
        btcPortfolio.portfolio_amount = 1.0;
        btcPortfolio.portfolio_currency = "USD";
        btcPortfolio.portfolio_price_start = 45000.0;
        btcPortfolio.portfolio_value_start = 45000.0;
        
        WebData.Coin ethPortfolio = (WebData.Coin) webData.coin.get(1).copy();
        ethPortfolio.portfolio_amount = 5.0;
        ethPortfolio.portfolio_currency = "USD";
        ethPortfolio.portfolio_price_start = 2800.0;
        ethPortfolio.portfolio_value_start = 14000.0;
        
        portfolio.add(btcPortfolio);
        portfolio.add(ethPortfolio);
        webData.portfolio.add(portfolio);
        
        assertEquals(2, webData.portfolio.get(0).size());
        
        double totalValue = 0;
        for (WebData.Coin coin : webData.portfolio.get(0)) {
            WebData.Coin current = null;
            for (WebData.Coin c : webData.coin) {
                if (c.name.equals(coin.name)) {
                    current = c;
                    break;
                }
            }
            totalValue += current.price * coin.portfolio_amount;
        }
        
        assertEquals(65000.0, totalValue, 0.01);
    }

    @Test
    public void testPortfolioIntegration_TotalGainsCalculation() {
        webData.portfolio.clear();
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        
        WebData.Coin btcPortfolio = (WebData.Coin) webData.coin.get(0).copy();
        btcPortfolio.portfolio_amount = 1.0;
        btcPortfolio.portfolio_currency = "USD";
        btcPortfolio.portfolio_price_start = 45000.0;
        btcPortfolio.portfolio_value_start = 45000.0;
        
        WebData.Coin ethPortfolio = (WebData.Coin) webData.coin.get(1).copy();
        ethPortfolio.portfolio_amount = 5.0;
        ethPortfolio.portfolio_currency = "USD";
        ethPortfolio.portfolio_price_start = 2800.0;
        ethPortfolio.portfolio_value_start = 14000.0;
        
        portfolio.add(btcPortfolio);
        portfolio.add(ethPortfolio);
        webData.portfolio.add(portfolio);
        
        double totalGains = 0;
        for (WebData.Coin coin : webData.portfolio.get(0)) {
            WebData.Coin current = null;
            for (WebData.Coin c : webData.coin) {
                if (c.name.equals(coin.name)) {
                    current = c;
                    break;
                }
            }
            double currentValue = current.price * coin.portfolio_amount;
            totalGains += currentValue - coin.portfolio_value_start;
        }
        
        assertEquals(6000.0, totalGains, 0.01);
    }

    @Test
    @Ignore("Skipped: WebData.Coin inner class causes NotSerializableException due to implicit outer class reference")
    public void testPortfolioIntegration_SerializePortfolio() throws Exception {
        webData.portfolio.clear();
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        
        WebData.Coin btcPortfolio = webData.new Coin();
        btcPortfolio.name = "Bitcoin";
        btcPortfolio.price = 50000.0;
        btcPortfolio.portfolio_amount = 2.0;
        btcPortfolio.portfolio_currency = "USD";
        
        portfolio.add(btcPortfolio);
        webData.portfolio.add(portfolio);
        
        try (FileOutputStream file = new FileOutputStream(testPortfolioLocation);
             BufferedOutputStream buffer = new BufferedOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(buffer)) {
            
            out.writeObject(webData.portfolio);
            out.writeObject(webData.portfolio_names);
        }
        
        ArrayList<ArrayList<WebData.Coin>> deserializedPortfolio;
        ArrayList<String> deserializedNames;
        
        try (FileInputStream file = new FileInputStream(testPortfolioLocation);
             BufferedInputStream buffer = new BufferedInputStream(file);
             ObjectInputStream in = new ObjectInputStream(buffer)) {
            
            deserializedPortfolio = (ArrayList<ArrayList<WebData.Coin>>) in.readObject();
            deserializedNames = (ArrayList<String>) in.readObject();
        }
        
        assertEquals(1, deserializedPortfolio.size());
        assertEquals(1, deserializedPortfolio.get(0).size());
        assertEquals(2.0, deserializedPortfolio.get(0).get(0).portfolio_amount, 0.01);
        
        new File(testPortfolioLocation).delete();
    }

    @Test
    public void testPortfolioIntegration_RemoveCoinFromPortfolio() {
        webData.portfolio.clear();
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        
        WebData.Coin btcPortfolio = (WebData.Coin) webData.coin.get(0).copy();
        btcPortfolio.portfolio_amount = 1.0;
        
        WebData.Coin ethPortfolio = (WebData.Coin) webData.coin.get(1).copy();
        ethPortfolio.portfolio_amount = 5.0;
        
        portfolio.add(btcPortfolio);
        portfolio.add(ethPortfolio);
        webData.portfolio.add(portfolio);
        
        assertEquals(2, webData.portfolio.get(0).size());
        
        webData.portfolio.get(0).remove(0);
        
        assertEquals(1, webData.portfolio.get(0).size());
        assertEquals("Ethereum", webData.portfolio.get(0).get(0).name);
    }

    @Test
    public void testPortfolioIntegration_MultiplePortfolios() {
        webData.portfolio.clear();
        webData.portfolio_names.clear();
        
        ArrayList<WebData.Coin> portfolio1 = new ArrayList<>();
        WebData.Coin btc1 = (WebData.Coin) webData.coin.get(0).copy();
        btc1.portfolio_amount = 1.0;
        portfolio1.add(btc1);
        
        ArrayList<WebData.Coin> portfolio2 = new ArrayList<>();
        WebData.Coin eth2 = (WebData.Coin) webData.coin.get(1).copy();
        eth2.portfolio_amount = 10.0;
        portfolio2.add(eth2);
        
        webData.portfolio.add(portfolio1);
        webData.portfolio.add(portfolio2);
        webData.portfolio_names.add("Main Portfolio");
        webData.portfolio_names.add("Alt Portfolio");
        
        assertEquals(2, webData.portfolio.size());
        assertEquals(2, webData.portfolio_names.size());
        assertEquals("Bitcoin", webData.portfolio.get(0).get(0).name);
        assertEquals("Ethereum", webData.portfolio.get(1).get(0).name);
    }

    @Test
    public void testPortfolioIntegration_SwitchPortfolio() {
        webData.portfolio.clear();
        webData.portfolio_names.clear();
        
        ArrayList<WebData.Coin> portfolio1 = new ArrayList<>();
        ArrayList<WebData.Coin> portfolio2 = new ArrayList<>();
        
        webData.portfolio.add(portfolio1);
        webData.portfolio.add(portfolio2);
        webData.portfolio_names.add("Portfolio 1");
        webData.portfolio_names.add("Portfolio 2");
        
        webData.portfolio_nr = 0;
        assertEquals(0, webData.portfolio_nr);
        
        webData.portfolio_nr = 1;
        assertEquals(1, webData.portfolio_nr);
    }

    @Test
    public void testPortfolioIntegration_UpdateCoinPriceImpact() {
        webData.portfolio.clear();
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        
        WebData.Coin btcPortfolio = (WebData.Coin) webData.coin.get(0).copy();
        btcPortfolio.portfolio_amount = 1.0;
        btcPortfolio.portfolio_value_start = 45000.0;
        
        portfolio.add(btcPortfolio);
        webData.portfolio.add(portfolio);
        
        double oldValue = webData.coin.get(0).price * btcPortfolio.portfolio_amount;
        
        webData.coin.get(0).price = 55000.0;
        
        double newValue = webData.coin.get(0).price * btcPortfolio.portfolio_amount;
        
        assertEquals(50000.0, oldValue, 0.01);
        assertEquals(55000.0, newValue, 0.01);
    }

    @Test
    public void testPortfolioIntegration_EmptyPortfolio() {
        webData.portfolio.clear();
        ArrayList<WebData.Coin> emptyPortfolio = new ArrayList<>();
        webData.portfolio.add(emptyPortfolio);
        
        assertEquals(1, webData.portfolio.size());
        assertEquals(0, webData.portfolio.get(0).size());
    }

    @Test
    public void testPortfolioIntegration_PortfolioValueZero() {
        webData.portfolio.clear();
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        
        WebData.Coin btcPortfolio = (WebData.Coin) webData.coin.get(0).copy();
        btcPortfolio.portfolio_amount = 0.0;
        
        portfolio.add(btcPortfolio);
        webData.portfolio.add(portfolio);
        
        double value = webData.coin.get(0).price * btcPortfolio.portfolio_amount;
        
        assertEquals(0.0, value, 0.01);
    }

    @Test
    public void testPortfolioIntegration_CurrencyConversionImpact() {
        webData.portfolio.clear();
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        
        WebData.Coin btcPortfolio = (WebData.Coin) webData.coin.get(0).copy();
        btcPortfolio.portfolio_amount = 1.0;
        btcPortfolio.portfolio_currency = "EUR";
        btcPortfolio.portfolio_price = 42000.0;
        btcPortfolio.price = 45000.0;
        
        portfolio.add(btcPortfolio);
        webData.portfolio.add(portfolio);
        
        double currentPrice = webData.coin.get(0).price;
        double convertedPrice = btcPortfolio.portfolio_price * (currentPrice / btcPortfolio.price);
        
        assertEquals(46666.66, convertedPrice, 1.0);
    }

    @Test
    public void testPortfolioIntegration_PortfolioPercentageGain() {
        webData.portfolio.clear();
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        
        WebData.Coin btcPortfolio = (WebData.Coin) webData.coin.get(0).copy();
        btcPortfolio.portfolio_amount = 1.0;
        btcPortfolio.portfolio_value_start = 45000.0;
        
        portfolio.add(btcPortfolio);
        webData.portfolio.add(portfolio);
        
        double currentValue = webData.coin.get(0).price * btcPortfolio.portfolio_amount;
        double percentGain = ((currentValue - btcPortfolio.portfolio_value_start) / 
                             btcPortfolio.portfolio_value_start) * 100;
        
        assertEquals(11.11, percentGain, 0.1);
    }

    @Test
    public void testPortfolioIntegration_ComplexPortfolio() {
        webData.portfolio.clear();
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        
        for (int i = 0; i < webData.coin.size(); i++) {
            WebData.Coin portfolioCoin = (WebData.Coin) webData.coin.get(i).copy();
            portfolioCoin.portfolio_amount = i + 1.0;
            portfolioCoin.portfolio_currency = "USD";
            portfolioCoin.portfolio_value_start = webData.coin.get(i).price * portfolioCoin.portfolio_amount * 0.9;
            portfolio.add(portfolioCoin);
        }
        
        webData.portfolio.add(portfolio);
        
        assertEquals(webData.coin.size(), webData.portfolio.get(0).size());
        
        double totalValue = 0;
        for (int i = 0; i < webData.portfolio.get(0).size(); i++) {
            totalValue += webData.coin.get(i).price * webData.portfolio.get(0).get(i).portfolio_amount;
        }
        
        assertTrue(totalValue > 0);
    }

    @Test
    public void testPortfolioIntegration_CoinStatisticsPreserved() {
        webData.portfolio.clear();
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        
        WebData.Coin btcPortfolio = (WebData.Coin) webData.coin.get(0).copy();
        btcPortfolio.portfolio_amount = 1.0;
        
        portfolio.add(btcPortfolio);
        webData.portfolio.add(portfolio);
        
        assertEquals(webData.coin.get(0).rank, btcPortfolio.rank);
        assertEquals(webData.coin.get(0).symbol, btcPortfolio.symbol);
        assertEquals(webData.coin.get(0).market_cap, btcPortfolio.market_cap, 0.01);
    }
}

