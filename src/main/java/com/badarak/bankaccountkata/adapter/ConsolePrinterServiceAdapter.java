package com.badarak.bankaccountkata.adapter;


import com.badarak.bankaccountkata.domain.Statement;
import com.badarak.bankaccountkata.domain.service.StatementPrinterService;

public class ConsolePrinterServiceAdapter implements StatementPrinterService {

    @Override
    public void print(Statement statement) {
        statement.getTransactions().forEach(System.out::println);
    }
}
