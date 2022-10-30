package com.badarak;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private double balance;
    private List<StatementLine> statementLines;

    public Account() {
        this.statementLines = new ArrayList<>();
    }

    public void deposit(double amount, Operation operation) {
        this.balance = this.balance + amount;
        addStatementLine(LocalDateTime.now(), operation, amount);
    }

    public void withdrawal(double amount, Operation operation) {
        if (balance - amount < 0) {
           throw new RuntimeException("Withdrawal will result in an overdraft");
        }

        balance -= amount;
        addStatementLine(LocalDateTime.now(), operation, amount);
    }

    public double getBalance() {
        return balance;
    }

    private void addStatementLine(LocalDateTime date, Operation operation, double amount) {
        statementLines.add(new StatementLine(date, operation, amount, this.balance));
    }

    public List<StatementLine> getStatementLines() {
        return statementLines;
    }
}
