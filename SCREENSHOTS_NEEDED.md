# Screenshots Needed for Manual Test Evidence

## 📸 Screenshot Checklist - 5 Manual Tests

---

### ✅ Test 1: Search/Filter Cryptocurrencies (TC_UI_001)

**Screenshots needed: 4**

1. **Screenshot 1.1 - Initial Coin List**
   - Navigate to: **Coin panel**
   - Capture: Full cryptocurrency list before search
   - Filename: `TC_UI_001_01_initial_coin_list.png`

2. **Screenshot 1.2 - Search "Bitcoin"**
   - Type: "Bitcoin" in search field
   - Capture: Filtered list showing only Bitcoin
   - Filename: `TC_UI_001_02_search_bitcoin.png`

3. **Screenshot 1.3 - Search "eth"**
   - Type: "eth" in search field
   - Capture: Filtered list showing Ethereum and related coins
   - Filename: `TC_UI_001_03_search_eth.png`

4. **Screenshot 1.4 - Cleared Search**
   - Clear the search field
   - Capture: Full list restored
   - Filename: `TC_UI_001_04_search_cleared.png`

---

### ✅ Test 2: Sort Cryptocurrency Data (TC_UI_002)

**Screenshots needed: 6**

1. **Screenshot 2.1 - Default Sort**
   - Show: Default cryptocurrency list order
   - Filename: `TC_UI_002_01_default_sort.png`

2. **Screenshot 2.2 - Sort by Rank (Ascending)**
   - Click: "Rank" column header once
   - Capture: List sorted by rank (1, 2, 3...)
   - Filename: `TC_UI_002_02_sort_rank_asc.png`

3. **Screenshot 2.3 - Sort by Name (Alphabetical)**
   - Click: "Name" column header
   - Capture: List sorted alphabetically (A-Z)
   - Filename: `TC_UI_002_03_sort_name_alpha.png`

4. **Screenshot 2.4 - Sort by Price (Descending)**
   - Click: "Price" column header twice
   - Capture: List sorted by price (highest to lowest)
   - Filename: `TC_UI_002_04_sort_price_desc.png`

5. **Screenshot 2.5 - Sort by 24h %**
   - Click: "24h %" column header
   - Capture: List sorted by 24-hour percentage change
   - Filename: `TC_UI_002_05_sort_24h_percent.png`

6. **Screenshot 2.6 - Sort by Market Cap**
   - Click: "Market Cap" column header
   - Capture: List sorted by market capitalization
   - Filename: `TC_UI_002_06_sort_market_cap.png`

---

### ✅ Test 3: Toggle Debug Mode (TC_UI_003)

**Screenshots needed: 3**

1. **Screenshot 3.1 - Settings Panel (Debug OFF)**
   - Navigate to: **Settings panel**
   - Capture: Debug Mode button showing "Off"
   - Filename: `TC_UI_003_01_debug_mode_off.png`

2. **Screenshot 3.2 - Debug Mode ON with Window**
   - Click: Debug Mode button to turn ON
   - Capture: Debug window visible + Settings panel showing "On"
   - Filename: `TC_UI_003_02_debug_mode_on.png`

3. **Screenshot 3.3 - Debug Mode OFF (Window Closed)**
   - Click: Debug Mode button to turn OFF
   - Capture: Debug window closed + Settings showing "Off"
   - Filename: `TC_UI_003_03_debug_mode_off_again.png`

---

### ✅ Test 4: View Application Logs (TC_UI_004)

**Screenshots needed: 2**

1. **Screenshot 4.1 - View Logs Button**
   - Location: **Settings panel**
   - Capture: "View Logs" button highlighted/visible
   - Filename: `TC_UI_004_01_view_logs_button.png`

2. **Screenshot 4.2 - Log File Opened**
   - Click: "View Logs" button
   - Capture: Text editor showing log file content
   - Make sure: Timestamps and log entries are visible
   - Filename: `TC_UI_004_02_log_file_content.png`

---

### ✅ Test 5: Reset Settings to Default (TC_UI_005)

**Screenshots needed: 4**

1. **Screenshot 5.1 - Modified Settings**
   - Navigate to: **Settings panel**
   - Change: Theme to "Dark", Currency to "EUR"
   - Capture: Settings showing modified values
   - Filename: `TC_UI_005_01_modified_settings.png`

2. **Screenshot 5.2 - Dark Theme Applied**
   - Navigate to: **Coin panel** (or any panel)
   - Capture: Application showing dark theme
   - Filename: `TC_UI_005_02_dark_theme_applied.png`

3. **Screenshot 5.3 - Reset Settings Button**
   - Navigate back to: **Settings panel**
   - Capture: "Reset Settings" button before clicking
   - Filename: `TC_UI_005_03_reset_button.png`

4. **Screenshot 5.4 - Settings Reset to Defaults**
   - Click: "Reset Settings" button
   - Capture: Settings showing defaults (Light theme, USD currency)
   - Filename: `TC_UI_005_04_settings_reset.png`

---

## 📁 Screenshot Organization

Save all screenshots in:
```
evidence/
  manual_tests/
    TC_UI_001(Search)/
      TC_UI_001_01_initial_coin_list.png
      TC_UI_001_02_search_bitcoin.png
      TC_UI_001_03_search_eth.png
      TC_UI_001_04_search_cleared.png
    TC_UI_002(Sort Cryptocurrency Data)/
      TC_UI_002_01_default_sort(by rank).png
      TC_UI_002_03_sort_name_alpha.png
      TC_UI_002_04_sort_price_desc.png
      TC_UI_002_05_sort_24h_percent.png
      TC_UI_002_06_sort_market_cap.png
    TC_UI_003(Toggle Debug Mode)/
      TC_UI_003_01_debug_mode_off.png
      TC_UI_003_02_debug_mode_on.png
      TC_UI_003_03_debug_mode_off_again.png
    TC_UI_004(View Application Logs)/
      TC_UI_004_01_view_logs_button.png
      TC_UI_004_02_log_file_content.png
    TC_UI_005(Reset Settings to Default)/
      TC_UI_005_01_modified_settings.png
      TC_UI_005_02_dark_theme_applied.png
      TC_UI_005_03_reset_button.png
      TC_UI_005_04_settings_reset.png
```

---

## 🚀 Quick Workflow

1. **Open application** (already running)
2. **Open this file** (SCREENSHOTS_NEEDED.md)
3. **Open Snipping Tool** (Windows + Shift + S)
4. **Go through each test** sequentially:
   - Navigate to the panel
   - Perform the action
   - Take screenshot
   - Save with correct filename
5. **Organize screenshots** in evidence/manual_tests/ folder
6. **Update test document** with screenshot references

---

## 📝 After Taking Screenshots

Update the `TESTING_DOCUMENTATION.md` to reference the screenshots:

Example:
```markdown
### Test Case TC_UI_001: Search/Filter Cryptocurrencies

**Evidence:**
- Initial state: See `evidence/manual_tests/TC_UI_001(Search)/TC_UI_001_01_initial_coin_list.png`
- Search Bitcoin: See `evidence/manual_tests/TC_UI_001(Search)/TC_UI_001_02_search_bitcoin.png`
- Search eth: See `evidence/manual_tests/TC_UI_001(Search)/TC_UI_001_03_search_eth.png`
- Cleared search: See `evidence/manual_tests/TC_UI_001(Search)/TC_UI_001_04_search_cleared.png`

**Result:** ✅ PASS - All search filtering operations work correctly
```

**Good luck! We're almost done! 🎉**

