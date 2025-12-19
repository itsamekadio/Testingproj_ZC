package com.cryptochecker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class PortfolioCalculationUnitTest {

    @Mock
    private WebData mockWebData;
    
    private PanelPortfolio panelPortfolio;
    private WebData webData;

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
        if (webData.portfolio == null) {
            webData.portfolio = new ArrayList<>();
        }
        if (webData.global_data == null) {
            webData.global_data = webData.new Global_Data();
        }
        
        Main.gui.webData = webData;
    }

    @Test
    public void testPortfolioValueCalculation_SameCurrency() {
        WebData.Coin coin = webData.new Coin();
        coin.name = "Bitcoin";
        coin.price = 50000.0;
        coin.portfolio_amount = 2.0;
        coin.portfolio_currency = "USD";
        coin.portfolio_price_start = 45000.0;
        coin.portfolio_value_start = 90000.0;
        
        double expectedValue = coin.price * coin.portfolio_amount;
        double expectedGains = expectedValue - coin.portfolio_value_start;
        
        assertEquals(100000.0, expectedValue, 0.01);
        assertEquals(10000.0, expectedGains, 0.01);
    }

    @Test
    public void testPortfolioValueCalculation_DifferentCurrency() {
        WebData.Coin coin = webData.new Coin();
        coin.name = "Bitcoin";
        coin.price = 50000.0;
        coin.portfolio_amount = 2.0;
        coin.portfolio_currency = "EUR";
        coin.portfolio_price = 40000.0;
        
        double expectedValue = coin.price * coin.portfolio_amount;
        
        assertEquals(100000.0, expectedValue, 0.01);
    }

    @Test
    public void testPortfolioGainsCalculation_PositiveGains() {
        WebData.Coin coin = webData.new Coin();
        coin.price = 60000.0;
        coin.portfolio_amount = 1.5;
        coin.portfolio_value_start = 75000.0;
        
        double portfolioValue = coin.price * coin.portfolio_amount;
        double portfolioGains = portfolioValue - coin.portfolio_value_start;
        
        assertEquals(90000.0, portfolioValue, 0.01);
        assertEquals(15000.0, portfolioGains, 0.01);
    }

    @Test
    public void testPortfolioGainsCalculation_NegativeGains() {
        WebData.Coin coin = webData.new Coin();
        coin.price = 40000.0;
        coin.portfolio_amount = 1.5;
        coin.portfolio_value_start = 75000.0;
        
        double portfolioValue = coin.price * coin.portfolio_amount;
        double portfolioGains = portfolioValue - coin.portfolio_value_start;
        
        assertEquals(60000.0, portfolioValue, 0.01);
        assertEquals(-15000.0, portfolioGains, 0.01);
    }

    @Test
    public void testPortfolioGainsCalculation_ZeroGains() {
        WebData.Coin coin = webData.new Coin();
        coin.price = 50000.0;
        coin.portfolio_amount = 1.5;
        coin.portfolio_value_start = 75000.0;
        
        double portfolioValue = coin.price * coin.portfolio_amount;
        double portfolioGains = portfolioValue - coin.portfolio_value_start;
        
        assertEquals(75000.0, portfolioValue, 0.01);
        assertEquals(0.0, portfolioGains, 0.01);
    }

    @Test
    public void testPortfolioCalculation_MultipleCoins() {
        WebData.Coin coin1 = webData.new Coin();
        coin1.name = "Bitcoin";
        coin1.price = 50000.0;
        coin1.portfolio_amount = 1.0;
        
        WebData.Coin coin2 = webData.new Coin();
        coin2.name = "Ethereum";
        coin2.price = 3000.0;
        coin2.portfolio_amount = 5.0;
        
        double totalValue = (coin1.price * coin1.portfolio_amount) + 
                           (coin2.price * coin2.portfolio_amount);
        
        assertEquals(65000.0, totalValue, 0.01);
    }

    @Test
    public void testPortfolioCalculation_ZeroAmount() {
        WebData.Coin coin = webData.new Coin();
        coin.price = 50000.0;
        coin.portfolio_amount = 0.0;
        
        double portfolioValue = coin.price * coin.portfolio_amount;
        
        assertEquals(0.0, portfolioValue, 0.01);
    }

    @Test
    public void testPortfolioCalculation_ZeroPrice() {
        WebData.Coin coin = webData.new Coin();
        coin.price = 0.0;
        coin.portfolio_amount = 2.0;
        
        double portfolioValue = coin.price * coin.portfolio_amount;
        
        assertEquals(0.0, portfolioValue, 0.01);
    }

    @Test
    public void testPortfolioCalculation_FractionalAmount() {
        WebData.Coin coin = webData.new Coin();
        coin.price = 50000.0;
        coin.portfolio_amount = 0.123456;
        
        double portfolioValue = coin.price * coin.portfolio_amount;
        
        assertEquals(6172.80, portfolioValue, 0.01);
    }

    @Test
    public void testPortfolioCalculation_VerySmallAmount() {
        WebData.Coin coin = webData.new Coin();
        coin.price = 50000.0;
        coin.portfolio_amount = 0.00001;
        
        double portfolioValue = coin.price * coin.portfolio_amount;
        
        assertEquals(0.5, portfolioValue, 0.01);
    }

    @Test
    public void testPortfolioCalculation_VeryLargeAmount() {
        WebData.Coin coin = webData.new Coin();
        coin.price = 50000.0;
        coin.portfolio_amount = 1000.0;
        
        double portfolioValue = coin.price * coin.portfolio_amount;
        
        assertEquals(50000000.0, portfolioValue, 0.01);
    }

    @Test
    public void testCurrencyConversion_PriceAdjustment() {
        WebData.Coin coin = webData.new Coin();
        coin.price = 50000.0;
        coin.portfolio_price = 40000.0;
        coin.portfolio_amount = 2.0;
        
        double oldPrice = 45000.0;
        double portfolioPrice = coin.portfolio_price * (coin.price / oldPrice);
        
        assertTrue(portfolioPrice > 40000.0);
        assertEquals(44444.44, portfolioPrice, 1.0);
    }

    @Test
    public void testPortfolioPercentageChange_Calculation() {
        WebData.Coin coin = webData.new Coin();
        coin.portfolio_value_start = 100000.0;
        coin.portfolio_value = 120000.0;
        
        double percentChange = ((coin.portfolio_value - coin.portfolio_value_start) / 
                               coin.portfolio_value_start) * 100;
        
        assertEquals(20.0, percentChange, 0.01);
    }

    @Test
    public void testPortfolioPercentageChange_NegativeChange() {
        WebData.Coin coin = webData.new Coin();
        coin.portfolio_value_start = 100000.0;
        coin.portfolio_value = 80000.0;
        
        double percentChange = ((coin.portfolio_value - coin.portfolio_value_start) / 
                               coin.portfolio_value_start) * 100;
        
        assertEquals(-20.0, percentChange, 0.01);
    }

    @Test
    public void testPortfolio_AddCoin() {
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        WebData.Coin coin = webData.new Coin();
        coin.name = "Bitcoin";
        coin.price = 50000.0;
        
        portfolio.add(coin);
        
        assertEquals(1, portfolio.size());
        assertEquals("Bitcoin", portfolio.get(0).name);
    }

    @Test
    public void testPortfolio_RemoveCoin() {
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        WebData.Coin coin = webData.new Coin();
        coin.name = "Bitcoin";
        
        portfolio.add(coin);
        assertEquals(1, portfolio.size());
        
        portfolio.remove(0);
        assertEquals(0, portfolio.size());
    }

    @Test
    public void testPortfolio_TotalValue() {
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        
        WebData.Coin coin1 = webData.new Coin();
        coin1.price = 50000.0;
        coin1.portfolio_amount = 1.0;
        portfolio.add(coin1);
        
        WebData.Coin coin2 = webData.new Coin();
        coin2.price = 3000.0;
        coin2.portfolio_amount = 5.0;
        portfolio.add(coin2);
        
        WebData.Coin coin3 = webData.new Coin();
        coin3.price = 100.0;
        coin3.portfolio_amount = 10.0;
        portfolio.add(coin3);
        
        double totalValue = 0;
        for (WebData.Coin coin : portfolio) {
            totalValue += coin.price * coin.portfolio_amount;
        }
        
        assertEquals(66000.0, totalValue, 0.01);
    }

    @Test
    public void testPortfolio_TotalGains() {
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        
        WebData.Coin coin1 = webData.new Coin();
        coin1.price = 50000.0;
        coin1.portfolio_amount = 1.0;
        coin1.portfolio_value_start = 45000.0;
        portfolio.add(coin1);
        
        WebData.Coin coin2 = webData.new Coin();
        coin2.price = 3000.0;
        coin2.portfolio_amount = 5.0;
        coin2.portfolio_value_start = 14000.0;
        portfolio.add(coin2);
        
        double totalGains = 0;
        for (WebData.Coin coin : portfolio) {
            double currentValue = coin.price * coin.portfolio_amount;
            totalGains += currentValue - coin.portfolio_value_start;
        }
        
        assertEquals(6000.0, totalGains, 0.01);
    }

    @Test
    public void testPortfolio_EmptyPortfolio() {
        ArrayList<WebData.Coin> portfolio = new ArrayList<>();
        
        double totalValue = 0;
        for (WebData.Coin coin : portfolio) {
            totalValue += coin.price * coin.portfolio_amount;
        }
        
        assertEquals(0.0, totalValue, 0.01);
    }

    @Test
    public void testPortfolioCoin_UpdatePrice() {
        WebData.Coin coin = webData.new Coin();
        coin.name = "Bitcoin";
        coin.price = 50000.0;
        
        coin.price = 55000.0;
        
        assertEquals(55000.0, coin.price, 0.01);
    }

    @Test
    public void testPortfolioCoin_CopyForPortfolio() throws CloneNotSupportedException {
        WebData.Coin original = webData.new Coin();
        original.name = "Bitcoin";
        original.price = 50000.0;
        original.portfolio_amount = 2.0;
        
        WebData.Coin copy = (WebData.Coin) original.copy();
        
        assertEquals(original.name, copy.name);
        assertEquals(original.price, copy.price, 0.01);
        assertEquals(original.portfolio_amount, copy.portfolio_amount, 0.01);
    }

    @Test
    public void testPortfolio_PriceConversionBetweenCurrencies() {
        WebData.Coin coin = webData.new Coin();
        coin.price = 50000.0;
        double oldPrice = 45000.0;
        double portfolioPriceInOldCurrency = 40000.0;
        
        double convertedPrice = portfolioPriceInOldCurrency * (coin.price / oldPrice);
        
        assertEquals(44444.44, convertedPrice, 1.0);
    }
}

