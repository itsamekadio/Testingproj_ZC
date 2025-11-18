# Crypto Checker - Test Planning & Test Design Documentation

## Table of Contents
1. [System Overview & Functional Requirements](#1-system-overview--functional-requirements)
2. [Test Objectives & Testing Strategy](#2-test-objectives--testing-strategy)
3. [Test Suites, Test Case Design, and Hierarchy](#3-test-suites-test-case-design-and-hierarchy)
4. [Requirements Traceability Matrix](#4-requirements-traceability-matrix)
5. [Black Box Test Implementation (JUnit)](#5-black-box-test-implementation-junit)
6. [Evidence (Screenshots, Logs, Execution Results)](#6-evidence-screenshots-logs-execution-results)

---

## 1. System Overview & Functional Requirements

### 1.1 System Overview

**Crypto Checker** is a cross-platform Java desktop application designed for tracking and managing cryptocurrency investments. The application provides users with real-time cryptocurrency data, portfolio management capabilities, currency conversion tools, and customizable user interface themes.

**Key Features:**
- Real-time cryptocurrency data fetching from CoinGecko API
- Multiple portfolio management
- Currency conversion between cryptocurrencies and fiat currencies
- Customizable themes (Light, Dark, Custom)
- Support for 30+ fiat currencies
- Global cryptocurrency market data display
- Data persistence through serialization

**Technology Stack:**
- **Language:** Java 8
- **Build Tool:** Maven 3.5.3
- **Dependencies:** Gson 2.8.2 (JSON parsing)
- **GUI Framework:** Java Swing
- **Data Source:** CoinGecko API (https://api.coingecko.com/api/v3/)

**Architecture:**
The application follows a modular architecture with separate panels for different functionalities:
- **Main.java:** Application entry point and core configuration
- **WebData.java:** Data fetching and management layer
- **PanelCoin.java:** Cryptocurrency listing and search
- **PanelPortfolio.java:** Portfolio management and tracking
- **PanelConverter.java:** Currency conversion functionality
- **PanelSettings.java:** Application settings and customization
- **Menu.java:** Navigation between panels
- **Debug.java:** Logging and debugging utilities

---

### 1.2 Functional Requirements

#### FR1: Cryptocurrency Data Display
- **FR1.1:** The system shall fetch and display cryptocurrency data from CoinGecko API
- **FR1.2:** The system shall display cryptocurrency rank, name, current price, percentage changes (1h, 24h, 7d), and market cap
- **FR1.3:** The system shall support searching/filtering cryptocurrencies by name
- **FR1.4:** The system shall allow sorting of cryptocurrency data by different columns
- **FR1.5:** The system shall display detailed information when a cryptocurrency is selected

#### FR2: Portfolio Management
- **FR2.1:** The system shall allow users to create multiple portfolios
- **FR2.2:** The system shall allow users to add cryptocurrencies to a portfolio with specified amounts
- **FR2.3:** The system shall allow users to set starting price (per piece, total value, or current value)
- **FR2.4:** The system shall calculate and display portfolio total value
- **FR2.5:** The system shall calculate and display portfolio gains/losses (absolute and percentage)
- **FR2.6:** The system shall allow users to remove cryptocurrencies from portfolio
- **FR2.7:** The system shall allow users to switch between multiple portfolios
- **FR2.8:** The system shall allow users to rename portfolios
- **FR2.9:** The system shall allow users to delete portfolios (minimum one portfolio must exist)
- **FR2.10:** The system shall prevent adding duplicate cryptocurrencies to the same portfolio

#### FR3: Currency Conversion
- **FR3.1:** The system shall allow conversion between two cryptocurrencies
- **FR3.2:** The system shall allow conversion from cryptocurrency to fiat currency
- **FR3.3:** The system shall display real-time conversion rates
- **FR3.4:** The system shall allow switching the source and target currencies
- **FR3.5:** The system shall display global market data (total market cap, 24h volume, Bitcoin dominance)

#### FR4: Settings and Customization
- **FR4.1:** The system shall support three theme modes: Light, Dark, and Custom
- **FR4.2:** The system shall allow users to customize colors for custom theme (background, foreground, positive/negative indicators, etc.)
- **FR4.3:** The system shall support 30+ fiat currencies (USD, EUR, GBP, SEK, AUD, etc.)
- **FR4.4:** The system shall allow users to toggle debug mode on/off
- **FR4.5:** The system shall allow users to view application logs
- **FR4.6:** The system shall allow users to reset settings to default
- **FR4.7:** The system shall allow users to delete stored data (coin data, portfolio data, settings, converter data, logs)

#### FR5: Data Persistence
- **FR5.1:** The system shall serialize and save cryptocurrency data to disk
- **FR5.2:** The system shall serialize and save portfolio data to disk
- **FR5.3:** The system shall serialize and save settings (theme, currency, debug mode) to disk
- **FR5.4:** The system shall serialize and save converter state to disk
- **FR5.5:** The system shall deserialize and load saved data on application startup
- **FR5.6:** The system shall handle missing or corrupted data files gracefully

#### FR6: Error Handling and Validation
- **FR6.1:** The system shall validate numeric input for portfolio amounts and prices
- **FR6.2:** The system shall handle API connection failures gracefully
- **FR6.3:** The system shall handle rate limiting (HTTP 429) from API
- **FR6.4:** The system shall display appropriate error messages to users
- **FR6.5:** The system shall prevent invalid operations (e.g., deleting last portfolio)

---

## 2. Test Objectives & Testing Strategy

### 2.1 Test Objectives

The primary objectives of testing the Crypto Checker application are:

1. **Functional Correctness:** Verify that all functional requirements are implemented correctly and the application behaves as specified
2. **Data Accuracy:** Ensure that cryptocurrency data, portfolio calculations, and currency conversions are mathematically correct
3. **Input Validation:** Validate that the system properly handles valid and invalid user inputs
4. **Error Handling:** Verify that the system gracefully handles error conditions (API failures, invalid data, edge cases)
5. **Data Persistence:** Confirm that serialization and deserialization of data work correctly
6. **Business Logic:** Test critical business logic such as portfolio value calculations, gain/loss computations, and currency conversions
7. **Boundary Conditions:** Test boundary values and edge cases to ensure robust behavior
8. **User Experience:** Validate that the application provides appropriate feedback and error messages

### 2.2 Testing Strategy

#### 2.2.1 Black Box Testing Approach

**Black Box Testing** is the primary testing strategy for this project. This approach tests the system's functionality without knowledge of its internal code structure, implementation details, or logic flow. Test cases are designed based on:

- **Functional Requirements:** Test cases derived from the functional requirements specification
- **Input-Output Behavior:** Testing based on inputs and expected outputs
- **Equivalence Partitioning:** Grouping inputs into equivalent classes that should produce similar outputs
- **Boundary Value Analysis:** Testing boundary conditions and edge cases
- **Error Guessing:** Testing based on experience and intuition about potential error conditions

**Why Black Box Testing?**
- Focuses on user perspective and functional behavior
- Tests the system as end-users would interact with it
- Independent of implementation, making tests resilient to code changes
- Ensures requirements are met from a functional standpoint
- Suitable for testing business logic, calculations, and data validation

#### 2.2.2 White Box Testing Approach

**White Box Testing** (also known as Structural Testing or Glass Box Testing) complements Black Box Testing by examining the internal structure, code logic, and implementation details of the application. This approach tests the system with knowledge of its internal code structure, control flow, and data flow.

**Why White Box Testing?**
- Ensures all code paths are executed and tested
- Identifies dead code, unreachable statements, and logical errors
- Validates internal logic, conditions, and branches
- Measures code coverage (statement, branch, condition, path)
- Helps achieve comprehensive test coverage
- Catches errors that black box testing might miss

**White Box Testing Coverage Criteria:**

**1. Statement Coverage (Line Coverage)**
- **Definition:** Every executable statement in the code is executed at least once
- **Goal:** 100% statement coverage ensures all lines of code are tested
- **Example:** In `calculatePortfolio()` method, ensure all calculation statements are executed
- **Coverage Formula:** (Executed Statements / Total Statements) × 100%

**2. Branch Coverage (Decision Coverage)**
- **Definition:** Every branch (true/false outcome) of every decision point is executed
- **Goal:** Test both true and false paths of all if/else, switch, and loop conditions
- **Example:** Test both paths in `if (gains >= 0)` - test when gains are positive and negative
- **Coverage Formula:** (Executed Branches / Total Branches) × 100%

**3. Condition Coverage (Predicate Coverage)**
- **Definition:** Every boolean condition in a decision is evaluated to both true and false
- **Goal:** Test all possible combinations of conditions in compound boolean expressions
- **Example:** For `if (price > 0 && amount > 0)`, test:
  - price > 0 = true, amount > 0 = true
  - price > 0 = true, amount > 0 = false
  - price > 0 = false, amount > 0 = true
  - price > 0 = false, amount > 0 = false

**4. Path Coverage**
- **Definition:** Every possible execution path through the code is tested
- **Goal:** Test all combinations of branches and conditions
- **Example:** In `calculateCurrency()` method with multiple if-else statements, test all possible paths
- **Challenge:** Can be exponential - use cyclomatic complexity to identify critical paths

**5. Loop Coverage**
- **Definition:** Test loops with zero iterations, one iteration, multiple iterations, and maximum iterations
- **Goal:** Ensure loops handle boundary conditions correctly
- **Example:** Test portfolio calculation loop with:
  - Empty portfolio (0 coins)
  - Single coin
  - Multiple coins
  - Large number of coins

**White Box Testing Techniques:**

**1. Control Flow Testing**
- Test all control structures (if-else, switch, loops, try-catch)
- Identify and test all decision points
- **Example:** Test all branches in `Main.Theme.update()` switch statement (LIGHT, DARK, CUSTOM cases)

**2. Data Flow Testing**
- Track variable definitions and uses
- Test def-use pairs (where variable is defined and where it's used)
- **Example:** Track `portfolio_value` from calculation to display

**3. Cyclomatic Complexity Analysis**
- Measure code complexity using McCabe's cyclomatic complexity
- V(G) = E - N + 2P (where E=edges, N=nodes, P=connected components)
- Higher complexity indicates more test cases needed
- **Example:** `calculatePortfolio()` method complexity analysis

**4. Code Coverage Tools**
- Use tools like JaCoCo, Cobertura, or IntelliJ Coverage to measure coverage
- Set coverage goals: Statement ≥ 80%, Branch ≥ 70%, Condition ≥ 60%

**White Box Test Examples:**

**Example 1: Statement Coverage - Portfolio Calculation**
```java
public void calculatePortfolio() {
    double value = 0.0;        // Statement 1
    double gains = 0.0;        // Statement 2
    for (int i = 0; i < portfolio.size(); ++i) {  // Statement 3
        value += portfolio.get(i).portfolio_value;   // Statement 4
        gains += portfolio.get(i).portfolio_gains; // Statement 5
    }
    // ... formatting statements
}
```
**Test Cases Needed:**
- Empty portfolio (size = 0) - executes statements 1, 2, 3
- Portfolio with 1 coin - executes all statements
- Portfolio with multiple coins - executes all statements including loop

**Example 2: Branch Coverage - Theme Update**
```java
public void update() {
    switch (currentTheme) {
        case LIGHT:    // Branch 1
            background = Color.WHITE;
            break;
        case DARK:     // Branch 2
            background = new Color(15, 15, 15);
            break;
        case CUSTOM:   // Branch 3
            background = customBackground;
            break;
    }
}
```
**Test Cases Needed:**
- Test LIGHT branch
- Test DARK branch
- Test CUSTOM branch

**Example 3: Condition Coverage - Input Validation**
```java
if (amount > 0 && price > 0) {
    value = amount * price;
} else {
    throw new IllegalArgumentException();
}
```
**Test Cases Needed:**
- amount > 0 = true, price > 0 = true (valid path)
- amount > 0 = true, price > 0 = false (invalid path)
- amount > 0 = false, price > 0 = true (invalid path)
- amount > 0 = false, price > 0 = false (invalid path)

**Example 4: Path Coverage - Currency Conversion**
```java
private String calculateCurrency(double x) {
    if (priceCurrency2 == 0.0 && buttonCurrency2.getText().equals(Main.currency)) {
        returnValue = priceCurrency1 * x;  // Path 1
    } else if (priceCurrency1 == 0 || priceCurrency2 == 0) {
        return String.valueOf(0);           // Path 2
    } else {
        returnValue = (priceCurrency1/priceCurrency2)*x;  // Path 3
    }
    // ... formatting logic with multiple paths
}
```
**Test Cases Needed:**
- Path 1: priceCurrency2 == 0 AND button text equals currency
- Path 2: priceCurrency1 == 0 OR priceCurrency2 == 0
- Path 3: Both prices are non-zero
- Additional paths for formatting logic (value > 1, value > 0.1, etc.)

#### 2.2.3 Combined Testing Strategy

**Integration of Black Box and White Box Testing:**

1. **Black Box First:** Design test cases based on requirements and specifications
2. **White Box Second:** Analyze code coverage and add tests to cover uncovered statements/branches
3. **Complementary Approach:**
   - Black Box ensures functional correctness
   - White Box ensures code quality and coverage
   - Together they provide comprehensive testing

**Coverage Goals:**
- **Statement Coverage:** ≥ 80%
- **Branch Coverage:** ≥ 70%
- **Condition Coverage:** ≥ 60%
- **Critical Path Coverage:** 100% (for business-critical methods)

#### 2.2.4 Testing Techniques

**1. Equivalence Partitioning (EP)**
- Divide input domain into equivalence classes
- Each class should produce similar behavior
- Test one representative value from each class
- **Example:** Portfolio amount input
  - Valid: Positive numbers (e.g., 1.0, 10.5, 100.0)
  - Invalid: Negative numbers (e.g., -1.0, -10.5)
  - Invalid: Zero (0.0)
  - Invalid: Non-numeric strings (e.g., "abc", "12.3.4")

**2. Boundary Value Analysis (BVA)**
- Test values at boundaries of equivalence classes
- Test just below, at, and just above boundaries
- **Example:** Portfolio amount boundaries
  - Minimum valid: 0.00000001 (smallest positive)
  - Maximum valid: Very large numbers (e.g., 999999999.99)
  - Boundary: 0.0 (invalid - zero)
  - Boundary: -0.00000001 (invalid - negative)

**3. Decision Table Testing**
- Used for complex business rules with multiple conditions
- **Example:** Portfolio calculation logic with different currency scenarios

**4. State Transition Testing**
- Test state changes (e.g., theme switching: Light → Dark → Custom → Light)
- Test portfolio switching between multiple portfolios

### 2.3 Test Levels

#### 2.3.1 Unit Testing
**Scope:** Individual methods and classes in isolation

**Focus Areas:**
- **WebData.Coin class:** Data structure validation, calculation methods (trimPrice, getInfo, getPortfolio)
- **Main.Theme class:** Theme switching logic, color updates
- **Calculation Methods:** Portfolio value calculation, gain/loss calculation, currency conversion
- **Validation Methods:** Input validation, duplicate checking

**Test Approach:**
- Mock external dependencies (API calls, file I/O)
- Test methods with various inputs using EP and BVA
- Verify return values and state changes

**Example Test Cases:**
- `testCalculatePortfolioValue_ValidInput()` - Test portfolio value calculation with valid amounts
- `testCalculatePortfolioValue_ZeroAmount()` - Test with zero amount (boundary)
- `testCalculatePortfolioValue_NegativeAmount()` - Test with negative amount (invalid)

#### 2.3.2 Integration Testing
**Scope:** Interaction between multiple components

**Focus Areas:**
- **WebData ↔ PanelPortfolio:** Data flow from API to portfolio display
- **PanelPortfolio ↔ Serialization:** Portfolio save/load functionality
- **PanelConverter ↔ WebData:** Currency conversion with real data
- **Settings ↔ All Panels:** Theme changes affecting all panels

**Test Approach:**
- Test component interactions without full GUI
- Verify data flow between components
- Test integration points (serialization/deserialization)

**Example Test Cases:**
- `testPortfolioSerialization_CompleteFlow()` - Save and load portfolio data
- `testThemeChange_AllPanelsUpdated()` - Verify all panels update when theme changes
- `testCurrencyConversion_WithRealData()` - Convert using actual coin prices

#### 2.3.3 System Testing
**Scope:** Complete system functionality as a whole

**Focus Areas:**
- End-to-end workflows (add coin to portfolio → calculate value → save → reload)
- Complete user scenarios
- System behavior under various conditions
- Performance with large datasets

**Test Approach:**
- Test complete user workflows
- Verify system meets all functional requirements
- Test error handling and recovery

**Example Test Cases:**
- `testCompletePortfolioWorkflow()` - Create portfolio, add coins, calculate, save, reload
- `testCurrencyConversionWorkflow()` - Select currencies, convert, switch, verify results
- `testSettingsPersistenceWorkflow()` - Change settings, restart app, verify persistence

### 2.4 Test Environment

**Hardware Requirements:**
- Standard desktop/laptop with Java 8 support
- Internet connection for API testing (or mocked API responses)

**Software Requirements:**
- Java Development Kit (JDK) 8 or higher
- Maven 3.5.3 or higher
- JUnit 4.13.2 or JUnit 5 (Jupiter)
- IDE (Eclipse, IntelliJ IDEA, or VS Code)

**Test Data:**
- Sample cryptocurrency data (can be mocked or use real API)
- Test portfolios with various coin combinations
- Edge case values (very large numbers, very small numbers, zero, negative)

### 2.5 Test Execution Strategy

1. **Test Order:**
   - Unit tests first (fastest, most granular)
   - Integration tests second
   - System tests third
   - Mutation tests (to validate test quality)
   - Performance tests (to validate system performance)

2. **Test Prioritization:**
   - **High Priority:** Critical business logic (portfolio calculations, currency conversion)
   - **Medium Priority:** Data persistence, input validation
   - **Low Priority:** UI behavior, theme switching (harder to test with JUnit)

3. **Test Automation:**
   - All unit and integration tests automated with JUnit
   - System tests can be partially automated
   - Mutation tests automated with mutation testing tools (e.g., PIT, Major)
   - Performance tests automated with performance testing frameworks

4. **Test Maintenance:**
   - Tests updated when requirements change
   - Tests reviewed and refactored regularly
   - Test coverage monitored and improved

---

## 3. Test Suites, Test Case Design, and Hierarchy

### 3.1 Test Suite Organization

Test suites are organized by functional modules and testing levels. Each test suite contains multiple test cases designed using both Black Box (Equivalence Partitioning, Boundary Value Analysis) and White Box (Statement, Branch, Condition, Path Coverage) techniques.

**Test Suite Structure:**
```
Test Suites
├── Unit Test Suites
│   ├── PortfolioCalculationTestSuite
│   ├── CurrencyConversionTestSuite
│   ├── ThemeManagementTestSuite
│   ├── DataValidationTestSuite
│   └── CoinDataTestSuite
├── Integration Test Suites
│   ├── DataPersistenceTestSuite
│   ├── ComponentIntegrationTestSuite
│   └── APIIntegrationTestSuite
├── System Test Suites
│   ├── PortfolioWorkflowTestSuite
│   ├── ConversionWorkflowTestSuite
│   └── SettingsWorkflowTestSuite
├── Mutation Test Suites
│   └── MutationTestingSuite
└── Performance Test Suites
    └── PerformanceTestingSuite
```

### 3.2 Test Hierarchy

The test hierarchy follows a pyramid structure with multiple levels:

```
                    ┌─────────────────────┐
                    │  Performance Tests  │  ← Performance Validation
                    │   (Load/Speed)       │
                    └──────────┬──────────┘
                               │
                    ┌──────────▼──────────┐
                    │   Mutation Tests    │  ← Test Quality Validation
                    │   (Test Coverage)    │
                    └──────────┬──────────┘
                               │
                    ┌──────────▼──────────┐
                    │   System Tests      │  ← End-to-End Workflows
                    │   (Workflows)       │
                    └──────────┬──────────┘
                               │
                    ┌──────────▼──────────┐
                    │ Integration Tests  │  ← Component Interactions
                    │   (Components)      │
                    └──────────┬──────────┘
                               │
                    ┌──────────▼──────────┐
                    │    Unit Tests       │  ← Many, Low-Level
                    │   (Methods/Classes) │
                    └─────────────────────┘
```

### 3.3 Test Case Design Methodology

Each test case is designed using the following template:

**Test Case Template:**
- **Test Case ID:** Unique identifier (e.g., TC_PF_001)
- **Test Suite:** Which suite it belongs to
- **Test Level:** Unit/Integration/System/Mutation/Performance
- **Test Type:** Black Box / White Box / Both
- **Requirement:** Related functional requirement (FR)
- **Test Objective:** What is being tested
- **Preconditions:** Setup required before test execution
- **Test Steps:** Detailed steps to execute
- **Input Data:** Test inputs (using EP and BVA)
- **Expected Result:** Expected outcome
- **Coverage Type:** Statement/Branch/Condition/Path (for White Box)
- **Priority:** High/Medium/Low

### 3.4 Unit Test Suites

#### 3.4.1 Portfolio Calculation Test Suite

**Purpose:** Test portfolio value and gain/loss calculations using Black Box (EP, BVA) and White Box (Statement, Branch Coverage) techniques.

**Test Case TC_PF_001: Calculate Portfolio Value - Valid Input (Black Box - EP)**
- **Test Level:** Unit
- **Test Type:** Black Box (Equivalence Partitioning)
- **Requirement:** FR2.4
- **Objective:** Verify portfolio value calculation with valid positive amounts
- **Preconditions:** Portfolio contains at least one coin with valid data
- **Test Steps:**
  1. Create portfolio with coin: amount = 10.0, price = 100.0
  2. Call calculatePortfolio()
  3. Verify portfolio value = 1000.0
- **Input Data:** 
  - Equivalence Class: Valid positive numbers
  - Test Values: 10.0 (amount), 100.0 (price)
- **Expected Result:** portfolio_value = 1000.0
- **Coverage:** Statement coverage for calculation logic
- **Priority:** High

**Test Case TC_PF_002: Calculate Portfolio Value - Zero Amount (Black Box - BVA)**
- **Test Level:** Unit
- **Test Type:** Black Box (Boundary Value Analysis)
- **Requirement:** FR2.4, FR6.1
- **Objective:** Test boundary condition: zero amount
- **Preconditions:** Portfolio setup
- **Test Steps:**
  1. Create portfolio with coin: amount = 0.0, price = 100.0
  2. Call calculatePortfolio()
  3. Verify portfolio value = 0.0
- **Input Data:**
  - Boundary Value: 0.0 (lower boundary of valid range)
- **Expected Result:** portfolio_value = 0.0
- **Coverage:** Branch coverage for zero amount condition
- **Priority:** High

**Test Case TC_PF_003: Calculate Portfolio Value - Negative Amount (Black Box - EP)**
- **Test Level:** Unit
- **Test Type:** Black Box (Equivalence Partitioning - Invalid Class)
- **Requirement:** FR6.1
- **Objective:** Verify system rejects negative amounts
- **Preconditions:** Portfolio setup
- **Test Steps:**
  1. Attempt to create portfolio with coin: amount = -5.0, price = 100.0
  2. Verify system rejects or handles error appropriately
- **Input Data:**
  - Equivalence Class: Invalid negative numbers
  - Test Values: -5.0, -0.0001
- **Expected Result:** Error thrown or validation message displayed
- **Coverage:** Branch coverage for validation logic
- **Priority:** High

**Test Case TC_PF_004: Calculate Portfolio Value - Very Small Amount (Black Box - BVA)**
- **Test Level:** Unit
- **Test Type:** Black Box (Boundary Value Analysis)
- **Requirement:** FR2.4
- **Objective:** Test minimum valid positive amount
- **Preconditions:** Portfolio setup
- **Test Steps:**
  1. Create portfolio with coin: amount = 0.00000001, price = 100.0
  2. Call calculatePortfolio()
  3. Verify calculation handles very small numbers correctly
- **Input Data:**
  - Boundary Value: 0.00000001 (minimum positive)
- **Expected Result:** portfolio_value = 0.000001 (or appropriate precision)
- **Coverage:** Statement coverage for decimal precision handling
- **Priority:** Medium

**Test Case TC_PF_005: Calculate Portfolio Value - Very Large Amount (Black Box - BVA)**
- **Test Level:** Unit
- **Test Type:** Black Box (Boundary Value Analysis)
- **Requirement:** FR2.4
- **Objective:** Test maximum valid amount
- **Preconditions:** Portfolio setup
- **Test Steps:**
  1. Create portfolio with coin: amount = 999999999.99, price = 100.0
  2. Call calculatePortfolio()
  3. Verify calculation handles very large numbers correctly
- **Input Data:**
  - Boundary Value: 999999999.99 (maximum reasonable value)
- **Expected Result:** portfolio_value calculated correctly without overflow
- **Coverage:** Statement coverage for large number handling
- **Priority:** Medium

**Test Case TC_PF_006: Calculate Portfolio Gains - Positive Gains (White Box - Branch Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Branch Coverage)
- **Requirement:** FR2.5
- **Objective:** Test branch where gains >= 0 (positive gains path)
- **Preconditions:** Portfolio with coins having positive gains
- **Test Steps:**
  1. Create portfolio: value_start = 1000.0, value_current = 1500.0
  2. Call calculatePortfolio()
  3. Verify gains = 500.0 and displayed in green color
- **Input Data:**
  - Condition: gains >= 0 (true branch)
  - Test Values: value_start = 1000.0, value_current = 1500.0
- **Expected Result:** gains = 500.0, color = green
- **Coverage:** Branch coverage - true path of `if (gains >= 0)`
- **Priority:** High

**Test Case TC_PF_007: Calculate Portfolio Gains - Negative Gains (White Box - Branch Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Branch Coverage)
- **Requirement:** FR2.5
- **Objective:** Test branch where gains < 0 (negative gains path)
- **Preconditions:** Portfolio with coins having negative gains
- **Test Steps:**
  1. Create portfolio: value_start = 1000.0, value_current = 800.0
  2. Call calculatePortfolio()
  3. Verify gains = -200.0 and displayed in red color
- **Input Data:**
  - Condition: gains >= 0 (false branch)
  - Test Values: value_start = 1000.0, value_current = 800.0
- **Expected Result:** gains = -200.0, color = red
- **Coverage:** Branch coverage - false path of `if (gains >= 0)`
- **Priority:** High

**Test Case TC_PF_008: Calculate Portfolio - Empty Portfolio (White Box - Statement Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Statement Coverage)
- **Requirement:** FR2.4
- **Objective:** Ensure all statements execute when portfolio is empty
- **Preconditions:** Empty portfolio (size = 0)
- **Test Steps:**
  1. Create empty portfolio
  2. Call calculatePortfolio()
  3. Verify value = 0.0, gains = 0.0
- **Input Data:**
  - Portfolio size: 0
- **Expected Result:** value = 0.0, gains = 0.0, percentage = "0.00%"
- **Coverage:** Statement coverage - loop not executed, initialization statements executed
- **Priority:** Medium

**Test Case TC_PF_009: Calculate Portfolio - Single Coin (White Box - Statement Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Statement Coverage)
- **Requirement:** FR2.4
- **Objective:** Execute all statements with single coin
- **Preconditions:** Portfolio with one coin
- **Test Steps:**
  1. Create portfolio with 1 coin: amount = 5.0, price = 200.0
  2. Call calculatePortfolio()
  3. Verify all calculation statements executed
- **Input Data:**
  - Portfolio size: 1
  - Coin data: amount = 5.0, price = 200.0
- **Expected Result:** value = 1000.0, all statements in loop executed once
- **Coverage:** Statement coverage - loop executed once, all statements covered
- **Priority:** Medium

**Test Case TC_PF_010: Calculate Portfolio - Multiple Coins (White Box - Loop Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Loop Coverage)
- **Requirement:** FR2.4
- **Objective:** Test loop with multiple iterations
- **Preconditions:** Portfolio with multiple coins
- **Test Steps:**
  1. Create portfolio with 3 coins
  2. Call calculatePortfolio()
  3. Verify loop executes correctly for all coins
- **Input Data:**
  - Portfolio size: 3
  - Coins: [amount=2.0, price=100], [amount=3.0, price=200], [amount=1.0, price=50]
- **Expected Result:** value = 850.0 (200 + 600 + 50), loop executed 3 times
- **Coverage:** Loop coverage - multiple iterations
- **Priority:** High

**Test Case TC_PF_011: Calculate Portfolio Percentage - Zero Value (White Box - Condition Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Condition Coverage)
- **Requirement:** FR2.5
- **Objective:** Test condition `if (value == 0)` for percentage calculation
- **Preconditions:** Portfolio with zero total value
- **Test Steps:**
  1. Create portfolio with value = 0.0
  2. Call calculatePortfolio()
  3. Verify percentage = "0.00%"
- **Input Data:**
  - Condition: value == 0 (true)
- **Expected Result:** percentGains = "0.00%"
- **Coverage:** Condition coverage - true branch of `if (value == 0)`
- **Priority:** Medium

**Test Case TC_PF_012: Calculate Portfolio Percentage - Non-Zero Value (White Box - Condition Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Condition Coverage)
- **Requirement:** FR2.5
- **Objective:** Test condition `if (value == 0)` for percentage calculation (false branch)
- **Preconditions:** Portfolio with non-zero value
- **Test Steps:**
  1. Create portfolio: value = 1000.0, gains = 200.0
  2. Call calculatePortfolio()
  3. Verify percentage calculated as gains/(value-gains) = 200/800 = 25%
- **Input Data:**
  - Condition: value == 0 (false)
  - Test Values: value = 1000.0, gains = 200.0
- **Expected Result:** percentGains = "25.00%"
- **Coverage:** Condition coverage - false branch of `if (value == 0)`
- **Priority:** High

#### 3.4.2 Currency Conversion Test Suite

**Purpose:** Test currency conversion logic using EP, BVA, and Path Coverage techniques.

**Test Case TC_CV_001: Convert Cryptocurrency to Fiat - Valid Conversion (Black Box - EP)**
- **Test Level:** Unit
- **Test Type:** Black Box (Equivalence Partitioning)
- **Requirement:** FR3.2
- **Objective:** Verify conversion from crypto to fiat currency
- **Preconditions:** Valid cryptocurrency and fiat currency selected
- **Test Steps:**
  1. Set priceCurrency1 = 50000.0 (Bitcoin price in USD)
  2. Set priceCurrency2 = 0.0, buttonCurrency2 = "USD"
  3. Call calculateCurrency(1.0)
  4. Verify result = 50000.0
- **Input Data:**
  - Equivalence Class: Valid positive conversion amounts
  - Test Values: x = 1.0, priceCurrency1 = 50000.0
- **Expected Result:** returnValue = 50000.0
- **Coverage:** Path coverage - Path 1 (crypto to fiat)
- **Priority:** High

**Test Case TC_CV_002: Convert Crypto to Crypto - Valid Conversion (Black Box - EP)**
- **Test Level:** Unit
- **Test Type:** Black Box (Equivalence Partitioning)
- **Requirement:** FR3.1
- **Objective:** Verify conversion between two cryptocurrencies
- **Preconditions:** Two valid cryptocurrencies selected
- **Test Steps:**
  1. Set priceCurrency1 = 50000.0 (Bitcoin)
  2. Set priceCurrency2 = 3000.0 (Ethereum)
  3. Call calculateCurrency(1.0)
  4. Verify result = 16.666... (50000/3000)
- **Input Data:**
  - Equivalence Class: Valid crypto-to-crypto conversion
  - Test Values: x = 1.0, priceCurrency1 = 50000.0, priceCurrency2 = 3000.0
- **Expected Result:** returnValue = 16.666...
- **Coverage:** Path coverage - Path 3 (crypto to crypto)
- **Priority:** High

**Test Case TC_CV_003: Convert with Zero Price - Error Handling (White Box - Path Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Path Coverage)
- **Requirement:** FR3.1, FR6.2
- **Objective:** Test path when priceCurrency1 is zero
- **Preconditions:** priceCurrency1 = 0.0
- **Test Steps:**
  1. Set priceCurrency1 = 0.0
  2. Set priceCurrency2 = 3000.0
  3. Call calculateCurrency(1.0)
  4. Verify result = "0" (error handling)
- **Input Data:**
  - Condition: priceCurrency1 == 0 (true)
  - Test Values: priceCurrency1 = 0.0, priceCurrency2 = 3000.0
- **Expected Result:** returnValue = "0"
- **Coverage:** Path coverage - Path 2 (zero price handling)
- **Priority:** High

**Test Case TC_CV_004: Format Large Value (> 1) - White Box Path Coverage**
- **Test Level:** Unit
- **Test Type:** White Box (Path Coverage)
- **Requirement:** FR3.3
- **Objective:** Test formatting path for values > 1
- **Preconditions:** Conversion result > 1
- **Test Steps:**
  1. Set conversion result = 100.5
  2. Call calculateCurrency formatting logic
  3. Verify format uses df1 (#.##)
- **Input Data:**
  - Condition: returnValue > 1 (true)
  - Test Value: 100.5
- **Expected Result:** "100.5" (formatted with 2 decimal places)
- **Coverage:** Path coverage - formatting path for value > 1
- **Priority:** Medium

**Test Case TC_CV_005: Format Small Value (< 0.0001) - White Box Path Coverage**
- **Test Level:** Unit
- **Test Type:** White Box (Path Coverage)
- **Requirement:** FR3.3
- **Objective:** Test formatting path for very small values
- **Preconditions:** Conversion result < 0.0001
- **Test Steps:**
  1. Set conversion result = 0.00005
  2. Call calculateCurrency formatting logic
  3. Verify format uses df6 (#.############)
- **Input Data:**
  - Condition: returnValue <= 0.0001 (true)
  - Test Value: 0.00005
- **Expected Result:** "0.00005" (formatted with 12 decimal places)
- **Coverage:** Path coverage - formatting path for very small values
- **Priority:** Medium

**Test Case TC_CV_006: Convert Boundary Value - Minimum Positive (Black Box - BVA)**
- **Test Level:** Unit
- **Test Type:** Black Box (Boundary Value Analysis)
- **Requirement:** FR3.1
- **Objective:** Test minimum valid input value
- **Preconditions:** Valid currencies selected
- **Test Steps:**
  1. Set priceCurrency1 = 50000.0, priceCurrency2 = 3000.0
  2. Call calculateCurrency(0.00000001)
  3. Verify result handles very small input
- **Input Data:**
  - Boundary Value: 0.00000001 (minimum positive)
- **Expected Result:** Result calculated and formatted correctly
- **Coverage:** Statement coverage for decimal precision
- **Priority:** Medium

---

### 3.5 Test Case Summary (First Half)

**Unit Test Suites Summary:**
- **Portfolio Calculation Test Suite:** 12 test cases (TC_PF_001 to TC_PF_012)
  - Black Box: 5 test cases (EP: 2, BVA: 3)
  - White Box: 7 test cases (Statement: 3, Branch: 2, Condition: 2, Loop: 1)
  
- **Currency Conversion Test Suite:** 6 test cases (TC_CV_001 to TC_CV_006)
  - Black Box: 3 test cases (EP: 2, BVA: 1)
  - White Box: 3 test cases (Path: 3)

**Coverage Achieved (First Half):**
- Portfolio Calculation: Statement coverage for calculatePortfolio(), Branch coverage for gains condition, Condition coverage for percentage calculation
- Currency Conversion: Path coverage for all three main conversion paths, Formatting path coverage for different value ranges

---

### 3.6 Unit Test Suites (Second Half - Part 1)

#### 3.6.1 Theme Management Test Suite

**Purpose:** Test theme switching logic, color updates, and theme state transitions using White Box (Branch, Path Coverage) and Black Box (State Transition) techniques.

**Test Case TC_TH_001: Switch Theme - Light to Dark (White Box - Branch Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Branch Coverage)
- **Requirement:** FR4.1
- **Objective:** Test branch for LIGHT → DARK theme transition
- **Preconditions:** Theme is currently LIGHT
- **Test Steps:**
  1. Initialize theme with LIGHT mode
  2. Call theme.change(themes.DARK)
  3. Call theme.update()
  4. Verify background = Color(15, 15, 15), foreground = Color.WHITE
- **Input Data:**
  - Current state: LIGHT
  - Transition: LIGHT → DARK
- **Expected Result:** Theme colors updated to dark mode values
- **Coverage:** Branch coverage - DARK case in switch statement
- **Priority:** High

**Test Case TC_TH_002: Switch Theme - Dark to Custom (White Box - Branch Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Branch Coverage)
- **Requirement:** FR4.1
- **Objective:** Test branch for DARK → CUSTOM theme transition
- **Preconditions:** Theme is currently DARK
- **Test Steps:**
  1. Initialize theme with DARK mode
  2. Set custom colors (customBackground, customForeground, etc.)
  3. Call theme.change(themes.CUSTOM)
  4. Call theme.update()
  5. Verify theme uses custom colors
- **Input Data:**
  - Current state: DARK
  - Transition: DARK → CUSTOM
  - Custom colors: customBackground = Color(0, 0, 0), customForeground = Color(14, 255, 0)
- **Expected Result:** Theme colors = custom colors
- **Coverage:** Branch coverage - CUSTOM case in switch statement
- **Priority:** High

**Test Case TC_TH_003: Switch Theme - Custom to Light (White Box - Branch Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Branch Coverage)
- **Requirement:** FR4.1
- **Objective:** Test branch for CUSTOM → LIGHT theme transition
- **Preconditions:** Theme is currently CUSTOM
- **Test Steps:**
  1. Initialize theme with CUSTOM mode
  2. Call theme.change(themes.LIGHT)
  3. Call theme.update()
  4. Verify background = Color.WHITE, foreground = Color.BLACK
- **Input Data:**
  - Current state: CUSTOM
  - Transition: CUSTOM → LIGHT
- **Expected Result:** Theme colors = light mode default colors
- **Coverage:** Branch coverage - LIGHT case in switch statement
- **Priority:** High

**Test Case TC_TH_004: Theme State Transition - Complete Cycle (Black Box - State Transition)**
- **Test Level:** Unit
- **Test Type:** Black Box (State Transition Testing)
- **Requirement:** FR4.1
- **Objective:** Test complete state transition cycle: LIGHT → DARK → CUSTOM → LIGHT
- **Preconditions:** Theme initialized
- **Test Steps:**
  1. Start with LIGHT theme
  2. Transition to DARK, verify colors
  3. Transition to CUSTOM, verify colors
  4. Transition back to LIGHT, verify colors
- **Input Data:**
  - State sequence: LIGHT → DARK → CUSTOM → LIGHT
- **Expected Result:** All transitions work correctly, colors update appropriately
- **Coverage:** Path coverage - all state transitions
- **Priority:** Medium

**Test Case TC_TH_005: Reset Custom Theme Colors (White Box - Statement Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Statement Coverage)
- **Requirement:** FR4.2
- **Objective:** Verify all custom color reset statements execute
- **Preconditions:** Custom theme with modified colors
- **Test Steps:**
  1. Modify custom colors
  2. Call theme.resetCustom()
  3. Verify all custom colors reset to default values
- **Input Data:**
  - Modified colors: customBackground = Color(100, 100, 100)
  - Reset operation: resetCustom()
- **Expected Result:** All custom colors = default values (defaultCustomBackground, etc.)
- **Coverage:** Statement coverage - all reset assignments executed
- **Priority:** Medium

**Test Case TC_TH_006: Custom Theme - All Color Properties (White Box - Condition Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Condition Coverage)
- **Requirement:** FR4.2
- **Objective:** Test condition `currentTheme == CUSTOM` for all color properties
- **Preconditions:** Theme in CUSTOM mode
- **Test Steps:**
  1. Set theme to CUSTOM
  2. Set all custom colors
  3. Call theme.update()
  4. Verify all 6 color properties use custom values
- **Input Data:**
  - Condition: currentTheme == CUSTOM (true)
  - Custom colors: background, foreground, green, red, selection, emptyBackground
- **Expected Result:** All 6 colors = custom values
- **Coverage:** Condition coverage - CUSTOM theme condition true
- **Priority:** High

#### 3.6.2 Data Validation Test Suite

**Purpose:** Test input validation, data type checking, and error handling using Black Box (EP, BVA) and White Box (Branch, Condition Coverage) techniques.

**Test Case TC_DV_001: Validate Portfolio Amount - Valid Positive Number (Black Box - EP)**
- **Test Level:** Unit
- **Test Type:** Black Box (Equivalence Partitioning)
- **Requirement:** FR6.1
- **Objective:** Verify system accepts valid positive numeric input
- **Preconditions:** Portfolio add coin dialog open
- **Test Steps:**
  1. Enter amount = "10.5"
  2. Parse input using Double.parseDouble()
  3. Verify parsing succeeds and amount = 10.5
- **Input Data:**
  - Equivalence Class: Valid positive numbers
  - Test Values: "10.5", "100", "0.001"
- **Expected Result:** amount parsed successfully, no error
- **Coverage:** Branch coverage - valid input path
- **Priority:** High

**Test Case TC_DV_002: Validate Portfolio Amount - Invalid Non-Numeric (Black Box - EP)**
- **Test Level:** Unit
- **Test Type:** Black Box (Equivalence Partitioning - Invalid Class)
- **Requirement:** FR6.1
- **Objective:** Verify system rejects non-numeric input
- **Preconditions:** Portfolio add coin dialog open
- **Test Steps:**
  1. Enter amount = "abc"
  2. Attempt to parse using Double.parseDouble()
  3. Verify NumberFormatException thrown
  4. Verify error message displayed
- **Input Data:**
  - Equivalence Class: Invalid non-numeric strings
  - Test Values: "abc", "12.3.4", "ten", ""
- **Expected Result:** Exception caught, error message: "Incorrect format!"
- **Coverage:** Branch coverage - exception handling path
- **Priority:** High

**Test Case TC_DV_003: Validate Portfolio Amount - Zero Value (Black Box - BVA)**
- **Test Level:** Unit
- **Test Type:** Black Box (Boundary Value Analysis)
- **Requirement:** FR6.1
- **Objective:** Test boundary: zero value
- **Preconditions:** Portfolio add coin dialog open
- **Test Steps:**
  1. Enter amount = "0"
  2. Parse input
  3. Verify amount = 0.0 (may be valid or invalid depending on business rule)
- **Input Data:**
  - Boundary Value: "0" (zero)
- **Expected Result:** Either rejected or accepted with amount = 0.0
- **Coverage:** Branch coverage - zero handling logic
- **Priority:** Medium

**Test Case TC_DV_004: Validate Portfolio Amount - Negative Value (Black Box - EP)**
- **Test Level:** Unit
- **Test Type:** Black Box (Equivalence Partitioning - Invalid Class)
- **Requirement:** FR6.1
- **Objective:** Verify system rejects negative values
- **Preconditions:** Portfolio add coin dialog open
- **Test Steps:**
  1. Enter amount = "-5.0"
  2. Parse input (parsing succeeds but value is negative)
  3. Verify validation rejects negative value
- **Input Data:**
  - Equivalence Class: Negative numbers
  - Test Values: "-5.0", "-0.001", "-100"
- **Expected Result:** Validation error or rejection
- **Coverage:** Branch coverage - negative value validation
- **Priority:** High

**Test Case TC_DV_005: Validate Portfolio Amount - Very Large Number (Black Box - BVA)**
- **Test Level:** Unit
- **Test Type:** Black Box (Boundary Value Analysis)
- **Requirement:** FR6.1
- **Objective:** Test maximum valid input boundary
- **Preconditions:** Portfolio add coin dialog open
- **Test Steps:**
  1. Enter amount = "999999999999.99"
  2. Parse input
  3. Verify system handles very large numbers
- **Input Data:**
  - Boundary Value: "999999999999.99" (very large)
- **Expected Result:** Parsed successfully or appropriate error for overflow
- **Coverage:** Statement coverage - large number handling
- **Priority:** Low

**Test Case TC_DV_006: Validate Duplicate Coin - Coin Already Exists (White Box - Branch Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Branch Coverage)
- **Requirement:** FR2.10
- **Objective:** Test branch where duplicate coin is detected
- **Preconditions:** Portfolio contains at least one coin
- **Test Steps:**
  1. Portfolio has coin "Bitcoin"
  2. Attempt to add "Bitcoin" again
  3. Call findPortfolioName("Bitcoin")
  4. Verify returns true (duplicate found)
- **Input Data:**
  - Condition: coin name exists in portfolio (true)
  - Test: findPortfolioName("Bitcoin") where Bitcoin already exists
- **Expected Result:** Returns true, duplicate detected
- **Coverage:** Branch coverage - true path of duplicate check
- **Priority:** High

**Test Case TC_DV_007: Validate Duplicate Coin - Coin Not Exists (White Box - Branch Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Branch Coverage)
- **Requirement:** FR2.10
- **Objective:** Test branch where coin is not duplicate
- **Preconditions:** Portfolio contains coins
- **Test Steps:**
  1. Portfolio has coin "Bitcoin"
  2. Attempt to add "Ethereum"
  3. Call findPortfolioName("Ethereum")
  4. Verify returns false (not duplicate)
- **Input Data:**
  - Condition: coin name exists in portfolio (false)
  - Test: findPortfolioName("Ethereum") where only Bitcoin exists
- **Expected Result:** Returns false, coin can be added
- **Coverage:** Branch coverage - false path of duplicate check
- **Priority:** High

**Test Case TC_DV_008: Validate Portfolio Name - Duplicate Name (White Box - Condition Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Condition Coverage)
- **Requirement:** FR2.8
- **Objective:** Test condition for duplicate portfolio name check
- **Preconditions:** Multiple portfolios exist
- **Test Steps:**
  1. Portfolios: ["Portfolio 1", "Portfolio 2"]
  2. Attempt to rename "Portfolio 2" to "Portfolio 1"
  3. Check condition: names.get(i).equals(message) && i != nr
  4. Verify duplicate detected
- **Input Data:**
  - Condition: name exists AND index != current (true)
  - Test: Rename portfolio 2 to "Portfolio 1"
- **Expected Result:** Error: "Name already exists!"
- **Coverage:** Condition coverage - compound condition true
- **Priority:** Medium

**Test Case TC_DV_009: Validate Portfolio Deletion - Last Portfolio (White Box - Branch Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Branch Coverage)
- **Requirement:** FR2.9, FR6.5
- **Objective:** Test branch preventing deletion of last portfolio
- **Preconditions:** Only one portfolio exists
- **Test Steps:**
  1. Portfolio list size = 1
  2. Attempt to delete portfolio
  3. Check condition: portfolio.size() == 1
  4. Verify deletion prevented
- **Input Data:**
  - Condition: portfolio.size() == 1 (true)
- **Expected Result:** Error: "You must have at least one portfolio!"
- **Coverage:** Branch coverage - true path of size check
- **Priority:** High

**Test Case TC_DV_010: Validate Portfolio Deletion - Multiple Portfolios (White Box - Branch Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Branch Coverage)
- **Requirement:** FR2.9
- **Objective:** Test branch allowing deletion when multiple portfolios exist
- **Preconditions:** Multiple portfolios exist
- **Test Steps:**
  1. Portfolio list size = 2
  2. Delete one portfolio
  3. Check condition: portfolio.size() == 1 (false)
  4. Verify deletion succeeds
- **Input Data:**
  - Condition: portfolio.size() == 1 (false)
  - Test: Delete portfolio when size = 2
- **Expected Result:** Portfolio deleted successfully
- **Coverage:** Branch coverage - false path of size check
- **Priority:** High

#### 3.6.3 Coin Data Test Suite

**Purpose:** Test coin data structure, formatting methods, and data retrieval using Black Box (EP) and White Box (Statement, Path Coverage) techniques.

**Test Case TC_CD_001: Format Price - Large Value (> 1) (White Box - Path Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Path Coverage)
- **Requirement:** FR1.2
- **Objective:** Test trimPrice() path for values > 1
- **Preconditions:** Coin with price > 1
- **Test Steps:**
  1. Create coin with price = 50000.0
  2. Call coin.trimPrice(50000.0)
  3. Verify format uses DecimalFormat("#.##")
- **Input Data:**
  - Condition: trimPrice > 1 (true)
  - Test Value: 50000.0
- **Expected Result:** "50000.0" (2 decimal places)
- **Coverage:** Path coverage - formatting path for price > 1
- **Priority:** Medium

**Test Case TC_CD_002: Format Price - Small Value (< 0.0001) (White Box - Path Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Path Coverage)
- **Requirement:** FR1.2
- **Objective:** Test trimPrice() path for very small values
- **Preconditions:** Coin with very small price
- **Test Steps:**
  1. Create coin with price = 0.00005
  2. Call coin.trimPrice(0.00005)
  3. Verify format uses DecimalFormat("#.############")
- **Input Data:**
  - Condition: trimPrice <= 0.0001 (true)
  - Test Value: 0.00005
- **Expected Result:** "0.00005" (12 decimal places)
- **Coverage:** Path coverage - formatting path for very small price
- **Priority:** Medium

**Test Case TC_CD_003: Format Price - Medium Value (0.1 to 1) (White Box - Path Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Path Coverage)
- **Requirement:** FR1.2
- **Objective:** Test trimPrice() path for medium values
- **Preconditions:** Coin with price between 0.1 and 1
- **Test Steps:**
  1. Create coin with price = 0.5
  2. Call coin.trimPrice(0.5)
  3. Verify format uses DecimalFormat("#.###")
- **Input Data:**
  - Condition: 0.1 < trimPrice <= 1 (true)
  - Test Value: 0.5
- **Expected Result:** "0.5" (3 decimal places)
- **Coverage:** Path coverage - formatting path for 0.1 < price <= 1
- **Priority:** Medium

**Test Case TC_CD_004: Get Coin Info - Complete Data (White Box - Statement Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Statement Coverage)
- **Requirement:** FR1.5
- **Objective:** Verify all statements in getInfo() execute
- **Preconditions:** Coin with all fields populated
- **Test Steps:**
  1. Create coin with all fields: rank, id, name, symbol, price, market_cap, etc.
  2. Call coin.getInfo()
  3. Verify all fields included in output string
- **Input Data:**
  - Complete coin data with all 15+ fields
- **Expected Result:** Info string contains all fields formatted correctly
- **Coverage:** Statement coverage - all concatenation statements executed
- **Priority:** High

**Test Case TC_CD_005: Get Portfolio Info - With Portfolio Data (White Box - Statement Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Statement Coverage)
- **Requirement:** FR2.5
- **Objective:** Verify getPortfolio() includes portfolio-specific data
- **Preconditions:** Coin with portfolio data
- **Test Steps:**
  1. Create coin with portfolio_amount, portfolio_value, portfolio_gains
  2. Call coin.getPortfolio()
  3. Verify output includes portfolio fields
- **Input Data:**
  - Coin with: portfolio_amount = 5.0, portfolio_value = 1000.0, portfolio_gains = 200.0
- **Expected Result:** Portfolio info includes amount, value, gains, currency, start price/value
- **Coverage:** Statement coverage - all portfolio field statements executed
- **Priority:** High

**Test Case TC_CD_006: Coin Copy Method - Clone Functionality (Black Box - EP)**
- **Test Level:** Unit
- **Test Type:** Black Box (Equivalence Partitioning)
- **Requirement:** FR2.2
- **Objective:** Verify coin.copy() creates independent copy
- **Preconditions:** Coin object exists
- **Test Steps:**
  1. Create coin with data
  2. Call coin.copy()
  3. Modify copied coin
  4. Verify original coin unchanged
- **Input Data:**
  - Equivalence Class: Valid coin objects
  - Test: Copy coin and modify copy
- **Expected Result:** Original coin data unchanged, copy is independent
- **Coverage:** Statement coverage - clone() method execution
- **Priority:** Medium

**Test Case TC_CD_007: Coin ToString - Name Return (White Box - Statement Coverage)**
- **Test Level:** Unit
- **Test Type:** White Box (Statement Coverage)
- **Requirement:** FR1.2
- **Objective:** Verify toString() returns coin name
- **Preconditions:** Coin with name
- **Test Steps:**
  1. Create coin with name = "Bitcoin"
  2. Call coin.toString()
  3. Verify returns "Bitcoin"
- **Input Data:**
  - Coin name: "Bitcoin"
- **Expected Result:** "Bitcoin"
- **Coverage:** Statement coverage - return statement executed
- **Priority:** Low

---

### 3.7 Test Case Summary (Second Half - Part 1)

**Unit Test Suites Summary (Part 1):**
- **Theme Management Test Suite:** 6 test cases (TC_TH_001 to TC_TH_006)
  - Black Box: 1 test case (State Transition: 1)
  - White Box: 5 test cases (Branch: 3, Statement: 1, Condition: 1)
  
- **Data Validation Test Suite:** 10 test cases (TC_DV_001 to TC_DV_010)
  - Black Box: 5 test cases (EP: 3, BVA: 2)
  - White Box: 5 test cases (Branch: 3, Condition: 2)
  
- **Coin Data Test Suite:** 7 test cases (TC_CD_001 to TC_CD_007)
  - Black Box: 1 test case (EP: 1)
  - White Box: 6 test cases (Path: 3, Statement: 3)

**Total Unit Test Cases (Complete):**
- Portfolio Calculation: 12 cases
- Currency Conversion: 6 cases
- Theme Management: 6 cases
- Data Validation: 10 cases
- Coin Data: 7 cases
- **Total: 41 Unit Test Cases**

**Coverage Achieved (Part 1):**
- Theme Management: Branch coverage for all theme switch cases, State transition coverage
- Data Validation: Branch coverage for validation logic, Condition coverage for compound conditions
- Coin Data: Path coverage for formatting methods, Statement coverage for info methods

---

### 3.8 Integration Test Suites

#### 3.8.1 Data Persistence Test Suite

**Purpose:** Test serialization and deserialization of data between components, ensuring data integrity across save/load operations.

**Test Case TC_IT_001: Portfolio Serialization and Deserialization - Complete Flow**
- **Test Level:** Integration
- **Test Type:** Black Box (End-to-End Flow)
- **Requirement:** FR5.2, FR5.5
- **Objective:** Verify portfolio data persists correctly through serialization/deserialization cycle
- **Preconditions:** Portfolio with multiple coins
- **Test Steps:**
  1. Create portfolio with 3 coins (different amounts, prices, gains)
  2. Call serializePortfolio()
  3. Clear portfolio from memory
  4. Call deserializePortfolio()
  5. Verify all portfolio data restored correctly
- **Input Data:**
  - Portfolio: 3 coins with various data
  - Serialization file: portfolio.ser
- **Expected Result:** All coins, amounts, prices, values, gains restored exactly
- **Coverage:** Integration coverage - PanelPortfolio ↔ File System
- **Priority:** High

**Test Case TC_IT_002: Settings Serialization and Deserialization - Theme Persistence**
- **Test Level:** Integration
- **Test Type:** Black Box (End-to-End Flow)
- **Requirement:** FR5.3, FR5.5
- **Objective:** Verify theme settings persist across application restarts
- **Preconditions:** Application with custom theme settings
- **Test Steps:**
  1. Set theme to DARK mode
  2. Set currency to EUR
  3. Enable debug mode
  4. Call serialize() in PanelSettings
  5. Restart application (simulate)
  6. Call deserializeSettings() in Main
  7. Verify theme, currency, debug mode restored
- **Input Data:**
  - Settings: theme=DARK, currency=EUR, debug=true
  - Serialization file: settings.ser
- **Expected Result:** All settings restored correctly
- **Coverage:** Integration coverage - PanelSettings ↔ Main ↔ File System
- **Priority:** High

**Test Case TC_IT_003: Converter State Serialization - Currency Selection Persistence**
- **Test Level:** Integration
- **Test Type:** Black Box (End-to-End Flow)
- **Requirement:** FR5.4, FR5.5
- **Objective:** Verify converter state (selected currencies, amounts) persists
- **Preconditions:** Converter with selected currencies and input value
- **Test Steps:**
  1. Select currency1 = "Bitcoin", currency2 = "Ethereum"
  2. Enter amount = 5.0
  3. Call serialize() in PanelConverter
  4. Clear converter state
  5. Call deserialize() in PanelConverter
  6. Verify currencies and amount restored
- **Input Data:**
  - Converter state: currency1="Bitcoin", currency2="Ethereum", amount=5.0
  - Serialization file: converter.ser
- **Expected Result:** All converter state restored
- **Coverage:** Integration coverage - PanelConverter ↔ File System
- **Priority:** Medium

**Test Case TC_IT_004: Corrupted Data File Handling - Error Recovery**
- **Test Level:** Integration
- **Test Type:** White Box (Error Path Coverage)
- **Requirement:** FR5.6
- **Objective:** Test error handling when deserialization fails
- **Preconditions:** Corrupted or missing data file
- **Test Steps:**
  1. Create corrupted portfolio.ser file (invalid data)
  2. Call deserializePortfolio()
  3. Verify exception caught
  4. Verify file deleted
  5. Verify default portfolio created
- **Input Data:**
  - Corrupted file or missing file
- **Expected Result:** Exception handled gracefully, default data created, file deleted
- **Coverage:** Branch coverage - exception handling path
- **Priority:** High

#### 3.8.2 Component Integration Test Suite

**Purpose:** Test interactions between different components (WebData, Panels, Main) without full GUI.

**Test Case TC_IT_005: WebData to PanelPortfolio - Data Flow Integration**
- **Test Level:** Integration
- **Test Type:** Black Box (Component Interaction)
- **Requirement:** FR1.1, FR2.4
- **Objective:** Verify coin data flows correctly from WebData to PanelPortfolio
- **Preconditions:** WebData contains coin data
- **Test Steps:**
  1. WebData.fetch() retrieves coin data
  2. PanelPortfolio.refreshPortfolio() called
  3. Verify portfolio coins updated with latest prices from WebData
  4. Verify portfolio calculations use updated prices
- **Input Data:**
  - WebData.coin: List of coins with prices
  - Portfolio: Coins matching WebData coins
- **Expected Result:** Portfolio values recalculated with new prices
- **Coverage:** Integration coverage - WebData ↔ PanelPortfolio
- **Priority:** High

**Test Case TC_IT_006: Theme Change - All Panels Update Integration**
- **Test Level:** Integration
- **Test Type:** White Box (Component Interaction)
- **Requirement:** FR4.1
- **Objective:** Verify theme change propagates to all panels
- **Preconditions:** All panels initialized
- **Test Steps:**
  1. Change theme to DARK in PanelSettings
  2. Call themeSwitch() on all panels
  3. Verify panelCoin.themeSwitch() called
  4. Verify panelPortfolio.themeSwitch() called
  5. Verify panelConverter.themeSwitch() called
  6. Verify panelSettings.themeSwitch() called
- **Input Data:**
  - Theme change: LIGHT → DARK
- **Expected Result:** All panels update colors correctly
- **Coverage:** Integration coverage - PanelSettings ↔ All Panels
- **Priority:** High

**Test Case TC_IT_007: Currency Change - Data Refresh Integration**
- **Test Level:** Integration
- **Test Type:** Black Box (Component Interaction)
- **Requirement:** FR4.3, FR1.1
- **Objective:** Verify currency change triggers data refresh
- **Preconditions:** Application running with USD currency
- **Test Steps:**
  1. Change currency from USD to EUR in PanelSettings
  2. Verify WebData.RefreshCoins() called
  3. Verify coin prices updated to EUR
  4. Verify portfolio values recalculated in EUR
  5. Verify converter uses EUR currency
- **Input Data:**
  - Currency change: USD → EUR
- **Expected Result:** All components update to new currency
- **Coverage:** Integration coverage - PanelSettings → WebData → All Panels
- **Priority:** High

**Test Case TC_IT_008: Portfolio Refresh - Price Update Integration**
- **Test Level:** Integration
- **Test Type:** Black Box (Component Interaction)
- **Requirement:** FR2.4
- **Objective:** Verify portfolio refresh updates prices and recalculates values
- **Preconditions:** Portfolio with coins, WebData has updated prices
- **Test Steps:**
  1. Portfolio has Bitcoin at old price = 40000.0
  2. WebData updated with Bitcoin price = 50000.0
  3. Call refreshPortfolio()
  4. Verify portfolio coin price updated to 50000.0
  5. Verify portfolio_value recalculated
  6. Verify portfolio_gains recalculated
- **Input Data:**
  - Old price: 40000.0, New price: 50000.0
  - Portfolio amount: 1.0
- **Expected Result:** portfolio_value = 50000.0, gains updated
- **Coverage:** Integration coverage - WebData ↔ PanelPortfolio refresh logic
- **Priority:** High

#### 3.8.3 API Integration Test Suite

**Purpose:** Test integration with external CoinGecko API, including error handling and retry logic.

**Test Case TC_IT_009: API Data Fetch - Successful Response**
- **Test Level:** Integration
- **Test Type:** Black Box (External Integration)
- **Requirement:** FR1.1, FR6.2
- **Objective:** Verify successful API data fetch and parsing
- **Preconditions:** Internet connection available, API accessible
- **Test Steps:**
  1. Call WebData.fetch()
  2. Verify API request sent to CoinGecko
  3. Verify response code = 200
  4. Verify JSON parsed correctly
  5. Verify coin list populated
  6. Verify global_data populated
- **Input Data:**
  - API URL: CoinGecko markets endpoint
  - Currency: USD
- **Expected Result:** Coin data and global data fetched and parsed successfully
- **Coverage:** Integration coverage - WebData ↔ CoinGecko API
- **Priority:** High

**Test Case TC_IT_010: API Rate Limiting - HTTP 429 Handling**
- **Test Level:** Integration
- **Test Type:** White Box (Error Path Coverage)
- **Requirement:** FR6.3
- **Objective:** Test retry logic when API returns 429 (rate limit)
- **Preconditions:** API rate limit reached
- **Test Steps:**
  1. Simulate API response code 429
  2. Verify retry logic triggered
  3. Verify 5 second wait before retry
  4. Verify up to 3 retry attempts
  5. Verify appropriate error handling if all retries fail
- **Input Data:**
  - API response: HTTP 429
  - Retry attempts: 3
- **Expected Result:** System waits and retries, handles gracefully
- **Coverage:** Branch coverage - rate limit handling path
- **Priority:** Medium

**Test Case TC_IT_011: API Connection Failure - Network Error Handling**
- **Test Level:** Integration
- **Test Type:** White Box (Error Path Coverage)
- **Requirement:** FR6.2
- **Objective:** Test error handling when API connection fails
- **Preconditions:** No internet connection or API unavailable
- **Test Steps:**
  1. Simulate network failure (no connection)
  2. Call WebData.fetch()
  3. Verify exception caught
  4. Verify error message displayed to user
  5. Verify application continues with existing data
- **Input Data:**
  - Network condition: No connection
- **Expected Result:** Error handled gracefully, user notified, app continues
- **Coverage:** Branch coverage - exception handling path
- **Priority:** High

---

### 3.9 System Test Suites

#### 3.9.1 Portfolio Workflow Test Suite

**Purpose:** Test complete end-to-end workflows for portfolio management functionality.

**Test Case TC_ST_001: Complete Portfolio Workflow - Add, Calculate, Save, Reload**
- **Test Level:** System
- **Test Type:** Black Box (End-to-End Workflow)
- **Requirement:** FR2.2, FR2.4, FR2.5, FR5.2, FR5.5
- **Objective:** Test complete workflow from adding coin to reloading portfolio
- **Preconditions:** Application started, empty portfolio
- **Test Steps:**
  1. Navigate to Portfolio panel
  2. Click "Add Coin"
  3. Select "Bitcoin" from list
  4. Enter amount = 2.0
  5. Select "Retrieve current value"
  6. Verify coin added to portfolio
  7. Verify portfolio value calculated
  8. Verify gains calculated
  9. Close and restart application
  10. Verify portfolio data loaded
  11. Verify all values correct
- **Input Data:**
  - Coin: Bitcoin
  - Amount: 2.0
  - Price: Current market price
- **Expected Result:** Complete workflow executes successfully, data persists
- **Coverage:** System coverage - Full portfolio workflow
- **Priority:** High

**Test Case TC_ST_002: Portfolio Management Workflow - Create, Rename, Switch, Delete**
- **Test Level:** System
- **Test Type:** Black Box (End-to-End Workflow)
- **Requirement:** FR2.1, FR2.7, FR2.8, FR2.9
- **Objective:** Test complete portfolio management operations
- **Preconditions:** Application started with default portfolio
- **Test Steps:**
  1. Click "Manage Portfolio"
  2. Select "New Portfolio"
  3. Verify new portfolio created
  4. Add coins to new portfolio
  5. Click "Switch Portfolio"
  6. Switch to original portfolio
  7. Click "Manage Portfolio" → "Rename Current"
  8. Rename to "My Portfolio"
  9. Verify rename successful
  10. Create third portfolio
  11. Delete second portfolio
  12. Verify deletion successful
  13. Verify cannot delete last portfolio
- **Input Data:**
  - Portfolio operations: Create, Rename, Switch, Delete
- **Expected Result:** All portfolio management operations work correctly
- **Coverage:** System coverage - Portfolio management workflow
- **Priority:** High

**Test Case TC_ST_003: Portfolio Refresh Workflow - Update Prices and Recalculate**
- **Test Level:** System
- **Test Type:** Black Box (End-to-End Workflow)
- **Requirement:** FR2.4, FR2.5
- **Objective:** Test portfolio refresh updates all values
- **Preconditions:** Portfolio with coins, prices changed in market
- **Test Steps:**
  1. Portfolio has Bitcoin at price 40000.0
  2. Market price changes to 50000.0
  3. Click "Refresh" button
  4. Wait for API fetch to complete
  5. Verify portfolio prices updated
  6. Verify portfolio values recalculated
  7. Verify gains/losses recalculated
  8. Verify percentage gains updated
- **Input Data:**
  - Initial price: 40000.0
  - Updated price: 50000.0
  - Amount: 1.0
- **Expected Result:** All portfolio calculations updated correctly
- **Coverage:** System coverage - Refresh workflow
- **Priority:** High

#### 3.9.2 Conversion Workflow Test Suite

**Purpose:** Test complete end-to-end workflows for currency conversion functionality.

**Test Case TC_ST_004: Currency Conversion Workflow - Crypto to Crypto**
- **Test Level:** System
- **Test Type:** Black Box (End-to-End Workflow)
- **Requirement:** FR3.1, FR3.3
- **Objective:** Test complete crypto-to-crypto conversion workflow
- **Preconditions:** Application started, Converter panel open
- **Test Steps:**
  1. Navigate to Converter panel
  2. Click currency1 button, select "Bitcoin"
  3. Click currency2 button, select "Ethereum"
  4. Enter amount = 1.0 in currency1 field
  5. Verify conversion calculated automatically
  6. Verify result displayed in currency2 field
  7. Verify info boxes show coin details
  8. Click "Switch" button
  9. Verify currencies and amounts swapped
- **Input Data:**
  - Currency1: Bitcoin (price = 50000.0)
  - Currency2: Ethereum (price = 3000.0)
  - Amount: 1.0 Bitcoin
- **Expected Result:** Conversion = 16.666... Ethereum, switch works correctly
- **Coverage:** System coverage - Conversion workflow
- **Priority:** High

**Test Case TC_ST_005: Currency Conversion Workflow - Crypto to Fiat**
- **Test Level:** System
- **Test Type:** Black Box (End-to-End Workflow)
- **Requirement:** FR3.2, FR3.3
- **Objective:** Test crypto-to-fiat conversion workflow
- **Preconditions:** Application started, Converter panel open
- **Test Steps:**
  1. Navigate to Converter panel
  2. Click currency1 button, select "Bitcoin"
  3. Click currency2 button, select "Current currency (USD)"
  4. Enter amount = 0.5 in currency1 field
  5. Verify conversion to USD calculated
  6. Verify result displayed correctly
  7. Change currency in Settings to EUR
  8. Verify converter updates to EUR
- **Input Data:**
  - Currency1: Bitcoin (price = 50000.0)
  - Currency2: USD (fiat)
  - Amount: 0.5 Bitcoin
- **Expected Result:** Conversion = 25000.0 USD, updates when currency changes
- **Coverage:** System coverage - Crypto to fiat workflow
- **Priority:** High

**Test Case TC_ST_006: Global Data Display Workflow**
- **Test Level:** System
- **Test Type:** Black Box (End-to-End Workflow)
- **Requirement:** FR3.5
- **Objective:** Test global market data display and refresh
- **Preconditions:** Application started, Converter panel open
- **Test Steps:**
  1. Navigate to Converter panel
  2. Verify global data displayed (market cap, 24h volume, Bitcoin dominance)
  3. Click "Global Data" button
  4. Verify detailed global data popup displayed
  5. Click "Refresh" button
  6. Verify global data updated
  7. Verify popup shows updated data
- **Input Data:**
  - Global data: Market cap, volume, dominance
- **Expected Result:** Global data displayed and refreshed correctly
- **Coverage:** System coverage - Global data workflow
- **Priority:** Medium

#### 3.9.3 Settings Workflow Test Suite

**Purpose:** Test complete end-to-end workflows for settings and customization.

**Test Case TC_ST_007: Settings Persistence Workflow - Theme and Currency**
- **Test Level:** System
- **Test Type:** Black Box (End-to-End Workflow)
- **Requirement:** FR4.1, FR4.3, FR5.3, FR5.5
- **Objective:** Test settings persist across application restarts
- **Preconditions:** Application started with default settings
- **Test Steps:**
  1. Navigate to Settings panel
  2. Change theme to DARK
  3. Verify all panels update to dark theme
  4. Change currency to EUR
  5. Verify currency updated, data refreshed
  6. Close application
  7. Restart application
  8. Verify theme is DARK
  9. Verify currency is EUR
  10. Verify all panels use correct theme
- **Input Data:**
  - Settings: theme=DARK, currency=EUR
- **Expected Result:** All settings persist and apply correctly after restart
- **Coverage:** System coverage - Settings persistence workflow
- **Priority:** High

**Test Case TC_ST_008: Custom Theme Workflow - Color Customization**
- **Test Level:** System
- **Test Type:** Black Box (End-to-End Workflow)
- **Requirement:** FR4.2
- **Objective:** Test complete custom theme creation workflow
- **Preconditions:** Application started, Settings panel open
- **Test Steps:**
  1. Navigate to Settings panel
  2. Change theme to CUSTOM
  3. Click "Background" color button
  4. Select color in color chooser
  5. Repeat for all 6 color properties
  6. Click "Apply & Select"
  7. Verify theme changes to CUSTOM
  8. Verify all panels use custom colors
  9. Restart application
  10. Verify custom theme persists
- **Input Data:**
  - Custom colors: 6 different colors for all properties
- **Expected Result:** Custom theme created, applied, and persists
- **Coverage:** System coverage - Custom theme workflow
- **Priority:** Medium

**Test Case TC_ST_009: Data Deletion Workflow - Selective Deletion**
- **Test Level:** System
- **Test Type:** Black Box (End-to-End Workflow)
- **Requirement:** FR4.7
- **Objective:** Test selective data deletion workflow
- **Preconditions:** Application with data (portfolio, settings, converter)
- **Test Steps:**
  1. Navigate to Settings panel
  2. Click "Delete Data"
  3. Select "Delete Portfolio Data" only
  4. Confirm deletion
  5. Verify portfolio data deleted
  6. Verify settings and converter data still exist
  7. Restart application
  8. Verify default portfolio created
  9. Verify settings and converter data still loaded
- **Input Data:**
  - Delete selection: Portfolio data only
- **Expected Result:** Only selected data deleted, other data preserved
- **Coverage:** System coverage - Data deletion workflow
- **Priority:** Medium

---

### 3.10 Test Case Summary (Complete Section 3)

**Integration Test Suites Summary:**
- **Data Persistence Test Suite:** 4 test cases (TC_IT_001 to TC_IT_004)
- **Component Integration Test Suite:** 4 test cases (TC_IT_005 to TC_IT_008)
- **API Integration Test Suite:** 3 test cases (TC_IT_009 to TC_IT_011)
- **Total Integration Tests: 11 test cases**

**System Test Suites Summary:**
- **Portfolio Workflow Test Suite:** 3 test cases (TC_ST_001 to TC_ST_003)
- **Conversion Workflow Test Suite:** 3 test cases (TC_ST_004 to TC_ST_006)
- **Settings Workflow Test Suite:** 3 test cases (TC_ST_007 to TC_ST_009)
- **Total System Tests: 9 test cases**

**Complete Test Suite Summary:**
- **Unit Tests:** 41 test cases
- **Integration Tests:** 11 test cases
- **System Tests:** 9 test cases
- **Total Test Cases: 61 test cases**

**Coverage Summary:**
- **Black Box Coverage:** Equivalence Partitioning, Boundary Value Analysis, State Transition Testing
- **White Box Coverage:** Statement, Branch, Condition, Path, Loop Coverage
- **Integration Coverage:** Component interactions, Data persistence, API integration
- **System Coverage:** End-to-end workflows, User scenarios, Error handling

---

### 3.11 Mutation Testing

**Purpose:** Test the quality of test cases by introducing faults (mutations) into the code and verifying that tests detect these faults.

#### 3.11.1 Mutation Testing Strategy

**Mutation Testing** evaluates test suite effectiveness by creating mutant versions of the code (with intentional bugs) and checking if tests can detect these mutants. A mutant is "killed" if at least one test fails; if all tests pass, the mutant "survives" (indicating weak test coverage).

**Mutation Operators for Crypto Checker:**

1. **Arithmetic Operator Replacement (AOR)**
   - Replace `+` with `-`, `*`, `/`
   - Replace `-` with `+`, `*`, `/`
   - Example: `value += gains` → `value -= gains`

2. **Relational Operator Replacement (ROR)**
   - Replace `>` with `>=`, `<`, `<=`, `==`, `!=`
   - Replace `>=` with `>`, `<`, `<=`, `==`, `!=`
   - Example: `if (gains >= 0)` → `if (gains > 0)`

3. **Conditional Operator Replacement (COR)**
   - Replace `&&` with `||`
   - Replace `||` with `&&`
   - Example: `if (price > 0 && amount > 0)` → `if (price > 0 || amount > 0)`

4. **Constant Replacement (CRP)**
   - Replace constants with other values
   - Example: `0.0` → `1.0`, `100.0` → `0.0`

5. **Statement Deletion (SDL)**
   - Delete statements
   - Example: Remove `value += coin.portfolio_value;`

6. **Return Value Replacement (RVR)**
   - Change return values
   - Example: `return true;` → `return false;`

#### 3.11.2 Mutation Test Cases

**Test Case TC_MT_001: Portfolio Calculation - Arithmetic Mutation**
- **Test Level:** Mutation
- **Test Type:** Mutation Testing
- **Objective:** Kill mutants in portfolio calculation arithmetic
- **Mutation:** Change `value += coin.portfolio_value;` to `value -= coin.portfolio_value;`
- **Test:** TC_PF_001 (Calculate Portfolio Value - Valid Input)
- **Expected:** Test should fail (detect mutation)
- **Priority:** High

**Test Case TC_MT_002: Gains Condition - Relational Mutation**
- **Test Level:** Mutation
- **Test Type:** Mutation Testing
- **Objective:** Kill mutants in gains condition check
- **Mutation:** Change `if (gains >= 0)` to `if (gains > 0)`
- **Test:** TC_PF_006 (Positive Gains), TC_PF_007 (Negative Gains)
- **Expected:** TC_PF_006 should fail when gains = 0 (boundary case)
- **Priority:** High

**Test Case TC_MT_003: Input Validation - Conditional Mutation**
- **Test Level:** Mutation
- **Test Type:** Mutation Testing
- **Objective:** Kill mutants in validation logic
- **Mutation:** Change `if (price > 0 && amount > 0)` to `if (price > 0 || amount > 0)`
- **Test:** TC_DV_001, TC_DV_004 (Validation tests)
- **Expected:** Tests should fail (detect mutation allows invalid input)
- **Priority:** High

**Test Case TC_MT_004: Currency Conversion - Constant Mutation**
- **Test Level:** Mutation
- **Test Type:** Mutation Testing
- **Objective:** Kill mutants in conversion constants
- **Mutation:** Change `priceCurrency2 == 0.0` to `priceCurrency2 == 1.0`
- **Test:** TC_CV_001 (Crypto to Fiat conversion)
- **Expected:** Test should fail (detect mutation breaks fiat conversion)
- **Priority:** Medium

**Test Case TC_MT_005: Theme Switch - Statement Deletion Mutation**
- **Test Level:** Mutation
- **Test Type:** Mutation Testing
- **Objective:** Kill mutants with deleted statements
- **Mutation:** Delete `background = Color.WHITE;` in LIGHT theme case
- **Test:** TC_TH_001 (Theme switching tests)
- **Expected:** Test should fail (detect missing statement)
- **Priority:** Medium

**Mutation Score Goal:** ≥ 80% (at least 80% of mutants should be killed by test suite)

---

### 3.12 Performance Testing

**Purpose:** Test system performance under various conditions, including response times, resource usage, and scalability.

#### 3.12.1 Performance Testing Strategy

**Performance Testing** evaluates system behavior under load, measures response times, and identifies performance bottlenecks.

**Performance Metrics:**
1. **Response Time:** Time to complete operations (API fetch, calculations, UI updates)
2. **Throughput:** Number of operations per second
3. **Resource Usage:** CPU, memory consumption
4. **Scalability:** Performance with large datasets

#### 3.12.2 Performance Test Cases

**Test Case TC_PT_001: API Fetch Performance - Response Time**
- **Test Level:** Performance
- **Test Type:** Performance Testing
- **Requirement:** FR1.1
- **Objective:** Measure API fetch response time
- **Preconditions:** Internet connection available
- **Test Steps:**
  1. Start timer
  2. Call WebData.fetch()
  3. Wait for completion
  4. Stop timer
  5. Record response time
- **Performance Criteria:**
  - **Target:** API fetch completes within 5 seconds
  - **Acceptable:** API fetch completes within 10 seconds
  - **Unacceptable:** API fetch takes > 15 seconds
- **Priority:** High

**Test Case TC_PT_002: Portfolio Calculation Performance - Large Portfolio**
- **Test Level:** Performance
- **Test Type:** Performance Testing
- **Requirement:** FR2.4
- **Objective:** Measure calculation time with large portfolio
- **Preconditions:** Portfolio with 100+ coins
- **Test Steps:**
  1. Create portfolio with 100 coins
  2. Start timer
  3. Call calculatePortfolio()
  4. Stop timer
  5. Record calculation time
- **Performance Criteria:**
  - **Target:** Calculation completes within 100ms
  - **Acceptable:** Calculation completes within 500ms
  - **Unacceptable:** Calculation takes > 1 second
- **Priority:** Medium

**Test Case TC_PT_003: Currency Conversion Performance - Real-time Updates**
- **Test Level:** Performance
- **Test Type:** Performance Testing
- **Requirement:** FR3.3
- **Objective:** Measure conversion calculation time during real-time input
- **Preconditions:** Converter with currencies selected
- **Test Steps:**
  1. Start timer
  2. Enter amount in currency1 field
  3. Measure time until currency2 field updates
  4. Repeat with multiple rapid inputs
- **Performance Criteria:**
  - **Target:** Conversion updates within 50ms
  - **Acceptable:** Conversion updates within 200ms
  - **Unacceptable:** Conversion takes > 500ms (noticeable lag)
- **Priority:** High

**Test Case TC_PT_004: Serialization Performance - Large Data Set**
- **Test Level:** Performance
- **Test Type:** Performance Testing
- **Requirement:** FR5.2, FR5.3
- **Objective:** Measure serialization time for large datasets
- **Preconditions:** Portfolio with 50+ coins, multiple portfolios
- **Test Steps:**
  1. Create large dataset (50 coins, 5 portfolios)
  2. Start timer
  3. Call serializePortfolio()
  4. Stop timer
  5. Record serialization time
- **Performance Criteria:**
  - **Target:** Serialization completes within 200ms
  - **Acceptable:** Serialization completes within 1 second
  - **Unacceptable:** Serialization takes > 2 seconds
- **Priority:** Medium

**Test Case TC_PT_005: Deserialization Performance - Startup Time**
- **Test Level:** Performance
- **Test Type:** Performance Testing
- **Requirement:** FR5.5
- **Objective:** Measure application startup time (deserialization)
- **Preconditions:** Saved data files exist
- **Test Steps:**
  1. Start timer
  2. Launch application
  3. Wait for all data loaded (deserialization complete)
  4. Stop timer
  5. Record startup time
- **Performance Criteria:**
  - **Target:** Application starts within 2 seconds
  - **Acceptable:** Application starts within 5 seconds
  - **Unacceptable:** Application takes > 10 seconds
- **Priority:** High

**Test Case TC_PT_006: Memory Usage - Large Portfolio**
- **Test Level:** Performance
- **Test Type:** Performance Testing
- **Requirement:** FR2.1
- **Objective:** Measure memory consumption with large portfolios
- **Preconditions:** Application running
- **Test Steps:**
  1. Record baseline memory usage
  2. Create portfolio with 100 coins
  3. Record memory usage
  4. Create 10 portfolios with 50 coins each
  5. Record peak memory usage
- **Performance Criteria:**
  - **Target:** Memory usage < 200MB for 100 coins
  - **Acceptable:** Memory usage < 500MB for 100 coins
  - **Unacceptable:** Memory usage > 1GB (memory leak suspected)
- **Priority:** Medium

**Test Case TC_PT_007: UI Responsiveness - Theme Switching**
- **Test Level:** Performance
- **Test Type:** Performance Testing
- **Requirement:** FR4.1
- **Objective:** Measure UI update time when switching themes
- **Preconditions:** Application with all panels visible
- **Test Steps:**
  1. Start timer
  2. Switch theme from LIGHT to DARK
  3. Wait for all panels to update
  4. Stop timer
  5. Record update time
- **Performance Criteria:**
  - **Target:** Theme switch completes within 100ms
  - **Acceptable:** Theme switch completes within 500ms
  - **Unacceptable:** Theme switch takes > 1 second (noticeable lag)
- **Priority:** Low

**Test Case TC_PT_008: Concurrent Operations - Multiple Refreshes**
- **Test Level:** Performance
- **Test Type:** Performance Testing
- **Requirement:** FR1.1, FR2.4
- **Objective:** Test system behavior under concurrent operations
- **Preconditions:** Application running
- **Test Steps:**
  1. Trigger portfolio refresh
  2. Simultaneously trigger coin data refresh
  3. Simultaneously trigger converter refresh
  4. Measure total completion time
  5. Verify no data corruption
- **Performance Criteria:**
  - **Target:** All operations complete within 10 seconds
  - **Acceptable:** All operations complete within 20 seconds
  - **Unacceptable:** Operations fail or data corrupted
- **Priority:** Medium

---

### 3.13 Updated Test Case Summary (Complete Section 3)

**Complete Test Suite Summary:**
- **Unit Tests:** 41 test cases
- **Integration Tests:** 11 test cases
- **System Tests:** 9 test cases
- **Mutation Tests:** 5 test cases
- **Performance Tests:** 8 test cases
- **Total Test Cases: 74 test cases**

**Coverage Summary:**
- **Black Box Coverage:** Equivalence Partitioning, Boundary Value Analysis, State Transition Testing
- **White Box Coverage:** Statement, Branch, Condition, Path, Loop Coverage
- **Integration Coverage:** Component interactions, Data persistence, API integration
- **System Coverage:** End-to-end workflows, User scenarios, Error handling
- **Mutation Coverage:** Test suite quality validation (≥80% mutation score goal)
- **Performance Coverage:** Response time, throughput, resource usage, scalability

---

## 4. Requirements Traceability Matrix

The Requirements Traceability Matrix (RTM) provides a clear mapping between functional requirements and test cases, ensuring complete coverage of all requirements. This matrix helps verify that every requirement is tested and identifies any gaps in test coverage.

### 4.1 Traceability Matrix Overview

**Purpose:**
- Ensure all functional requirements are covered by test cases
- Identify requirements with insufficient test coverage
- Track test case coverage for each requirement
- Support test planning and test execution tracking

**Coverage Metrics:**
- **Total Functional Requirements:** 38 requirements (FR1.1 - FR6.5)
- **Total Test Cases:** 74 test cases
- **Coverage Goal:** 100% requirement coverage (87% automated, 13% manual)

### 4.2 Requirements Traceability Matrix

| Requirement ID | Requirement Description | Test Case IDs | Test Level | Test Type | Coverage Status |
|---------------|------------------------|---------------|------------|-----------|-----------------|
| **FR1: Cryptocurrency Data Display** |
| FR1.1 | Fetch and display cryptocurrency data from CoinGecko API | TC_IT_009, TC_IT_010, TC_IT_011, TC_PT_001 | Integration, Performance | Black Box, White Box | ✅ Covered |
| FR1.2 | Display rank, name, price, percentage changes, market cap | TC_CD_001, TC_CD_002, TC_CD_003, TC_CD_004, TC_CD_007 | Unit | White Box, Black Box | ✅ Covered |
| FR1.3 | Support searching/filtering cryptocurrencies by name | *[UI Testing - Manual]* | System | Black Box | ⚠️ Manual |
| FR1.4 | Allow sorting of cryptocurrency data by different columns | *[UI Testing - Manual]* | System | Black Box | ⚠️ Manual |
| FR1.5 | Display detailed information when cryptocurrency is selected | TC_CD_004, TC_CD_005 | Unit | White Box | ✅ Covered |
| **FR2: Portfolio Management** |
| FR2.1 | Allow users to create multiple portfolios | TC_ST_002, TC_PT_006 | System, Performance | Black Box | ✅ Covered |
| FR2.2 | Allow users to add cryptocurrencies to portfolio with specified amounts | TC_ST_001, TC_CD_006 | System, Unit | Black Box | ✅ Covered |
| FR2.3 | Allow users to set starting price (per piece, total value, or current value) | TC_ST_001 | System | Black Box | ✅ Covered |
| FR2.4 | Calculate and display portfolio total value | TC_PF_001, TC_PF_002, TC_PF_004, TC_PF_005, TC_PF_008, TC_PF_009, TC_PF_010, TC_IT_005, TC_IT_008, TC_ST_001, TC_ST_003, TC_PT_002 | Unit, Integration, System, Performance | Black Box, White Box | ✅ Covered |
| FR2.5 | Calculate and display portfolio gains/losses (absolute and percentage) | TC_PF_006, TC_PF_007, TC_PF_011, TC_PF_012, TC_CD_005, TC_ST_001, TC_ST_003 | Unit, System | Black Box, White Box | ✅ Covered |
| FR2.6 | Allow users to remove cryptocurrencies from portfolio | TC_ST_001 | System | Black Box | ✅ Covered |
| FR2.7 | Allow users to switch between multiple portfolios | TC_ST_002 | System | Black Box | ✅ Covered |
| FR2.8 | Allow users to rename portfolios | TC_DV_008, TC_ST_002 | Unit, System | White Box, Black Box | ✅ Covered |
| FR2.9 | Allow users to delete portfolios (minimum one portfolio must exist) | TC_DV_009, TC_DV_010, TC_ST_002 | Unit, System | White Box, Black Box | ✅ Covered |
| FR2.10 | Prevent adding duplicate cryptocurrencies to the same portfolio | TC_DV_006, TC_DV_007 | Unit | White Box | ✅ Covered |
| **FR3: Currency Conversion** |
| FR3.1 | Allow conversion between two cryptocurrencies | TC_CV_002, TC_CV_003, TC_CV_006, TC_ST_004 | Unit, System | Black Box, White Box | ✅ Covered |
| FR3.2 | Allow conversion from cryptocurrency to fiat currency | TC_CV_001, TC_ST_005 | Unit, System | Black Box | ✅ Covered |
| FR3.3 | Display real-time conversion rates | TC_CV_001, TC_CV_002, TC_CV_004, TC_CV_005, TC_ST_004, TC_ST_005, TC_PT_003 | Unit, System, Performance | Black Box, White Box | ✅ Covered |
| FR3.4 | Allow switching the source and target currencies | TC_ST_004 | System | Black Box | ✅ Covered |
| FR3.5 | Display global market data (total market cap, 24h volume, Bitcoin dominance) | TC_ST_006 | System | Black Box | ✅ Covered |
| **FR4: Settings and Customization** |
| FR4.1 | Support three theme modes: Light, Dark, and Custom | TC_TH_001, TC_TH_002, TC_TH_003, TC_TH_004, TC_IT_006, TC_ST_007, TC_ST_008, TC_PT_007 | Unit, Integration, System, Performance | Black Box, White Box | ✅ Covered |
| FR4.2 | Allow users to customize colors for custom theme | TC_TH_005, TC_TH_006, TC_ST_008 | Unit, System | White Box, Black Box | ✅ Covered |
| FR4.3 | Support 30+ fiat currencies | TC_IT_007, TC_ST_007 | Integration, System | Black Box | ✅ Covered |
| FR4.4 | Allow users to toggle debug mode on/off | *[Settings Testing - Manual]* | System | Black Box | ⚠️ Manual |
| FR4.5 | Allow users to view application logs | *[Settings Testing - Manual]* | System | Black Box | ⚠️ Manual |
| FR4.6 | Allow users to reset settings to default | *[Settings Testing - Manual]* | System | Black Box | ⚠️ Manual |
| FR4.7 | Allow users to delete stored data | TC_ST_009 | System | Black Box | ✅ Covered |
| **FR5: Data Persistence** |
| FR5.1 | Serialize and save cryptocurrency data to disk | TC_IT_009 | Integration | Black Box | ✅ Covered |
| FR5.2 | Serialize and save portfolio data to disk | TC_IT_001, TC_ST_001, TC_PT_004 | Integration, System, Performance | Black Box | ✅ Covered |
| FR5.3 | Serialize and save settings (theme, currency, debug mode) to disk | TC_IT_002, TC_ST_007, TC_PT_004 | Integration, System, Performance | Black Box | ✅ Covered |
| FR5.4 | Serialize and save converter state to disk | TC_IT_003 | Integration | Black Box | ✅ Covered |
| FR5.5 | Deserialize and load saved data on application startup | TC_IT_001, TC_IT_002, TC_IT_003, TC_ST_001, TC_ST_007, TC_PT_005 | Integration, System, Performance | Black Box | ✅ Covered |
| FR5.6 | Handle missing or corrupted data files gracefully | TC_IT_004 | Integration | White Box | ✅ Covered |
| **FR6: Error Handling and Validation** |
| FR6.1 | Validate numeric input for portfolio amounts and prices | TC_PF_002, TC_PF_003, TC_DV_001, TC_DV_002, TC_DV_003, TC_DV_004, TC_DV_005 | Unit | Black Box, White Box | ✅ Covered |
| FR6.2 | Handle API connection failures gracefully | TC_IT_009, TC_IT_011 | Integration | Black Box, White Box | ✅ Covered |
| FR6.3 | Handle rate limiting (HTTP 429) from API | TC_IT_010 | Integration | White Box | ✅ Covered |
| FR6.4 | Display appropriate error messages to users | TC_DV_002, TC_IT_011 | Unit, Integration | Black Box, White Box | ✅ Covered |
| FR6.5 | Prevent invalid operations (e.g., deleting last portfolio) | TC_DV_009, TC_ST_002 | Unit, System | White Box, Black Box | ✅ Covered |

### 4.3 Coverage Summary by Requirement Category

| Requirement Category | Total Requirements | Covered by Tests | Manual Testing | Coverage % |
|---------------------|-------------------|------------------|----------------|------------|
| FR1: Cryptocurrency Data Display | 5 | 3 | 2 | 60% (3/5 automated) |
| FR2: Portfolio Management | 10 | 10 | 0 | 100% |
| FR3: Currency Conversion | 5 | 5 | 0 | 100% |
| FR4: Settings and Customization | 7 | 4 | 3 | 57% (4/7 automated) |
| FR5: Data Persistence | 6 | 6 | 0 | 100% |
| FR6: Error Handling and Validation | 5 | 5 | 0 | 100% |
| **Total** | **38** | **33** | **5** | **87% Automated** |

### 4.4 Test Coverage Analysis

**Fully Automated Requirements (33 requirements):**
- All portfolio management features (FR2.1 - FR2.10)
- All currency conversion features (FR3.1 - FR3.5)
- All data persistence features (FR5.1 - FR5.6)
- All error handling features (FR6.1 - FR6.5)
- Core cryptocurrency data display (FR1.1, FR1.2, FR1.5)
- Core settings features (FR4.1, FR4.2, FR4.3, FR4.7)

**Manual Testing Required (5 requirements):**
- FR1.3: Search/filter functionality (UI interaction)
- FR1.4: Sorting functionality (UI interaction)
- FR4.4: Debug mode toggle (UI interaction)
- FR4.5: View logs (UI interaction)
- FR4.6: Reset settings (UI interaction)

**Note:** Manual testing requirements are primarily UI interactions that are difficult to automate with JUnit. These can be tested manually or with UI automation tools like Selenium or TestFX.

### 4.5 Test Case Distribution by Requirement

**Most Tested Requirements:**
1. **FR2.4 (Portfolio Value Calculation):** 12 test cases
   - Unit tests: 8 cases
   - Integration tests: 2 cases
   - System tests: 2 cases
   - Performance tests: 1 case

2. **FR2.5 (Portfolio Gains/Losses):** 7 test cases
   - Unit tests: 4 cases
   - System tests: 2 cases
   - Integration tests: 1 case

3. **FR4.1 (Theme Modes):** 8 test cases
   - Unit tests: 4 cases
   - Integration tests: 1 case
   - System tests: 2 cases
   - Performance tests: 1 case

4. **FR3.3 (Real-time Conversion Rates):** 7 test cases
   - Unit tests: 5 cases
   - System tests: 2 cases
   - Performance tests: 1 case

### 4.6 Requirements Coverage by Test Level

| Test Level | Requirements Covered | Test Cases | Coverage % |
|------------|---------------------|------------|------------|
| Unit Tests | 25 requirements | 41 test cases | 66% |
| Integration Tests | 12 requirements | 11 test cases | 32% |
| System Tests | 18 requirements | 9 test cases | 47% |
| Mutation Tests | 5 requirements | 5 test cases | 13% |
| Performance Tests | 8 requirements | 8 test cases | 21% |

### 4.7 Traceability Matrix Benefits

1. **Complete Coverage Verification:** Ensures all requirements have corresponding test cases
2. **Gap Analysis:** Identifies requirements without adequate test coverage
3. **Test Planning:** Helps prioritize test execution based on requirement criticality
4. **Change Impact Analysis:** When requirements change, easily identify affected test cases
5. **Compliance:** Demonstrates requirement coverage for quality assurance
6. **Maintenance:** Track which tests need updating when requirements are modified

### 4.8 Requirements Not Covered by Automated Tests

The following requirements require manual testing or UI automation tools:

| Requirement ID | Requirement | Reason | Recommended Testing Approach |
|---------------|-------------|--------|------------------------------|
| FR1.3 | Search/filter cryptocurrencies | UI interaction with search field | Manual testing or Selenium/TestFX |
| FR1.4 | Sort cryptocurrency data | UI interaction with table headers | Manual testing or Selenium/TestFX |
| FR4.4 | Toggle debug mode | UI button interaction | Manual testing |
| FR4.5 | View application logs | File system interaction | Manual testing |
| FR4.6 | Reset settings to default | UI button interaction | Manual testing |

**Recommendation:** Consider implementing UI automation tests using tools like:
- **TestFX** (for JavaFX/Swing applications)
- **Selenium** (for web-based UIs)
- **JUnit + Robot** (for Swing GUI testing)

### 4.9 Traceability Matrix Maintenance

**Update Frequency:**
- Update matrix when new requirements are added
- Update matrix when requirements are modified
- Update matrix when test cases are added, modified, or removed
- Review matrix during test planning and test review sessions

**Version Control:**
- Maintain version history of traceability matrix
- Document changes and rationale for updates
- Include matrix in test documentation repository

---

## 5. Black Box Test Implementation (JUnit)

*[To be filled in next]*

---

## 6. Evidence (Screenshots, Logs, Execution Results)

*[To be filled in next]*

