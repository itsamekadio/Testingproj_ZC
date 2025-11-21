# Manual Black Box Test Cases - Crypto Checker Application

## Overview
This document contains **5 Manual Black Box Test Cases** that require UI interaction. These tests are identified in the Testing Documentation as manual tests because they involve GUI elements that are difficult to automate with JUnit alone.

---

## Test Environment Setup

### Prerequisites
1. **Java JDK 8** installed and configured
2. **Maven** installed (for building the application)
3. **Crypto Checker Application** built and ready to run

### Build and Run Instructions
```bash
# Navigate to project directory
cd Testingproj_ZC

# Build the application
$env:JAVA_HOME="C:\Program Files\Java\jdk-1.8"; $env:PATH="C:\Program Files\Java\jdk-1.8\bin;$env:PATH"; mvn clean package

# Run the application
java -jar target/crypto-checker-1.1-no-dependencies.jar

# OR use the helper script (easier)
.\build_and_run.ps1 build
```

---

## Manual Test Cases

### 📋 Test Case 1: Search/Filter Cryptocurrencies (FR1.3)

**Test Case ID:** TC_UI_001  
**Requirement:** FR1.3 - The system shall support searching/filtering cryptocurrencies by name  
**Test Level:** System  
**Test Type:** Black Box (UI Interaction)  
**Priority:** High  
**Objective:** Verify that users can search and filter cryptocurrencies by name in the coin list

#### Preconditions:
- Application is running
- Internet connection is available (to fetch cryptocurrency data)
- Coin panel is visible with cryptocurrency list loaded

#### Test Steps:

1. **Launch the Application**
   - Start the Crypto Checker application
   - Wait for the application to fully load

2. **Navigate to Coin Panel**
   - Click on the "Coin" or "Cryptocurrencies" tab/button
   - Verify that a list of cryptocurrencies is displayed

3. **Locate the Search Field**
   - Find the search/filter text field (usually at the top of the coin list)
   - Verify the search field is visible and clickable

4. **Test Search Functionality - Valid Search**
   - Click in the search field
   - Type "Bitcoin" (or "BTC")
   - **Expected Result:** The list should filter to show only Bitcoin-related entries
   - **Actual Result:** _______________

5. **Test Search Functionality - Partial Search**
   - Clear the search field
   - Type "eth"
   - **Expected Result:** The list should show Ethereum and other coins starting with "eth"
   - **Actual Result:** _______________

6. **Test Search Functionality - Case Insensitivity**
   - Clear the search field
   - Type "BITCOIN" (uppercase)
   - **Expected Result:** The list should still filter to show Bitcoin
   - **Actual Result:** _______________

7. **Test Search Functionality - No Results**
   - Clear the search field
   - Type "XYZ123NonExistentCoin"
   - **Expected Result:** The list should be empty or show "No results found"
   - **Actual Result:** _______________

8. **Test Clear Search**
   - Clear the search field (delete all text)
   - **Expected Result:** All cryptocurrencies should be displayed again
   - **Actual Result:** _______________

#### Expected Results:
- ✅ Search field is responsive and accepts input
- ✅ List filters in real-time as user types
- ✅ Search is case-insensitive
- ✅ Partial matches are shown
- ✅ Clearing search restores full list
- ✅ No errors or crashes occur during search

#### Pass/Fail Criteria:
- **PASS:** All filtering operations work correctly and display expected results
- **FAIL:** Any filtering operation fails, crashes, or shows incorrect results

#### Test Status: ⬜ Not Run | ⬜ Pass | ⬜ Fail

#### Notes/Defects:
```
_____________________________________________________________
_____________________________________________________________
```

---

### 📋 Test Case 2: Sort Cryptocurrency Data (FR1.4)

**Test Case ID:** TC_UI_002  
**Requirement:** FR1.4 - The system shall allow sorting of cryptocurrency data by different columns  
**Test Level:** System  
**Test Type:** Black Box (UI Interaction)  
**Priority:** High  
**Objective:** Verify that users can sort cryptocurrency data by clicking on column headers

#### Preconditions:
- Application is running
- Internet connection is available
- Coin panel is visible with cryptocurrency list displayed in a table format

#### Test Steps:

1. **Launch the Application**
   - Start the Crypto Checker application
   - Navigate to the Coin panel

2. **Verify Initial State**
   - Observe the default sorting of the cryptocurrency list
   - Note the current order (usually by rank or default order)

3. **Test Sort by Rank**
   - Click on the "Rank" column header
   - **Expected Result:** List sorts by rank in ascending order (1, 2, 3...)
   - **Actual Result:** _______________
   - Click on "Rank" header again
   - **Expected Result:** List sorts by rank in descending order
   - **Actual Result:** _______________

