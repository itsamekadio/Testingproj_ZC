# Quick Start Guide - Manual Black Box Testing

## 🚀 How to Start Testing (3 Simple Steps)

### Step 1: Build the Application

Open PowerShell in the project directory and run:

```powershell
.\build_and_run.ps1 build
```

**OR** if you prefer the manual way:

```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-1.8"
$env:PATH="C:\Program Files\Java\jdk-1.8\bin;$env:PATH"
mvn clean package -DskipTests
```

✅ You should see: **BUILD SUCCESS**

---

### Step 2: Run the Application

```powershell
java -jar target\crypto-checker-1.1-no-dependencies.jar
```

**OR** use the helper script:

```powershell
.\build_and_run.ps1 run
```

✅ The Crypto Checker application window should open

---

### Step 3: Open the Test Cases Document

Open the file: **`MANUAL_BLACK_BOX_TEST_CASES.md`**

This document contains all 5 manual test cases with step-by-step instructions.

---

## 📋 The 5 Manual Test Cases

### **Test 1: Search/Filter Cryptocurrencies** (HIGH Priority)
- **Location in app:** Coin panel → Search field
- **What to test:** Type "Bitcoin", "eth", etc. and verify filtering works
- **Time:** ~5 minutes

### **Test 2: Sort Cryptocurrency Data** (HIGH Priority)
- **Location in app:** Coin panel → Click column headers
- **What to test:** Click Rank, Name, Price columns to sort
- **Time:** ~5 minutes

### **Test 3: Toggle Debug Mode** (MEDIUM Priority)
- **Location in app:** Settings panel → Debug Mode button
- **What to test:** Turn ON/OFF and verify debug window appears/disappears
- **Time:** ~3 minutes

### **Test 4: View Application Logs** (MEDIUM Priority)
- **Location in app:** Settings panel → View Logs button
- **What to test:** Click button and verify log file opens
- **Time:** ~2 minutes

### **Test 5: Reset Settings to Default** (MEDIUM Priority)
- **Location in app:** Settings panel → Reset Settings button
- **What to test:** Change settings, then reset and verify defaults restored
- **Time:** ~5 minutes

**Total Testing Time:** ~20-25 minutes

---

## 📝 How to Execute Each Test

For each test case in `MANUAL_BLACK_BOX_TEST_CASES.md`:

1. **Read the Test Steps** - Follow them sequentially
2. **Perform Each Action** - Do exactly what the step says
3. **Record Actual Results** - Write what actually happens
4. **Compare Results** - Does actual match expected?
5. **Mark Pass/Fail** - Check the appropriate box: ✅ Pass or ❌ Fail
6. **Document Issues** - If fail, use the defect reporting template

---

## ✅ Quick Test Execution Checklist

```
☐ Build application successfully
☐ Run application successfully
☐ Open MANUAL_BLACK_BOX_TEST_CASES.md
☐ Execute TC_UI_001 (Search/Filter)
☐ Execute TC_UI_002 (Sort Data)
☐ Execute TC_UI_003 (Toggle Debug)
☐ Execute TC_UI_004 (View Logs)
☐ Execute TC_UI_005 (Reset Settings)
☐ Complete Test Execution Summary table
☐ Report any defects found
```

---

## 🎯 Application Navigation Tips

**Main Panels:**
- **Coin** - Cryptocurrency list with search and sorting
- **Portfolio** - Manage crypto portfolios
- **Converter** - Convert between cryptocurrencies and fiat
- **Settings** - Debug mode, logs, reset settings, themes

**Where to Find Test Features:**

| Test Case | Panel | Look For |
|-----------|-------|----------|
| TC_UI_001 (Search) | Coin | Search text field at top |
| TC_UI_002 (Sort) | Coin | Click column headers (Rank, Name, Price, etc.) |
| TC_UI_003 (Debug) | Settings | "Debug Mode" button |
| TC_UI_004 (Logs) | Settings | "View Logs" button |
| TC_UI_005 (Reset) | Settings | "Reset Settings" button |

---

## 🐛 What to Do If You Find a Bug

1. **Mark the test as FAIL** ❌
2. **Note what went wrong** in the Notes/Defects section
3. **Take a screenshot** if possible
4. **Use the Defect Reporting Template** at the end of `MANUAL_BLACK_BOX_TEST_CASES.md`

---

## 💡 Common Issues & Solutions

### Issue: Application won't start
**Solution:** Make sure you built with the correct JDK:
```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-1.8"
$env:PATH="C:\Program Files\Java\jdk-1.8\bin;$env:PATH"
java -jar target\crypto-checker-1.1-no-dependencies.jar
```

### Issue: "No such file" error
**Solution:** Build the application first:
```powershell
.\build_and_run.ps1 build
```

### Issue: Search field not visible
**Solution:** Make sure you're on the **Coin** panel (first tab/button)

### Issue: Can't find Settings panel
**Solution:** Look for a Settings tab/button in the main window navigation

---

## 📊 After Testing

Once all 5 tests are complete:

1. **Update the Test Execution Summary** table in `MANUAL_BLACK_BOX_TEST_CASES.md`
2. **Calculate pass rate:** (Passed Tests / Total Tests) × 100%
3. **Document all defects** using the template provided
4. **Save your completed test document**

---

## 🎓 Example: How to Test Search (TC_UI_001)

Here's exactly what to do:

1. **Start the app** → `java -jar target\crypto-checker-1.1-no-dependencies.jar`
2. **Click "Coin" tab** → You should see a list of cryptocurrencies
3. **Find the search box** → Usually at the top of the coin list
4. **Type "Bitcoin"** → Start typing in the search field
5. **Observe** → Does the list filter to show only Bitcoin?
   - ✅ **YES** = Write "List filtered correctly to show Bitcoin only" in Actual Result
   - ❌ **NO** = Write what actually happened and mark as FAIL
6. **Clear search** → Delete the text
7. **Type "eth"** → The list should show Ethereum and related coins
8. **Continue** with remaining steps in TC_UI_001
9. **Mark Pass or Fail** → Check the box at the bottom of TC_UI_001
10. **Move to next test** → TC_UI_002

---

## 📞 Need Help?

- **Full Test Details:** See `MANUAL_BLACK_BOX_TEST_CASES.md`
- **Test Documentation:** See `TESTING_DOCUMENTATION.md` Section 4.8
- **Automated Tests:** Run `.\build_and_run.ps1 test` (13 automated tests)

---

## ✨ Ready to Start?

```powershell
# 1. Build
.\build_and_run.ps1 build

# 2. Run (when build completes)
java -jar target\crypto-checker-1.1-no-dependencies.jar

# 3. Open MANUAL_BLACK_BOX_TEST_CASES.md and start testing!
```

**Good luck with your testing! 🎉**

---

**Note:** These manual tests cover the 13% of requirements that can't be automated with JUnit. Combined with the 13 automated tests that already passed (87%), you'll have 100% test coverage!

