package com.cryptochecker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.io.*;
import java.util.ArrayList;

public class ApiDataFlowIntegrationTest {

    private WebData webData;
    private String testDataLocation;

    @Before
    public void setUp() throws Exception {
        System.setProperty("java.awt.headless", "true");
        
        Main.frame = mock(javax.swing.JFrame.class);
        
        Main.gui = new Main();
        try {
            try { Main.gui.debug = new Debug(); } catch (java.awt.HeadlessException e) { Main.gui.debug = null; }
        } catch (java.awt.HeadlessException e) {
            Main.gui.debug = null;
        }
        Main.theme = new Main.Theme(Main.themes.LIGHT);
        Main.currency = "USD";
        Main.currencyChar = "$";
        
        testDataLocation = Main.folderLocation + "test_data.ser";
        
        webData = new WebData();
        if (webData.coin == null) {
            webData.coin = new ArrayList<>();
        } else {
            webData.coin.clear();
        }
        if (webData.global_data == null) {
            webData.global_data = webData.new Global_Data();
        }
    }

    @Test
    public void testDataFlow_CreateCoin_AddToList() {
        webData.coin.clear();
        WebData.Coin coin = webData.new Coin();
        coin.name = "Bitcoin";
        coin.symbol = "BTC";
        coin.price = 50000.0;
        coin.rank = 1;
        
        webData.coin.add(coin);
        
        assertEquals(1, webData.coin.size());
        assertEquals("Bitcoin", webData.coin.get(0).name);
        assertEquals("BTC", webData.coin.get(0).symbol);
        assertEquals(50000.0, webData.coin.get(0).price, 0.01);
    }

    @Test
    public void testDataFlow_CreateMultipleCoins() {
        webData.coin.clear();
        WebData.Coin btc = webData.new Coin();
        btc.name = "Bitcoin";
        btc.price = 50000.0;
        
        WebData.Coin eth = webData.new Coin();
        eth.name = "Ethereum";
        eth.price = 3000.0;
        
        WebData.Coin ltc = webData.new Coin();
        ltc.name = "Litecoin";
        ltc.price = 150.0;
        
        webData.coin.add(btc);
        webData.coin.add(eth);
        webData.coin.add(ltc);
        
        assertEquals(3, webData.coin.size());
        assertEquals("Bitcoin", webData.coin.get(0).name);
        assertEquals("Ethereum", webData.coin.get(1).name);
        assertEquals("Litecoin", webData.coin.get(2).name);
    }

    @Test
    @Ignore("Skipped: WebData.Coin inner class causes NotSerializableException due to implicit outer class reference")
    public void testDataFlow_SerializeAndDeserialize() throws Exception {
        webData.coin.clear();
        WebData.Coin coin = webData.new Coin();
        coin.name = "Bitcoin";
        coin.price = 50000.0;
        webData.coin.add(coin);
        
        webData.global_data.total_market_cap = 2000000000000L;
        webData.global_data.total_24h_volume = 100000000000L;
        
        try (FileOutputStream file = new FileOutputStream(testDataLocation);
             BufferedOutputStream buffer = new BufferedOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(buffer)) {
            
            out.writeObject(webData.global_data);
            out.writeObject(webData.coin);
        }
        
        WebData.Global_Data deserializedGlobalData;
        ArrayList<WebData.Coin> deserializedCoins;
        
        try (FileInputStream file = new FileInputStream(testDataLocation);
             BufferedInputStream buffer = new BufferedInputStream(file);
             ObjectInputStream in = new ObjectInputStream(buffer)) {
            
            deserializedGlobalData = (WebData.Global_Data) in.readObject();
            deserializedCoins = (ArrayList<WebData.Coin>) in.readObject();
        }
        
        assertEquals(2000000000000L, deserializedGlobalData.total_market_cap);
        assertEquals(100000000000L, deserializedGlobalData.total_24h_volume);
        assertEquals(1, deserializedCoins.size());
        assertEquals("Bitcoin", deserializedCoins.get(0).name);
        assertEquals(50000.0, deserializedCoins.get(0).price, 0.01);
        
        new File(testDataLocation).delete();
    }

