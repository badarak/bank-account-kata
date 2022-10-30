package com.badarak;

import java.time.LocalDateTime;

public class StatementLine {
    private LocalDateTime date;
    private Operation operation;
    private double amount;
    private double balance;

    public StatementLine(LocalDateTime date, Operation operation, double amount, double balance) {
        this.date = date;
        this.operation = operation;
        this.amount = amount;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "{" +
                "date=" + date.toLocalDate() +
                ", operation=" + operation +
                ", amount=" + amount +
                ", balance=" + balance +
                '}';
    }
}
