package com.badarak.bankaccountkata.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Statement {
    private final List<Transaction> transactions;

    public Statement(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public static Statement emptyStatement() {
        return new Statement(new ArrayList<>());
    }

    public Statement addNewTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        return this;
    }

    public Optional<Transaction> getLastTransaction() {
        return this.transactions.stream().reduce((first,second) -> second);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
