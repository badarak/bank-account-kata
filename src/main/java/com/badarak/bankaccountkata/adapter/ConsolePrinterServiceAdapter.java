package com.badarak.bankaccountkata.adapter;


import com.badarak.bankaccountkata.business.Statement;
import com.badarak.bankaccountkata.business.StatementPrinterService;

public class ConsolePrinterServiceAdapter implements StatementPrinterService {

    @Override
    public void print(Statement statement) {
        statement.getTransactions().forEach(System.out::println);
    }
}
