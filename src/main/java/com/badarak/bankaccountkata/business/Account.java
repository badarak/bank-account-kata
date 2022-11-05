package com.badarak.bankaccountkata.business;

import com.badarak.bankaccountkata.exception.UnsufficientBalanceException;
import java.time.LocalDateTime;

public class Account {
    private final AccountNumber accountId;
    private final Amount balance;
    private final Statement statement;


    public Account(AccountNumber accountId, Amount balance, Statement statement) {
        this.accountId = accountId;
        this.balance = balance;
        this.statement = statement;
    }

    public Account deposit(Amount amount, LocalDateTime date) {
        Amount newBalance = balance.add(amount);
        return treatOperation(TransactionType.DEPOSIT, amount, newBalance, date);
    }

    private Account treatOperation(TransactionType type, Amount amount, Amount newBalance, LocalDateTime date) {
        Statement newStatement = this.statement.addNewTransaction(new Transaction(date, type, amount, newBalance));
        return new Account(accountId, newBalance, newStatement);
    }

    public Account withdraw(Amount amount, LocalDateTime date) {
        if (amount.isGreaterThan(balance)) {
           throw new UnsufficientBalanceException("Insufficient balance for this withdrawal.");
        }

        Amount newBalance = balance.minus(amount);
        return treatOperation(TransactionType.WITHDRAWAL, amount, newBalance, date);
    }

    public Amount getBalance() {
        return balance;
    }

    public AccountNumber getAccountId() {
        return accountId;
    }

    public Statement getStatement() {
        return statement;
    }
}