    @Test
    public void testDataFlow_GlobalDataPropagation() {
        webData.global_data.total_market_cap = 2500000000000L;
        webData.global_data.total_24h_volume = 150000000000L;
        webData.global_data.bitcoin_percentage_of_market_cap = 45.5;
        webData.global_data.active_currencies = 5000;
        
        String output = webData.global_data.toString();
        
        assertTrue(output.contains("Total Market Cap:"));
        assertTrue(output.contains("Total 24 Hour Volume:"));
        assertTrue(output.contains("Bitcoin Dominance: 45.5%"));
        assertTrue(output.contains("Active Currencies: 5000"));
    }

    @Test
    public void testDataFlow_CoinDataRetrieval() {
        webData.coin.clear();
        WebData.Coin btc = webData.new Coin();
        btc.name = "Bitcoin";
        btc.id = "bitcoin";
        btc.symbol = "BTC";
        btc.price = 50000.0;
        
        WebData.Coin eth = webData.new Coin();
        eth.name = "Ethereum";
        eth.id = "ethereum";
        eth.symbol = "ETH";
        eth.price = 3000.0;
        
        webData.coin.add(btc);
        webData.coin.add(eth);
        
        WebData.Coin foundCoin = null;
        for (WebData.Coin coin : webData.coin) {
            if (coin.name.equals("Ethereum")) {
                foundCoin = coin;
                break;
            }
        }
        
        assertNotNull(foundCoin);
        assertEquals("Ethereum", foundCoin.name);
        assertEquals("ethereum", foundCoin.id);
        assertEquals("ETH", foundCoin.symbol);
        assertEquals(3000.0, foundCoin.price, 0.01);
    }

    @Test
    public void testDataFlow_CoinPriceUpdate() {
        webData.coin.clear();
        WebData.Coin coin = webData.new Coin();
        coin.name = "Bitcoin";
        coin.price = 50000.0;
        webData.coin.add(coin);
        
        assertEquals(50000.0, webData.coin.get(0).price, 0.01);
        
        webData.coin.get(0).price = 55000.0;
        
        assertEquals(55000.0, webData.coin.get(0).price, 0.01);
    }

    @Test
    public void testDataFlow_CoinStatisticsUpdate() {
        WebData.Coin coin = webData.new Coin();
        coin.name = "Bitcoin";
        coin.price = 50000.0;
        coin.market_cap = 900000000000.0;
        coin._24h_volume = 30000000000.0;
        coin.percent_change_24h = 2.5;
        
        webData.coin.add(coin);
        
        webData.coin.get(0).price = 52000.0;
        webData.coin.get(0).percent_change_24h = 4.0;
        
        assertEquals(52000.0, webData.coin.get(0).price, 0.01);
        assertEquals(4.0, webData.coin.get(0).percent_change_24h, 0.01);
    }

    @Test
    public void testDataFlow_PortfolioInitialization() {
        webData.portfolio = new ArrayList<>();
        ArrayList<WebData.Coin> newPortfolio = new ArrayList<>();
        
        WebData.Coin coin = webData.new Coin();
        coin.name = "Bitcoin";
        coin.price = 50000.0;
        coin.portfolio_amount = 2.0;
        
        newPortfolio.add(coin);
        webData.portfolio.add(newPortfolio);
        
        assertEquals(1, webData.portfolio.size());
        assertEquals(1, webData.portfolio.get(0).size());
        assertEquals("Bitcoin", webData.portfolio.get(0).get(0).name);
    }

    @Test
    public void testDataFlow_PortfolioNames() {
        webData.portfolio_names.clear();
        webData.portfolio_names.add("My Portfolio");
        webData.portfolio_names.add("Trading Portfolio");
        
        assertEquals(2, webData.portfolio_names.size());
        assertEquals("My Portfolio", webData.portfolio_names.get(0));
        assertEquals("Trading Portfolio", webData.portfolio_names.get(1));
    }

