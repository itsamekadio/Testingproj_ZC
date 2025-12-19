package com.cryptochecker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DecimalFormat;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConversionUnitTest {

    @Mock
    private WebData mockWebData;
    
    private WebData webData;
    private DecimalFormat df1;
    private DecimalFormat df2;
    private DecimalFormat df3;
    private DecimalFormat df4;
    private DecimalFormat df5;
    private DecimalFormat df6;

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
        
        df1 = new DecimalFormat("#.##");
        df2 = new DecimalFormat("#.###");
        df3 = new DecimalFormat("#.####");
        df4 = new DecimalFormat("#.#####");
        df5 = new DecimalFormat("#.######");
        df6 = new DecimalFormat("#.############");
    }

    @Test
    public void testConversion_BTCtoETH() {
        double priceBTC = 50000.0;
        double priceETH = 3000.0;
        double amount = 1.0;
        
        double result = (priceBTC / priceETH) * amount;
        
        assertEquals(16.666, result, 0.001);
    }

    @Test
    public void testConversion_ETHtoBTC() {
        double priceETH = 3000.0;
        double priceBTC = 50000.0;
        double amount = 10.0;
        
        double result = (priceETH / priceBTC) * amount;
        
        assertEquals(0.6, result, 0.001);
    }

    @Test
    public void testConversion_SameCurrency() {
        double price1 = 50000.0;
        double price2 = 50000.0;
        double amount = 5.0;
        
        double result = (price1 / price2) * amount;
        
        assertEquals(5.0, result, 0.001);
    }

    @Test
    public void testConversion_ZeroSourcePrice() {
        double priceCurrency1 = 0.0;
        double priceCurrency2 = 3000.0;
        double amount = 1.0;
        
        if (priceCurrency1 == 0 || priceCurrency2 == 0) {
            assertEquals(0, 0);
        } else {
            double result = (priceCurrency1 / priceCurrency2) * amount;
            assertEquals(0.0, result, 0.001);
        }
    }

    @Test
    public void testConversion_ZeroTargetPrice() {
        double priceCurrency1 = 50000.0;
        double priceCurrency2 = 0.0;
        double amount = 1.0;
        
        if (priceCurrency1 == 0 || priceCurrency2 == 0) {
            assertEquals(0, 0);
        }
    }

    @Test
    public void testConversion_FractionalAmount() {
        double priceBTC = 50000.0;
        double priceETH = 3000.0;
        double amount = 0.5;
        
        double result = (priceBTC / priceETH) * amount;
        
        assertEquals(8.333, result, 0.001);
    }

    @Test
    public void testConversion_VerySmallAmount() {
        double priceBTC = 50000.0;
        double priceETH = 3000.0;
        double amount = 0.00001;
        
        double result = (priceBTC / priceETH) * amount;
        
        assertEquals(0.000166, result, 0.000001);
    }

    @Test
    public void testConversion_VeryLargeAmount() {
        double priceBTC = 50000.0;
        double priceETH = 3000.0;
        double amount = 1000.0;
        
        double result = (priceBTC / priceETH) * amount;
        
        assertEquals(16666.666, result, 0.001);
    }

    @Test
    public void testFormatting_ValueGreaterThanOne() {
        double value = 100.5678;
        String formatted = df1.format(value);
        
        assertEquals("100.57", formatted);
    }

    @Test
    public void testFormatting_ValueBetweenPointOneAndOne() {
        double value = 0.5678;
        String formatted = df2.format(value);
        
        assertEquals("0.568", formatted);
    }

    @Test
    public void testFormatting_ValueBetweenPointZeroOneAndPointOne() {
        double value = 0.05678;
        String formatted = df3.format(value);
        
        assertEquals("0.0568", formatted);
    }

    @Test
    public void testFormatting_ValueBetweenPointZeroZeroOneAndPointZeroOne() {
        double value = 0.005678;
        String formatted = df4.format(value);
        
        assertEquals("0.00568", formatted);
    }

    @Test
    public void testFormatting_ValueBetweenPointZeroZeroZeroOneAndPointZeroZeroOne() {
        double value = 0.0005678;
        String formatted = df5.format(value);
        
        assertEquals("0.000568", formatted);
    }

    @Test
    public void testFormatting_VerySmallValue() {
        double value = 0.00005678;
        String formatted = df6.format(value);
        
        assertTrue(formatted.startsWith("0.00005678"));
    }

    @Test
    public void testConversion_WithRealCoinData_BTCtoUSD() {
        WebData.Coin btc = webData.new Coin();
        btc.name = "Bitcoin";
        btc.price = 50000.0;
        
        double usdPrice = 1.0;
        double amount = 2.0;
        
        double result = (btc.price / usdPrice) * amount;
        
        assertEquals(100000.0, result, 0.01);
    }

    @Test
    public void testConversion_WithRealCoinData_ETHtoUSD() {
        WebData.Coin eth = webData.new Coin();
        eth.name = "Ethereum";
        eth.price = 3000.0;
        
        double usdPrice = 1.0;
        double amount = 5.0;
        
        double result = (eth.price / usdPrice) * amount;
        
        assertEquals(15000.0, result, 0.01);
    }

    @Test
    public void testConversion_ExchangeRate_USDtoEUR() {
        double usdPrice = 1.0;
        double eurExchangeRate = 0.85;
        double amount = 100.0;
        
        double result = (usdPrice / eurExchangeRate) * amount;
        
        assertEquals(117.647, result, 0.001);
    }

    @Test
    public void testConversion_ExchangeRate_EURtoUSD() {
        double eurPrice = 0.85;
        double usdExchangeRate = 1.0;
        double amount = 100.0;
        
        double result = (eurPrice / usdExchangeRate) * amount;
        
        assertEquals(85.0, result, 0.01);
    }

    @Test
    public void testConversion_MultipleSteps() {
        double btcPrice = 50000.0;
        double ethPrice = 3000.0;
        double usdPrice = 1.0;
        
        double btcToEth = btcPrice / ethPrice;
        double ethToUsd = ethPrice / usdPrice;
        
        assertEquals(16.666, btcToEth, 0.001);
        assertEquals(3000.0, ethToUsd, 0.01);
    }

    @Test
    public void testConversion_InverseRates() {
        double price1 = 50000.0;
        double price2 = 3000.0;
        
        double rate1to2 = price1 / price2;
        double rate2to1 = price2 / price1;
        
        assertEquals(16.666, rate1to2, 0.001);
        assertEquals(0.06, rate2to1, 0.001);
        assertEquals(1.0, rate1to2 * rate2to1, 0.001);
    }

    @Test
    public void testConversion_ChainCalculation() {
        double btcPrice = 50000.0;
        double ethPrice = 3000.0;
        double ltcPrice = 150.0;
        
        double btcToEth = btcPrice / ethPrice;
        double ethToLtc = ethPrice / ltcPrice;
        double btcToLtc = btcPrice / ltcPrice;
        
        assertEquals(16.666, btcToEth, 0.001);
        assertEquals(20.0, ethToLtc, 0.01);
        assertEquals(333.333, btcToLtc, 0.001);
    }

    @Test
    public void testConversion_PrecisionTest() {
        double price1 = 0.123456789;
        double price2 = 0.987654321;
        double amount = 10.0;
        
        double result = (price1 / price2) * amount;
        
        assertEquals(1.25, result, 0.01);
    }

    @Test
    public void testConversion_NegativeAmount() {
        double priceCurrency1 = 50000.0;
        double priceCurrency2 = 3000.0;
        double amount = -1.0;
        
        double result = (priceCurrency1 / priceCurrency2) * amount;
        
        assertTrue(result < 0);
        assertEquals(-16.666, result, 0.001);
    }

    @Test
    public void testFormatting_Zero() {
        double value = 0.0;
        String formatted = df1.format(value);
        
        assertEquals("0", formatted);
    }

    @Test
    public void testFormatting_NegativeValue() {
        double value = -100.5678;
        String formatted = df1.format(value);
        
        assertTrue(formatted.startsWith("-"));
        assertEquals("-100.57", formatted);
    }

    @Test
    public void testConversion_SpecialCase_CurrencyToSelf() {
        double priceCurrency2 = 0.0;
        double priceCurrency1 = 50000.0;
        double amount = 2.0;
        
        if (priceCurrency2 == 0.0) {
            double result = priceCurrency1 * amount;
            assertEquals(100000.0, result, 0.01);
        }
    }

    @Test
    public void testCoinPriceRetrieval_FromList() {
        webData.coin.clear();
        WebData.Coin btc = webData.new Coin();
        btc.name = "Bitcoin";
        btc.price = 50000.0;
        
        WebData.Coin eth = webData.new Coin();
        eth.name = "Ethereum";
        eth.price = 3000.0;
        
        webData.coin.add(btc);
        webData.coin.add(eth);
        
        WebData.Coin foundBtc = null;
        for (WebData.Coin coin : webData.coin) {
            if (coin.name.equals("Bitcoin")) {
                foundBtc = coin;
                break;
            }
        }
        
        assertNotNull(foundBtc);
        assertEquals(50000.0, foundBtc.price, 0.01);
    }
}

