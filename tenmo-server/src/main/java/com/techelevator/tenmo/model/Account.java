package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class Account {

    private int accountId;
    @Min(value = 0, message = "balance cannot go below zero")
    private BigDecimal accountBalance;

    public Account(int accountId, BigDecimal accountBalance) {
        this.accountId = accountId;
        this.accountBalance = accountBalance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }
}