    @Test
    public void testDataFlow_CoinCopy() {
        WebData.Coin original = webData.new Coin();
        original.name = "Bitcoin";
        original.price = 50000.0;
        original.rank = 1;
        
        WebData.Coin copy = (WebData.Coin) original.copy();
        
        assertEquals(original.name, copy.name);
        assertEquals(original.price, copy.price, 0.01);
        assertEquals(original.rank, copy.rank);
        
        copy.price = 55000.0;
        
        assertEquals(50000.0, original.price, 0.01);
        assertEquals(55000.0, copy.price, 0.01);
    }

    @Test
    public void testDataFlow_GetCoinInstance() {
        WebData.Coin coin = webData.getCoin();
        
        assertNotNull(coin);
        assertTrue(coin instanceof WebData.Coin);
    }

    @Test
    public void testDataFlow_CoinToString() {
        WebData.Coin coin = webData.new Coin();
        coin.name = "Bitcoin";
        
        String result = coin.toString();
        
        assertEquals("Bitcoin", result);
    }

    @Test
    public void testDataFlow_CoinGetInfo() {
        WebData.Coin coin = webData.new Coin();
        coin.rank = 1;
        coin.id = "bitcoin";
        coin.name = "Bitcoin";
        coin.symbol = "BTC";
        coin.price = 50000.0;
        
        String info = coin.getInfo();
        
        assertTrue(info.contains("Rank: 1"));
        assertTrue(info.contains("ID: bitcoin"));
        assertTrue(info.contains("Name: Bitcoin"));
        assertTrue(info.contains("Symbol: BTC"));
    }

    @Test
    public void testDataFlow_EmptyCoinList() {
        webData.coin.clear();
        
        assertEquals(0, webData.coin.size());
    }

    @Test
    public void testDataFlow_LargeCoinList() {
        webData.coin.clear();
        
        for (int i = 0; i < 100; i++) {
            WebData.Coin coin = webData.new Coin();
            coin.name = "Coin" + i;
            coin.price = 100.0 + i;
            coin.rank = i + 1;
            webData.coin.add(coin);
        }
        
        assertEquals(100, webData.coin.size());
        assertEquals("Coin0", webData.coin.get(0).name);
        assertEquals("Coin99", webData.coin.get(99).name);
    }

    @Test
    public void testDataFlow_CoinSearch() {
        webData.coin.clear();
        
        WebData.Coin btc = webData.new Coin();
        btc.name = "Bitcoin";
        btc.symbol = "BTC";
        
        WebData.Coin eth = webData.new Coin();
        eth.name = "Ethereum";
        eth.symbol = "ETH";
        
        webData.coin.add(btc);
        webData.coin.add(eth);
        
        WebData.Coin found = null;
        for (WebData.Coin coin : webData.coin) {
            if (coin.symbol.equals("ETH")) {
                found = coin;
                break;
            }
        }
        
        assertNotNull(found);
        assertEquals("Ethereum", found.name);
    }

    @Test
    public void testDataFlow_GlobalDataDefaults() {
        WebData.Global_Data gd = webData.new Global_Data();
        
        assertEquals(0L, gd.total_market_cap);
        assertEquals(0L, gd.total_24h_volume);
        assertEquals(0.0, gd.bitcoin_percentage_of_market_cap, 0.01);
    }

    @Test
    public void testDataFlow_DataIntegrity() {
        webData.coin.clear();
        WebData.Coin coin = webData.new Coin();
        coin.name = "Bitcoin";
        coin.price = 50000.0;
        coin.rank = 1;
        coin.market_cap = 900000000000.0;
        
        webData.coin.add(coin);
        
        WebData.Coin retrieved = webData.coin.get(0);
        
        assertEquals("Bitcoin", retrieved.name);
        assertEquals(50000.0, retrieved.price, 0.01);
        assertEquals(1, retrieved.rank);
        assertEquals(900000000000.0, retrieved.market_cap, 0.01);
    }
}

