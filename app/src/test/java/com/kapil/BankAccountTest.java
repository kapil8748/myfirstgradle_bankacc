package com.kapil;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BankAccountTest — 20 test cases covering:
 *  - Happy-path operations (deposit, withdraw, transfer, interest)
 *  - Exception / negative scenarios
 *  - Edge cases (zero, boundary values)
 *  - Parameterized tests
 *  - Object-state assertions
 *
 *  Reports are generated to reports/ExtentReport.html via ExtentReportListener.
 */
@ExtendWith(ExtentReportListener.class)
@DisplayName("🏦 BankAccount — Full Test Suite")
class BankAccountTest {

    private BankAccount account;
    private BankAccount targetAccount;

    @BeforeEach
    void setUp() {
        account       = new BankAccount("Kapil", "ACC-001", 5000.0);
        targetAccount = new BankAccount("Rahul", "ACC-002", 1000.0);
    }

    // ════════════════════════════════════════════════
    // 1. CONSTRUCTOR TESTS
    // ════════════════════════════════════════════════

    @Test
    @DisplayName("TC01 — Constructor: valid account should set balance correctly")
    void tc01_validConstructor() {
        assertEquals(5000.0, account.getBalance(), 0.001);
        assertEquals("Kapil",   account.getAccountHolder());
        assertEquals("ACC-001", account.getAccountNumber());
        assertFalse(account.isFrozen());
    }

