package com.badarak;

public class Account {

    private double balance;
    private final double OVERDRAFT_LIMIT = 500;

    public Account() {
    }

    public Account(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        this.balance = this.balance + amount;

    }

    public void withdrawal(double amount) {
        if (balance - amount < 0) {
           throw new RuntimeException("Withdrawal will result in an overdraft");
        }

        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}
