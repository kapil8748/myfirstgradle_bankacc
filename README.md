# 🏦 BankAccount — JUnit 5 Test Suite with Gradle & Extent Reports

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=flat-square&logo=java)
![JUnit 5](https://img.shields.io/badge/JUnit-5.10.2-green?style=flat-square&logo=junit5)
![Gradle](https://img.shields.io/badge/Gradle-8.x-02303A?style=flat-square&logo=gradle)
![ExtentReports](https://img.shields.io/badge/ExtentReports-5.1.1-blueviolet?style=flat-square)
![Tests](https://img.shields.io/badge/Tests-22%20passing-brightgreen?style=flat-square)

A hands-on Java testing project with a **BankAccount** domain class tested across **22 test cases** — covering happy paths, exceptions, edge cases, and parameterized tests. Built with **Gradle**, **JUnit 5**, and **Extent Reports**.

---

## 📋 Table of Contents
- [About](#about)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Running Tests](#running-tests)
- [Test Report](#test-report)
- [Test Coverage](#test-coverage)
- [What I Learned](#what-i-learned)
- [Author](#author)

---

## 📌 About

This project tests a realistic `BankAccount` class (deposit, withdraw, transfer, interest, freeze/unfreeze) using full JUnit 5 features and generates a polished HTML report via Extent Reports.

**Highlights:**
- 22 test cases including 10+ exception scenarios
- `assertThrows`, `assertAll`, `assertDoesNotThrow`, `assertEquals`, `assertTrue`, `assertFalse`
- Parameterized tests with `@ValueSource`
- Custom `ExtentReportListener` using JUnit 5 Extension API
- Dark-themed HTML report with system info panel

---

## 🛠️ Tech Stack

| Tool | Version | Purpose |
|---|---|---|
| Java | 17+ | Core language |
| JUnit Jupiter | 5.10.2 | Unit testing framework |
| Gradle | 8.x | Build tool & dependency management |
| ExtentReports | 5.1.1 | HTML test reporting (Dark theme) |
| IntelliJ IDEA | Latest | IDE |

---

## 📁 Project Structure

```
bankaccount-gradle/
├── src/
│   ├── main/java/com/kapil/
│   │   └── BankAccount.java              # Class under test
│   └── test/java/com/kapil/
│       ├── BankAccountTest.java          # 22 JUnit 5 test cases
│       ├── ExtentReportListener.java     # JUnit Extension → Extent
│       └── ExtentReportManager.java      # Singleton report setup
├── reports/
│   └── ExtentReport.html                # Generated after test run
├── build.gradle
├── settings.gradle
├── .gitignore
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17+ (`java -version`)
- Gradle 8+ (`gradle -version`) — or use `./gradlew` wrapper

### Clone
```bash
git clone https://github.com/kapil8748/bankaccount-gradle.git
cd bankaccount-gradle
```

### Build
```bash
./gradlew build
```

---

## ▶️ Running Tests

```bash
./gradlew test
```

Expected output:
```
> Task :test
TC01 — Constructor: valid account   PASSED
TC02 — Negative initial balance     PASSED
...
22 tests completed, 0 failed
BUILD SUCCESSFUL
```

---

## 📊 Test Report

After running, open the HTML report in any browser:

```
reports/ExtentReport.html
```

Features:
- Dark theme dashboard
- Pass/fail per test with timestamps
- System info panel (Java version, framework, tester)
- Full exception stack traces on failures

---

## ✅ Test Coverage

| # | Test Case | Method | Assertion Type | Expected |
|---|---|---|---|---|
| TC01 | Valid constructor sets balance | Constructor | `assertEquals` | ✅ |
| TC02 | Negative initial balance | Constructor | `assertThrows` | ✅ Exception |
| TC03 | Blank holder name | Constructor | `assertThrows` | ✅ Exception |
| TC04 | Null account number | Constructor | `assertThrows` | ✅ Exception |
| TC05 | Valid deposit increases balance | `deposit()` | `assertEquals` | ✅ |
| TC06 | Zero deposit | `deposit()` | `assertThrows` | ✅ Exception |
| TC07 | Negative deposit | `deposit()` | `assertThrows` | ✅ Exception |
| TC08 | Deposit on frozen account | `deposit()` | `assertThrows` | ✅ Exception |
| TC09 | Valid withdraw decreases balance | `withdraw()` | `assertEquals` | ✅ |
| TC10 | Withdraw exact balance → zero | `withdraw()` | `assertEquals` | ✅ |
| TC11 | Withdraw more than balance | `withdraw()` | `assertThrows` | ✅ Exception |
| TC12 | Negative withdrawal | `withdraw()` | `assertThrows` | ✅ Exception |
| TC13 | Withdraw on frozen account | `withdraw()` | `assertThrows` | ✅ Exception |
| TC14 | Valid transfer updates both accounts | `transfer()` | `assertAll` | ✅ |
| TC15 | Transfer to null target | `transfer()` | `assertThrows` | ✅ Exception |
| TC16 | Transfer to same account | `transfer()` | `assertThrows` | ✅ Exception |
| TC17 | Transfer more than balance | `transfer()` | `assertThrows` | ✅ Exception |
| TC18 | 10% interest on ₹5000 = ₹5500 | `applyInterest()` | `assertEquals` | ✅ |
| TC19 | Negative interest rate | `applyInterest()` | `assertThrows` | ✅ Exception |
| TC20 | Interest on frozen account | `applyInterest()` | `assertThrows` | ✅ Exception |
| TC21 | Unfreeze re-enables operations | `freeze/unfreeze` | `assertDoesNotThrow` | ✅ |
| TC22 | Parameterized valid deposits | `deposit()` | `assertEquals` | ✅ × 4 |

---

## 🧠 What I Learned

- Switching from Maven to **Gradle** for dependency management
- Writing **exception-focused tests** with `assertThrows` and verifying message content
- Using `assertAll` to group multiple assertions in one test
- `assertDoesNotThrow` for verifying no exception is thrown
- **Parameterized tests** with `@ValueSource` to test multiple inputs cleanly
- Building a reusable **JUnit 5 Extension** for Extent Reports integration
- Using `ThreadLocal<ExtentTest>` for thread-safe test logging

---

## 👤 Author

**Kapil**
- GitHub: [@kapil8748](https://github.com/kapil8748)
- LinkedIn: [linkedin.com/in/kapil-y-b991742bb](https://linkedin.com/in/kapil-y-b991742bb)
- Email: ykapilnfc@gmail.com

---

> ⭐ Star this repo if it helped you learn Java testing!