4. **Test Sort by Name**
   - Click on the "Name" or "Coin" column header
   - **Expected Result:** List sorts alphabetically by name (A-Z)
   - **Actual Result:** _______________
   - Click on "Name" header again
   - **Expected Result:** List sorts in reverse alphabetical order (Z-A)
   - **Actual Result:** _______________

5. **Test Sort by Price**
   - Click on the "Price" column header
   - **Expected Result:** List sorts by price in ascending order (lowest to highest)
   - **Actual Result:** _______________
   - Click on "Price" header again
   - **Expected Result:** List sorts by price in descending order (highest to lowest)
   - **Actual Result:** _______________

6. **Test Sort by Percentage Changes**
   - Click on the "1h %" column header
   - **Expected Result:** List sorts by 1-hour percentage change
   - **Actual Result:** _______________
   - Repeat for "24h %" and "7d %" columns
   - **Expected Results:** Each column sorts correctly
   - **Actual Results:** _______________

7. **Test Sort by Market Cap**
   - Click on the "Market Cap" column header
   - **Expected Result:** List sorts by market capitalization
   - **Actual Result:** _______________
   - Click again for reverse order
   - **Expected Result:** Reverse sort works correctly
   - **Actual Result:** _______________

8. **Test Sort Persistence During Search**
   - Sort by price (descending)
   - Enter "bit" in search field
   - **Expected Result:** Filtered results maintain price sorting
   - **Actual Result:** _______________

#### Expected Results:
- ✅ All column headers are clickable
- ✅ Clicking a column header sorts the data by that column
- ✅ Clicking the same header again reverses the sort order
- ✅ Sort indicators (arrows) show current sort direction
- ✅ Sorting works correctly with filtered/searched results
- ✅ Data accuracy is maintained after sorting

#### Pass/Fail Criteria:
- **PASS:** All sorting operations work correctly for all columns
- **FAIL:** Any column fails to sort, sorts incorrectly, or causes errors

#### Test Status: ⬜ Not Run | ⬜ Pass | ⬜ Fail

#### Notes/Defects:
```
_____________________________________________________________
_____________________________________________________________
```

---

### 📋 Test Case 3: Toggle Debug Mode (FR4.4)

**Test Case ID:** TC_UI_003  
**Requirement:** FR4.4 - The system shall allow users to toggle debug mode on/off  
**Test Level:** System  
**Test Type:** Black Box (UI Interaction)  
**Priority:** Medium  
**Objective:** Verify that users can toggle debug mode on and off through the settings panel

#### Preconditions:
- Application is running
- Settings panel is accessible

#### Test Steps:

1. **Launch the Application**
   - Start the Crypto Checker application
   - Note if any debug window is visible initially

2. **Navigate to Settings Panel**
   - Click on the "Settings" tab/button
   - Verify the Settings panel is displayed

3. **Locate Debug Mode Toggle**
   - Find the "Debug Mode" button or toggle switch
   - Note the current state (should show "Off" or "On")

4. **Test Enable Debug Mode**
   - Click the Debug Mode button to turn it **ON**
   - **Expected Results:**
     - Button text changes to "On"
     - A debug window/console appears
     - Debug messages start appearing in the console
   - **Actual Result:** _______________

5. **Test Debug Window Functionality**
   - Perform various actions in the application (navigate panels, add portfolio item, convert currency)
   - **Expected Result:** Debug messages appear in the debug window showing actions taken
   - **Actual Result:** _______________

6. **Test Disable Debug Mode**
   - Click the Debug Mode button to turn it **OFF**
   - **Expected Results:**
     - Button text changes to "Off"
     - Debug window closes or becomes hidden
     - Debug messages stop appearing
   - **Actual Result:** _______________

7. **Test Debug Mode Persistence**
   - Enable debug mode (turn it ON)
   - Close the application
   - Restart the application
   - Navigate to Settings panel
   - **Expected Result:** Debug mode should be ON and debug window should appear
   - **Actual Result:** _______________

8. **Test Toggle Multiple Times**
   - Toggle debug mode ON and OFF repeatedly (5 times)
   - **Expected Result:** Each toggle works correctly without errors
   - **Actual Result:** _______________

#### Expected Results:
- ✅ Debug mode button is visible and clickable
- ✅ Button shows current state ("On" or "Off")
- ✅ Toggling ON opens debug window
- ✅ Toggling OFF closes debug window
- ✅ Debug messages appear only when mode is ON
- ✅ Debug mode setting persists across application restarts
- ✅ No crashes or errors when toggling

#### Pass/Fail Criteria:
- **PASS:** Debug mode toggles correctly, window appears/disappears as expected, setting persists
- **FAIL:** Toggle doesn't work, window doesn't appear/disappear, or errors occur

#### Test Status: ⬜ Not Run | ⬜ Pass | ⬜ Fail

