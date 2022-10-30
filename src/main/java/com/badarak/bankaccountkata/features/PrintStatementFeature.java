package com.badarak.bankaccountkata.features;

import com.badarak.bankaccountkata.domain.Account;
import com.badarak.bankaccountkata.domain.repository.AccountRepository;
import com.badarak.bankaccountkata.domain.service.StatementPrinterService;

public class PrintStatementFeature {
    private final AccountRepository accountRepository;
    private final StatementPrinterService printerService;


    public PrintStatementFeature(AccountRepository accountRepository, StatementPrinterService printerService) {
        this.accountRepository = accountRepository;
        this.printerService = printerService;
    }

    public void printStatement(String iban){
        Account account = accountRepository.findAccountByIban(iban);
        printerService.print(account.getStatement());
    }
}
