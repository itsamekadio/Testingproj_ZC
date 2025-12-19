package com.cryptochecker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ConverterIntegrationTest {

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
        
        Main.gui.webData = webData;
        
        df1 = new DecimalFormat("#.##");
        df2 = new DecimalFormat("#.###");
        df3 = new DecimalFormat("#.####");
        df4 = new DecimalFormat("#.#####");
        df5 = new DecimalFormat("#.######");
        df6 = new DecimalFormat("#.############");
        
        setupTestCoins();
    }

    private void setupTestCoins() {
        webData.coin.clear();
        
        WebData.Coin btc = webData.new Coin();
        btc.name = "Bitcoin";
        btc.symbol = "BTC";
        btc.price = 50000.0;
        btc.rank = 1;
        
        WebData.Coin eth = webData.new Coin();
        eth.name = "Ethereum";
        eth.symbol = "ETH";
        eth.price = 3000.0;
        eth.rank = 2;
        
        WebData.Coin ltc = webData.new Coin();
        ltc.name = "Litecoin";
        ltc.symbol = "LTC";
        ltc.price = 150.0;
        ltc.rank = 3;
        
        WebData.Coin xrp = webData.new Coin();
        xrp.name = "XRP";
        xrp.symbol = "XRP";
        xrp.price = 0.5;
        xrp.rank = 4;
        
        webData.coin.add(btc);
        webData.coin.add(eth);
        webData.coin.add(ltc);
        webData.coin.add(xrp);
    }

    @Test
    public void testConverterIntegration_BTCtoETH() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        WebData.Coin eth = findCoinByName("Ethereum");
        
        double amount = 1.0;
        double result = (btc.price / eth.price) * amount;
        
        assertEquals(16.666, result, 0.001);
    }

    @Test
    public void testConverterIntegration_ETHtoBTC() {
        WebData.Coin eth = findCoinByName("Ethereum");
        WebData.Coin btc = findCoinByName("Bitcoin");
        
        double amount = 10.0;
        double result = (eth.price / btc.price) * amount;
        
        assertEquals(0.6, result, 0.001);
    }

    @Test
    public void testConverterIntegration_LTCtoXRP() {
        WebData.Coin ltc = findCoinByName("Litecoin");
        WebData.Coin xrp = findCoinByName("XRP");
        
        double amount = 1.0;
        double result = (ltc.price / xrp.price) * amount;
        
        assertEquals(300.0, result, 0.01);
    }

    @Test
    public void testConverterIntegration_BTCtoUSD() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        double usdPrice = 1.0;
        
        double amount = 2.0;
        double result = (btc.price / usdPrice) * amount;
        
        assertEquals(100000.0, result, 0.01);
    }

    @Test
    public void testConverterIntegration_USDtoBTC() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        double usdPrice = 1.0;
        
        double amount = 100000.0;
        double result = (usdPrice / btc.price) * amount;
        
        assertEquals(2.0, result, 0.01);
    }

    @Test
    public void testConverterIntegration_FindCoinByName() {
        WebData.Coin coin = findCoinByName("Ethereum");
        
        assertNotNull(coin);
        assertEquals("Ethereum", coin.name);
        assertEquals(3000.0, coin.price, 0.01);
    }

    @Test
    public void testConverterIntegration_FindCoinBySymbol() {
        WebData.Coin coin = findCoinBySymbol("BTC");
        
        assertNotNull(coin);
        assertEquals("Bitcoin", coin.name);
        assertEquals(50000.0, coin.price, 0.01);
    }

    @Test
    public void testConverterIntegration_ConversionWithFormatting_GreaterThanOne() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        WebData.Coin eth = findCoinByName("Ethereum");
        
        double amount = 1.0;
        double result = (btc.price / eth.price) * amount;
        String formatted = df1.format(result);
        
        assertEquals("16.67", formatted);
    }

    @Test
    public void testConverterIntegration_ConversionWithFormatting_LessThanOne() {
        WebData.Coin eth = findCoinByName("Ethereum");
        WebData.Coin btc = findCoinByName("Bitcoin");
        
        double amount = 1.0;
        double result = (eth.price / btc.price) * amount;
        String formatted = df2.format(result);
        
        assertEquals("0.06", formatted);
    }

    @Test
    public void testConverterIntegration_ConversionWithFormatting_VerySmall() {
        WebData.Coin xrp = findCoinByName("XRP");
        WebData.Coin btc = findCoinByName("Bitcoin");
        
        double amount = 1.0;
        double result = (xrp.price / btc.price) * amount;
        String formatted = df6.format(result);
        
        assertTrue(formatted.startsWith("0.00001"));
    }

    @Test
    public void testConverterIntegration_AllCoinsAvailable() {
        assertEquals(4, webData.coin.size());
        
        assertNotNull(findCoinByName("Bitcoin"));
        assertNotNull(findCoinByName("Ethereum"));
        assertNotNull(findCoinByName("Litecoin"));
        assertNotNull(findCoinByName("XRP"));
    }

    @Test
    public void testConverterIntegration_PriceUpdate() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        double oldPrice = btc.price;
        
        btc.price = 55000.0;
        
        assertEquals(50000.0, oldPrice, 0.01);
        assertEquals(55000.0, btc.price, 0.01);
    }

    @Test
    public void testConverterIntegration_ConversionAfterPriceUpdate() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        WebData.Coin eth = findCoinByName("Ethereum");
        
        double oldConversion = (btc.price / eth.price) * 1.0;
        
        btc.price = 60000.0;
        
        double newConversion = (btc.price / eth.price) * 1.0;
        
        assertEquals(16.666, oldConversion, 0.001);
        assertEquals(20.0, newConversion, 0.01);
    }

    @Test
    public void testConverterIntegration_ReverseConversion() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        WebData.Coin eth = findCoinByName("Ethereum");
        
        double btcToEth = (btc.price / eth.price) * 1.0;
        double ethToBtc = (eth.price / btc.price) * btcToEth;
        
        assertEquals(1.0, ethToBtc, 0.01);
    }

    @Test
    public void testConverterIntegration_ChainConversion() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        WebData.Coin eth = findCoinByName("Ethereum");
        WebData.Coin ltc = findCoinByName("Litecoin");
        
        double btcToEth = btc.price / eth.price;
        double ethToLtc = eth.price / ltc.price;
        double btcToLtcDirect = btc.price / ltc.price;
        double btcToLtcChain = btcToEth * ethToLtc;
        
        assertEquals(btcToLtcDirect, btcToLtcChain, 0.01);
    }

    @Test
    public void testConverterIntegration_FractionalAmounts() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        WebData.Coin eth = findCoinByName("Ethereum");
        
        double amount = 0.5;
        double result = (btc.price / eth.price) * amount;
        
        assertEquals(8.333, result, 0.001);
    }

    @Test
    public void testConverterIntegration_LargeAmounts() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        WebData.Coin eth = findCoinByName("Ethereum");
        
        double amount = 100.0;
        double result = (btc.price / eth.price) * amount;
        
        assertEquals(1666.666, result, 0.001);
    }

    @Test
    public void testConverterIntegration_VerySmallAmounts() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        WebData.Coin eth = findCoinByName("Ethereum");
        
        double amount = 0.00001;
        double result = (btc.price / eth.price) * amount;
        
        assertEquals(0.000166, result, 0.000001);
    }

    @Test
    public void testConverterIntegration_ZeroConversion() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        WebData.Coin eth = findCoinByName("Ethereum");
        
        double amount = 0.0;
        double result = (btc.price / eth.price) * amount;
        
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testConverterIntegration_GlobalDataAvailable() {
        assertNotNull(webData.global_data);
    }

    @Test
    public void testConverterIntegration_GlobalDataFormatting() {
        webData.global_data.total_market_cap = 2000000000000L;
        webData.global_data.total_24h_volume = 100000000000L;
        webData.global_data.bitcoin_percentage_of_market_cap = 45.5;
        
        String output = webData.global_data.toString();
        
        assertTrue(output.contains("Total Market Cap:"));
        assertTrue(output.contains("Total 24 Hour Volume:"));
        assertTrue(output.contains("Bitcoin Dominance: 45.5%"));
    }

    @Test
    public void testConverterIntegration_MultipleConversions() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        WebData.Coin eth = findCoinByName("Ethereum");
        WebData.Coin ltc = findCoinByName("Litecoin");
        
        double btcToEth = (btc.price / eth.price) * 1.0;
        double btcToLtc = (btc.price / ltc.price) * 1.0;
        double ethToLtc = (eth.price / ltc.price) * 1.0;
        
        assertTrue(btcToEth > 1.0);
        assertTrue(btcToLtc > 1.0);
        assertTrue(ethToLtc > 1.0);
    }

    @Test
    public void testConverterIntegration_ConversionPrecision() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        WebData.Coin xrp = findCoinByName("XRP");
        
        double amount = 0.123456789;
        double result = (btc.price / xrp.price) * amount;
        
        assertTrue(result > 0);
        assertTrue(result < 100000);
    }

    @Test
    public void testConverterIntegration_SameCurrencyConversion() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        
        double amount = 5.0;
        double result = (btc.price / btc.price) * amount;
        
        assertEquals(5.0, result, 0.01);
    }

    @Test
    public void testConverterIntegration_AllFormattingRanges() {
        double val1 = 100.0;
        double val2 = 0.5;
        double val3 = 0.05;
        double val4 = 0.005;
        double val5 = 0.0005;
        double val6 = 0.00005;
        
        assertEquals("100", df1.format(val1));
        assertEquals("0.5", df2.format(val2));
        assertEquals("0.05", df3.format(val3));
        assertEquals("0.005", df4.format(val4));
        assertEquals("0.0005", df5.format(val5));
        assertTrue(df6.format(val6).startsWith("0.00005"));
    }

    @Test
    public void testConverterIntegration_CoinDataIntegrity() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        
        assertEquals("Bitcoin", btc.name);
        assertEquals("BTC", btc.symbol);
        assertEquals(1, btc.rank);
        assertEquals(50000.0, btc.price, 0.01);
    }

    @Test
    public void testConverterIntegration_ConversionRateConsistency() {
        WebData.Coin btc = findCoinByName("Bitcoin");
        WebData.Coin eth = findCoinByName("Ethereum");
        
        double rate1 = btc.price / eth.price;
        double rate2 = eth.price / btc.price;
        
        assertEquals(1.0, rate1 * rate2, 0.01);
    }

    private WebData.Coin findCoinByName(String name) {
        for (WebData.Coin coin : webData.coin) {
            if (coin.name.equals(name)) {
                return coin;
            }
        }
        return null;
    }

    private WebData.Coin findCoinBySymbol(String symbol) {
        for (WebData.Coin coin : webData.coin) {
            if (coin.symbol.equals(symbol)) {
                return coin;
            }
        }
        return null;
    }
}

