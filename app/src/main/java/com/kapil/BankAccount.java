package com.kapil;

/**
 * BankAccount — core domain class for unit testing practice.
 * Demonstrates: deposits, withdrawals, transfers, interest,
 * and various exception-throwing scenarios.
 */
public class BankAccount {

    private final String accountHolder;
    private final String accountNumber;
    private double balance;
    private boolean frozen;

    public BankAccount(String accountHolder, String accountNumber, double initialBalance) {
        if (accountHolder == null || accountHolder.isBlank())
            throw new IllegalArgumentException("Account holder name cannot be empty");
        if (accountNumber == null || accountNumber.isBlank())
            throw new IllegalArgumentException("Account number cannot be empty");
        if (initialBalance < 0)
            throw new IllegalArgumentException("Initial balance cannot be negative");

        this.accountHolder  = accountHolder;
        this.accountNumber  = accountNumber;
        this.balance        = initialBalance;
        this.frozen         = false;
    }

    // ── Deposit ────────────────────────────────────────────────────────────
    public void deposit(double amount) {
        checkFrozen();
        if (amount <= 0)
            throw new IllegalArgumentException("Deposit amount must be positive");
        balance += amount;
    }

    // ── Withdraw ───────────────────────────────────────────────────────────
    public void withdraw(double amount) {
        checkFrozen();
        if (amount <= 0)
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        if (amount > balance)
            throw new IllegalStateException("Insufficient funds. Balance: " + balance);
        balance -= amount;
    }

    // ── Transfer ───────────────────────────────────────────────────────────
    public void transfer(BankAccount target, double amount) {
        if (target == null)
            throw new IllegalArgumentException("Target account cannot be null");
        if (target == this)
            throw new IllegalArgumentException("Cannot transfer to the same account");
        this.withdraw(amount);      // reuses withdraw validation
        target.deposit(amount);     // reuses deposit validation
    }

    // ── Interest ───────────────────────────────────────────────────────────
    public void applyInterest(double ratePercent) {
        checkFrozen();
        if (ratePercent < 0)
            throw new IllegalArgumentException("Interest rate cannot be negative");
        balance += balance * (ratePercent / 100.0);
    }

    // ── Freeze / Unfreeze ──────────────────────────────────────────────────
    public void freeze()   { this.frozen = true;  }
    public void unfreeze() { this.frozen = false; }

    // ── Helpers ────────────────────────────────────────────────────────────
    private void checkFrozen() {
        if (frozen)
            throw new IllegalStateException("Account is frozen. Operation not allowed.");
    }

    // ── Getters ────────────────────────────────────────────────────────────
    public double  getBalance()       { return balance;        }
    public String  getAccountHolder() { return accountHolder;  }
    public String  getAccountNumber() { return accountNumber;  }
    public boolean isFrozen()         { return frozen;         }
}