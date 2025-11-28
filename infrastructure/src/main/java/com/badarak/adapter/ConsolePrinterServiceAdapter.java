package com.badarak.adapter;


import com.badarak.bankaccountkata.domain.model.Statement;
import com.badarak.bankaccountkata.domain.service.StatementPrinterService;

public class ConsolePrinterServiceAdapter implements StatementPrinterService {

    @Override
    public void print(Statement statement) {
        statement.getTransactions().forEach(System.out::println);
    }
}
