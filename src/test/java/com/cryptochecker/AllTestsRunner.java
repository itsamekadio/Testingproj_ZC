package com.cryptochecker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Master Test Suite Runner
 * Runs ALL tests (White Box + Black Box) in the crypto-checker project
 * 
 * Total Tests: 200+
 * - White Box: 190+
 * - Black Box: 13
 * 
 * Usage:
 * - Right-click this file in IntelliJ
 * - Select "Run 'AllTestsRunner' with Coverage"
 * - View coverage report at target/site/jacoco/index.html
 * 
 * Expected Coverage: 60-70% instruction, 50-60% branch
 * 
 * Note: MenuWhiteBoxTest excluded due to complex UI dependencies
 */
@RunWith(Suite.class)
@SuiteClasses({
        // ========== WHITE BOX TESTS ==========
        MainWhiteBoxTest.class,
        WebDataWhiteBoxTest.class,
        MenuWhiteBoxTest.class,
        CoinDataWhiteBoxTest.class,

        PanelConverterWhiteBoxTest.class,
        PanelCoinWhiteBoxTest.class,
        PanelSettingsWhiteBoxTest.class,
        // MenuWhiteBoxTest.class, // EXCLUDED - Complex UI dependencies cause
        // NullPointerException
        DebugWhiteBoxTest.class,
        ThemeManagementWhiteBoxTest.class,
        PortfolioCalculationWhiteBoxTest.class,
        DataValidationWhiteBoxTest.class,
        CurrencyConversionWhiteBoxTest.class,

        // ========== BLACK BOX TESTS ==========
        PortfolioCalculationBlackBoxTest.class,
        CurrencyConversionBlackBoxTest.class,
        DataValidationBlackBoxTest.class
})
public class AllTestsRunner {
    // This class remains empty'
    // It is used only as a holder for the @RunWith and @SuiteClasses annotations
}
