package com.badarak.bankaccountkata.domain.usecase;


import com.badarak.bankaccountkata.domain.model.Account;
import com.badarak.bankaccountkata.domain.service.StatementPrinterService;
import com.badarak.bankaccountkata.domain.repository.AccountRepository;

public class PrintStatementUseCase {
    private final AccountRepository accountRepository;
    private final StatementPrinterService printerService;


    public PrintStatementUseCase(AccountRepository accountRepository, StatementPrinterService printerService) {
        this.accountRepository = accountRepository;
        this.printerService = printerService;
    }

    public void printStatement(String iban){
        Account account = accountRepository.findAccountByIban(iban);
        printerService.print(account.getStatement());
    }
}