#### Notes/Defects:
```
_____________________________________________________________
_____________________________________________________________
```

---

### 📋 Test Case 4: View Application Logs (FR4.5)

**Test Case ID:** TC_UI_004  
**Requirement:** FR4.5 - The system shall allow users to view application logs  
**Test Level:** System  
**Test Type:** Black Box (UI Interaction)  
**Priority:** Medium  
**Objective:** Verify that users can view application logs through the settings panel

#### Preconditions:
- Application is running and has generated some log entries
- Settings panel is accessible
- System has a default text editor configured

#### Test Steps:

1. **Launch the Application**
   - Start the Crypto Checker application
   - Perform several actions to generate log entries (navigate panels, add portfolio items, etc.)

2. **Navigate to Settings Panel**
   - Click on the "Settings" tab/button
   - Verify the Settings panel is displayed

3. **Locate View Logs Button**
   - Find the "View Logs" button
   - Verify the button is visible and enabled

4. **Test View Logs Functionality**
   - Click the "View Logs" button
   - **Expected Results:**
     - System default text editor opens
     - A log file is displayed (likely named `crypto-checker.log` or similar)
     - Log file contains application events and messages
   - **Actual Result:** _______________

5. **Verify Log Content**
   - Review the log file content
   - **Expected Results:**
     - Logs contain timestamps
     - Logs contain application events (startup, panel navigation, etc.)
     - Logs contain any errors or warnings that occurred
     - Recent actions you performed are logged
   - **Actual Result:** _______________

6. **Test Log Location**
   - Note the file path shown in the text editor or file explorer
   - **Expected Result:** Log file is in the application directory or user's app data folder
   - **Actual Result:** _______________

7. **Test Multiple Log Views**
   - Close the log file
   - Perform more actions in the application
   - Click "View Logs" button again
   - **Expected Result:** Log file opens again with new entries appended
   - **Actual Result:** _______________

8. **Test Error Handling**
   - If possible, make the log file read-only or move it
   - Try to view logs
   - **Expected Result:** Appropriate error message or the log opens anyway
   - **Actual Result:** _______________

#### Expected Results:
- ✅ View Logs button is visible and clickable
- ✅ Clicking button opens log file in text editor
- ✅ Log file contains relevant application events
- ✅ Log entries have timestamps
- ✅ Recent actions are logged
- ✅ Log file can be viewed multiple times
- ✅ No errors when viewing logs

#### Pass/Fail Criteria:
- **PASS:** Log file opens successfully and contains relevant, timestamped application events
- **FAIL:** Log file doesn't open, is empty, corrupted, or button causes errors

#### Test Status: ⬜ Not Run | ⬜ Pass | ⬜ Fail

#### Notes/Defects:
```
_____________________________________________________________
_____________________________________________________________
```

---

### 📋 Test Case 5: Reset Settings to Default (FR4.6)

**Test Case ID:** TC_UI_005  
**Requirement:** FR4.6 - The system shall allow users to reset settings to default  
**Test Level:** System  
**Test Type:** Black Box (UI Interaction)  
**Priority:** Medium  
**Objective:** Verify that users can reset all application settings to their default values

#### Preconditions:
- Application is running
- Settings have been modified from defaults

#### Test Steps:

1. **Launch the Application**
   - Start the Crypto Checker application

2. **Modify Settings**
   - Navigate to Settings panel
   - Change the following settings from defaults:
     - **Theme:** Change to "Dark" (default is "Light")
     - **Currency:** Change to "EUR" (default is "USD")
     - **Debug Mode:** Turn ON (default is OFF)
   - Note all the changes made

3. **Navigate Other Panels**
   - Go to Coin panel - verify dark theme is applied
   - Go to Portfolio panel - verify EUR currency is shown
   - Verify debug window is visible
   - **Expected Result:** All modified settings are active
   - **Actual Result:** _______________

4. **Return to Settings Panel**
   - Navigate back to Settings panel
   - Locate the "Reset Settings" button

5. **Test Reset Settings**
   - Click the "Reset Settings" button
   - **Expected Results:**
     - Confirmation dialog may appear (if implemented)
     - Theme changes to "Light"
     - Currency changes to "USD"
     - Debug mode turns OFF (debug window closes)
     - Settings panel shows default values
   - **Actual Result:** _______________

6. **Verify Reset on Other Panels**
   - Navigate to Coin panel
   - **Expected Result:** Light theme is applied
   - **Actual Result:** _______________
   - Navigate to Portfolio panel
   - **Expected Result:** Currency is shown as USD ($)
   - **Actual Result:** _______________
   - Check for debug window
   - **Expected Result:** Debug window is closed/hidden
   - **Actual Result:** _______________

