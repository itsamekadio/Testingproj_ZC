$tests=@(
    'com.cryptochecker.CurrencyConversionBlackBoxTest#testCalculateCurrency_EP_CryptoToFiat',
    'com.cryptochecker.CurrencyConversionBlackBoxTest#testCalculateCurrency_EP_CryptoToCrypto',
    'com.cryptochecker.CurrencyConversionBlackBoxTest#testCalculateCurrency_BVA_MinimumPositiveInput',
    'com.cryptochecker.DataValidationBlackBoxTest#testValidateAmount_EP_ValidPositiveNumber',
    'com.cryptochecker.DataValidationBlackBoxTest#testValidateAmount_EP_InvalidNonNumeric',
    'com.cryptochecker.DataValidationBlackBoxTest#testValidateAmount_BVA_ZeroValue',
    'com.cryptochecker.DataValidationBlackBoxTest#testValidateAmount_EP_InvalidNegativeValue',
    'com.cryptochecker.DataValidationBlackBoxTest#testValidateAmount_BVA_VeryLargeNumber',
    'com.cryptochecker.PortfolioCalculationBlackBoxTest#testCalculatePortfolio_EP_ValidPositiveAmount',
    'com.cryptochecker.PortfolioCalculationBlackBoxTest#testCalculatePortfolio_BVA_ZeroAmount',
    'com.cryptochecker.PortfolioCalculationBlackBoxTest#testCalculatePortfolio_EP_InvalidNegativeAmount',
    'com.cryptochecker.PortfolioCalculationBlackBoxTest#testCalculatePortfolio_BVA_MinimumPositiveAmount',
    'com.cryptochecker.PortfolioCalculationBlackBoxTest#testCalculatePortfolio_BVA_MaximumAmount'
);

$log = ".\evidence\individual_test_runs.txt";

foreach ($test in $tests) {

    Add-Content -Path $log -Value ("==== Running $test ==== " + (Get-Date -Format "u"));

    $temp = "tmp_$($test.GetHashCode()).log";

    mvn "-Dtest=$test" test 2>&1 | Out-File $temp;

    Get-Content $temp | Add-Content -Path $log;

    Remove-Item $temp -ErrorAction SilentlyContinue;

    Add-Content -Path $log -Value "";
}
