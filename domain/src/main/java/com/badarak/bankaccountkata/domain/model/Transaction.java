package com.badarak.bankaccountkata.domain.model;

import java.time.LocalDateTime;

public class Transaction {
    private final LocalDateTime date;
    private final Amount amount;
    private final Amount newBalance;
    private final TransactionType type;

    public Transaction(LocalDateTime date, TransactionType type, Amount amount, Amount newBalance) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.newBalance = newBalance;
    }

    public Amount getNewBalance() {
        return newBalance;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Amount getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "{" +
                "date=" + date.toLocalDate() +
                ", type=" + type +
                ", amount=" + amount +
                ", balance=" + newBalance +
                '}';
    }
}
