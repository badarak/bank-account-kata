package com.badarak.bankaccountkata.features;

import com.badarak.bankaccountkata.adapter.ConsolePrinterServiceAdapter;
import com.badarak.bankaccountkata.adapter.InMemoryAccountRepositoryAdapter;
import com.badarak.bankaccountkata.business.Account;
import com.badarak.bankaccountkata.business.AccountNumber;
import com.badarak.bankaccountkata.business.Amount;
import com.badarak.bankaccountkata.business.Statement;
import com.badarak.bankaccountkata.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.valueOf;

public class PrintStatementFeatureTest {
    public static final String IBAN = "FR1234567891011213";
    public static final int INITIAL_BALANCE_AMOUNT = 100;
    private PrintStatementFeature printStatementFeature;
    private Account account ;
    private AccountRepository accountRepository;


    @BeforeEach
    public void setUp(){
        accountRepository = new InMemoryAccountRepositoryAdapter();
        account = new Account(new AccountNumber(IBAN),
                Amount.amountOf(valueOf(INITIAL_BALANCE_AMOUNT)),
                Statement.emptyStatement());
        accountRepository.save(account);
        printStatementFeature = new PrintStatementFeature(accountRepository, new ConsolePrinterServiceAdapter());
    }

    @Test
    void printStatement() {
        Account accountToPrint = account.deposit(Amount.amountOf(valueOf(100)))
                .withdraw(Amount.amountOf(valueOf(200)))
                .deposit(Amount.amountOf(valueOf(300)))
                .withdraw(Amount.amountOf(valueOf(100)))
                .deposit(Amount.amountOf(valueOf(100)));

        accountRepository.save(accountToPrint);

        printStatementFeature.printStatement(IBAN);
    }
}