7. **Test Custom Theme Reset**
   - Navigate to Settings panel
   - Change theme to "Custom"
   - Modify custom colors (change all 6 color properties)
   - Click "Apply & Select"
   - Verify custom theme is applied
   - Click "Reset Settings"
   - **Expected Results:**
     - Theme returns to "Light"
     - Custom colors are reset to defaults
     - All panels show light theme
   - **Actual Result:** _______________

8. **Test Reset Persistence**
   - After resetting settings, close the application
   - Restart the application
   - Navigate to Settings panel
   - **Expected Result:** Settings remain at default values (not reverted to pre-reset values)
   - **Actual Result:** _______________

9. **Test Multiple Resets**
   - Modify settings again
   - Reset settings
   - Repeat 2-3 times
   - **Expected Result:** Reset works correctly each time
   - **Actual Result:** _______________

#### Expected Results:
- ✅ Reset Settings button is visible and clickable
- ✅ All settings return to default values:
  - Theme: Light
  - Currency: USD ($)
  - Debug Mode: OFF
  - Custom colors: Default values
- ✅ Changes are immediately visible across all panels
- ✅ Reset persists after application restart
- ✅ No crashes or errors when resetting
- ✅ Data (portfolios, converter state) is NOT deleted

#### Pass/Fail Criteria:
- **PASS:** All settings reset to defaults, changes persist, no data loss, no errors
- **FAIL:** Settings don't reset, some settings remain changed, data is lost, or errors occur

#### Test Status: ⬜ Not Run | ⬜ Pass | ⬜ Fail

#### Notes/Defects:
```
_____________________________________________________________
_____________________________________________________________
```

---

## Test Execution Summary

| Test Case ID | Test Name | Requirement | Priority | Status | Pass/Fail | Notes |
|--------------|-----------|-------------|----------|--------|-----------|-------|
| TC_UI_001 | Search/Filter Cryptocurrencies | FR1.3 | High | ⬜ | ⬜ | |
| TC_UI_002 | Sort Cryptocurrency Data | FR1.4 | High | ⬜ | ⬜ | |
| TC_UI_003 | Toggle Debug Mode | FR4.4 | Medium | ⬜ | ⬜ | |
| TC_UI_004 | View Application Logs | FR4.5 | Medium | ⬜ | ⬜ | |
| TC_UI_005 | Reset Settings to Default | FR4.6 | Medium | ⬜ | ⬜ | |

### Overall Test Statistics
- **Total Manual Test Cases:** 5
- **Tests Executed:** 0 / 5
- **Tests Passed:** 0 / 5
- **Tests Failed:** 0 / 5
- **Pass Rate:** 0%

---

## Defect Reporting Template

If any test fails, use this template to report defects:

```
Defect ID: DEF-XXX
Test Case ID: TC_UI_XXX
Severity: [Critical/High/Medium/Low]
Priority: [High/Medium/Low]

Summary:
[Brief description of the defect]

Steps to Reproduce:
1. [Step 1]
2. [Step 2]
3. [Step 3]

Expected Result:
[What should happen]

Actual Result:
[What actually happened]

Environment:
- OS: [Windows/Mac/Linux]
- Java Version: [e.g., 1.8.0_471]
- Application Version: [e.g., 1.1]

Screenshots/Logs:
[Attach screenshots or relevant log entries]

Additional Notes:
[Any other relevant information]
```

---

## Test Recommendations

### Automation Opportunities
While these tests are currently manual, they could be automated using:

1. **TestFX** - JavaFX UI testing framework
2. **AssertJ Swing** - Swing GUI testing framework  
3. **Java Robot Class** - Lower-level UI automation
4. **Selenium** (if the app is web-based)

### Test Environment Notes
- Ensure stable internet connection for tests involving cryptocurrency data fetching
- Test on multiple operating systems if possible (Windows, Mac, Linux)
- Test with different screen resolutions
- Test with different system themes (light/dark mode)

### Additional Manual Tests to Consider
- **Accessibility Testing:** Keyboard navigation, screen reader compatibility
- **Usability Testing:** User experience, intuitive navigation
- **Visual Testing:** UI consistency, alignment, responsive design
- **Internationalization:** Test with different currencies and locales

---

## Document Information

**Document Version:** 1.0  
**Last Updated:** November 21, 2025  
**Created By:** Testing Team  
**Document Status:** ✅ Ready for Test Execution  

**Related Documents:**
- TESTING_DOCUMENTATION.md - Complete test documentation
- Requirements Traceability Matrix - Section 4.2
- Automated Test Results - Section 6 (Evidence)

---

## Approval Signatures

| Role | Name | Signature | Date |
|------|------|-----------|------|
| Test Lead | | | |
| QA Manager | | | |
| Project Manager | | | |

---

**End of Manual Black Box Test Cases Document**