    @Test
    @DisplayName("TC02 — Constructor: negative initial balance should throw IllegalArgumentException")
    void tc02_negativeInitialBalance_throwsException() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new BankAccount("Test", "ACC-999", -100.0)
        );
        assertTrue(ex.getMessage().contains("negative"),
            "Exception message should mention 'negative'");
    }

    @Test
    @DisplayName("TC03 — Constructor: blank account holder name should throw IllegalArgumentException")
    void tc03_blankHolderName_throwsException() {
        assertThrows(IllegalArgumentException.class,
            () -> new BankAccount("  ", "ACC-999", 0.0));
    }

    @Test
    @DisplayName("TC04 — Constructor: null account number should throw IllegalArgumentException")
    void tc04_nullAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class,
            () -> new BankAccount("Kapil", null, 100.0));
    }

    // ════════════════════════════════════════════════
    // 2. DEPOSIT TESTS
    // ════════════════════════════════════════════════

    @Test
    @DisplayName("TC05 — Deposit: valid amount should increase balance")
    void tc05_validDeposit_balanceIncreases() {
        account.deposit(2000.0);
        assertEquals(7000.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC06 — Deposit: zero amount should throw IllegalArgumentException")
    void tc06_zeroDeposit_throwsException() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> account.deposit(0)
        );
        assertTrue(ex.getMessage().contains("positive"));
    }

    @Test
    @DisplayName("TC07 — Deposit: negative amount should throw IllegalArgumentException")
    void tc07_negativeDeposit_throwsException() {
        assertThrows(IllegalArgumentException.class,
            () -> account.deposit(-500.0));
    }

    @Test
    @DisplayName("TC08 — Deposit: on frozen account should throw IllegalStateException")
    void tc08_depositOnFrozenAccount_throwsException() {
        account.freeze();
        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            () -> account.deposit(100.0)
        );
        assertTrue(ex.getMessage().contains("frozen"));
    }

    // ════════════════════════════════════════════════
    // 3. WITHDRAW TESTS
    // ════════════════════════════════════════════════

    @Test
    @DisplayName("TC09 — Withdraw: valid amount should decrease balance")
    void tc09_validWithdraw_balanceDecreases() {
        account.withdraw(1500.0);
        assertEquals(3500.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC10 — Withdraw: exact balance withdrawal should leave zero balance")
    void tc10_withdrawExactBalance_balanceBecomesZero() {
        account.withdraw(5000.0);
        assertEquals(0.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC11 — Withdraw: more than balance should throw IllegalStateException")
    void tc11_withdrawMoreThanBalance_throwsException() {
        IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            () -> account.withdraw(9999.0)
        );
        assertTrue(ex.getMessage().contains("Insufficient funds"));
    }

    @Test
    @DisplayName("TC12 — Withdraw: negative amount should throw IllegalArgumentException")
    void tc12_negativeWithdraw_throwsException() {
        assertThrows(IllegalArgumentException.class,
            () -> account.withdraw(-100.0));
    }

    @Test
    @DisplayName("TC13 — Withdraw: on frozen account should throw IllegalStateException")
    void tc13_withdrawOnFrozenAccount_throwsException() {
        account.freeze();
        assertThrows(IllegalStateException.class,
            () -> account.withdraw(100.0));
    }

    // ════════════════════════════════════════════════
    // 4. TRANSFER TESTS
    // ════════════════════════════════════════════════

    @Test
    @DisplayName("TC14 — Transfer: valid transfer should update both account balances")
    void tc14_validTransfer_balancesUpdated() {
        account.transfer(targetAccount, 2000.0);
        assertAll("Transfer balances",
            () -> assertEquals(3000.0, account.getBalance(),       0.001),
            () -> assertEquals(3000.0, targetAccount.getBalance(), 0.001)
        );
    }

    @Test
    @DisplayName("TC15 — Transfer: to null target should throw IllegalArgumentException")
    void tc15_transferToNull_throwsException() {
        assertThrows(IllegalArgumentException.class,
            () -> account.transfer(null, 500.0));
    }

    @Test
    @DisplayName("TC16 — Transfer: to same account should throw IllegalArgumentException")
    void tc16_transferToSelf_throwsException() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> account.transfer(account, 500.0)
        );
        assertTrue(ex.getMessage().contains("same account"));
    }

    @Test
    @DisplayName("TC17 — Transfer: more than balance should throw IllegalStateException")
    void tc17_transferMoreThanBalance_throwsException() {
        assertThrows(IllegalStateException.class,
            () -> account.transfer(targetAccount, 99999.0));
    }

    // ════════════════════════════════════════════════
    // 5. INTEREST TESTS
    // ════════════════════════════════════════════════

    @Test
    @DisplayName("TC18 — Interest: 10% on ₹5000 should give ₹5500")
    void tc18_applyInterest_balanceCorrect() {
        account.applyInterest(10.0);
        assertEquals(5500.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC19 — Interest: negative rate should throw IllegalArgumentException")
    void tc19_negativeInterestRate_throwsException() {
        assertThrows(IllegalArgumentException.class,
            () -> account.applyInterest(-5.0));
    }

    @Test
    @DisplayName("TC20 — Interest: on frozen account should throw IllegalStateException")
    void tc20_interestOnFrozenAccount_throwsException() {
        account.freeze();
        assertThrows(IllegalStateException.class,
            () -> account.applyInterest(10.0));
    }

    // ════════════════════════════════════════════════
    // 6. FREEZE / UNFREEZE TEST
    // ════════════════════════════════════════════════

    @Test
    @DisplayName("TC21 — Freeze/Unfreeze: unfreezing should allow operations again")
    void tc21_unfreezeAllowsOperations() {
        account.freeze();
        assertTrue(account.isFrozen());
        account.unfreeze();
        assertFalse(account.isFrozen());
        // Should NOT throw after unfreeze
        assertDoesNotThrow(() -> account.deposit(100.0));
        assertEquals(5100.0, account.getBalance(), 0.001);
    }

    // ════════════════════════════════════════════════
    // 7. PARAMETERIZED TEST
    // ════════════════════════════════════════════════

    @ParameterizedTest(name = "TC22 — Deposit valid amount: ₹{0}")
    @ValueSource(doubles = {100.0, 500.0, 1000.0, 9999.99})
    @DisplayName("TC22 — Parameterized: multiple valid deposit amounts")
    void tc22_parameterized_validDeposits(double amount) {
        double before = account.getBalance();
        account.deposit(amount);
        assertEquals(before + amount, account.getBalance(), 0.001);
    }
}