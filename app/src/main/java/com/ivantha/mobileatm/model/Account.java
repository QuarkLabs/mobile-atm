package com.ivantha.mobileatm.model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Account {
    BigDecimal balance = new BigDecimal(0.0);
    List<Transaction> transactions = new LinkedList<>();

    boolean spendingLimitApplied = true;
    BigDecimal spendingLimit = new BigDecimal(100.0);

    public Account() {

    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public boolean isSpendingLimitApplied() {
        return spendingLimitApplied;
    }

    public void setSpendingLimitApplied(boolean spendingLimitApplied) {
        this.spendingLimitApplied = spendingLimitApplied;
    }

    public BigDecimal getSpendingLimit() {
        return spendingLimit;
    }

    public void setSpendingLimit(BigDecimal spendingLimit) {
        this.spendingLimit = spendingLimit;
    }

    public void setSpendingLimit(double spendingLimit) {
        this.spendingLimit = BigDecimal.valueOf(spendingLimit);
    }
}
