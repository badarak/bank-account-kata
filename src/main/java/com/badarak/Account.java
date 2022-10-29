package com.badarak;

public class Account {

    private double balance;

    public Account() {
    }

    public Account(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        this.balance = this.balance + amount;

    }

    public double getBalance() {
        return balance;
    }
}
